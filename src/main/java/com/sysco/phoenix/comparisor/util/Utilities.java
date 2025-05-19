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
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class Utilities {


    public List<CSVRecords> MultipartToCsvConverter(MultipartFile file, String sortByFlag, String sortValue) {
        try {
            List<String[]> csvData = new ArrayList<>();
            List<CSVRecords> csvFileWithRecords = new ArrayList<>();
            Boolean isRankColumnExists = false;

            CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
            reader.readNext();

            String[] line;
            while ((line = reader.readNext()) != null) {
                csvData.add(line);
            }


            if (sortByFlag.toCharArray().length > 0) {
                switch (sortByFlag) {
                    case "AUD_ID":
                        HashMap<String, Object> audIdFunctionResponse = sortTheAudienceFileForGiveSortFlagAndSortValue(csvData, sortValue, 0);
                        isRankColumnExists = (Boolean) audIdFunctionResponse.get("BooleanVal");
                        csvFileWithRecords = (List<CSVRecords>) audIdFunctionResponse.get("ListVal");
                        break;

                    case "ACC_ID":
                        HashMap<String, Object> accIdFunctionResponse = sortTheAudienceFileForGiveSortFlagAndSortValue(csvData, sortValue, 1);
                        isRankColumnExists = (Boolean) accIdFunctionResponse.get("BooleanVal");
                        csvFileWithRecords = (List<CSVRecords>) accIdFunctionResponse.get("ListVal");
                        break;

                    case "SITE_ID":
                        HashMap<String, Object> siteIdFunctionResponse = sortTheAudienceFileForGiveSortFlagAndSortValue(csvData, sortValue, 2);
                        isRankColumnExists = (Boolean) siteIdFunctionResponse.get("BooleanVal");
                        csvFileWithRecords = (List<CSVRecords>) siteIdFunctionResponse.get("ListVal");
                        break;
                    case "SELLER_ID":

                        HashMap<String, Object> sellerIdFunctionResponse = sortTheAudienceFileForGiveSortFlagAndSortValue(csvData, sortValue, 3);
                        isRankColumnExists = (Boolean) sellerIdFunctionResponse.get("BooleanVal");
                        csvFileWithRecords = (List<CSVRecords>) sellerIdFunctionResponse.get("ListVal");
                        break;

                    case "RANK":
                        for (String[] row : csvData) {
                            if (row[5].equals(sortValue)) {
                                csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]));
                            }
                        }
                        break;

                    case "START_DATE":
                        HashMap<String, Object> startDateFunctionResponse = sortTheAudienceFileForGiveSortFlagAndSortValue(csvData, sortValue, csvData.get(0).length < 8 ? 5 : 6);
                        isRankColumnExists = (Boolean) startDateFunctionResponse.get("BooleanVal");
                        csvFileWithRecords = (List<CSVRecords>) startDateFunctionResponse.get("ListVal");
                        break;

                    case "END_DATE":
                        HashMap<String, Object> endDateFunctionResponse = sortTheAudienceFileForGiveSortFlagAndSortValue(csvData, sortValue, csvData.get(0).length < 8 ? 6 : 7);
                        isRankColumnExists = (Boolean) endDateFunctionResponse.get("BooleanVal");
                        csvFileWithRecords = (List<CSVRecords>) endDateFunctionResponse.get("ListVal");
                        break;
                }
            } else {
                for (String[] row : csvData) {
                    if (row.length < 8) {
                        csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], null, row[5], row[6]));
                    } else {
                        isRankColumnExists = true;
                        csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]));
                    }
                }
            }


            if (isRankColumnExists) {
                csvFileWithRecords.sort(Comparator.comparingInt(record -> Integer.parseInt(record.getRank())));
            }

            return csvFileWithRecords;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public HashMap<String, Object> sortTheAudienceFileForGiveSortFlagAndSortValue(List<String[]> csvData, String sortValue, int index) {
        HashMap<String, Object> functionResponse = new HashMap<>();
        List<CSVRecords> csvFileWithRecords = new ArrayList<>();
        Boolean isRankColumnExists = false;
        for (String[] row : csvData) {
            if (row.length < 8) {
                if (row[index].equals(sortValue)) {
                    csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], null, row[5], row[6]));
                }
            } else {
                isRankColumnExists = true;
                if (row[index].equals(sortValue)) {
                    csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]));
                }
            }
        }
        functionResponse.put("BooleanVal", isRankColumnExists);
        functionResponse.put("ListVal", csvFileWithRecords);
        return functionResponse;
    }


    public List<GraphRequestDto> multipartToJSONConverter(MultipartFile file) {
        try {

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

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public byte[] customAudienceFileGenerator(List<GraphRequestDto> graphRequestDtoList,String audienceId, String accountId, String startDate, String endDate) {
        StringBuilder sb = new StringBuilder();
        sb.append("audience_id,account_id,site_id,seller_id,supc,start_date,end_date").append("\n");

        for (GraphRequestDto dto : graphRequestDtoList) {
            sb.append(audienceId).append(",");
            sb.append(accountId).append(",");
            sb.append(dto.getSiteId()).append(",");
            sb.append(dto.getSellerId()).append(",");
            sb.append(dto.getProductId()).append(",");
            sb.append(" ").append(",");
            sb.append(startDate).append(",");
            sb.append(endDate).append("\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    public byte[] staticAudienceFileGenerator(List<GraphRequestDto> graphRequestDtoList,String audienceId, String accountId, String startDate, String endDate) {
        StringBuilder sb = new StringBuilder();
        sb.append("audience_id,account_id,site_id,seller_id,supc,rank,start_date,end_date").append("\n");

        for (GraphRequestDto dto : graphRequestDtoList) {
            sb.append(audienceId).append(",");
            sb.append(accountId).append(",");
            sb.append(dto.getSiteId()).append(",");
            sb.append(dto.getSellerId()).append(",");
            sb.append(dto.getProductId()).append(",");
            sb.append(ThreadLocalRandom.current().nextInt(1, 10)).append(",");
            sb.append(startDate).append(",");
            sb.append(endDate).append("\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }
}
