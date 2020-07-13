package com.carry.www.utils.constant;

/**
 * 类描述：常量工具类
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年07月08日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public class Constants
{
    // UTF-8 字符集
    public static final String UTF8 = "UTF-8";

    // UTF-8 字符集
    public static final String GBK= "GBK";

    // 通用成功标识
    public static final String SUCCESS = "0";

    //  通用失败标识
    public static final String FAIL = "1";

    // 登录成功
    public static final String LOGIN_SUCCESS = "Success";

   // 注销
    public static final String LOGOUT = "Logout";

    //  登录失败
    public static final String LOGIN_FAIL = "Error";

   // 当前页
    public static String PAGE_NO = "pageNo";

    // 每页显示记录数
    public static String PAGE_SIZE = "pageSize";

    // Authorization
    public static final String TOKEN_HEADER = "Authorization";

    // token 类型
	public static final String TOKEN_PREFIX = "Bearer ";

    // #################jwt START ##############################
    // secret
	public static final String TOKEN_SECRET = "hdys_jwt_secret";

	// iss
	public static final String ISS = "hdys";
    // #################jwt END ##############################

	public static final String APP_NAME = "hdys";

	// 角色的key
	public static final String ROLE_CLAIMS = "rol";

	// 过期时间 12 小时
	public static final long EXPIRATION_12 = 12*60*60;

    // 过期时间 24小时
    public static final long EXPIRATION_24 = 12*60*60*2;

    /**
     * 不需要认证的接口
     */
    public static final String antMatchers = "/index,/login/**,/favicon.ico,/actuator/**";
}
