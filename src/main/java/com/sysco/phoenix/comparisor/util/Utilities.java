package com.sysco.phoenix.comparisor.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.sysco.phoenix.comparisor.model.CSVRecords;
import com.sysco.phoenix.comparisor.model.request.GraphRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class Utilities {


    public List<CSVRecords> MultipartToCsvConverter(MultipartFile file) {
        try {
            List<String[]> csvData = new ArrayList<>();
            List<CSVRecords> csvFileWithRecords = new ArrayList<>();

            CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
            reader.readNext();

            String[] line;
            while ((line = reader.readNext()) != null) {
                csvData.add(line);
            }
            for (String[] row : csvData) {
                csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]));
            }

            return csvFileWithRecords;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<GraphRequestDto> multipartToJSONConverter(MultipartFile file){
        try{

            List<GraphRequestDto> searchProductResultList = new ArrayList<>();

            for (JsonNode record : new ObjectMapper()
                    .readTree(file.getInputStream())
                    .path("data")
                    .path("searchProductsV2")
                    .path("results")) {
                Map<String, Object> nodeMap = new ObjectMapper().convertValue(record, Map.class);
                GraphRequestDto result = new ModelMapper().map(nodeMap, GraphRequestDto.class);
                searchProductResultList.add(result);
            }

            return searchProductResultList;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
