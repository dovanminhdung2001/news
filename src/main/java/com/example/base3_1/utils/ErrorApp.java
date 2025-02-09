package com.example.base3_1.utils;
 

public enum ErrorApp {
    SUCCESS(200, "msg.success"),
    BAD_REQUEST(400, "msg.bad.request"),
    BAD_REQUEST_PATH(400, "msg.bad.request.path"),
    UNAUTHORIZED(401, "msg.unauthorized"),
    FORBIDDEN(403, "msg.access.denied"),
    INTERNAL_SERVER(500, "msg.internal.server"),

    FILE_INVALID_SIZE(4041, "msg.validate.import.file.exceed-file-size"),
    FILE_INVALID_TEMPLATE(4005, "msg.validate.import.file.wrong-template"),
    FILE_EMPTY(4130, "msg.validate.import.file.empty"),

    // Ticket Rating
    TICKET_MARK_BY_OTHER(4001, "ticket-rating.access-ticket-rating-marking-by-other"),
    //Assign law
    DIFF_PARTNER(4200, "assign-law.validate.diff-partner"),



    ERROR_ID_NOT_EMPTY(400,"msg.error.id.not.empty"),

    ERROR_NOT_FOND(400,"msg.error.is.not.found"),
    ERROR_NAME_NOT_EMPTY(400,"msg.error.name.not.empty"),

    ERROR_NAME_EXIST(400,"msg.error.name.exist"),
    ERROR_CODE_NOT_EMPTY(400,"msg.error.code.not.empty"),

    ERROR_CODE_EXIST(400,"msg.error.code.exist"),
    ERROR_NAME_LENGTH_GREATER_255(400,"msg.error.name.max.length.255"),
    ERROR_CODE_LENGTH_GREATER_10(400,"msg.error.code.max.length.10"),

    ERROR_CODE_NOT_VALID(400,"msg.error.code.not.valid"),

    ERROR_APPLY_FROM_NOT_EMPTY(400,"msg.error.apply.from.not.empty"),

    ERROR_APPLY_FROM_GREATER_NOW(400,"msg.error.apply.from.not.greater.now"),

    ERROR_APPLY_FROM_GREATER_APPLY_TO(400,"msg.error.apply.from.not.greater.apply.to"),

    ERROR_CHANNEL_NOT_EMPTY(400,"msg.error.channel.not.empty"),

    ERROR_CHANNEL_NOT_FOUND(400,"msg.error.channel.not.found"),

    ERROR_IS_MULTIPLE_SENIORITY_NOT_EMPTY(400,"msg.error.is.multiple.seniority.not.empty"),

    ERROR_SENIORITY_NOT_EMPTY(400,"msg.error.seniority.not.empty"),

    ERROR_SENIORITY_EMPTY(400,"msg.error.seniority.empty"),

    ERROR_SENIORITY_NOT_FOUND(400,"msg.error.seniority.not.found"),

    ERROR_GROUP_NOT_EMPTY(400,"msg.error.group.not.empty"),

    ERROR_LEVEL_NOT_EMPTY(400,"msg.error.level.not.empty"),

    ERROR_GROUP_NOT_FOUND(400,"msg.error.group.not.found"),

    ERROR_LEVEL_NOT_FOUND(400,"msg.error.level.not.found"),

    ERROR_LEVEL_NOT_SAME_GROUP(400,"msg.error.level.same.group"),

    ERROR_LEVEL_NOT_LEVEL_TYPE(400,"msg.error.level.not.par.type"),

    ERROR_GROUP_NOT_GROUP_TYPE(400,"msg.error.group.not.par.type"),

    ERROR_ERROR_TYPE_NOT_EMPTY(400,"msg.error.type.not.empty"),

    ERROR_SCORE_ELIMINATE_NOT_EMPTY(400,"msg.error.score.eliminate.not.empty"),

    ERROR_SCORE_ELIMINATE_NOT_VALID(400,"msg.error.score.eliminate.not.valid"),
    ERROR_CRITERIA_NOT_EMPTY(400,"msg.error.criteria.not.empty"),

    ERROR_CRITERIA_NOT_FOUND(400,"msg.error.criteria.not.found"),

    ERROR_DESCRIPTION_GREATER_LENGTH_500(400,"msg.error.description.max.length.500"),

    ERROR_GROUP_LEVEL_CHANNEL_SENIORITY_EXIST(400,"msg.error.group.level.channel.seniority.exist"),

