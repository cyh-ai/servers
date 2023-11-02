package com.integration.core.Enum;

/**
 * @author cyh
 * 异常code类
 */
public class ErrorConstans {

    public final static String CONFIG_MISSING = "0000000";
    public final static String CONFIG_WRONG = "0000001";
    public final static String FILE_BUILDING_CHECK_NOT_PASSED = "0000002";
    public final static String FILE_BUILDING_UNSUCCESSFUL = "0000003";
    public final static String FILE_CAN_NOT_BE_WRITTEN = "0000004";
    public final static String FILE_BACKUP_UNSUCCESSFUL = "0000005";
    public final static String FILE_SEND_UNSUCCESSFUL = "0000006";

    public final static String REPLACE_WRONG = "0000007";
    /**
     * 重复连接异常
     */
    public final static String REPEAT_REQUEST = "0000008";


    /**
     * 参数校验
     */
    public final static String PARAM_CHECK_FAIL = "2099902";

    /**
     * HTTP或HTTPS协议异常CODE
     */
    public final static String PROTOCOL_ANOMALY = "1001001";

    /**
     * 参数校验
     */
    public final static String REFERENCE_CORE_UTIL_CHECK = "0000301";

    /**
     * 测试异常
     */
    public final static String TEST_ERROR = "0000302";

    /**
     * admin通用报错
     */
    public final static String ADMIN_COMMON_EXCP = "2099904";


}
