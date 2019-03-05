package com.saturday.system.domain.info;

import org.springframework.util.Base64Utils;

public class RsaKeyInfo {
    private String publicKey;
    private byte[] privateKey;

    public RsaKeyInfo(byte[] publicKey, byte[] privateKey) {
        this.publicKey = Base64Utils.encodeToString(publicKey);
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(byte[] privateKey) {
        this.privateKey = privateKey;
    }
}
