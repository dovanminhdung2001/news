package com.example.base3_1.service.impl;

import com.example.base3_1.dto.ReportOutputType;
import com.example.base3_1.dto.ReportRequestObject;
import com.example.base3_1.dto.RequestExportReportDTO;
import com.example.base3_1.dto.Response;
import com.example.base3_1.repository.ReportRepositoryJPA;
import com.example.base3_1.service.BaseReportService;
import com.example.base3_1.service.ReportService;
import com.example.base3_1.utils.Constants.BIRTCODES;
import com.example.base3_1.utils.CustomException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.*;
import org.eclipse.birt.report.model.api.*;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
//import javax.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ReportServiceImpl extends BaseReportService implements ReportService {

//    ApplicationContext context;
    IReportEngine birtEngine;
    final String imagesPath;
    final String tmpPath;
    final HTMLServerImageHandler htmlImageHandler = new HTMLServerImageHandler();

//    @Value(value = "${minio.report}")
//    String minioBucket;
    @Value(value = "${useResource}")
    boolean useResource;
    static final String SHOW = "1";
    static final String HIDDEN = "0";
    static final String HIVE_DATA_SOURCE = "Hive Data Source";
    static final long ONE_DAY_TO_MILLISECOND = 24 * 3600 * 1000L;

    @Autowired
    ReportRepositoryJPA reportRepositoryJPA;

    public ReportServiceImpl(ReportRepositoryJPA reportRepositoryJPA, Environment env) {
        this.reportRepositoryJPA = reportRepositoryJPA;
        imagesPath = StringUtils.isEmpty(env.getProperty("report.image-path")) ? "/tmp/report/images/" : env.getProperty("report.image-path");
        tmpPath = StringUtils.isEmpty(env.getProperty("report.tmp-path")) ? "/tmp/report/tmp/" : env.getProperty("report.tmp-path");
    }

    @SneakyThrows
    @PostConstruct
    private void initialize() {
        EngineConfig config = new EngineConfig();
//        config.getAppContext().put("spring", this.context);
        Platform.startup(config);
        IReportEngineFactory factory = (IReportEngineFactory) Platform
                .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
        birtEngine = factory.createReportEngine(config);
    }

