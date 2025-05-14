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
import java.util.*;

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
//                        for (String[] row : csvData) {
//                            if (row.length < 8) {
//                                if (row[0].equals(sortValue)) {
//                                    csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], null, row[6], row[7]));
//                                }
//                            } else {
//                                isRankColumnExists = true;
//                                if (row[0].equals(sortValue)) {
//                                    csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]));
//                                }
//                            }
//                        }
                        HashMap<String, Object> audIdFunctionResponse = sortTheAudienceFileForGiveSortFlagAndSortValue(csvData, sortValue, 0);
                        isRankColumnExists = (Boolean) audIdFunctionResponse.get("BooleanVal");
                        csvFileWithRecords = (List<CSVRecords>) audIdFunctionResponse.get("ListVal");
                        break;
                    case "ACC_ID":
//                        for (String[] row : csvData) {
//                            if (row.length < 8) {
//                                if (row[1].equals(sortValue)) {
//                                    csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], null, row[6], row[7]));
//                                }
//                            } else {
//                                isRankColumnExists = true;
//                                if (row[1].equals(sortValue)) {
//                                    csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]));
//                                }
//                            }
//                        }

                        HashMap<String, Object> accIdFunctionResponse = sortTheAudienceFileForGiveSortFlagAndSortValue(csvData, sortValue, 1);
                        isRankColumnExists = (Boolean) accIdFunctionResponse.get("BooleanVal");
                        csvFileWithRecords = (List<CSVRecords>) accIdFunctionResponse.get("ListVal");
                        break;
                    case "SITE_ID":
//                        for (String[] row : csvData) {
//                            if (row.length < 8) {
//                                if (row[2].equals(sortValue)) {
//                                    csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], null, row[6], row[7]));
//                                }
//                            } else {
//                                isRankColumnExists = true;
//                                if (row[2].equals(sortValue)) {
//                                    csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]));
//                                }
//                            }
//                        }

                        HashMap<String, Object> siteIdFunctionResponse = sortTheAudienceFileForGiveSortFlagAndSortValue(csvData, sortValue, 2);
                        isRankColumnExists = (Boolean) siteIdFunctionResponse.get("BooleanVal");
                        csvFileWithRecords = (List<CSVRecords>) siteIdFunctionResponse.get("ListVal");
                        break;
                    case "SELLER_ID":
//                        for (String[] row : csvData) {
//                            if (row.length < 8) {
//                                if (row[3].equals(sortValue)) {
//                                    csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], null, row[6], row[7]));
//                                }
//                            } else {
//                                isRankColumnExists = true;
//                                if (row[3].equals(sortValue)) {
//                                    csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]));
//                                }
//                            }
//                        }

                        HashMap<String, Object> sellerIdFunctionResponse = sortTheAudienceFileForGiveSortFlagAndSortValue(csvData, sortValue, 3);
                        isRankColumnExists = (Boolean) sellerIdFunctionResponse.get("BooleanVal");
                        csvFileWithRecords = (List<CSVRecords>) sellerIdFunctionResponse.get("ListVal");
                        break;
                    case "RANK":
                        for (String[] row : csvData) {
                            if (row[5].equals(sortValue)) {
                                csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], null, row[6], row[7]));
                            }
                        }
                        break;
                    case "START_DATE":
//                        for (String[] row : csvData) {
//                            if (row.length < 8) {
//                                if (row[6].equals(sortValue)) {
//                                    csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], null, row[6], row[7]));
//                                }
//                            } else {
//                                isRankColumnExists = true;
//                                if (row[6].equals(sortValue)) {
//                                    csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]));
//                                }
//                            }
//                        }

                        HashMap<String, Object> startDateFunctionResponse = sortTheAudienceFileForGiveSortFlagAndSortValue(csvData, sortValue, 6);
                        isRankColumnExists = (Boolean) startDateFunctionResponse.get("BooleanVal");
                        csvFileWithRecords = (List<CSVRecords>) startDateFunctionResponse.get("ListVal");
                        break;
                    case "END_DATE":
//                        for (String[] row : csvData) {
//                            if (row.length < 8) {
//                                if (row[7].equals(sortValue)) {
//                                    csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], null, row[6], row[7]));
//                                }
//                            } else {
//                                isRankColumnExists = true;
//                                if (row[7].equals(sortValue)) {
//                                    csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]));
//                                }
//                            }
//                        }

                        HashMap<String, Object> endDateFunctionResponse = sortTheAudienceFileForGiveSortFlagAndSortValue(csvData, sortValue, 7);
                        isRankColumnExists = (Boolean) endDateFunctionResponse.get("BooleanVal");
                        csvFileWithRecords = (List<CSVRecords>) endDateFunctionResponse.get("ListVal");
                        break;
                }
            } else {
                for (String[] row : csvData) {
                    if (row.length < 8) {
                        csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], null, row[6], row[7]));
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
                    csvFileWithRecords.add(new CSVRecords(row[0], row[1], row[2], row[3], row[4], null, row[6], row[7]));
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
}
