package javagit;

import java.io.File;
import java.security.MessageDigest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;

public class Blob {

	//定义blob属性
	private String sha;
	private byte[] content;
	private String filepath;
		
	//浠ユ枃浠惰矾寰勪綔涓哄弬鏁扮殑blob鏋勯�犲嚱鏁�
	public Blob(String filepath) throws Exception {
		this.filepath = filepath;
		this.sha = CalHash.blobHash(filepath);
		this.content = setContent(filepath);
	}
	
	//灏佽
	public String getPath() {
		return filepath;
	}

	public String getSha() {
		return sha;
	}
	
	//璇诲彇鏂囦欢鍐呭
	public byte[] setContent(String filepath) throws IOException {
		File file = new File(filepath);
		
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			return null;
		}
		
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[(int)fileSize];
		int offset = 0, numread = 0;
		
		while (offset < buffer.length && (numread = fis.read(buffer, offset, buffer.length - offset)) >= 0) {
			offset += numread;
        }
		
		if (offset != buffer.length) {
			fis.close();
			throw new IOException("Could not completely read file " + file.getName());
	    }
		
		fis.close();
		return buffer;
	}
	
	public byte[] getContent() {
		return content;
	}
}