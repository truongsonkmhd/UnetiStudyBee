package com.truongsonkmhd.unetistudy.controller;

import com.truongsonkmhd.unetistudy.dto.custom.request.permission.PermissionRequest;
import com.truongsonkmhd.unetistudy.dto.common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.sevice.PermissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permissions")
@Slf4j(topic = "PERMISSION-CONTROLLER")
@Tag(name = "permission Controller")
@RequiredArgsConstructor
public class PermissionController {

    PermissionService permissionService;

    @PostMapping("/create")
    ResponseEntity<IResponseMessage> create(@RequestBody PermissionRequest request){
        return  ResponseEntity.ok().body(SuccessResponseMessage.CreatedSuccess(permissionService.create(request)));
    }

    @PutMapping("/update/{permissionId}")
    ResponseEntity<IResponseMessage> update(@RequestBody PermissionRequest request ,@PathVariable long permissionId){
        return ResponseEntity.ok().body(SuccessResponseMessage.UpdatedSuccess(permissionService.update(permissionId,request)));
    }

    @GetMapping
    ResponseEntity<IResponseMessage> getAll(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(permissionService.getAll()));
    }

    @DeleteMapping("/{permissionId}")
    ResponseEntity<IResponseMessage> delete(@PathVariable long permissionId) {
        return ResponseEntity.ok().body(
                SuccessResponseMessage.DeletedSuccess(permissionService.delete(permissionId))
        );
    }


}
