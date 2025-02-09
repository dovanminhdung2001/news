package com.example.base3_1.controller;

import com.example.base3_1.dto.ReportOutputType;
import com.example.base3_1.dto.RequestExportReportDTO;
import com.example.base3_1.service.impl.ReportServiceImpl;
import com.example.base3_1.utils.Constants;
import com.example.base3_1.utils.CustomException;
import com.example.base3_1.utils.ErrorApp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.eclipse.birt.report.engine.api.EngineException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

//import javax.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX + Constants.VERSION_API_V1 + "/report")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportController {

    final ReportServiceImpl reportService;
    final String kzAccountId = "c73d23988068164e62cf31d016bbd69c";
    final String realmName = "demo";
    final String groupId = "b61bef87-8fdd-4e26-a992-9f1262717cd9";

    @ResponseBody
    @PostMapping("/execute-report/inspector-quality")
    public Object executeReportInspectorQuality(HttpServletResponse response, HttpServletRequest request,
                                                @RequestBody RequestExportReportDTO requestExportReportDTO) throws IOException {
        JSONObject jsonObject = new JSONObject(requestExportReportDTO.getQueryParams());
        requestExportReportDTO.setParams(jsonObject);
        ReportOutputType format = ReportOutputType.from(requestExportReportDTO.getExportType());
        requestExportReportDTO.setKzAccountId(kzAccountId);
        requestExportReportDTO.setGroupId(groupId);
        requestExportReportDTO.setRealmName(realmName);
        try {
            reportService.executeReportInspectorQuality(requestExportReportDTO, format, response, request);
} catch (CustomException e) {
            return reportService.sendErr(e);
        } catch (EngineException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/execute-report/scoring-structure")
    public Object executeScoringStructureReport(HttpServletResponse response, HttpServletRequest request,
                                                @RequestBody RequestExportReportDTO requestExportReportDTO,
                                                @RequestParam("report-by") String reportBy) throws IOException {
        if (reportBy.isEmpty() || !(reportBy.equals("duration") || reportBy.equals("type")))
            throw new CustomException(ErrorApp.BAD_REQUEST);

        JSONObject jsonObject = new JSONObject(requestExportReportDTO.getQueryParams());
        requestExportReportDTO.setParams(jsonObject);
        ReportOutputType format = ReportOutputType.from(requestExportReportDTO.getExportType());
        requestExportReportDTO.setKzAccountId(kzAccountId);
        requestExportReportDTO.setGroupId(groupId);
        requestExportReportDTO.setRealmName(realmName);
        try {
            reportService.executeScoringStructureReport(requestExportReportDTO, format, reportBy, response, request);
} catch (CustomException e) {
            return reportService.sendErr(e);
        } catch (EngineException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/execute-report/detail-quality-report")
    public Object detailQualityReport(HttpServletResponse response, HttpServletRequest request,
                                                @RequestBody RequestExportReportDTO requestExportReportDTO) throws IOException {
        JSONObject jsonObject = new JSONObject(requestExportReportDTO.getQueryParams());
        requestExportReportDTO.setParams(jsonObject);
        ReportOutputType format = ReportOutputType.from(requestExportReportDTO.getExportType());
        requestExportReportDTO.setKzAccountId(kzAccountId);
        requestExportReportDTO.setGroupId(groupId);
        requestExportReportDTO.setRealmName(realmName);
        try {
            reportService.executeDetailQualityReport(requestExportReportDTO, format, response, request);
} catch (CustomException e) {
            return reportService.sendErr(e);
        } catch (EngineException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/execute-report/line-error")
    public void executeReportLineError(HttpServletResponse response, HttpServletRequest request,
                                       @RequestBody RequestExportReportDTO requestExportReportDTO,
                                       @RequestHeader String groupId
    ) throws IOException, EngineException, JSONException {
        JSONObject jsonObject = new JSONObject(requestExportReportDTO.getQueryParams());
        requestExportReportDTO.setParams(jsonObject);
        ReportOutputType format = ReportOutputType.from(requestExportReportDTO.getExportType());
        requestExportReportDTO.setKzAccountId(kzAccountId);
        requestExportReportDTO.setGroupId(groupId);
        requestExportReportDTO.setRealmName(realmName);
        reportService.executeReportLineError(requestExportReportDTO, format, response, request);
    }

    @ResponseBody
    @PostMapping("/execute-report/qualifier-criteria")
    public Object executeCriteriaReport(HttpServletResponse response, HttpServletRequest request,
                                                @RequestBody RequestExportReportDTO requestExportReportDTO) throws IOException {
        JSONObject jsonObject = new JSONObject(requestExportReportDTO.getQueryParams());
        requestExportReportDTO.setParams(jsonObject);
        ReportOutputType format = ReportOutputType.from(requestExportReportDTO.getExportType());
        requestExportReportDTO.setKzAccountId(kzAccountId);
        requestExportReportDTO.setGroupId(groupId);
        requestExportReportDTO.setRealmName(realmName);
        try {
            reportService.executeCriteriaReport(requestExportReportDTO, format, response, request);
        } catch (CustomException e) {
            return reportService.sendErr(e);
        } catch (EngineException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/execute-report/detail-scoring-agent")
    public Object detailScoringAgentReport(HttpServletResponse response, HttpServletRequest request,
                                      @RequestBody RequestExportReportDTO requestExportReportDTO) throws IOException {
        JSONObject jsonObject = new JSONObject(requestExportReportDTO.getQueryParams());
        requestExportReportDTO.setParams(jsonObject);
        ReportOutputType format = ReportOutputType.from(requestExportReportDTO.getExportType());
        requestExportReportDTO.setKzAccountId(kzAccountId);
        requestExportReportDTO.setGroupId(groupId);
        requestExportReportDTO.setRealmName(realmName);
        try {
            reportService.executeDetailScoringAgentReport(requestExportReportDTO, format, response, request);
        } catch (CustomException e) {
            return reportService.sendErr(e);
        } catch (EngineException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/execute-report/seniority-report")
    public Object seniorityReport(HttpServletResponse response, HttpServletRequest request,
                                           @RequestBody RequestExportReportDTO requestExportReportDTO) throws IOException {
        JSONObject jsonObject = new JSONObject(requestExportReportDTO.getQueryParams());
        requestExportReportDTO.setParams(jsonObject);
        ReportOutputType format = ReportOutputType.from(requestExportReportDTO.getExportType());
        requestExportReportDTO.setKzAccountId(kzAccountId);
        requestExportReportDTO.setGroupId(groupId);
        requestExportReportDTO.setRealmName(realmName);
        try {
            reportService.executeSeniorityReport(requestExportReportDTO, format, response, request);
} catch (CustomException e) {
            return reportService.sendErr(e);
        } catch (EngineException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/execute-report/graduate-quality-report")
    public Object graduateQualityReport(HttpServletResponse response, HttpServletRequest request,
                                  @RequestBody RequestExportReportDTO requestExportReportDTO) throws IOException {
        JSONObject jsonObject = new JSONObject(requestExportReportDTO.getQueryParams());
        requestExportReportDTO.setParams(jsonObject);
        ReportOutputType format = ReportOutputType.from(requestExportReportDTO.getExportType());
        requestExportReportDTO.setKzAccountId(kzAccountId);
        requestExportReportDTO.setGroupId(groupId);
        requestExportReportDTO.setRealmName(realmName);
        try {
            reportService.executeGraduateQualityReport(requestExportReportDTO, format, response, request);
} catch (CustomException e) {
            return reportService.sendErr(e);
        } catch (EngineException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/execute-report/bc_04_compare_quality_two_consecutive_months")
    public Object bc04(HttpServletResponse response, HttpServletRequest request,
                                  @RequestBody RequestExportReportDTO requestExportReportDTO) throws IOException {
        JSONObject jsonObject = new JSONObject(requestExportReportDTO.getQueryParams());
        requestExportReportDTO.setParams(jsonObject);
        ReportOutputType format = ReportOutputType.from(requestExportReportDTO.getExportType());
        requestExportReportDTO.setKzAccountId(kzAccountId);
        requestExportReportDTO.setGroupId(groupId);
        requestExportReportDTO.setRealmName(realmName);
        try {
            reportService.bc04(requestExportReportDTO, format, response, request);
} catch (CustomException e) {
            return reportService.sendErr(e);
        } catch (EngineException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/execute-report/bc_12_cutomer_trend_and_response_ability")
    public Object executeBc12 (HttpServletResponse response, HttpServletRequest request,
                       @RequestBody RequestExportReportDTO requestExportReportDTO) throws IOException {
        JSONObject jsonObject = new JSONObject(requestExportReportDTO.getQueryParams());
        requestExportReportDTO.setParams(jsonObject);
        ReportOutputType format = ReportOutputType.from(requestExportReportDTO.getExportType());
        requestExportReportDTO.setKzAccountId(kzAccountId);
        requestExportReportDTO.setGroupId(groupId);
        requestExportReportDTO.setRealmName(realmName);
        try {
            reportService.executeBc12(requestExportReportDTO, format, response, request);
} catch (CustomException e) {
            return reportService.sendErr(e);
        } catch (EngineException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/execute-report/emotion-quality-call")
    public Object emotionQualityCallReport(HttpServletResponse response, HttpServletRequest request,
                       @RequestBody RequestExportReportDTO requestExportReportDTO) throws IOException {
        JSONObject jsonObject = new JSONObject(requestExportReportDTO.getQueryParams());
        requestExportReportDTO.setParams(jsonObject);
        ReportOutputType format = ReportOutputType.from(requestExportReportDTO.getExportType());
        requestExportReportDTO.setKzAccountId(kzAccountId);
        requestExportReportDTO.setGroupId(groupId);
        requestExportReportDTO.setRealmName(realmName);
        try {
            reportService.executeEmotionQualityCallReport(requestExportReportDTO, format, response, request);
} catch (CustomException e) {
            return reportService.sendErr(e);
        } catch (EngineException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/execute-report/daily-answer-quality")
    public Object dailyAnswerQualityReport(HttpServletResponse response, HttpServletRequest request,
                                           @RequestBody RequestExportReportDTO requestExportReportDTO) throws IOException {
        JSONObject jsonObject = new JSONObject(requestExportReportDTO.getQueryParams());
        requestExportReportDTO.setParams(jsonObject);
        ReportOutputType format = ReportOutputType.from(requestExportReportDTO.getExportType());
        requestExportReportDTO.setKzAccountId(kzAccountId);
        requestExportReportDTO.setGroupId(groupId);
        requestExportReportDTO.setRealmName(realmName);
        try {
            reportService.executeDailyAnswerQualityReport(requestExportReportDTO, format, response, request);
} catch (CustomException e) {
            return reportService.sendErr(e);
        } catch (EngineException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/execute-report/qs_02_static_call_evaluate_rate")
    public Object qs02(HttpServletResponse response, HttpServletRequest request,
                       @RequestBody RequestExportReportDTO requestExportReportDTO) throws IOException {
        JSONObject jsonObject = new JSONObject(requestExportReportDTO.getQueryParams());
        requestExportReportDTO.setParams(jsonObject);
        ReportOutputType format = ReportOutputType.from(requestExportReportDTO.getExportType());
        requestExportReportDTO.setKzAccountId(kzAccountId);
        requestExportReportDTO.setGroupId(groupId);
        requestExportReportDTO.setRealmName(realmName);
        try {
            reportService.executeQs02(requestExportReportDTO, format, response, request);
} catch (CustomException e) {
            return reportService.sendErr(e);
        } catch (EngineException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/execute-report/bc_19_call_type_inspection_structure")
    public Object bc19(HttpServletResponse response, HttpServletRequest request,
                                           @RequestBody RequestExportReportDTO requestExportReportDTO) throws IOException {
        JSONObject jsonObject = new JSONObject(requestExportReportDTO.getQueryParams());
        requestExportReportDTO.setParams(jsonObject);
        ReportOutputType format = ReportOutputType.from(requestExportReportDTO.getExportType());
        requestExportReportDTO.setKzAccountId(kzAccountId);
        requestExportReportDTO.setGroupId(groupId);
        requestExportReportDTO.setRealmName(realmName);
        try {
            reportService.executeBc19(requestExportReportDTO, format, response, request);
        } catch (CustomException e) {
            return reportService.sendErr(e);
        } catch (EngineException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/execute-report/bc_23_partner_inspection_structure")
    public Object bc23(HttpServletResponse response, HttpServletRequest request,
                       @RequestBody RequestExportReportDTO requestExportReportDTO) throws IOException {
        JSONObject jsonObject = new JSONObject(requestExportReportDTO.getQueryParams());
        requestExportReportDTO.setParams(jsonObject);
        ReportOutputType format = ReportOutputType.from(requestExportReportDTO.getExportType());
        requestExportReportDTO.setKzAccountId(kzAccountId);
        requestExportReportDTO.setGroupId(groupId);
        requestExportReportDTO.setRealmName(realmName);
        try {
            reportService.executeBc23(requestExportReportDTO, format, response, request);
        } catch (CustomException e) {
            return reportService.sendErr(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
