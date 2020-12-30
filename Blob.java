import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Blob {

	private String sha;
	private byte[] content;
	private String filepath;
		
	public Blob(String filepath) throws Exception {
		this.filepath = filepath;
		this.sha = CalHash.blobHash(filepath);
		this.content = setContent(filepath);
	}
	
	public String getPath() {
		return filepath;
	}

	public String getSha() {
		return sha;
	}
	
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