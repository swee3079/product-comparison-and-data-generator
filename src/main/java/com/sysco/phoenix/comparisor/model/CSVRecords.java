package com.sysco.phoenix.comparisor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CSVRecords {
    private String audience_id;
    private String account_id;
    private String site_id;
    private String seller_id;
    private String supc;
    private String rank;
    private String start_date;
    private String end_date;
}
