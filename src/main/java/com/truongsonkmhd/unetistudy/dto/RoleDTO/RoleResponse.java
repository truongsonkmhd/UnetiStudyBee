package com.truongsonkmhd.unetistudy.dto.RoleDTO;

import com.truongsonkmhd.unetistudy.dto.PermissionDTO.PermissionResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
    String name;
    String description;
    Set<PermissionResponse> permissions;
}
