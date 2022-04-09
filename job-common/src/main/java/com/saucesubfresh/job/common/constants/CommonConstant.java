package com.saucesubfresh.job.common.constants;

/**
 * @author 李俊平
 */
public interface CommonConstant {
  /**
   * 认证请求头
   */
  String AUTHENTICATION_HEADER = "Authorization";

  /**
   * Basic Token 前缀
   */
  String AUTHENTICATION_TYPE = "Bearer ";

  /**
   * 用户信息Http请求头
   */
  String USER_TOKEN_HEADER = "user";

  /**
   * 统一用户 id
   */
  String USER_ID = "id";

  /**
   * ip:port pattern
   */
  String ADDRESS_PATTERN = "%s:%d";


  public static final class Symbol {
    private Symbol() {
    }

    /**
     * The constant COMMA.
     */
    public static final String COMMA = ",";
    public static final String SPOT = ".";
    /**
     * The constant UNDER_LINE.
     */
    public final static String UNDER_LINE = "_";
    /**
     * The constant PER_CENT.
     */
    public final static String PER_CENT = "%";
    /**
     * The constant AT.
     */
    public final static String AT = "@";
    /**
     * The constant PIPE.
     */
    public final static String PIPE = "||";
    public final static String SHORT_LINE = "-";
    public final static String SPACE = " ";
    public static final String SLASH = "/";
    public static final String MH = ":";

  }


}
