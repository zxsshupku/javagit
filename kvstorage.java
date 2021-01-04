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
	
	//����keyֵ�����ض�Ӧ��value
	public static String getValue(String key) throws Exception {
        File dir = new File(".\\object");
        File[] fs = dir.listFiles(); //��Ŀ¼����ļ���������fs
        StringBuilder value = new StringBuilder();
        //��˳�����ÿ������������Ƿ�ƥ��key
        if (fs.length == 0) 
        	return "ָ��·����û���ļ�����";
        for (File fl : fs) {
            if (fl.getName().equals(key)) {
                FileInputStream is = new FileInputStream(fl);
                // ���ڶ��ļ��Ļ�����
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
        return "δ�����ļ���" + key;
	}
	
	//��֧�Ͷ���·����һ�� ������һ��
	public static void createBranch(String name, String content) throws IOException {
		File file = new File(".\\branches\\" + name);
		file.createNewFile();
		FileWriter writer = new FileWriter(file, false); //false��ʾ��д
		writer.write(content);
		writer.flush();
		writer.close();
	}
	
	public static String getBValue(String key) throws Exception {
        File dir = new File(".\\branches");
        File[] fs = dir.listFiles(); 
        StringBuilder value = new StringBuilder();
        if (fs.length == 0) 
        	return "ָ��·����û���ļ�����";
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
        return "δ���ַ�֧��" + key;
	}
}
