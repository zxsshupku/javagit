import java.io.File;
import java.io.FileWriter;

public class loadback {
	
	public static void loadBack(String commitkey, String path) throws Exception {
		String commitvalue = kvstorage.getValue(commitkey);
		String [] split = commitvalue.split(" ");
		String rootkey = split[1];
		recover(rootkey, path);
	}
	
	public static void recover(String rootkey, String path) throws Exception {
		String content = kvstorage.getValue(rootkey);
		String [] split = content.split(" ");
		int i = 0;
		//数组中每三个构成一组 分别是type，key，name
		while(i < split.length) {
			if(split[i] == "tree") {
				recoverFileFolder(split[i+2], path);
				recover(split[i+1], path + File.separator + split[i+2]);
				i = i + 3;
				continue;
			}
			if(split[i] == "blob") {
				recoverFile(split[i+1], split[i+2], path);
				i = i + 3;
				continue;
			}
		}
	}
	
	public static void recoverFile(String key, String name, String path) throws Exception {
		String content = kvstorage.getValue(key);
		File file = new File(path, name);
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		writer.write(content);
		writer.flush();
		writer.close();
	}
	
	public static void recoverFileFolder(String name, String path) {
		File dir = new File(path, name);
		dir.mkdir();
	}
	
}
