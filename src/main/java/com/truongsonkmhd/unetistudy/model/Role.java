package com.truongsonkmhd.unetistudy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // phù hợp với PostgreSQL
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "description")
    private String description;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "is_activated")
    private Boolean isActivated;

    @ManyToMany
    @JoinTable(
            name = "tbl_role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    @JsonIgnoreProperties(
            value = {"roles"},
            allowSetters = true
    )
    private Set<User> users = new HashSet<>();

}
