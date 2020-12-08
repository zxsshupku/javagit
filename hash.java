package javagit;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

public class hash {
	public String filehash(String path) throws Exception{
		String sha1 = "";
		File file = new File(path);
		MessageDigest md = MessageDigest.getInstance(sha1);
		InputStream is = new BufferedInputStream(new FileInputStream(file));
		byte[] buffer = new byte[1024];
		int numRead = 0;
		do {
			numRead = is.read(buffer);
			if(numRead > 0) {
				md.update(buffer, 0, numRead);
			}
		}while(numRead != -1);
		is.close();
		byte[] sha = md.digest();
		for(int i = 0; i < sha.length; i++) {
			sha1 += Integer.toString(sha[i]&0xFF, 16);
		}
		return sha1;
	}

	public String stringhash(String input) throws Exception {
		byte[] data = input.getBytes();
		String sha1 = "";
		MessageDigest md = MessageDigest.getInstance(sha1);
		md.update(data);
		byte[] sha = md.digest();
		for(int i = 0; i < sha.length; i++) {
			sha1 += Integer.toString(sha[i]&0xFF, 16);
		}
		return sha1;
	}
}
