package com.demo.Student.Sort.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StudentSortService {
    String sortStudentsToFile(MultipartFile file, String sortType) throws IOException;

    String sortStudentsToJson(MultipartFile file, String sortType) throws IOException;
}