    AP_PARAM_ID_NOT_EMPTY(400,"msg.ap.param.id.not.empty"),

    AP_PARAM_NOT_FOND(400,"msg.ap.param.is.not.found"),

    AP_PARAM_PARENT_NOT_FOND(400,"msg.ap.param.parent.is.not.found"),

    AP_PARAM_PARENT_NOT_SAME_AP_PARAM(400,"msg.ap.param.parent.is.not.same.param"),

    AP_PARAM_IS_DEFAULT(400,"msg.ap.param.is.default"),
    AP_PARAM_NAME_NOT_EMPTY(400,"msg.ap.param.name.not.empty"),

    AP_PARAM_PAR_TYPE_NOT_EMPTY(400,"msg.ap.param.par.type.not.empty"),

    AP_PARAM_IS_MULTIPLE_CHANNEL_NOT_EMPTY(400,"msg.ap.param.is.multiple.channel.not.empty"),



    AP_PARAM_IS_MULTIPLE_CHANNEL_NOT_FLOW_PARENT_TYPE(400,"msg.ap.param.is.multiple.channel.not.flow"),

    AP_PARAM__CHANNEL_NOT_EMPTY(400,"msg.ap.param.channel.not.empty"),

    AP_PARAM__CHANNEL_EMPTY(400,"msg.ap.param.channel.empty"),

    AP_PARAM__CHANNEL_NOT_FOUND(400,"msg.ap.param.channel.not.found"),

    AP_PARAM_PAR_VALUE_NOT_EMPTY(400,"msg.ap.param.value.not.empty"),

    AP_PARAM_DATA_TYPE_NOT_EMPTY(400,"msg.ap.param.data.type.not.empty"),

    AP_PARAM_DATA_TYPE_NOT_VALID(400,"msg.ap.param.import.data.type.valid"),

    AP_PARAM_PAR_VALUE_NOT_VALID(400,"msg.ap.param.import.par.value.valid"),

    AP_PARAM_NAME_EXIST(400,"msg.ap.param.name.exist"),
    AP_PARAM_CODE_NOT_EMPTY(400,"msg.ap.param.code.not.empty"),

    AP_PARAM_CODE_EXIST(400,"msg.ap.param.code.exist"),
    AP_PARAM_NAME_LENGTH_GREATER_255(400,"msg.ap.param.name.max.length.255"),
    AP_PARAM_CODE_LENGTH_GREATER_10(400,"msg.ap.param.code.max.length.10"),

    AP_PARAM_CODE_NOT_VALID(400,"msg.ap.param.code.not.valid"),

    AP_PARAM_NAME_PAR_TYPE_NOT_VALID(400,"msg.ap.param.name.par.type"),

    AP_PARAM_DESCRIPTION_GREATER_LENGTH_500(400,"msg.ap.param.description.max.length.500"),

    AP_PARAM_TYPE_NOT_FOND(400,"msg.ap.param.type.is.not.found"),


    NOT_FOUND_KEYCLOAK(4000, "keycloak.validate.not-found"),
    //criteria message
    CRITERIA_NAME_EXISTS(400,"msg.criteria.name.exists"),
    MAX_SCORE_INVALID(400, "msg.max.score.invalid"),
    MIN_SCORE_INVALID(400, "msg.min.score.invalid"),
    SCORE_DUPLICATE(400, "msg.score.duplicate"),
    NAME_OF_FAIL_DUPLICATE(400, "msg.name.of.fail.duplicate"),
    NAME_OF_FAIL_CAUSE_DUPLICATE(400, "msg.name.of.fail.cause.duplicate"),
    SCORE_RANGE_OVERLAP(400, "msg.score.range.overlap"),
    CRITERIA_CODE_EXISTS(400,"msg.criteria.code.exists"),
    CRITERIA_ID_NULL(400,"msg.criteria.id.null"),
    CRITERIA_ID_EXISTS(400,"msg.criteria.id.exists"),
    CRITERIA_NOT_FOUND(400,"msg.criteria.not.found"),
    CRITERIA_NAME_REQUIRED(400,"msg.criteria.name.required"),
    CRITERIA_CODE_REQUIRED(400,"msg.criteria.code.required"),
    PARENT_ID_REQUIRED(400,"msg.parent.id.required"),
    IS_ACTIVE_REQUIRED(400,"msg.is.active.required"),
    CRITERIA_NAME_LENGTH(400,"msg.criteria.name.length"),
    DESCRIPTION_LENGTH(400,"msg.description.length"),
    CRITERIA_CODE_INVALID(400,"msg.criteria.code.invalid"),
    CRITERIA_ORDER_REQUIRED(400,"msg.criteria.order.required"),
    CRITERIA_NAME_FAIL_LENGTH(400,"msg.criteria.name.fail.length"),
    CRITERIA_NAME_FAIL_CAUSE_LENGTH(400,"msg.criteria.name.fail.cause.length"),
    CRITERIA_IS_NOT_ACTIVE(400,"msg.criteria.is.not.active"),
    CRITERIA_ORDER_NOT_ZERO(400,"msg.criteria.order.not.zero"),
    CRITERIA_ORDER_INVALID(400,"msg.criteria.order.invalid"),
    CRITERIA_ORDER_LENGTH(400,"msg.criteria.order.length"),
    CRITERIA_IN_USE(400,"msg.criteria.in.use"),


