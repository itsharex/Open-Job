package org.open.job.common.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

/**
 * 对称加密 AES
 *
 * 高级加密标准（英语：Advanced Encryption Standard，缩写：AES），在密码学中又称Rijndael加密法，是美国联邦政府采用的一种区块加密标准。
 * 这个标准用来替代原先的DES，已经被多方分析且广为全世界所使用。经过五年的甄选流程，高级加密标准由美国国家标准与技术研究院（NIST）于2001年11月26日发布于FIPS PUB 197，并在2002年5月26日成为有效的标准。
 * 2006年，高级加密标准已然成为对称密钥加密中最流行的算法之一。
 *
 * AES加密原理
 * 采用对称分组密码体制，密钥的长度最少支持为128、192、256位；加密分组长度128位，如果数据块及密钥长度不足时，会补齐进行加密。
 *
 * @author 李俊平
 * @date 2020-06-24 18:27
 */
public class AESUtil {

  private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);


  private static final String ALGORITHM = "AES";

  // 默认密码算法
  private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

  /**
   * 生成指定长度的 key
   * @param keySize 用来指定 key 的长度
   * @return
   */
  public static byte[] createKey(int keySize) {
    try {
      KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
      keyGenerator.init(keySize);
      return keyGenerator.generateKey().getEncoded();
    } catch (NoSuchAlgorithmException e) {
      logger.error("AES密钥/向量生成异常_" + e.getMessage(), e);
      return null;
    }
  }

  /**
   * AES 加密
   * @param source 需要加密的字符串，要加密的明文
   * @param key 密钥，AES固定格式为128/256 bits.即：16/32bytes，即加密内容应该为 16字节的倍数。DES固定格式为128bits，即8bytes。
   * @param iv 偏移量，AES 为 16bytes. DES 为 8bytes
   * @return 返回加密后的字节数组，可以选择编码。主要编解码方式有Base64, HEX, UUE,7bit等等。此处看服务器需要什么编码方式
   * 所以也可以这样返回：return new BASE64Encoder().encode(encryptedData); 返回Base64转码后的加密数据
   */
  public static String enc(byte[] source, byte[] key, byte[] iv) {
    try {
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, ALGORITHM), new IvParameterSpec(iv));
      byte[] encryptedData = cipher.doFinal(source);
      return Base64Util.encodeBytesToString(encryptedData);
    } catch (Exception e) {
      logger.error("AES加密异常_" + e.getMessage(), e);
      return null;
    }
  }

  /**
   * AES解密
   * @param source 已加密的密文
   * @param key 密钥，AES固定格式为128/256 bits.即：16/32bytes，即加密内容应该为 16字节的倍数。DES固定格式为128bits，即8bytes。
   * @param iv iv 偏移量，AES 为 16bytes. DES 为 8bytes
   * @return 返回解密后的明文
   */
  public static String dec(byte[] source, byte[] key, byte[] iv) {
    try {
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, ALGORITHM), new IvParameterSpec(iv));
      byte[] result = cipher.doFinal(source);
      return new String(result, StandardCharsets.UTF_8);
    } catch (Exception e) {
      logger.error("AES解密异常_" + e.getMessage(), e);
      return null;
    }
  }
}
