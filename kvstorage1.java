package javagit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;


public class kvstorage1 {
	/*
	 * 最简单的key-value存储方式
	Key作为文件名，文件内容作为value
	给定value：“hello world”
	Hash(“hello world”) == 34234234
	创建文件 objects/34234234 --> hello world
	给定34234234（哈希值），要找到value的值
	支持以下功能
	给定value，向存储中添加对应的key-value
	给定key，查找得到对应的value值√

	封装成class对外提供接口
	单元测试

	 */
	
	private String key;
	private String content;
	private String path;
	
	//新建文件，value为根据哈希值得到的字符串，key为哈希值
	public void getkey(String sha1) throws Exception {
		this.key=CalHash.blobHash(sha1);
	}
	
	//
	public void createFile(String key) throws Exception {
		File file = new File(".\\object\\"+"key");
		file.createNewFile();
		FileWriter newFile = new FileWriter(file);
		newFile.write(getValue(key));	
	}
	
	//给定key值，返回对应的value
	public String getValue(String key) throws Exception {
        File dir = new File(path);
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
	
}
