package com.banking.system;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Service;

@Service
public class EncryptionDecryptionService {
	public PublicKey readPublicKeyFromMod(String VPA_RSA_MODULUS, String VPA_RSA_EXPONENT) {
        byte[] modulusBytes = Base64.getDecoder().decode(VPA_RSA_MODULUS);
        byte[] exponentBytes = Base64.getDecoder().decode(VPA_RSA_EXPONENT);
        BigInteger modulus = new BigInteger(1, modulusBytes);
        BigInteger publicExponent = new BigInteger(1, exponentBytes);
        PublicKey publicKey = null;
        RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus, publicExponent);
        KeyFactory factory;
        try {
            factory = KeyFactory.getInstance("RSA");
            publicKey = factory.generatePublic(rsaPubKey);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public PrivateKey readPrivateKeyFromMod(String VPA_RSA_MODULUS, String VPA_RSA_PRIVATE_KEY_D) {
        byte[] modulusBytes = Base64.getDecoder().decode(VPA_RSA_MODULUS);
        byte[] dBytes = Base64.getDecoder().decode(VPA_RSA_PRIVATE_KEY_D);
        BigInteger modulus = new BigInteger(1, modulusBytes);
        BigInteger d = new BigInteger(1, dBytes);
        PrivateKey privateKey = null;
        RSAPrivateKeySpec privateSpec = new RSAPrivateKeySpec(modulus, d);
        KeyFactory factory;
        try {
            factory = KeyFactory.getInstance("RSA");
            privateKey = factory.generatePrivate(privateSpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public byte[] encrypt(String data, PublicKey publicKey)
            throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException,
            NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }

    public String decrypt(byte[] data, PrivateKey privateKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }
}
