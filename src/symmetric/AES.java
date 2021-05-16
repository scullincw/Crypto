package symmetric;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	
	public static void main(String[] args) throws Exception {
	    /*
         * �˴�ʹ��AES-128-ECB����ģʽ��key��ҪΪ16λ��
         */
	    String cKey = "1234567890123456";
	    // ��Ҫ���ܵ��ִ�
	    String cSrc = "buxuewushu";
	    System.out.println(cSrc);
	    // ����
	    String enString = encrypt(cSrc, cKey);
	    System.out.println("���ܺ���ִ��ǣ�" + enString);

	    // ����
	    String deString = decrypt(enString, cKey);
	    System.out.println("���ܺ���ִ��ǣ�" + deString);
	}

	// ����
	public static String encrypt(String sSrc, String sKey) throws Exception {
	    if (sKey == null) {
	        System.out.print("KeyΪ��null");
	        return null;
	    }
	    // �ж�Key�Ƿ�Ϊ16λ
	    if (sKey.length() != 16) {
	        System.out.print("Key���Ȳ���16λ");
	        return null;
	    }
	    byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "symmetric.AES");
	    Cipher cipher = Cipher.getInstance("symmetric.AES/ECB/PKCS5Padding");//"�㷨/ģʽ/���뷽ʽ"
	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	    byte[] encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));

	    return Base64.getEncoder().encodeToString(encrypted);//�˴�ʹ��BASE64��ת�빦�ܣ�ͬʱ����2�μ��ܵ����á�
	}

	// ����
	public static String decrypt(String sSrc, String sKey) throws Exception {
	    try {
	        // �ж�Key�Ƿ���ȷ
	        if (sKey == null) {
	            System.out.print("KeyΪ��null");
	            return null;
	        }
	        // �ж�Key�Ƿ�Ϊ16λ
	        if (sKey.length() != 16) {
	            System.out.print("Key���Ȳ���16λ");
	            return null;
	        }
	        byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
	        SecretKeySpec skeySpec = new SecretKeySpec(raw, "symmetric.AES");
	        Cipher cipher = Cipher.getInstance("symmetric.AES/ECB/PKCS5Padding");
	        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
	        byte[] encrypted1 = Base64.getDecoder().decode(sSrc);//����base64����
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
