package com.sysco.phoenix.comparisor.controller;

import com.sysco.phoenix.comparisor.model.response.ResponseDataDto;
import com.sysco.phoenix.comparisor.service.ComparisonService;
import com.sysco.phoenix.comparisor.util.ResponseCodes;
import com.sysco.phoenix.comparisor.util.ResponseGenerator;
import com.sysco.phoenix.comparisor.util.ResponseMessage;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/comparison")
@Log4j2
public class FileComparisonController {

    @Autowired
    private ComparisonService comparisonService;

    @PostMapping("/compare")
    public ResponseEntity<?> fileComparison(@NonNull @RequestParam("csvFile") MultipartFile csvFile,
                                            @NonNull @RequestParam("jsonFile") MultipartFile jsonFile){
        try {
            log.info("FileComparisonController => fileComparison() => Controller invoked...");
            return comparisonService.performFileComparison(csvFile, jsonFile);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseGenerator().generateResponse(ResponseCodes.ERROR_RESP_CODE,ResponseMessage.ERROR_MESSAGE,new ResponseDataDto());
        }
    }


}
