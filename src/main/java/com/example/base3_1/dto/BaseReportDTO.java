package com.example.base3_1.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseReportDTO implements Serializable {
    private Long totalPage;
    private Long totalRow;
    private Long page;
    private Long pageSize;
    private String keyword;
    private List<Long> ids;
}
