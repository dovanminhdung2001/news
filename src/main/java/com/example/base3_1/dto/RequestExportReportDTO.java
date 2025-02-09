package com.example.base3_1.dto;


import lombok.Data;
import org.json.JSONObject;

import java.util.Map;

@Data
public class RequestExportReportDTO {
    private String reportCode;
    private String exportType;
    private int pageNumber;
    private Map<String, Object> queryParams;
    private JSONObject params;
    private String realmName;
    private String userName;
    private String kzAccountId;
    private String groupId;
    private Integer type;

    public RequestExportReportDTO() {
        pageNumber = 1;
    }

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public JSONObject getParams() {
        return params;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
