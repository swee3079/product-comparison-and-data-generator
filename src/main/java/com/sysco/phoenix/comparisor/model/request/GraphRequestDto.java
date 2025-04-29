package com.sysco.phoenix.comparisor.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class GraphRequestDto {

    private String sellerId;
    private String siteId;
    private String productId;
    private String name;
    private String description;
    private Object brand;
    private Boolean isOrderable;
}
