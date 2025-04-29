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
public class SupcRespDto {
    @JsonProperty("graph response SUPC")
    private String productIdFromGraphResponse;
    @JsonProperty("audience file SUPC")
    private String productIdFromAudienceInput;
    @JsonProperty("audience file index")
    private String productIndexNumberFromInput;
}
