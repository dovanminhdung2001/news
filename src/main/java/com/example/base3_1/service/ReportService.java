package com.example.base3_1.service;

import com.example.base3_1.dto.ReportOutputType;
import com.example.base3_1.dto.RequestExportReportDTO;
import org.eclipse.birt.report.engine.api.EngineException;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//import javax.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public interface ReportService {
    boolean isBirtTemplate(MultipartFile file) throws IOException;
    void generateMainReport(RequestExportReportDTO requestExportReportDTO, ReportOutputType output, HttpServletResponse response, HttpServletRequest request) throws IOException, EngineException, JSONException;
}