    //evaluation
    EVALUATION_NOT_FOUND(400,"msg.evaluation.not.found"),
    EVALUATION_CODE_EXISTS(400,"msg.evaluation.code.exists"),
    EVALUATION_NAME_EXISTS(400,"msg.evaluation.name.exists"),
    EVALUATION_NAME_REQUIRED(400,"msg.evaluation.name.required"),
    EVALUATION_CODE_REQUIRED(400,"msg.evaluation.code.required"),
    EVALUATION_CHANNEL_REQUIRED(400,"msg.evaluation.channel.required"),
    EVALUATION_APPLY_FROM_REQUIRED(400,"msg.evaluation.apply.from.required"),
    EVALUATION_TOTAL_SCORE_REQUIRED(400,"msg.evaluation.total.score.required"),
    APPLY_FROM_IS_PAST(400,"msg.evaluation.apply.from.is.past"),
    APPLY_TO_BEFORE_FROM(400,"msg.evaluation.apply.to.before.from"),
    EVALUATION_NAME_LENGTH(400,"msg.evaluation.name.length"),
    EVALUATION_CODE_INVALID(400,"msg.evaluation.code.length"),
    EVALUATION_DESCRIPTION_LENGTH(400,"msg.evaluation.description.length"),
    EVALUATION_CRITERIA_SCORE_INVALID(400,"msg.evaluation.criteria.score.invalid"),
    EVALUATION_TOTAL_SCORE_INVALID(400,"msg.evaluation.total.score.invalid"),
    EVALUATION_CLASSIFY_NULL(400,"msg.evaluation.classify.null"),
    EVALUATION_RATE_NULL(400,"msg.evaluation.rate.null"),
    EVALUATION_REQUEST_REQUIRED(400,"msg.evaluation.request.required"),
    EVALUATION_CHANNEL_NOT_EXISTS(400,"msg.evaluation.channel.not.exists"),
    EVALUATION_SENIORITY_NOT_EXISTS(400,"msg.evaluation.seniority.not.exists"),
    EVALUATION_CRITERIA_ID_NOT_EXISTS(400,"msg.evaluation.criteria.id.not.exists"),
    EVALUATION_CLASSIFY_NOT_EXISTS(400,"msg.evaluation.evaluation.classify.not.exists"),
    EVALUATION_RATE_NOT_EXISTS(400,"msg.evaluation.evaluation.rate.not.exists"),
    EVALUATION_CRITERIA_SCORE_GREATER_TOTAL(400,"msg.evaluation.criteria.score.greater.total"),
    EVALUATION_CLASSIFY_EXISTS(400,"msg.evaluation.classify.exists"),
    EVALUATION_RATE_EXISTS(400,"msg.evaluation.rate.exists"),
    EVALUATION_TOTAL_SCORE_EXCEEDS(400,"msg.evaluation.criteria.score.exceeds"),
    MAX_SCORE_GREATER_TOTAL(400,"msg.evaluation.max.score.exceeds"),
    APPLY_FROM_EXISTS(400,"msg.evaluation.apply.from.is.exists"),
    TOTAL_SCORE_NOT_ZERO(400,"msg.evaluation.total.score.not.zero"),
    EVALUATION_CRITERIA_SCORE_NOT_ZERO(400,"msg.evaluation.criteria.score.not.zero"),
    MAX_SCORE_NOT_ZERO(400,"msg.max.score.not.zero"),
    MIN_SCORE_NOT_ZERO(400,"msg.min.score.not.zero"),


    ;
    private final int code;
    private final String description;

    ErrorApp(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}

