package symmetric;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	
	public static void main(String[] args) throws Exception {
	    /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
	    String cKey = "1234567890123456";
	    // 需要加密的字串
	    String cSrc = "buxuewushu";
	    System.out.println(cSrc);
	    // 加密
	    String enString = encrypt(cSrc, cKey);
	    System.out.println("加密后的字串是：" + enString);

	    // 解密
	    String deString = decrypt(enString, cKey);
	    System.out.println("解密后的字串是：" + deString);
	}

	// 加密
	public static String encrypt(String sSrc, String sKey) throws Exception {
	    if (sKey == null) {
	        System.out.print("Key为空null");
	        return null;
	    }
	    // 判断Key是否为16位
	    if (sKey.length() != 16) {
	        System.out.print("Key长度不是16位");
	        return null;
	    }
	    byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "symmetric.AES");
	    Cipher cipher = Cipher.getInstance("symmetric.AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	    byte[] encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));

	    return Base64.getEncoder().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
	}

	// 解密
	public static String decrypt(String sSrc, String sKey) throws Exception {
	    try {
	        // 判断Key是否正确
	        if (sKey == null) {
	            System.out.print("Key为空null");
	            return null;
	        }
	        // 判断Key是否为16位
	        if (sKey.length() != 16) {
	            System.out.print("Key长度不是16位");
	            return null;
	        }
	        byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
	        SecretKeySpec skeySpec = new SecretKeySpec(raw, "symmetric.AES");
	        Cipher cipher = Cipher.getInstance("symmetric.AES/ECB/PKCS5Padding");
	        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
	        byte[] encrypted1 = Base64.getDecoder().decode(sSrc);//先用base64解密
	        try {
	            byte[] original = cipher.doFinal(encrypted1);
	            return new String(original, StandardCharsets.UTF_8);
	        } catch (Exception e) {
	            System.out.println(e.toString());
	            return null;
	        }
	    } catch (Exception ex) {
	        System.out.println(ex.toString());
	        return null;
	    }
	}
}
