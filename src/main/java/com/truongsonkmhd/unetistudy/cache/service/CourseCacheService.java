package com.truongsonkmhd.unetistudy.cache.service;

import com.truongsonkmhd.unetistudy.cache.CacheConstants;
import com.truongsonkmhd.unetistudy.cache.AppCacheService;
import com.truongsonkmhd.unetistudy.cache.strategy.CacheStrategy;
import com.truongsonkmhd.unetistudy.dto.course_dto.CourseTreeResponse;
import com.truongsonkmhd.unetistudy.model.course.Course;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Service quản lý cache cho Course
 * Áp dụng Cache-Aside, Read-Through và Time-based Expiration
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CourseCacheService {

    private final AppCacheService appCacheService;

    /**
     * Cache-Aside: Lấy course by ID
     */
    public Course getCourseById(UUID courseId, Supplier<Course> loader) {
        CacheStrategy<UUID, Course> cache = appCacheService.getCache(CacheConstants.COURSE_BY_ID);
        return cache.getOrLoad(courseId, loader);
    }

    /**
     * Cache-Aside: Lấy course by slug
     */
    public Course getCourseBySlug(String slug, Supplier<Course> loader) {
        CacheStrategy<String, Course> cache = appCacheService.getCache(CacheConstants.COURSE_BY_SLUG);
        return cache.getOrLoad(slug, loader);
    }

    /**
     * Cache-Aside với custom TTL: Lấy course tree (published)
     * Cache lâu hơn vì dữ liệu published ít thay đổi
     */
    public CourseTreeResponse getCoursePublishedTree(String slug, Supplier<CourseTreeResponse> loader) {
        CacheStrategy<String, CourseTreeResponse> cache = appCacheService.getCache(CacheConstants.COURSE_PUBLISHED_TREE);
        return cache.getOrLoad(slug, loader, CacheConstants.TTL_LONG); // 1 hour
    }

    /**
     * Lấy course từ cache (không load từ DB)
     */
    public Optional<Course> getCachedCourseById(UUID courseId) {
        CacheStrategy<UUID, Course> cache = appCacheService.getCache(CacheConstants.COURSE_BY_ID);
        return cache.get(courseId);
    }

    /**
     * Write-Through: Cập nhật course và cache đồng thời
     */
    public Course updateCourseWithCache(UUID courseId, Course course,
            java.util.function.Function<Course, Course> dbWriter) {
        CacheStrategy<UUID, Course> cache = appCacheService.getCache(CacheConstants.COURSE_BY_ID);
        Course savedCourse = cache.writeThrough(courseId, course, dbWriter);

        // Cũng cần update cache by slug và invalidate published tree
        if (savedCourse != null) {
            if (savedCourse.getSlug() != null) {
                CacheStrategy<String, Course> slugCache = appCacheService.getCache(CacheConstants.COURSE_BY_SLUG);
                slugCache.put(savedCourse.getSlug(), savedCourse);
            }

            // Invalidate published tree vì có thể đã thay đổi
            invalidatePublishedTree(savedCourse.getSlug());
        }

        return savedCourse;
    }

    /**
     * Put course vào cache
     */
    public void cacheCourse(Course course) {
        if (course == null)
            return;

        CacheStrategy<UUID, Course> idCache = appCacheService.getCache(CacheConstants.COURSE_BY_ID);
        idCache.put(course.getCourseId(), course);

        if (course.getSlug() != null) {
            CacheStrategy<String, Course> slugCache = appCacheService.getCache(CacheConstants.COURSE_BY_SLUG);
            slugCache.put(course.getSlug(), course);
        }
    }

    /**
     * Put course tree vào cache
     */
    public void cacheCourseTree(String slug, CourseTreeResponse courseTree) {
        CacheStrategy<String, CourseTreeResponse> cache = appCacheService.getCache(CacheConstants.COURSE_PUBLISHED_TREE);
        cache.put(slug, courseTree);
    }

    /**
     * Evict course từ tất cả related caches
     */
    public void evictCourse(UUID courseId, String slug) {
        CacheStrategy<UUID, Course> idCache = appCacheService.getCache(CacheConstants.COURSE_BY_ID);
        idCache.evict(courseId);

        if (slug != null) {
            CacheStrategy<String, Course> slugCache = appCacheService.getCache(CacheConstants.COURSE_BY_SLUG);
            slugCache.evict(slug);

            invalidatePublishedTree(slug);
        }

        log.info("Evicted course from cache: {}", courseId);
    }

    /**
     * Invalidate published tree cache
     */
    public void invalidatePublishedTree(String slug) {
        if (slug != null) {
            CacheStrategy<String, CourseTreeResponse> treeCache = appCacheService
                    .getCache(CacheConstants.COURSE_PUBLISHED_TREE);
            treeCache.evict(slug);
            log.debug("Invalidated published tree for slug: {}", slug);
        }
    }

    /**
     * Invalidate ALL published tree caches
     */
    public void invalidateAllPublishedTrees() {
        CacheStrategy<?, ?> treeCache = appCacheService.getCache(CacheConstants.COURSE_PUBLISHED_TREE);
        treeCache.evictAll();
        log.info("Invalidated all published tree caches");
    }

    /**
     * Evict tất cả course caches
     */
    public void evictAllCourses() {
        CacheStrategy<?, ?> idCache = appCacheService.getCache(CacheConstants.COURSE_BY_ID);
        CacheStrategy<?, ?> slugCache = appCacheService.getCache(CacheConstants.COURSE_BY_SLUG);
        CacheStrategy<?, ?> treeCache = appCacheService.getCache(CacheConstants.COURSE_PUBLISHED_TREE);

        idCache.evictAll();
        slugCache.evictAll();
        treeCache.evictAll();

        log.info("Evicted all courses from cache");
    }

    /**
     * Refresh course trong cache
     */
    public void refreshCourse(UUID courseId, Supplier<Course> loader) {
        CacheStrategy<UUID, Course> cache = appCacheService.getCache(CacheConstants.COURSE_BY_ID);
        cache.refresh(courseId, loader);
    }

    /**
     * Warm up cache với danh sách courses
     */
    public void warmUpCache(Iterable<Course> courses) {
        CacheStrategy<UUID, Course> idCache = appCacheService.getCache(CacheConstants.COURSE_BY_ID);
        CacheStrategy<String, Course> slugCache = appCacheService.getCache(CacheConstants.COURSE_BY_SLUG);

        int count = 0;
        for (Course course : courses) {
            idCache.put(course.getCourseId(), course);
            if (course.getSlug() != null) {
                slugCache.put(course.getSlug(), course);
            }
            count++;
        }

        log.info("Warmed up course cache with {} courses", count);
    }
}
