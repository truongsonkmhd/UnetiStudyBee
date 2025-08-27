package com.truongsonkmhd.unetistudy.repository;

import com.truongsonkmhd.unetistudy.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    @Query("select r from Role r where r.code in :codes")
    List<Role> findAllByNames(@Param("codes") Collection<String> codes);
}
