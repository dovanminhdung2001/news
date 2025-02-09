package com.example.base3_1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Date;

/**
 * Envelope for responses.
 *
 * @author hashcode | thangdd@hotmail.com
 * @since 2021-04-03
 */

public class Response<T> implements Serializable {

    public enum Statuses {
        @JsonProperty("error")
        ERROR,
        @JsonProperty("failure")
        FAILURE,
        @JsonProperty("success")
        SUCCESS
    }

    @JsonProperty("auth_token")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String authToken;

    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer error;

    private Statuses status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String path;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonProperty("page_size")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pageSize;

    @JsonProperty("current_page")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer currentPage;

    @JsonProperty("total_pages")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalPages;

    @JsonProperty("total_elements")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long totalElements;

    @JsonProperty("request_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String requestId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String revision;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date timestamp;

    public Response() {
    }

    public Response(Object object) {
        this(Statuses.SUCCESS);

        if (object instanceof Page) {
            Page pageObject = (Page) object;
            this.data = (T) pageObject.getContent();

            this.pageSize = pageObject.getSize();
            this.currentPage = pageObject.getNumber();
            this.totalPages = pageObject.getTotalPages();
            this.totalElements = pageObject.getTotalElements();
        } else {
            this.data = (T) object;
        }
    }

    public Response(Statuses status) {
        this.status = status;
        this.timestamp = new Date();
    }

    public static Response of(Page page) {
        Response res = new Response(page.getContent());

        res.pageSize = page.getSize();
        res.currentPage = page.getNumber();
        res.totalPages = page.getTotalPages();
        res.totalElements = page.getTotalElements();
        return res;
    }

    public Response withMessage(String message) {
        if (this.message == null) {
            this.message = message;
        }
        return this;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public Statuses getStatus() {
        return status;
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZZ", timezone = "Asia/Ho_Chi_Minh")
    public Date getTimestampISO8601() {
        return timestamp;
    }
}
