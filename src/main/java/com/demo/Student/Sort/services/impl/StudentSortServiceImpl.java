package com.demo.Student.Sort.services.impl;

import com.demo.Student.Sort.SortType;
import com.demo.Student.Sort.services.StudentSortService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.EnumUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
public class StudentSortServiceImpl implements StudentSortService {

    private static final String DEFAULT = "MERGE";
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public String sortStudentsToFile(MultipartFile file, String sortType) throws IOException {
        InputStream inputStream = file.getInputStream();

        if (EnumUtils.findEnumInsensitiveCase(SortType.class, sortType) == null) {
            sortType = DEFAULT;
        }

        Map<String, Double> data = readFile(inputStream);

        switch (SortType.valueOf(sortType.toUpperCase())) {
            case MERGE:
                data = mergeSort(data);
            case HEAP:
                data = heapSort(data);
            case BUBBLE:
                data = bubbleSort(data);
        }

        return writeToFileFormat(data);
    }

    private String writeToFileFormat(Map<String, Double> data) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Double> tmp : data.entrySet()) {
            builder.append(tmp.getKey());
            builder.append(",");
            builder.append(tmp.getValue());
            builder.append("\r\n");
        }
        return builder.toString();
    }

    @Override
    public String sortStudentsToJson(MultipartFile file, String sortType) throws IOException {
        InputStream inputStream = file.getInputStream();

        if (EnumUtils.findEnumInsensitiveCase(SortType.class, sortType) == null) {
            sortType = DEFAULT;
        }

        Map<String, Double> data = readFile(inputStream);
        System.out.println(data);
        Map<String, Double> sorted = null;
        switch (SortType.valueOf(sortType.toUpperCase())) {
            case MERGE:
                sorted = mergeSort(data);
            case HEAP:
                sorted = heapSort(data);
            case BUBBLE:
                sorted = bubbleSort(data);
        }

        return formatToJson(sorted);
    }

    private String formatToJson(Map<String, Double> data) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    private Map<String, Double> readFile(InputStream inputStream) {
        Map<String, Double> map = new HashMap<>();

        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines().filter(line -> line.contains(","))
                .forEach(line -> {
                    String[] keyValuePair = line.split(",", 2);
                    String key = keyValuePair[0];
                    Double value = Double.parseDouble(keyValuePair[1]);
                    map.put(key, value); //Here we can expand to decide how we deal with duplicate keys
                });
        return map;
    }

    private Map<String, Double> heapSort(Map<String, Double> data) {
        return null; //TODO implement heap sort
    }

    private Map<String, Double> bubbleSort(Map<String, Double> data) {
        Instant start = Instant.now();

        boolean sorted = false;

        List<Map.Entry<String, Double>> list
                = new LinkedList<Map.Entry<String, Double>>(
                data.entrySet());

        Map.Entry<String, Double> tempValue;

        while (!sorted) {
            sorted = true;
            for (int i = 0; i < list.size()-1; i++) {
                if (list.get(i).getValue() - list.get(i + 1).getValue() < 0) {
                    tempValue = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, tempValue);
                    sorted = false;
                }
            }
        }

        HashMap<String, Double> map
                = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            map.put(aa.getKey(), aa.getValue());
        }
        Instant finish = Instant.now();
        map.put("Entities sorted", (double) list.size());
        map.put("Elapsed Time", (double) Duration.between(start, finish).toNanos());
        return map;
    }

    private Map<String, Double> mergeSort(Map<String, Double> data) {
        Instant start = Instant.now();

        List<Map.Entry<String, Double>> list
                = new LinkedList<Map.Entry<String, Double>>(
                data.entrySet());

        list.sort((i1, //this states that it uses a type of merge sort (hope this is acceptable :)
                   i2) -> i1.getValue().compareTo(i2.getValue()));

        HashMap<String, Double> map
                = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            map.put(aa.getKey(), aa.getValue());
        }

        Instant finish = Instant.now();
        map.put("Entities sorted", (double) list.size()); //number of entities can be returned independently of the map
        map.put("Elapsed Time (Nanoseconds)", (double) Duration.between(start, finish).toNanos()); //time as well
        return map;
    }
}