//    public void setApplicationContext(ApplicationContext context) {
//        this.context = context;
//    }

    //*******************************************************************************************************************//
    //************************************             SECTION COMMON           *****************************************//
    //*******************************************************************************************************************//

    @Override
    public boolean isBirtTemplate(MultipartFile file) throws IOException {
        try {
            birtEngine.openReportDesign(file.getInputStream());
            return true;
        } catch (EngineException ex) {
            log.info(ex.getMessage(), ex);
            return false;
        }
    }

    private void setTitleBirtFile(RequestExportReportDTO requestExportReportDTO) {
        try {
            requestExportReportDTO.getParams().put("groupType", 0);
            requestExportReportDTO.getQueryParams().put("highestParentGroupName", "highestParentGroupName");
            String nationalTitle = ("msg.national-title");
            String motto = ("msg.motto");
            requestExportReportDTO.getQueryParams().put("groupName", "groupName");
            requestExportReportDTO.getQueryParams().put("nationalTitle", nationalTitle);
            requestExportReportDTO.getQueryParams().put("motto", motto);
        } catch (Exception e) {
            log.error("Set title err ---- ", e);
        }
    }

    @Override
    public void generateMainReport(RequestExportReportDTO requestExportReportDTO, ReportOutputType output, HttpServletResponse response, HttpServletRequest request) throws IOException, EngineException, JSONException {
        IReportRunnable report = getReportRunnable(requestExportReportDTO.getReportCode());

        requestExportReportDTO.getParams().put("locale",  "vi" );
        requestExportReportDTO.getQueryParams().put("highestParentGroupName", requestExportReportDTO.getQueryParams().get("highestParentGroupName"));
        requestExportReportDTO.getParams().put("timeZone", 7);
        if (report == null) return;
        switch (output) {
            case HTML:
                generateHTMLReport(response, requestExportReportDTO);
                break;
            case PDF:
                generatePDFReport(report, response, request, requestExportReportDTO);
                break;
            case XLSX:
                generateXLSXReport(requestExportReportDTO, response, request);
                break;
            default:
                throw new IllegalArgumentException("Output type not recognized:" + output);
        }
    }

    private void generateHTMLReport(HttpServletResponse response, RequestExportReportDTO requestExportReportDTO) throws IOException {
        IRenderOption options = initRenderTaskHtml(response);
        try {
            doRenderHtml(response, requestExportReportDTO, options);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void doRenderHtml(HttpServletResponse response, RequestExportReportDTO requestExportReportDTO, IRenderOption options) throws IOException, ParseException, EngineException, JSONException {
        HashMap<String, Object> param = new ObjectMapper().readValue(requestExportReportDTO.getParams().toString(), HashMap.class);
        param.put("kzAccountId", requestExportReportDTO.getKzAccountId());
        param.put("groupId", requestExportReportDTO.getGroupId());
        param.put("realmName", requestExportReportDTO.getRealmName());
        ReportRequestObject requestObject = new ReportRequestObject();
        requestObject.setReportCode(requestExportReportDTO.getReportCode());
        List<Map<String, Object>> paramDefine = getReportInputControl(requestObject);
        makeReportParam(paramDefine, param, requestExportReportDTO.getReportCode());
        String documentFileName = tmpPath + requestExportReportDTO.getReportCode() + UUID.randomUUID();
        IRenderTask renderTask = null;
        IReportDocument reportDocument = null;
        try {
            recreateReportDocument(requestExportReportDTO.getReportCode(), param, documentFileName);
            reportDocument = birtEngine.openReportDocument(documentFileName);
            long pages = reportDocument.getPageCount();
            response.setHeader("x-total-pages", String.valueOf(pages));
            response.setHeader("X-Frame-Options", "SAMEORIGIN");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "x-total-pages, x-total-count");
            renderTask = birtEngine.createRenderTask(reportDocument, reportDocument.getReportRunnable());
            renderTask.setRenderOption(options);
            renderTask.setPageNumber(requestExportReportDTO.getPageNumber());
            renderTask.setParameterValues(param);
            renderTask.render();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (renderTask != null)
                renderTask.close();
            if (reportDocument != null)
                reportDocument.close();
            if (new FileInfo(documentFileName).exists()) {
                File f = new File(documentFileName);
                f.delete();
            }
        }
    }

    private void recreateReportDocument(String reportCode, Map<String, Object> parameters, String documentFileName)
            throws EngineException, IOException {
        log.info("Need to create document file for {} report.", reportCode);
        IReportRunnable reportRunnable = getReportRunnable(reportCode);
        IRunTask runTask = null;
        try {
            runTask = birtEngine.createRunTask(reportRunnable);
            for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
                runTask.setParameterValue(parameter.getKey(), parameter.getValue());
            }
            runTask.run(documentFileName);
            runTask.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (runTask != null)
                runTask.close();
        }
    }

    private IRenderOption initRenderTaskHtml(HttpServletResponse response) throws IOException {
        response.setContentType(birtEngine.getMIMEType(IRenderOption.OUTPUT_FORMAT_HTML));
        IRenderOption options = new RenderOption();
        HTMLRenderOption htmlOptions = new HTMLRenderOption(options);
        htmlOptions.setOutputFormat(IRenderOption.OUTPUT_FORMAT_HTML);
        htmlOptions.setBaseImageURL(imagesPath);
        htmlOptions.setImageHandler(htmlImageHandler);
        htmlOptions.setEmbeddable(true);
        htmlOptions.setHtmlPagination(true);
        htmlOptions.setOutputStream(response.getOutputStream());
        return options;
    }

    private void generatePDFReport(IReportRunnable report, HttpServletResponse response, HttpServletRequest request, RequestExportReportDTO requestExportReportDTO) throws JsonProcessingException {
        IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
        response.setContentType(birtEngine.getMIMEType("pdf"));
        IRenderOption options = new RenderOption();
        PDFRenderOption pdfRenderOption = new PDFRenderOption(options);
        pdfRenderOption.setOutputFormat("pdf");
        pdfRenderOption.setOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.FIT_TO_PAGE_SIZE);
        pdfRenderOption.setEmbededFont(true);
        runAndRenderTask.setRenderOption(pdfRenderOption);
        runAndRenderTask.getAppContext().put(EngineConstants.APPCONTEXT_PDF_RENDER_CONTEXT, request);
        try {
            pdfRenderOption.setOutputStream(response.getOutputStream());
            requestExportReportDTO.getQueryParams().put("kzAccountId", requestExportReportDTO.getKzAccountId());
            requestExportReportDTO.getQueryParams().put("groupId", requestExportReportDTO.getGroupId());
            requestExportReportDTO.getQueryParams().put("realmName", requestExportReportDTO.getRealmName());
            runAndRenderTask.setParameterValues(requestExportReportDTO.getQueryParams());
            runAndRenderTask.run();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            runAndRenderTask.close();
        }
    }

    public void generateXLSXReport(RequestExportReportDTO requestExportReportDTO, HttpServletResponse response, HttpServletRequest request) throws IOException, EngineException {
        IReportRunnable report = getReportRunnable(requestExportReportDTO.getReportCode());
        if (report == null) return;
        generateExcel(report, response, requestExportReportDTO);
    }

    private void generateExcel(IReportRunnable report, HttpServletResponse response, RequestExportReportDTO requestExportReportDTO) throws IOException {
        try {
            ReportDesignHandle design = (ReportDesignHandle) report.getDesignHandle();
            SlotHandle shBody = design.getBody();
            Iterator slots = shBody.iterator();
            while (slots.hasNext()) {
                Object shContents = slots.next();
                if (shContents instanceof TableHandle) {
                    TableHandle tableHandle = (TableHandle) shContents;
                    tableHandle.setProperty("pageBreakInterval", 0);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
        initRenderTaskExcel(response, requestExportReportDTO, runAndRenderTask);
        try {
            doRender(requestExportReportDTO, runAndRenderTask);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            runAndRenderTask.close();
        }
    }

    private IRenderOption initRenderTaskExcel(HttpServletResponse response, RequestExportReportDTO requestExportReportDTO, IRunAndRenderTask runAndRenderTask) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; " + requestExportReportDTO.getReportCode() + ".xlsx");
        EXCELRenderOption excelRenderOption = new EXCELRenderOption();
        excelRenderOption.setEmitterID("uk.co.spudsoft.birt.emitters.excel.XlsxEmitter");
        excelRenderOption.setOutputFormat("xlsx");
        excelRenderOption.setOutputFileName(requestExportReportDTO.getReportCode() + ".xlsx");
        excelRenderOption.setEnableMultipleSheet(false);
        runAndRenderTask.setRenderOption(excelRenderOption);
        runAndRenderTask.getAppContext().put(EngineConstants.APPCONTEXT_BIRT_VIEWER_HTTPSERVET_REQUEST, requestExportReportDTO);
        excelRenderOption.setOutputStream(response.getOutputStream());
        return excelRenderOption;
    }

    private void doRender(RequestExportReportDTO requestExportReportDTO, IRunAndRenderTask runAndRenderTask) throws IOException, ParseException, EngineException, DesignFileException {
        HashMap<String, Object> param = new ObjectMapper().readValue(requestExportReportDTO.getParams().toString(), HashMap.class);
        param.put("kzAccountId", requestExportReportDTO.getKzAccountId());
        param.put("groupId", requestExportReportDTO.getGroupId());
        param.put("realmName", requestExportReportDTO.getRealmName());
        ReportRequestObject requestObject = new ReportRequestObject();
        requestObject.setReportCode(requestExportReportDTO.getReportCode());
        List<Map<String, Object>> paramDefine = getReportInputControl(requestObject);
        makeReportParam(paramDefine, param, requestExportReportDTO.getReportCode());
        runAndRenderTask.setParameterValues(param);
        runAndRenderTask.validateParameters();
        runAndRenderTask.run();
    }
    public static String safeToString(Object obj1) {
        return safeToString(obj1, "");
    }

    public static String safeToString(Object obj1, String defaultValue) {
        if (obj1 == null || obj1.toString().isEmpty()) {
            return defaultValue;
        }
        return obj1.toString();
    }

    public static Date string2Date(String input) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        //Parsing the given String to Date object
        Date date = formatter.parse(input);
        return date;
    }

    @SneakyThrows
    private void makeReportParam(List<Map<String, Object>> paramDefine, HashMap<String, Object> param, String reportCode) throws ParseException, EngineException {
        boolean hiveExists = false;
        IReportRunnable reportRunnable = getReportRunnable(reportCode);
        DesignElementHandle designElementHandle = reportRunnable.getDesignHandle();
        DataSourceHandle dataSourceHandle = designElementHandle.getRoot().findDataSource(HIVE_DATA_SOURCE);
        if ((dataSourceHandle) == null || dataSourceHandle.toString().isEmpty()) {
            hiveExists = true;
        }
        for (Map.Entry entry : param.entrySet()) {
            if (!CollectionUtils.isEmpty(getParamDetail((String) entry.getKey(), paramDefine))) {
                Map<String, Object> paramDetail = getParamDetail((String) entry.getKey(), paramDefine);
                if (!ObjectUtils.isEmpty(paramDetail.get("dataType"))) {
                    if (safeToString(BIRTCODES.TYPE_DATE, "").equals(paramDetail.get("dataType"))) {

                        Date date = string2Date(safeToString(entry.getValue()));
                        java.sql.Date prdId = new java.sql.Date(date.getTime());
                        param.put((String) entry.getKey(), prdId);
                    }
                } else {
                    log.info("paramDetail.get(dataType) is null");
                }
            } else {
                log.info("getParamDetail {} is null", entry.getKey());
            }
            if (entry.getValue() == null && !(entry.getValue() instanceof List) && hiveExists) {
                param.put((String) entry.getKey(), "");
            }
            if (entry.getValue() != null && entry.getValue() instanceof List) {
                List<Object> currentValue = (List<Object>) entry.getValue();
                entry.setValue(currentValue.toArray());
            }
        }
    }

    public List<Map<String, Object>> getReportInputControl(ReportRequestObject requestObject) throws EngineException, IOException {
        List<Map<String, Object>> paramsDefListRet = new ArrayList<>();
        IReportRunnable report = getReportRunnable(requestObject.getReportCode());
        if (report == null) return paramsDefListRet;
        IGetParameterDefinitionTask iGetParameterDefinitionTask = birtEngine.createGetParameterDefinitionTask(report);
        Collection params = iGetParameterDefinitionTask.getParameterDefns(true);
        //Get Parameter Definition Task tu 1 request hoac tao moi
        Iterator iter = params.iterator();
        //Duyet tung dinh nghia param
        try {
            while (iter.hasNext()) {
                IParameterDefnBase param = (IParameterDefnBase) iter.next();
                //Loai param la loai cascading group
                if (param instanceof IParameterGroupDefn) {
                    IParameterGroupDefn group = (IParameterGroupDefn) param;
                    //Duyet cac group de lay ra cac scalarparameter.
                    ArrayList listScalars = group.getContents();
                    for (int index = 0; index < listScalars.size(); index++) {
                        IScalarParameterDefn scalar = (IScalarParameterDefn) listScalars.get(index);
                        paramsDefListRet.add(loadParameterDetails(iGetParameterDefinitionTask, scalar, group, index));
                    }
                } else {
                    //Param la loai khai bao kieu Scalar
                    IScalarParameterDefn scalar = (IScalarParameterDefn) param;
                    paramsDefListRet.add(loadParameterDetails(iGetParameterDefinitionTask, scalar, null, 0));
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            iGetParameterDefinitionTask.close();
        }

        return paramsDefListRet;
    }

    private Map<String, Object> getParamDetail(String paramCode, List<Map<String, Object>> paramDefine) {
        for (Map<String, Object> map : paramDefine) {
            if (map.get("name").equals(paramCode)) {
                return map;
            }
        }
        return new HashMap<>();
    }

    private HashMap<String, Object> loadParameterDetails(IGetParameterDefinitionTask task, IScalarParameterDefn scalar,
                                                         IParameterGroupDefn group, int scalarOrderInGroup) throws Exception {
        if (task == null || scalar == null) {
            throw new Exception("ParameterDefinitionTask and ScalarParameterDefn must not be Null!");
        }

        HashMap<String, Object> parameter = new HashMap<>();
        //Kiem tra Lay thong tin cua param trong cache neu da luu thong tin
        Object defaultValue = null;
        defaultValue = task.getDefaultValue(scalar);
        parameter.put(BIRTCODES.KEY_SCALAR_ORDER_IN_GROUP, scalarOrderInGroup);
        if (group == null) {
            parameter.put(BIRTCODES.KEY_PARAM_GROUP, "Default");
        } else {
            parameter.put(BIRTCODES.KEY_PARAM_GROUP, group.getName());
        }

        parameter.put(BIRTCODES.KEY_NAME, scalar.getName());
        parameter.put(BIRTCODES.KEY_HELP_TEXT, scalar.getHelpText());
        parameter.put(BIRTCODES.KEY_DISPLAY_NAME, scalar.getDisplayName());
        parameter.put(BIRTCODES.KEY_DEFAULT_VALUE, defaultValue);
        parameter.put(BIRTCODES.KEY_DATA_TYPE, String.valueOf(scalar.getDataType()));
        parameter.put(BIRTCODES.KEY_DISPLAY_FORMAT, String.valueOf(scalar.getDisplayFormat()));
        parameter.put(BIRTCODES.KEY_TYPE_NAME, String.valueOf(scalar.getTypeName()));
        parameter.put(BIRTCODES.KEY_CONTROL_TYPE, String.valueOf(scalar.getControlType()));
        parameter.put(BIRTCODES.KEY_PARAMETER_TYPE, String.valueOf(scalar.getParameterType()));
        parameter.put(BIRTCODES.KEY_SELECTION_LIST_TYPE, String.valueOf(scalar.getSelectionListType()));
        parameter.put(BIRTCODES.KEY_SCALAR_PARAM_TYPE, String.valueOf(scalar.getScalarParameterType()));
        parameter.put(BIRTCODES.KEY_PROMPT_TEXT, String.valueOf(scalar.getPromptText()));

        if (scalar.isHidden()) {
            parameter.put(BIRTCODES.KEY_HIDDEN, SHOW);
        } else {
            parameter.put(BIRTCODES.KEY_HIDDEN, HIDDEN);
        }
        if (scalar.isValueConcealed()) {
            parameter.put(BIRTCODES.KEY_CONCEAL_ENTRY, SHOW); //ie passwords etc
        } else {
            parameter.put(BIRTCODES.KEY_CONCEAL_ENTRY, HIDDEN);
        }
        if (scalar.isRequired()) {
            parameter.put(BIRTCODES.KEY_REQUIRED, SHOW); //* khi hien thi
        } else {
            parameter.put(BIRTCODES.KEY_REQUIRED, HIDDEN);
        }

        if (scalar.getControlType() != IScalarParameterDefn.TEXT_BOX) {
            if (group == null) {
                Collection selectionList;
                try {
                    selectionList = task.getSelectionList(scalar.getName());
                } catch (Throwable t) {
                    log.info(t.getMessage(), t);
                    return parameter;
                }
                if (selectionList != null) {
                    List<List> dynamicList = new ArrayList<>();

                    for (Iterator sliter = selectionList.iterator(); sliter.hasNext(); ) {
                        IParameterSelectionChoice selectionItem = (IParameterSelectionChoice) sliter.next();
                        Object value = selectionItem.getValue();
                        String label = selectionItem.getLabel();
                        List labelAndValue = new ArrayList();
                        labelAndValue.add(label);// label vi tri 0, value vi tri 1 trong list
                        labelAndValue.add(value);
                        dynamicList.add(labelAndValue);
                    }
                    parameter.put(BIRTCODES.KEY_SELECTION_LIST, dynamicList);
                }
            }
        }
        return parameter;
    }

    public static Date clearTime(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    private void validFromDateAndToDate(Map<String, Object> queryParams) {
        Object fromDateObj = queryParams.getOrDefault("fromDate", null);

        if (fromDateObj == null)
            throw new CustomException(999, ("msg.common.validate.not.null - From date"));
        if (!(fromDateObj instanceof Long || fromDateObj instanceof Double || fromDateObj instanceof Integer))
            throw new CustomException(999, ("msg.common.validate.type.invalid.type - From Date - number"));

        Object toDateObj = queryParams.getOrDefault("toDate", null);

        if (toDateObj == null)
            throw new CustomException(999, ("msg.common.validate.not.null - To date"));
        if (!(toDateObj instanceof Long || toDateObj instanceof Double || toDateObj instanceof Integer))
            throw new CustomException(999, ("msg.common.validate.type.invalid.type - To date - number"));

        long fromDate = Long.parseLong(fromDateObj.toString());
        long toDate = Long.parseLong(toDateObj.toString());
        long now = clearTime(new Date()).getTime() + ONE_DAY_TO_MILLISECOND;

//        if (fromDate > toDate)
//            throw new CustomException(999, ("msg.common.validate.date.from-date-after-to-date"));
//
//        if (toDate > now)
//            throw new CustomException(999, ("msg.common.validate.date.future-date - To date"));
//
//        if ((toDate - fromDate) / ONE_DAY_TO_MILLISECOND > SEARCH_DURATION_DATE)
//            throw new CustomException(999, ("msg.common.validate.max.invalid - Time range", SEARCH_DURATION_DATE + " days"));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        String fromDateStr = sdf.format(new Date(fromDate));
        String toDateStr = sdf.format(new Date(toDate));
        String nowStr = sdf.format(new Date());

        queryParams.put("fromDateStr", fromDateStr);
        queryParams.put("toDateStr", toDateStr);
        queryParams.put("nowStr", nowStr);
    }

    private IReportRunnable getReportRunnable(String code) throws EngineException, IOException {
        String fileName = code + ".rptdesign";
        byte[] fileBirt;
        if (useResource) {
            fileBirt = getFileAsBytes(fileName);
        } else {
//            fileBirt = minioService.getFile(minioBucket, fileName);
            fileBirt = null;
        }
        if (fileBirt != null) {
            ByteArrayInputStream bai = new ByteArrayInputStream(fileBirt);
            return birtEngine.openReportDesign(bai);
        }
        return null;
    }

    public byte[] getFileAsBytes(String filename) throws IOException {
//        ClassPathResource resource = new ClassPathResource("reports/" + filename);
        File resource = new File("E:/Users/dovan/eclipse-workspace-default-java/Learn birt/" + filename);
        Path path = resource.toPath();
        return Files.readAllBytes(path);
    }

    public ResponseEntity<?> sendErr(CustomException e) {
        Response body = new Response(Response.Statuses.ERROR);
        body.setError(e.getErrorApp() != null ?  e.getErrorApp().getCode() : e.getCodeError());
        body.setMessage(e.getMessage());
        body.setPath(ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    //*******************************************************************************************************************//
    //************************************             SECTION REPORT           *****************************************//
    //*******************************************************************************************************************//

    public void executeReportInspectorQuality(RequestExportReportDTO requestExportReportDTO, ReportOutputType format, HttpServletResponse response, HttpServletRequest request) throws IOException, EngineException, JSONException {
        validFromDateAndToDate(requestExportReportDTO.getQueryParams());
        setTitleBirtFile(requestExportReportDTO);
        try {
            response.setHeader("x-total-count", reportRepositoryJPA.callGetReportAcdCallCG002Total(
                    requestExportReportDTO.getQueryParams().get("fromDate"),
                    requestExportReportDTO.getQueryParams().get("toDate"),
                    requestExportReportDTO.getQueryParams().get("channelIds"),
                    requestExportReportDTO.getQueryParams().get("channelNames"),
                    requestExportReportDTO.getQueryParams().get("markLevels"),
                    requestExportReportDTO.getQueryParams().get("markLevelNames"),
                    requestExportReportDTO.getKzAccountId(),
                    requestExportReportDTO.getRealmName()
            ).toString());
        } catch (Exception e) {
            log.error("Has error: ", e);
            response.setHeader("x-total-count", "-1");
        }
        generateMainReport(requestExportReportDTO, format, response, request);
    }

    public void executeScoringStructureReport(RequestExportReportDTO requestExportReportDTO, ReportOutputType format, String reportBy, HttpServletResponse response, HttpServletRequest request) throws IOException, EngineException, JSONException {
        validFromDateAndToDate(requestExportReportDTO.getQueryParams());
        setTitleBirtFile(requestExportReportDTO);
        try {
            Long total = 0L;
            if (reportBy.equals("duration")) {
                total = reportRepositoryJPA.callScoringStructureByDurationReport(
                            requestExportReportDTO.getQueryParams().get("fromDate"),
                            requestExportReportDTO.getQueryParams().get("toDate"),
                            requestExportReportDTO.getRealmName(),
                            requestExportReportDTO.getQueryParams().get("channelIdList"),
                            requestExportReportDTO.getQueryParams().get("sourceCodeList"));
            } else if (reportBy.equals("type")) {
                total = reportRepositoryJPA.callScoringStructureByTypeReport(
                            requestExportReportDTO.getQueryParams().get("fromDate"),
                            requestExportReportDTO.getQueryParams().get("toDate"),
                            requestExportReportDTO.getRealmName(),
                            requestExportReportDTO.getQueryParams().get("channelIdList"),
                            requestExportReportDTO.getQueryParams().get("sourceCodeList"));
            }
            response.setHeader("x-total-count", total.toString());
        } catch (Exception e) {
            log.error("Has error: ", e);
            response.setHeader("x-total-count", "-1");
        }
        generateMainReport(requestExportReportDTO, format, response, request);
    }

    public void executeDetailQualityReport(RequestExportReportDTO requestExportReportDTO, ReportOutputType format, HttpServletResponse response, HttpServletRequest request) throws IOException, EngineException, JSONException {
        validFromDateAndToDate(requestExportReportDTO.getQueryParams());
        setTitleBirtFile(requestExportReportDTO);
        try {
            response.setHeader("x-total-count", String.valueOf(reportRepositoryJPA.getResultQualityReport(
                    requestExportReportDTO.getKzAccountId(),
                    requestExportReportDTO.getQueryParams().get("fromDate"),
                    requestExportReportDTO.getQueryParams().get("toDate"),
                    requestExportReportDTO.getQueryParams().get("listChannelId"),
                    requestExportReportDTO.getQueryParams().get("listCallee"),
                    requestExportReportDTO.getQueryParams().get("listAgentId"),
                    requestExportReportDTO.getRealmName(),
                    requestExportReportDTO.getGroupId()
            ).size()));
        } catch (Exception e) {
            log.error("Has error: ", e);
            response.setHeader("x-total-count", "-1");
        }
        generateMainReport(requestExportReportDTO, format, response, request);
    }
    public void executeReportLineError(RequestExportReportDTO requestExportReportDTO, ReportOutputType format, HttpServletResponse response, HttpServletRequest request) throws IOException, EngineException, JSONException {
        validFromDateAndToDate(requestExportReportDTO.getQueryParams());
        setTitleBirtFile(requestExportReportDTO);
        try {
            response.setHeader("x-total-count", String.valueOf(reportRepositoryJPA.getReportErrorLine(
                    requestExportReportDTO.getQueryParams().get("fromDate"),
                    requestExportReportDTO.getQueryParams().get("toDate"),
                    requestExportReportDTO.getQueryParams().get("channelIds") == null ? "" : requestExportReportDTO.getQueryParams().get("channelIds"),
                    requestExportReportDTO.getQueryParams().get("sourceIds") == null ? "" : requestExportReportDTO.getQueryParams().get("sourceIds"),
                    requestExportReportDTO.getKzAccountId(),
                    requestExportReportDTO.getGroupId(),
                    requestExportReportDTO.getRealmName()
            ).size()));
        } catch (Exception e) {
            log.error("Has error: ", e);
            response.setHeader("x-total-count", "-1");
        }
        generateMainReport(requestExportReportDTO, format, response, request);
    }

    public void executeCriteriaReport(RequestExportReportDTO requestExportReportDTO, ReportOutputType format, HttpServletResponse response, HttpServletRequest request) throws IOException, EngineException, JSONException {
        validFromDateAndToDate(requestExportReportDTO.getQueryParams());
        setTitleBirtFile(requestExportReportDTO);
        try {
            response.setHeader("x-total-count", reportRepositoryJPA.callCriteriaReport(
                    requestExportReportDTO.getQueryParams().get("fromDate"),
                    requestExportReportDTO.getQueryParams().get("toDate"),
                    requestExportReportDTO.getRealmName(),
                    requestExportReportDTO.getQueryParams().get("channelIdList"),
                    requestExportReportDTO.getQueryParams().get("sourceCodeList")).toString());
        } catch (Exception e) {
            log.error("Has error: ", e);
            response.setHeader("x-total-count", "-1");
        }
        generateMainReport(requestExportReportDTO, format, response, request);
    }

    public static boolean isValidTimeZone(String timeZoneID) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneID);
        return !timeZone.getID().equals("GMT") || timeZoneID.equals("GMT");
    }


    public void executeDetailScoringAgentReport(RequestExportReportDTO requestExportReportDTO, ReportOutputType format, HttpServletResponse response, HttpServletRequest request) throws IOException, EngineException, JSONException {
        validFromDateAndToDate(requestExportReportDTO.getQueryParams());
        setTitleBirtFile(requestExportReportDTO);
        try {
            response.setHeader("x-total-count", String.valueOf(reportRepositoryJPA.getResultDetailScoringReport(
                    requestExportReportDTO.getQueryParams().get("fromDate"),
                    requestExportReportDTO.getQueryParams().get("toDate"),
                    requestExportReportDTO.getRealmName(),
                    requestExportReportDTO.getGroupId(),
                    requestExportReportDTO.getQueryParams().get("listChannelId"),
                    requestExportReportDTO.getQueryParams().get("listSourceCode"),
                    requestExportReportDTO.getQueryParams().get("listAgentId")
            ).size()));
        } catch (Exception e) {
            log.error("Has error: ", e);
            response.setHeader("x-total-count", "-1");
        }
        generateMainReport(requestExportReportDTO, format, response, request);
    }

    public void executeSeniorityReport(RequestExportReportDTO requestExportReportDTO, ReportOutputType format, HttpServletResponse response, HttpServletRequest request) throws IOException, EngineException, JSONException {
        validFromDateAndToDate(requestExportReportDTO.getQueryParams());
        setTitleBirtFile(requestExportReportDTO);
        try {
            response.setHeader("x-total-count", "0");
        } catch (Exception e) {
            log.error("Has error: ", e);
            response.setHeader("x-total-count", "-1");
        }
        generateMainReport(requestExportReportDTO, format, response, request);
    }

    public void bc04(RequestExportReportDTO requestExportReportDTO, ReportOutputType format, HttpServletResponse response, HttpServletRequest request) throws IOException, EngineException, JSONException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        String nowStr = sdf.format(new Date());

        requestExportReportDTO.getQueryParams().put("nowStr", nowStr);
        setTitleBirtFile(requestExportReportDTO);

        try {
            Map<String, Object> params = requestExportReportDTO.getQueryParams();

//            response.setHeader("x-total-count", String.valueOf(reportRepositoryJPA.bc_04_compare_quality_two_consecutive_months(
//                    params.get("monthStr") == null ? "" : params.get("monthStr"),
//                    requestExportReportDTO.getRealmName(),
//                    requestExportReportDTO.getGroupId(),
//                    requestExportReportDTO.getKzAccountId(),
//                    params.get("groupType") == null ? 1 : params.get("groupType"),
//                    params.get("apParamCodeRegionList") == null ? "" : params.get("apParamCodeRegionList"),
//                    params.get("apParamCodeSeniorityList") == null ? "": params.get("apParamCodeSeniorityList"),
//                    params.get("sourceCodeList") == null ? "": params.get("sourceCodeList"),
//                    params.get("channelIdList") == null ? "": params.get("channelIdList")
//            ).size()));
        } catch (Exception e) {
            log.error("Has error: ", e);
            response.setHeader("x-total-count", "-1");
        }
        generateMainReport(requestExportReportDTO, format, response, request);
    }

    public void executeGraduateQualityReport(RequestExportReportDTO requestExportReportDTO, ReportOutputType format, HttpServletResponse response, HttpServletRequest request) throws IOException, EngineException, JSONException {
        setTitleBirtFile(requestExportReportDTO);
        try {
            response.setHeader("x-total-count", String.valueOf(reportRepositoryJPA.getGraduateQualityReport(
                    requestExportReportDTO.getQueryParams().get("fullName") == null ? "" : requestExportReportDTO.getQueryParams().get("fullName"),
                    requestExportReportDTO.getQueryParams().get("AgentUserName") == null ? "" : requestExportReportDTO.getQueryParams().get("AgentUserName"),
                    requestExportReportDTO.getQueryParams().get("agentNumber") == null ? "" : requestExportReportDTO.getQueryParams().get("agentNumber"),
                    requestExportReportDTO.getQueryParams().get("os") == null ? "" : requestExportReportDTO.getQueryParams().get("os"),
                    requestExportReportDTO.getQueryParams().get("line") == null ? "" : requestExportReportDTO.getQueryParams().get("line"),
                    requestExportReportDTO.getGroupId(),
                    requestExportReportDTO.getRealmName(),
                    requestExportReportDTO.getKzAccountId()
            ).size()));
        } catch (Exception e) {
            log.error("Has error: ", e);
            response.setHeader("x-total-count", "-1");
        }
        generateMainReport(requestExportReportDTO, format, response, request);
    }

    public void executeEmotionQualityCallReport(RequestExportReportDTO requestExportReportDTO, ReportOutputType format, HttpServletResponse response, HttpServletRequest request) throws IOException, EngineException, JSONException {
        setTitleBirtFile(requestExportReportDTO);
        validFromDateAndToDate(requestExportReportDTO.getQueryParams());
        if (requestExportReportDTO.getQueryParams().get("reportType").equals(1)) {
            requestExportReportDTO.setReportCode("CALL_EMOTION_QUALITY");
        } else {
            requestExportReportDTO.setReportCode("CALL_EMOTION_QUALITY_DETAIL");
        }
        try {
            response.setHeader("x-total-count", String.valueOf(reportRepositoryJPA.getEmotionQualityCallReport(
                    requestExportReportDTO.getQueryParams().get("fromDate"),
                    requestExportReportDTO.getQueryParams().get("toDate"),
                    requestExportReportDTO.getQueryParams().get("reportType") == null ? "" : requestExportReportDTO.getQueryParams().get("reportType"),
                    requestExportReportDTO.getQueryParams().get("evaluationType") == null ? "" : requestExportReportDTO.getQueryParams().get("evaluationType"),
                    requestExportReportDTO.getQueryParams().get("agent") == null ? "" : requestExportReportDTO.getQueryParams().get("agent"),
                    requestExportReportDTO.getQueryParams().get("listChannel") == null ? "" : requestExportReportDTO.getQueryParams().get("listChannel"),
                    requestExportReportDTO.getQueryParams().get("seniority") == null ? "" : requestExportReportDTO.getQueryParams().get("seniority"),
                    requestExportReportDTO.getQueryParams().get("os") == null ? "" : requestExportReportDTO.getQueryParams().get("os"),
                    requestExportReportDTO.getQueryParams().get("region") == null ? "" : requestExportReportDTO.getQueryParams().get("region"),
                    requestExportReportDTO.getGroupId(),
                    requestExportReportDTO.getRealmName(),
                    requestExportReportDTO.getKzAccountId()
            ).size()));
        } catch (Exception e) {
            log.error("Has error: ", e);
            response.setHeader("x-total-count", "-1");
        }
        generateMainReport(requestExportReportDTO, format, response, request);
    }

    public void executeBc12(RequestExportReportDTO requestExportReportDTO, ReportOutputType format, HttpServletResponse response, HttpServletRequest request) throws IOException, EngineException, JSONException {
        setTitleBirtFile(requestExportReportDTO);
        validFromDateAndToDate(requestExportReportDTO.getQueryParams());
        generateMainReport(requestExportReportDTO, format, response, request);
     }

    public void executeDailyAnswerQualityReport(RequestExportReportDTO requestExportReportDTO, ReportOutputType format, HttpServletResponse response, HttpServletRequest request) throws IOException, EngineException, JSONException {
        setTitleBirtFile(requestExportReportDTO);
        validFromDateAndToDate(requestExportReportDTO.getQueryParams());
        generateMainReport(requestExportReportDTO, format, response, request);
    }

    public void executeBc19(RequestExportReportDTO requestExportReportDTO, ReportOutputType format, HttpServletResponse response, HttpServletRequest request) throws IOException, EngineException, JSONException {
        setTitleBirtFile(requestExportReportDTO);
        validFromDateAndToDate(requestExportReportDTO.getQueryParams());
        generateMainReport(requestExportReportDTO, format, response, request);
    }

    public void executeQs02(RequestExportReportDTO requestExportReportDTO, ReportOutputType format, HttpServletResponse response, HttpServletRequest request) throws IOException, EngineException, JSONException {
        setTitleBirtFile(requestExportReportDTO);
        validFromDateAndToDate(requestExportReportDTO.getQueryParams());
        generateMainReport(requestExportReportDTO, format, response, request);
    }

    public void executeBc23(RequestExportReportDTO requestExportReportDTO, ReportOutputType format, HttpServletResponse response, HttpServletRequest request) throws EngineException, JSONException, IOException {
        setTitleBirtFile(requestExportReportDTO);
        validFromDateAndToDate(requestExportReportDTO.getQueryParams());
        generateMainReport(requestExportReportDTO, format, response, request);
    }
}
