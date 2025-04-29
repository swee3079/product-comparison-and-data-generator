package com.sysco.phoenix.comparisor.util;

import com.sysco.phoenix.comparisor.model.response.ResponseDTO;
import com.sysco.phoenix.comparisor.model.response.ResponseDataDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseGenerator {


    public ResponseEntity<?> generateResponse(String responseCode, String responseMessage, ResponseDataDto data){
            return new ResponseEntity<>(
                    new ResponseDTO(responseCode, responseMessage, data),
                    responseCode.equals(ResponseCodes.SUCCESS_RESP_CODE) ? HttpStatus.OK : HttpStatus.BAD_REQUEST
            );
    }
}
