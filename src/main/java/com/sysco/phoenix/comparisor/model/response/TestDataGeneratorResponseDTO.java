package com.sysco.phoenix.comparisor.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class TestDataGeneratorResponseDTO {

    @JsonProperty("customAudienceFile")
    private String customAudienceFile;

    @JsonProperty("staticAudienceFile")
    private String staticAudienceFile;
}
