package org.open.job.common.crypto;

import java.security.MessageDigest;

/**
 * MD5消息摘要算法
 * 一种被广泛使用的密码散列函数，可以产生出一个128位（16字节）的散列值（hash value），用于确保信息传输完整一致。
 * MD5典型应用
 * 1）MD5的典型应用是对一段信息（Message）产生信息摘要（Message-Digest），以防止被篡改。
 * 2）MD5的典型应用是对一段Message(字节串)产生fingerprint(指纹），以防止被“篡改”。
 * 3）MD5还广泛用于操作系统的登陆认证上，如Unix、各类BSD系统登录密码、数字签名等诸多方面。
 *
 * 注意： 使用md5的方式对文件进行加密，以获取md5值，可以知道该文件的内容是否被修改过，
 * 因为修改过文件内容，再次对该文件进行加密的话，获取的md5值将发生变化。
 * @author: 李俊平
 * @Date: 2021-06-06 11:05
 */
public class MD5Util {

  public final static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

  private final static String KEY_ALGORITHM = "MD5";
  /**
   * md5加密
   * @param str 指定字符串
   * @return 加密后的字符串
   */
  public static String md5(String str) {
    str = (str == null ? "" : str);
    try {
      byte[] btInput = str.getBytes();
      MessageDigest mdInst = MessageDigest.getInstance(KEY_ALGORITHM);
      mdInst.update(btInput);
      byte[] md = mdInst.digest();
      int j = md.length;
      char[] strA = new char[j * 2];
      int k = 0;
      for (byte byte0 : md) {
        strA[k++] = hexDigits[byte0 >>> 4 & 0xf];
        strA[k++] = hexDigits[byte0 & 0xf];
      }
      return new String(strA);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * md5加盐加密: md5(md5(str) + md5(salt))
   * @param str 字符串
   * @param salt 盐，加盐的目的就是防止相同的字符串加密出来的结果相同，例如，多个人的密码可能相同为了防止加密结果相同所以加盐
   * @return 加密后的字符串
   */
  public static String md5BySalt(String str, String salt) {
    return md5(md5(str) + md5(salt));
  }
}
