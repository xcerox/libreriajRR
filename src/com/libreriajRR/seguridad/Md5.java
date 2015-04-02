
package com.libreriajRR.seguridad;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Md5 {

    public Md5() {
    }

    private static String md5(final String input) throws NoSuchAlgorithmException{
        final MessageDigest md = MessageDigest.getInstance("MD5");
        final byte[] messageDigest = md.digest(input.getBytes());
        final BigInteger number = new BigInteger(1, messageDigest);
        return String.format("%032x", number);
    }
    
    private Cipher initCipher(final int mode,final String initialVectorString, final String secretKey) throws NoSuchAlgorithmException,NoSuchPaddingException,InvalidAlgorithmParameterException, InvalidKeyException{
        final SecretKeySpec skeySpec = new SecretKeySpec(md5(secretKey).getBytes(),"AES");
        final IvParameterSpec initialVector = new IvParameterSpec(initialVectorString.getBytes());
        final Cipher cipher = Cipher.getInstance("AES/CFBB/NoPadding");
        cipher.init(mode, skeySpec, initialVector);
        return cipher;
    }
    
    public String encrypt(final String dataToEncrypt, final String initialVector, final  String secretKey){
        String encryptedData = null;
        try {
            final Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, initialVector, secretKey);
            final byte[] encryptedByteArray = cipher.doFinal(dataToEncrypt.getBytes());
            encryptedData = (new BASE64Encoder()).encode(encryptedByteArray);
        } catch (Exception e) {
            System.err.println("Problema encriptando la data");
            e.printStackTrace();
        }
        return encryptedData;
    }
    
    public String decrypt(final String encryptedData, final String initialVector, final String secrectKey){
        String decriptedData = null;
        try {
            final Cipher cipher = initCipher(Cipher.DECRYPT_MODE, initialVector, secrectKey);
            final byte[] encryptedByteArray = (new BASE64Decoder()).decodeBuffer(encryptedData);
            final byte[] decriptedByteArray = cipher.doFinal(encryptedByteArray);
            decriptedData = new String(decriptedByteArray, "UTF8");
        } catch (Exception e) {
            System.err.println("Problema desencriptando la data");
            e.printStackTrace();
        }
        return decriptedData;
    } 
    
    
 
}




