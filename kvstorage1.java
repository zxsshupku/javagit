package javagit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;


public class kvstorage1 {
	/*
	 * ��򵥵�key-value�洢��ʽ
	Key��Ϊ�ļ������ļ�������Ϊvalue
	����value����hello world��
	Hash(��hello world��) == 34234234
	�����ļ� objects/34234234 --> hello world
	����34234234����ϣֵ����Ҫ�ҵ�value��ֵ
	֧�����¹���
	����value����洢����Ӷ�Ӧ��key-value
	����key�����ҵõ���Ӧ��valueֵ��

	��װ��class�����ṩ�ӿ�
	��Ԫ����

	 */
	
	private String key;
	private String content;
	private String path;
	
	//�½��ļ���valueΪ���ݹ�ϣֵ�õ����ַ�����keyΪ��ϣֵ
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
	
	//����keyֵ�����ض�Ӧ��value
	public String getValue(String key) throws Exception {
        File dir = new File(path);
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
	
}
