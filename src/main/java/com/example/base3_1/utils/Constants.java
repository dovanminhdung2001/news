package com.example.base3_1.utils;

public class Constants {
    public static final String REQUEST_MAPPING_PREFIX = "/api";
    public static final String REQUEST_MAPPING_PUBLIC_PREFIX = "/public/api";
    public static final String REQUEST_MAPPING_BASIC_PREFIX = "/basic/api";
    public static final String REQUEST_MAPPING_PRIVATE_PREFIX = "/private/api";
    public static final String VERSION_API_V1 = "/v1";
    public static final String COMMON_DATE_FORMAT = "dd/MM/yyyy";
    public static final String COMMON_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String MONTH_DAY_YEAR_FORMAT = "MMddyyyy";
    public static final String DAY_MONTH_YEAR_FORMAT = "ddMMyyyy";
    public static final String LOCALE_VN = "vi";
    public static final String FILE_DATE_FORMAT = "ddMMyyyy_HHmmss";
    public static final String TIMEZONE_VN = "Asia/Ho_Chi_Minh";
    public static String APIKEY_PREFIX = "api-key ";
    public static final int ONE_DAY_TIME = 86399999;
    public static final String TIME_FORMAT = "HH:mm";

    public final static int MAX_EXCEL_FILE_ROWS = 1048576;
    public static final int LENGTH_DESCRIPTION = 500;
    public static final int LENGTH_CODE = 50;
    public static final int LENGTH_NAME = 255;

    public static final short ALIGN_LEFT = 1;
    public static final short VERTICAL_CENTER = 1;

    public static final String LEFT = "LEFT";
    public static final String RIGHT = "RIGHT";
    public static final String CENTER = "CENTER";


    public static String ERROR_GROUP = "error_group";

    public static String ERROR_LEVEL = "error_level";

    public interface HEADER {
        String REALM = "x-ipcc2-realm";
        String AUTHORIZATION = "Authorization";
    }

    public interface STATUS {
        short PENDING_ACTIVE = 2;
        short ACTIVE = 1;
        short INACTIVE = 0;
    }

    public interface DATA_TYPE {
        Short FREE_TEXT = 1;
        Short NUMBER = 2;
    }
    public interface IS_ACTIVE {
        short ACTIVE = 1;
        short INACTIVE = 0;
    }
    public interface USED {
        short IS_USED = 1;
        short NOT_IS_USED = 0;
        String IS_USED_VALUE = "Đã được sử dụng";
        String NOT_IS_USED_VALUE = "Chưa được sử dụng";
    }

    public interface IS_DEFAULT_PARAM {
        short ACTIVE = 1;
        short INACTIVE = 0;
    }

    public interface ERROR_TYPE {
        short SUBTRACT_PROPORTION = 1;
        short SUBTRACT_DENSITY = 2;
    }

    public interface AP_PARAM_TYPE {
        String ERROR_GROUP = "error_group";
        String ERROR_LEVEL = "error_level";
        String EVALUATION_CLASSIFY="evaluation_classify";
        String EVALUATION_RATE="evaluation_rate";
        String SENIORITY = "seniority";

        String PARAM_TYPE = "PARAM_TYPE";

        String REGION = "region";

        String TICKET_CATEGORY = "ticket_category";

        String SALE_RESULT = "sale_result";

        String SALE_ERROR = "sale_error";

        String SALE_SERVICE = "sale_service";

        String AGENT_SKILL = "agent_skill";
    }

    public interface CRITERIA_IS_ACTIVE {
        String ACTIVE = "Kích hoạt";
        String IN_ACTIVE = "Không kích hoạt";

    }

    public interface IS_MULTIPLE_CHANNEL {
        short ACTIVE = 1;
        short INACTIVE = 0;

    }

    public interface GROUP_TYPE {
        Short TENANT = 0;
        Short CDV = 1;
        Short OS = 2;
    }

    public interface IS_MULTIPLE_SENIORITY {
        short ACTIVE = 1;
        short INACTIVE = 0;

    }

