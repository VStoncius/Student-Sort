package com.demo.Student.Sort.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StudentSortService {

    public static final String DEFAULT = "MERGE";

    public byte[] sortStudentsToFile(MultipartFile file, String sortType) throws IOException;

    public String sortStudentsToJson(MultipartFile file, String sortType) throws IOException;
}
