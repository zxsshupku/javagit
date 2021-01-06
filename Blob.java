
import java.io.FileReader;
import java.io.BufferedReader;

public class Blob {
	private String sha;
	private String content;
	private String filepath;
		
	public Blob(String filepath) throws Exception {
		this.filepath = filepath;
		this.sha = CalHash.blobHash(filepath);
		this.content = setContent(filepath);
		kvstorage.createFile(sha, content);
	}
	
	public String getPath() {
		return filepath;
	}

	public String getSha() {
		return sha;
	}
	
	public String setContent(String filepath) throws Exception {
		FileReader fr = new FileReader(filepath);
		BufferedReader br = new BufferedReader(fr);
		StringBuffer buffer = new StringBuffer();
		String tmp = null;
		while ((tmp = br.readLine()) != null) {
			buffer.append(tmp.trim());
		}
		br.close();
		return buffer.toString();
	}

	
	public String getContent() {
		return content;
	}
}