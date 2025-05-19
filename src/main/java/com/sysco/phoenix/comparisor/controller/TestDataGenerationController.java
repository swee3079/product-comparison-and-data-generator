package com.sysco.phoenix.comparisor.controller;

import com.sysco.phoenix.comparisor.model.response.ResponseDataDto;
import com.sysco.phoenix.comparisor.service.ComparisonService;
import com.sysco.phoenix.comparisor.service.TestDataGeneratorService;
import com.sysco.phoenix.comparisor.util.ResponseCodes;
import com.sysco.phoenix.comparisor.util.ResponseGenerator;
import com.sysco.phoenix.comparisor.util.ResponseMessage;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/testDataGenerator")
@Log4j2
public class TestDataGenerationController {

    @Autowired
    private TestDataGeneratorService testDataGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<?> testDataGenerator(@NonNull @RequestParam("graphResponseFile") MultipartFile graphResponseFile,
                                                 @RequestParam("audienceId") String audienceId,
                                                 @RequestParam("accountId") String accountId,
                                                 @RequestParam("startDate") String startDate,
                                                 @RequestParam("endDate") String endDate) {
        try {
            log.info("TestDataGenerationController => testDataGenerator() => Controller invoked...");

            return testDataGeneratorService.testDataGeneratorService(graphResponseFile, audienceId, accountId, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
