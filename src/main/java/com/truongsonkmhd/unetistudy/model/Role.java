package com.truongsonkmhd.unetistudy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_role")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // phù hợp với PostgreSQL
    @Column(name = "id")
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "code")
    String code;
    @Column(name = "description")
    String description;
    @Column(name = "is_deleted")
    Boolean isDeleted;
    @Column(name = "is_activated")
    Boolean isActivated;

    @ManyToMany
    @JoinTable(
            name = "tbl_role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    Set<Permission> permissions;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    Set<User> users = new HashSet<>();

}
