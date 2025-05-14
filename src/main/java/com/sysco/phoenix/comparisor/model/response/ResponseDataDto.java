package com.sysco.phoenix.comparisor.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ResponseDataDto {


    @JsonProperty("Total SUPCs from graph response")
    private int graphResponseProductCount;

    @JsonProperty("Total SUPCs from audience file")
    private int audienceFileProductCount;

    @JsonProperty("Matching SUPC List")
    private List<SupcRespDto> matchingSupcList;

    @JsonProperty("matching but un-ordered List")
    private List<SupcRespDto> unMatchingSupcList;


    @JsonProperty("Un-matching & not exist in graph")
    private List<SupcRespDto> unMatchingSupcListWithoutExistenceInGraphResponse;  //does not exists in graph response but exists in audience file

    @JsonProperty("Un-matching & not exist in audience file")
    private List<SupcRespDto> unMatchingSupcListWithoutExistenceInAudienceFile; // does not exists in audience file but exists in graph response

}