    public interface REGEX {
        String CODE_REQUEST = "^\\s*[a-zA-Z0-9_]*\\s*$";
        String CODE_NOT_LOWER = "^[A-Z0-9_]*$";
        String FULL_NAME_PATTERN =
                "[0-9a-zA-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨ" +
                        "ỰỬỮỲÝỴỶỸĐàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ\\s]*";
        String USERNAME_PATTERN = "^[a-zA-Z0-9.\\-_]*$";
        String EMAIL_PATTERN = "^([0-9a-zA-Z-.]+@[0-9a-zA-Z-.]+\\.[a-zA-Z]{2,9})$";
        String PHONE_PATTERN = "(0|84)?[1-9](\\d){8}";
        String CODE = "[a-zA-Z0-9_]+";
        String CODE_ = "^(?!.*_\\d$).*$\n";
        String REGEX_PATH = "^/|(/[\\w-]+)+$";
        String BOD_PATTERN = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|2[0-9])[0-9]{2})$";
        String NUMBER = "^(0|[0-9][0-9]*)$";

        String HOTLINE = "^\\d{7,12}$";

        String DATE = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\\d|3[0-1])$";

        String TIME = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
        String LOT_NUMBER = "^[a-zA-Z0-9._\\-\\/]+$";
    }

    public interface IS_OFFICIAL_PROMOTED {
        Short ACTIVE = 1;
        Short IN_ACTIVE = 0;
    }

    public interface SUPERVISOR_TYPE {
        Short SUPERVISOR = 1;
        Short INSPECTOR = 2;
        Short INSPECTOR2 = 3;
    }

    public interface ENTITY_STATUS {
        short ACTIVE = 1;
        short INACTIVE = 0;
    }

    public interface IS_SYNC {
        short SYNC = 1;
        short NOT_SYNC = 0;
    }

    public interface CHANGE_ACTION {
        String INSERT = "1";
        String UPDATE = "2";
        String DELETE = "2";
        String DELETE_HARD = "3";
    }


    public interface CHANNEL_TYPE {
        int CHAT = 1;
        int EMAIL = 2;
        int FACEBOOK_COMMENT = 3;
        int VOICE = 4;
        int CALL_ME_BACK = 5;
        int VIDEO_CALL = 6;
    }

    public interface CHANNEL_ID {
        int VOICE = 1;
        int EMAIL = 2;
        int FACEBOOK_MESSENGER = 3;
        int FACEBOOK_PAGE = 4;
        int CHAT_WEB = 5;
        int CALL_ME_BACK = 6;
        int ZALO = 7;
        int MOCHA = 8;
        int PRIVATE = 9;
        int NOTE = 9;
        int CHAT_INTERNAL = 10;
        int CHATBOT = 11;
        int VIDEO = 12;
        int SUPPORT_CHAT = 13;
    }

    public interface MARK_TYPE {
        short OFFLINE = 0;
        short ONLINE = 1;
    }
    public interface LAST_MARK {
        short NO = 0;
        short YES = 1;
    }

    public interface IS_IMPORTANT {
        short YES = 1;
        short NO = 0;
    }

    public interface TEMPLATE_NAME_IMPORT_ASSIGN_QUALITY {
        String BY_CALL = "Import_Assign_Quality_By_Call_Template";
        String BY_AGENT = "Import_Assign_Quality_By_AGENT_Template";
    }

    public interface TEMPLATE_TYPE_IMPORT_ASSIGN_QUALITY {
        Short BY_CALL= 1;
        Short BY_AGENT = 2;
    }

    public interface BIRTCODES {
        int TEXT_BOX = 0;
        int LIST_BOX = 1;
        int RADIO_BUTTON = 2;
        int CHECK_BOX = 3;
        int AUTO_SUGGEST = 4;
        int AUTO = 0;
        int LEFT = 1;
        int CENTER = 2;
        int RIGHT = 3;
        int TYPE_ANY = 0;
        int TYPE_STRING = 1;
        int TYPE_FLOAT = 2;
        int TYPE_DECIMAL = 3;
        int TYPE_DATE_TIME = 4;
        int TYPE_BOOLEAN = 5;
        int TYPE_INTEGER = 6;
        int TYPE_DATE = 7;
        int TYPE_TIME = 8;
        int SELECTION_LIST_NONE = 0;
        int SELECTION_LIST_DYNAMIC = 1;
        int SELECTION_LIST_STATIC = 2;
        int SCALAR_PARAMETER = 0;
        int FILTER_PARAMETER = 1;
        int LIST_PARAMETER = 2;
        int TABLE_PARAMETER = 3;
        int PARAMETER_GROUP = 4;
        int CASCADING_PARAMETER_GROUP = 5;
        String KEY_PARAM_GROUP = "parameterGroup";
        String KEY_NAME = "name";
        String KEY_HELP_TEXT = "helpText";
        String KEY_DISPLAY_NAME = "displayName";
        String KEY_DEFAULT_VALUE = "defaultValue";
        String KEY_DATA_TYPE = "dataType";
        String KEY_DISPLAY_FORMAT = "displayFormat";
        String KEY_TYPE_NAME = "typeName";
        String KEY_CONTROL_TYPE = "controlType";
        String KEY_PARAMETER_TYPE = "parameterType";
        String KEY_SELECTION_LIST_TYPE = "selectionListType";
        String KEY_SCALAR_PARAM_TYPE = "scalarParamType";
        String KEY_PROMPT_TEXT = "promptText";
        String KEY_HIDDEN = "hidden";
        String KEY_CONCEAL_ENTRY = "concealEntry";
        String KEY_REQUIRED = "isRequired";
        String KEY_SELECTION_LIST = "selectionList";
        String KEY_SCALAR_ORDER_IN_GROUP = "scalarOrderInGroup";
        String KEY_CACHED_LEVEL0_CAS_PARAM = "cachedLevel0CasParam";
    }


}
