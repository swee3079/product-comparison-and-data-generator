package com.sysco.phoenix.comparisor.service.impl;

import com.sysco.phoenix.comparisor.model.request.GraphRequestDto;
import com.sysco.phoenix.comparisor.model.response.TestDataGeneratorResponseDTO;
import com.sysco.phoenix.comparisor.service.TestDataGeneratorService;
import com.sysco.phoenix.comparisor.util.Utilities;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Service
@Log4j2
public class TestDataGeneratorServiceImpl implements TestDataGeneratorService {

    @Autowired
    private Utilities utilities;

    @Override
    public ResponseEntity<?> testDataGeneratorService(MultipartFile graphResponseFile, String audienceId, String accountId, String startDate, String endDate) throws Exception {
        try {
            List<GraphRequestDto> graphResponseResultList = utilities.multipartToJSONConverter(graphResponseFile);
            byte[] customerAudienceFile = utilities.customAudienceFileGenerator(graphResponseResultList, audienceId, accountId, startDate, endDate);
            byte[] staticAudienceFile = utilities.staticAudienceFileGenerator(graphResponseResultList, audienceId, accountId, startDate, endDate);
            return new ResponseEntity<>(
                    new TestDataGeneratorResponseDTO(
                            Base64.getEncoder().encodeToString(customerAudienceFile),
                            Base64.getEncoder().encodeToString(staticAudienceFile)),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
