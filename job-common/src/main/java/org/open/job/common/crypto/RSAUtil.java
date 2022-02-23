package org.open.job.common.crypto;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


/**
 * RSA: 非对称加密算法
 * 可使用PKCS#1、PKCS#8格式的公私钥，对数据进行加密解密。
 * RSA算法广泛应用与加密与认证两个领域
 * 1.加密（保证数据安全性）
 *
 * 使用公钥加密，需使用私钥解密。
 *
 * 这种广泛应用在保证数据的安全性的方面，用户将自己的公钥广播出去，所有人给该用户发数据时使用该公钥加密，但是只有该用户可以使用自己的私钥解密，保证了数据的安全性。
 *
 * 2.认证（用于身份判断）
 *
 * 使用私钥签名，需使用公钥验证签名。
 *
 * 用户同样将自己的公钥广播出去，给别人发送数据时，使用私钥加密，在这里，我们更乐意称它为签名，然后别人用公钥验证签名，如果解密成功，则可以判断对方的身份。
 *
 * 注意下面方法中的加密解密方法
 * 如果是使用公钥加密则需要私钥进行解密
 * 如果是使用私钥加密则需要公钥进行解密
 * @author: 李俊平
 * @Date: 2021-03-03 22:34
 */
@Slf4j
public class RSAUtil {

  private static final String ALGORITHM = "RSA/ECB/PKCS1Padding";

  private static final String KEY_ALGORITHM = "RSA";

  /**
   * 生成密钥对
   * @param keySize the keySize. This is an algorithm-specific metric, such as modulus length, specified innumber of bits
   * @return 返回公钥和私钥对
   */
  public static RSAKey generateKeyPair(int keySize) {
    try {
      SecureRandom sr = new SecureRandom();
      KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);
      kpg.initialize(keySize, sr);
      KeyPair kp = kpg.generateKeyPair();
      Key publicKey = kp.getPublic();
      byte[] publicKeyBytes = publicKey.getEncoded();
      String pub = Base64.getEncoder().encodeToString(publicKeyBytes);
      Key privateKey = kp.getPrivate();
      byte[] privateKeyBytes = privateKey.getEncoded();
      String pri = Base64.getEncoder().encodeToString(privateKeyBytes);
      return new RSAKey(pub, pri);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * RSA公钥加密
   * @param source 内容
   * @param key 公钥(PublicKey) 或者 私钥(PrivateKey)
   * @return 加密后内容, 可以选择编码, 这里返回Base64编码后的加密数据
   */
  public static String enc(byte[] source, Key key) {
    try {
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, key);
      byte[] encryptedData = cipher.doFinal(source);
      return Base64Util.encodeBytesToString(encryptedData);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * RSA私钥解密
   * @param source 已加密内容
   * @param key 私钥(PrivateKey) 或者 公钥(PublicKey)
   * @return 解密后内容
   */
  public static String dec(byte[] source, Key key) {
    try {
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, key);
      byte[] result = cipher.doFinal(source);
      return new String(result, StandardCharsets.UTF_8);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 根据公钥字符串获取 公钥对象
   * @param key 公钥字符串
   * @return 公钥对象
   */
  public static PublicKey getPublicKey(String key) {
    try {
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
      KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
      return keyFactory.generatePublic(keySpec);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 根据私钥字符串获取 私钥对象
   * @param key 私钥字符串
   * @return 私钥对象
   */
  public static PrivateKey getPrivateKey(String key) {
    try {
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key));
      KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
      return keyFactory.generatePrivate(keySpec);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
