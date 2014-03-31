package com.jb.pmd.blaster;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptionEngine
{
    private static char[] key = null;
    private static final byte[] salt = {
        (byte) 0x45, (byte) 0xab, (byte) 0xec, (byte) 0x16,
        (byte) 0x54, (byte) 0xba, (byte) 0xce, (byte) 0x61,
    };    
    
    private static void initEncryption() throws NoSuchAlgorithmException, 
            UnsupportedEncodingException
    {
        String k = "";
        
        byte[] byteHash = MessageDigest.getInstance("MD5").digest(
                (System.getProperty("os.name") + System.getProperty("os.arch") 
                + System.getProperty("os.version")
                + System.getProperty("user.name")).getBytes("UTF-8"));
        
        for (byte b : byteHash)
            k += Byte.toString(b);
        
        key = k.toCharArray();
    }
                
    public static String encrypt(String value)
    {
        String ret = null;
        
        try
        {
            if(key == null) initEncryption();
        
            SecretKeyFactory keyFactory 
                    = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey k = keyFactory.generateSecret(new PBEKeySpec(key));
            Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
            cipher.init(Cipher.ENCRYPT_MODE, k, 
                    new PBEParameterSpec(salt, 20));
            
            ret = new BASE64Encoder().encode(
                    cipher.doFinal(value.getBytes("UTF-8")));
        }
        catch(NoSuchAlgorithmException | InvalidKeySpecException 
                | NoSuchPaddingException | InvalidKeyException 
                | InvalidAlgorithmParameterException 
                | UnsupportedEncodingException | IllegalBlockSizeException 
                | BadPaddingException e)
        {
            System.out.println("[Error] Failed to encrypt value.");
        }
        
        return ret;
    }

    public static String decrypt(String value)
    {
        String ret = null;
    
        try
        {
            if(key == null) initEncryption();

            SecretKeyFactory keyFactory 
                    = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey k = keyFactory.generateSecret(
                    new PBEKeySpec(key));
            Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
            cipher.init(Cipher.DECRYPT_MODE, k, 
                    new PBEParameterSpec(salt, 20));
            
            ret = new String(cipher.doFinal(
                    new BASE64Decoder().decodeBuffer(value)), "UTF-8");
        }
        catch(NoSuchAlgorithmException | InvalidKeySpecException 
                | NoSuchPaddingException | InvalidKeyException 
                | InvalidAlgorithmParameterException 
                | IllegalBlockSizeException | BadPaddingException 
                | IOException e)
        {
            System.out.println("[Error] Failed to decrypt value.");
        }
        
        return ret;
    }
}