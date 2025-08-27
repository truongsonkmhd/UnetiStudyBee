package com.truongsonkmhd.unetistudy.repository;

import com.truongsonkmhd.unetistudy.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {
    @Query("select p from Permission p where p.name in :names")
    List<Permission> findByNames(@Param("names") Collection<String> names);
}
