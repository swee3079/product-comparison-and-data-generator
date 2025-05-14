package com.sysco.phoenix.comparisor.service;

import com.sysco.phoenix.comparisor.model.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ComparisonService {
    public ResponseEntity<?> performFileComparison(MultipartFile csvFile, MultipartFile jsonFile,String sortByFlag,String sortValue) throws Exception;

}
