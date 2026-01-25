package com.truongsonkmhd.unetistudy.controller;

import com.truongsonkmhd.unetistudy.dto.a_common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.a_common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.dto.class_dto.CreateClazzRequest;
import com.truongsonkmhd.unetistudy.sevice.ClassService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/class")
@Slf4j(topic = "CLASS-CONTROLLER")
@Tag(name = "class controller")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    @PostMapping("/add")
    public ResponseEntity<IResponseMessage> createClass(@RequestBody CreateClazzRequest request) {
        return ResponseEntity.ok().body(SuccessResponseMessage.CreatedSuccess(classService.saveClass(request)));
    }

    @GetMapping("/getAll")
    public ResponseEntity<IResponseMessage> searchAdvanceContest(){

        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(
               SuccessResponseMessage.LoadedSuccess(classService.getALlClass())
        ));
    }

}
