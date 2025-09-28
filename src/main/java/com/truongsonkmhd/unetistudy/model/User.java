package com.truongsonkmhd.unetistudy.model;

import com.truongsonkmhd.unetistudy.common.Gender;
import com.truongsonkmhd.unetistudy.common.UserStatus;
import com.truongsonkmhd.unetistudy.common.UserType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_user")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    UUID id;

    @Column(name = "full_name", length = 255)
    String fullName;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "gender", length = 255)
    Gender gender;

    @Column(name = "date_of_birth", length = 255)
    Date birthday;

    @Column(name = "email", length = 255)
    String email;

    @Column(name = "phone", length = 15)
    String phone;

    @Column(name = "username",unique = true,nullable = false, length = 255)
    String username;

    @Column(name = "password", length = 255)
    String password;

    @Column(name = "avatar")
    String avatar;

    @Column(name = "is_deleted")
    Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type", length = 255)
    UserType type;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", length = 255 ,insertable = false)
    //insertable = false → Hibernate sẽ không đưa cột status vào câu lệnh insert nếu bạn không set ⇒ DB dùng DEFAULT 'ACTIVE'.
    UserStatus status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true) //orphanRemoval: nếu xóa user => address cũng sẽ mất
    Set<Address> addresses = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tbl_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Token token;

}
