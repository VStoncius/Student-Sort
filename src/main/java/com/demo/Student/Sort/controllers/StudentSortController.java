package com.demo.Student.Sort.controllers;

import com.demo.Student.Sort.services.StudentSortService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/students")
public class StudentSortController {

    @Autowired
    private StudentSortService studentSortService; //if the projects is large using constructor with private final StudentService instead of @Autowired would be adviced to save some time on start-up

    @PostMapping(value = "/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public @ResponseBody byte[] sortStudentsToFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam String sortType) throws IOException {
        return studentSortService.sortStudentsToFile(file, sortType).getBytes();
    }

    @PostMapping(value = "/json",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> sortStudentsToJson(
            @RequestParam("file") MultipartFile file,
            @RequestParam String sortType) throws IOException {
        return new ResponseEntity<String>(studentSortService.sortStudentsToJson(file, sortType), HttpStatus.OK);
    }
}
