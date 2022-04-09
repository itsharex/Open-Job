package com.saucesubfresh.job.common.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 一、安全散列算法（缩写为SHA）是一个密码散列函数家族，是FIPS所认证的安全散列算法。能计算出一个数字消息所对应到的，长度固定的字符串（又称消息摘要）的算法。
 * 二、SHA家族的六个算法，分别是SHA-1、SHA-224、SHA-256、SHA-384，和SHA-512、SHA3，由美国国家安全局（NSA）所设计。
 * 三、SHA1目前已经证实安全性存在问题，可在2的63次方个计算复杂度内找到碰撞；但新的SHA2算法SHA-224、SHA-256、SHA-384，和SHA-512函数，并没有像SHA-1一样接受公众密码社群做详细的检验，所以它们的密码安全性还不被大家广泛的信。
 * @author: 李俊平
 * @Date: 2021-06-06 11:08
 */
public class SHAUtil {

  public final static String hexDigits = "0123456789abcdef";

  private final static String KEY_SHA1_ALGORITHM = "SHA1";

  private final static String KEY_SHA256_ALGORITHM = "SHA-256";

  /**
   * sha1加密
   *
   * @param str 指定字符串
   * @return 加密后的字符串
   */
  public static String sha1(String str) {
    try {
      str = (str == null ? "" : str);
      MessageDigest md = MessageDigest.getInstance(KEY_SHA1_ALGORITHM);
      byte[] b = str.getBytes();
      md.update(b);
      byte[] b2 = md.digest();
      int len = b2.length;
      char[] ch = hexDigits.toCharArray();
      char[] chs = new char[len * 2];
      for (int i = 0, k = 0; i < len; i++) {
        byte b3 = b2[i];
        chs[k++] = ch[b3 >>> 4 & 0xf];
        chs[k++] = ch[b3 & 0xf];
      }
      return new String(chs);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * sha256加密
   *
   * @param str 指定字符串
   * @return 加密后的字符串
   */
  public static String sha256(String str) {
    try {
      str = (str == null ? "" : str);
      MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA256_ALGORITHM);
      messageDigest.update(str.getBytes(StandardCharsets.UTF_8));

      byte[] bytes = messageDigest.digest();
      StringBuilder builder = new StringBuilder();
      String temp;
      for (byte aByte : bytes) {
        temp = Integer.toHexString(aByte & 0xFF);
        if (temp.length() == 1) {
          builder.append("0");
        }
        builder.append(temp);
      }
      return builder.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
