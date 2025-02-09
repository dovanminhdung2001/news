package com.example.base3_1.dto;

import org.eclipse.birt.report.engine.api.IRenderOption;

public enum ReportOutputType {
    HTML(IRenderOption.OUTPUT_FORMAT_HTML),
    PDF(IRenderOption.OUTPUT_FORMAT_PDF),
    XLSX("XLSX"),
    INVALID("invalid");

    String val;
    ReportOutputType(String val) {
        this.val = val;
    }

    public static ReportOutputType from(String text) {
        for (ReportOutputType output : values()) {
            if(output.val.equalsIgnoreCase(text)) return output;
        }
        return INVALID;
    }
}
