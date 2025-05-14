package com.sysco.phoenix.comparisor.service.impl;

import com.sysco.phoenix.comparisor.model.CSVRecords;
import com.sysco.phoenix.comparisor.model.request.GraphRequestDto;
import com.sysco.phoenix.comparisor.model.response.ResponseDataDto;
import com.sysco.phoenix.comparisor.model.response.SupcRespDto;
import com.sysco.phoenix.comparisor.service.ComparisonService;
import com.sysco.phoenix.comparisor.util.ResponseCodes;
import com.sysco.phoenix.comparisor.util.ResponseGenerator;
import com.sysco.phoenix.comparisor.util.ResponseMessage;
import com.sysco.phoenix.comparisor.util.Utilities;
import jakarta.annotation.Nullable;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ComparisonServiceImpl implements ComparisonService {

    @Autowired
    private Utilities utilities;

    @Autowired
    private ResponseGenerator responseGenerator;


    @Override
    public ResponseEntity<?> performFileComparison(MultipartFile csvFile, MultipartFile jsonFile,String sortByFlag,String sortValue) throws Exception {
        log.info("ComparisonServiceImpl => performFileComparison() => Service invoked...");
        List<SupcRespDto> matchingSupcList = new ArrayList<>();
        List<SupcRespDto> matchingUnorderedSupcList = new ArrayList<>();
        List<SupcRespDto> unMatchingSupcListWithoutExistenceInGraphResponse = new ArrayList<>();
        List<SupcRespDto> unMatchingSupcListWithoutExistenceInAudienceFile = new ArrayList<>();
        String responseCode = ResponseCodes.SUCCESS_RESP_CODE;
        String responseMessage = ResponseMessage.SUCCESS_MESSAGE;
        int currentExecutedIndexOfGraphResponse = 0;

        try {
            List<CSVRecords> audienceFileInputResultList = utilities.MultipartToCsvConverter(csvFile,sortByFlag,sortValue);
            List<GraphRequestDto> graphResponseResultList = utilities.multipartToJSONConverter(jsonFile);

            if (audienceFileInputResultList == null || audienceFileInputResultList.isEmpty()) {
                return responseGenerator.generateResponse(
                        ResponseCodes.ERROR_RESP_CODE,
                        ResponseMessage.CSV_FILE_IS_EMPTY_ERROR_MESSAGE,
                        null);
            }

            if (graphResponseResultList == null || graphResponseResultList.isEmpty()) {
                return responseGenerator.generateResponse(
                        ResponseCodes.ERROR_RESP_CODE,
                        ResponseMessage.JSON_FILE_IS_EMPTY_ERROR_MESSAGE,
                        null);
            }


            log.info("CSV File Size is : {}", audienceFileInputResultList.size());
            log.info("JSON File Size is : {}", graphResponseResultList.size());

            for (int i = 0; i <= graphResponseResultList.size() - 1; i++) {
                ++currentExecutedIndexOfGraphResponse;
                String productIdFromGraph = graphResponseResultList.get(i).getProductId();


                if (i <= audienceFileInputResultList.size() - 1) {
                    String productIdFromCSV = audienceFileInputResultList.get(i).getSupc();


                    if (productIdFromGraph.equals(productIdFromCSV)) {
                        matchingSupcList.add(new SupcRespDto(productIdFromCSV, productIdFromGraph, String.valueOf(i + 1)));
                    } else {
                        boolean existsInUnmatch = false;
                        for (int j = 0; j <= graphResponseResultList.size() - 1; j++) {
                            String productIdFromGraph2 = graphResponseResultList.get(j).getProductId();


                            if (productIdFromGraph2.equals(productIdFromCSV)) {
                                existsInUnmatch = true;
                                responseCode = ResponseCodes.ERROR_RESP_CODE;
                                responseMessage = ResponseMessage.ERROR_MESSAGE;
                                matchingUnorderedSupcList.add(new SupcRespDto(productIdFromCSV, productIdFromGraph2, String.valueOf(i + 1)));
                                break;
                            }
                        }

                        if (!existsInUnmatch) {
                            responseCode = ResponseCodes.ERROR_RESP_CODE;
                            responseMessage = ResponseMessage.ERROR_MESSAGE;
                            unMatchingSupcListWithoutExistenceInGraphResponse.add(new SupcRespDto(null, productIdFromCSV, String.valueOf(i + 1)));
                            unMatchingSupcListWithoutExistenceInAudienceFile.add(new SupcRespDto(productIdFromGraph, null, null));
                        }
                    }
                }else {
                    int notExisting = 0;
                    for (int k = 0; k <= audienceFileInputResultList.size() - 1; k++) {
                        String supcFromAudienceFile = audienceFileInputResultList.get(k).getSupc();

                        if (productIdFromGraph.equals(supcFromAudienceFile)) {
                            boolean recordAlreadyExists=false;
                            for(SupcRespDto record : matchingUnorderedSupcList) {
                                if(!record.getProductIdFromAudienceInput().equals(supcFromAudienceFile)) {
                                    recordAlreadyExists=true;
                                }
                            }
                            if(!recordAlreadyExists) {
                                matchingUnorderedSupcList.add(new SupcRespDto(supcFromAudienceFile, productIdFromGraph, null));
                            }
                            break;
                        }
                        ++notExisting;
                    }

                    if (notExisting == audienceFileInputResultList.size()) {
                        unMatchingSupcListWithoutExistenceInAudienceFile.add(new SupcRespDto(productIdFromGraph, null, null));
                    }
                }
            }


            if (graphResponseResultList.size() < audienceFileInputResultList.size()) {
                for (int k = currentExecutedIndexOfGraphResponse; k <= audienceFileInputResultList.size() - 1; k++) {
                    unMatchingSupcListWithoutExistenceInGraphResponse.add(new SupcRespDto(null, audienceFileInputResultList.get(k).getSupc(), String.valueOf(k + 1)));
                }
            }

            return
                    responseGenerator.generateResponse(
                            responseCode,
                            responseMessage,
                            new ResponseDataDto(
                                    graphResponseResultList.size(),
                                    audienceFileInputResultList.size(),
                                    matchingSupcList,
                                    matchingUnorderedSupcList,
                                    unMatchingSupcListWithoutExistenceInGraphResponse,
                                    unMatchingSupcListWithoutExistenceInAudienceFile));
        } catch (Exception e) {
            e.printStackTrace();
            return responseGenerator.generateResponse(
                    ResponseCodes.ERROR_RESP_CODE,
                    ResponseMessage.ERROR_MESSAGE,
                    new ResponseDataDto());
        }
    }
}
