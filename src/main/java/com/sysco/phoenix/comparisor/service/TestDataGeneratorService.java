package com.sysco.phoenix.comparisor.service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface TestDataGeneratorService {
    public ResponseEntity<?> testDataGeneratorService(MultipartFile graphResponseFile,String audienceId, String accountId, String startDate, String endDate) throws Exception;

}
