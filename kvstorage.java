import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;


public class kvstorage {

	public static void createFile(String sha, String content) throws IOException {
		File file = new File(".\\object\\" + sha);
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		writer.write(content);
		writer.flush();
		writer.close();
	}
	
	//给定key值，返回对应的value
	public static String getValue(String key) throws Exception {
        File dir = new File(".\\object");
        File[] fs = dir.listFiles(); //将目录里的文件放入数组fs
        StringBuilder value = new StringBuilder();
        //按顺序遍历每个对象的名字是否匹配key
        if (fs.length == 0) 
        	return "指定路径内没有文件存在";
        for (File fl : fs) {
            if (fl.getName().equals(key)) {
                FileInputStream is = new FileInputStream(fl);
                // 用于读文件的缓存区
                byte[] buffer = new byte[1024];
                int numRead;
                do {
                    numRead = is.read(buffer);
                    if (numRead > 0) {
                        value.append(new String(buffer)); 
                    }
                } while (numRead != -1);
                is.close();
                return value.toString();
            }
        }
        return "未发现文件：" + key;
	}
	
	//分支和对象路径不一样 复制了一遍
	public static void createBranch(String name, String content) throws IOException {
		File file = new File(".\\branches\\" + name);
		file.createNewFile();
		FileWriter writer = new FileWriter(file, false); //false表示重写
		writer.write(content);
		writer.flush();
		writer.close();
	}
	
	public static String getBValue(String key) throws Exception {
        File dir = new File(".\\branches");
        File[] fs = dir.listFiles(); 
        StringBuilder value = new StringBuilder();
        if (fs.length == 0) 
        	return "指定路径内没有文件存在";
        for (File fl : fs) {
            if (fl.getName().equals(key)) {
                FileInputStream is = new FileInputStream(fl);
                byte[] buffer = new byte[1024];
                int numRead;
                do {
                    numRead = is.read(buffer);
                    if (numRead > 0) {
                        value.append(new String(buffer)); 
                    }
                } while (numRead != -1);
                is.close();
                return value.toString();
            }
        }
        return "未发现分支：" + key;
	}
}
