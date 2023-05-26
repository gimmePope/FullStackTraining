package com.pocosoft.demo.util;

import java.security.MessageDigest;
import java.util.Random;

public class OTPUtil {

	
	public static String generateOTP()
	{
		Random rand = new Random();
		String otp = "";
		
		for(int i =0; i < 6; i++)
			otp = otp + rand.nextInt(10);
		
		return otp;
		
	}
	
	
	public static String hash(String plainText, String algorithm) {
	    StringBuilder hexString = new StringBuilder();
	    if (algorithm.equals("SHA512")) {
	        algorithm = "SHA-512";
	    }
	    try {
	        MessageDigest md = MessageDigest.getInstance(algorithm);
	        md.update(plainText.getBytes());

	        byte byteData[] = md.digest();

	        //convert the byte to hex format method 1
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < byteData.length; i++) {
	            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }

	        //System.out.println("Hex format : " + sb.toString());

	        //convert the byte to hex format method 2
	        for (int i = 0; i < byteData.length; i++) {
	            String hex = Integer.toHexString(0xff & byteData[i]);
	            if (hex.length() == 1) {
	                hexString.append('0');
	            }
	            hexString.append(hex);
	        }

	    } catch (Exception ex) {
	        System.out.println(ex.getMessage());
	    }
	    return hexString.toString();
	}
	
	
}
