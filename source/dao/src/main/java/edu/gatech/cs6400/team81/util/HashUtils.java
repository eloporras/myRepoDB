package edu.gatech.cs6400.team81.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
	private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String sha256(String input){
		return hash(input, "SHA-256");
	}
	
	public static String md5(String input){
		return hash(input, "MD5");
	}
	
	public static String hash(String input, String algorithm){
		String hash = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(input.getBytes());
			hash = byteArray2Hex(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hash;
	}


	public static String byteArray2Hex(byte[] bytes) {
	    StringBuffer sb = new StringBuffer(bytes.length * 2);
	    for(final byte b : bytes) {
	        sb.append(HEX[(b & 0xF0) >> 4]);
	        sb.append(HEX[b & 0x0F]);
	    }
	    return sb.toString();
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		System.out.println(md5("password123"));
		System.out.println(sha256("MyPassWord"));
	}
}
