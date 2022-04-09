package com.saucesubfresh.job.common.crypto;

/**
 * 密钥对
 * private=私钥, public=公钥
 */
public class RSAKey {

    private String publicKey;

    private String privateKey;

    public RSAKey(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }
}
