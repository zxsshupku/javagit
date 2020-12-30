
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class CalHash {
	
	 public static byte[] SHA1Checksum(String content) throws Exception {
        MessageDigest complete = MessageDigest.getInstance("SHA-1");
        complete.update(content.getBytes());
        return complete.digest();
	 }
	 
	 public static byte[] SHA1Checksum(InputStream is) throws Exception {
		 byte[] buffer = new byte[1024];
	        MessageDigest complete = MessageDigest.getInstance("SHA-1");
	        int numRead = 0;
	        do {
	            numRead = is.read(buffer);
	            if(numRead > 0){
	                complete.update(buffer, 0, numRead);
	            }
	        }while (numRead != -1);
	        is.close();
	        return complete.digest();
	 }
	 
	 //计算单个Blob对象哈希值的静态方法
	 public static String blobHash(String filepath) throws Exception {
		File file = new File(filepath);
		FileInputStream is = new FileInputStream(file);
	    byte[] sha1 = SHA1Checksum(is);
	    String result = "";
	    for (int i = 0; i < sha1.length; i++) {
	        String append = Integer.toString(sha1[i]&0xFF, 16);
	        if (append.length() < 2) {
	    	result = result + "0" + append;
	        } else {
	        	result += append;
	        }
	    }
	    return result;
	 }
	 
	 //计算tree和commit哈希值的静态方法
	 public static String struHash(String content) throws Exception {
		byte[] sha1 = SHA1Checksum(content);
		String result = "";
		    for (int i = 0; i < sha1.length; i++) {
		        String append = Integer.toString(sha1[i]&0xFF, 16);
		        if (append.length() < 2) {
		    	result = result + "0" + append;
		        } else {
		        	result += append;
		        }
		    }
	    return result;
	 }
}
