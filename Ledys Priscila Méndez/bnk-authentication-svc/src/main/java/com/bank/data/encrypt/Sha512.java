package com.bank.data.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service("Sha512")
public class Sha512 implements IEncryptText {

	@Override
	public String execute(String texto) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-512");

	        md.update(texto.getBytes());

	        byte byteData[] = md.digest();

	        //convert the byte to hex format method 1
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
