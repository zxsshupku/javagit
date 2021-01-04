import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class command {

	public void getCommand(String command) throws Exception {
		String[] split = command.split(" ");
		if(split[0] == "git" && split[1] == "add") {
			gitAdd(split[2]);
		}
		
		else if(split[0] == "git" && split[1] == "log") {
			gitLog();
		}
		
		
		else if(split[0] == "reset") {
			reset(split[1], split[2]);
		}
		
		else if(split[0] == "commit") {
			commit();
		}
		else 
			System.out.print("input error");
			
	}
	
	public void gitLog() throws Exception {
		boolean flag = true;
		String commitkey = head.getCurrentCommit();
		System.out.println(commitkey);
		while(flag) {
			String commitvalue = kvstorage.getValue(commitkey);
			String[] split = commitvalue.split(" ");
			if(3 < split.length) {
				commitkey = split[3];
				System.out.println(commitkey);
			}
			else
				flag = false;
		}
	}
	
	//将目标路径某文件复制到暂存区 然后在commit时对整个暂存区文件夹生成树
	public void gitAdd(String path) throws Exception {
		File file = new File(path);
		fileCopy(file, ".\\buffer");
	}
	
	public void commit() throws Exception{
		Tree t = GenIniTree.genInitialTree(".\\buffer");
		String precommit = head.getCurrentCommit();
		Commit c = new Commit(t, precommit);
		branch.commitChange(c.getSha());
	}
	
	//要求参数输入key和文件恢复路径
	public void reset(String commitkey, String path) throws Exception {
		loadback.loadBack(commitkey, path);
	}
	

	public void fileCopy(File file, String path) throws IOException {
		File bufferdir = new File(path);
		if(!bufferdir.exists()) {
			bufferdir.mkdir();
		}
		//若复制文件夹则开始DFS
		if(file.isDirectory()) {
			File dir = new File(path + "\\" + file.getName());
			dir.mkdir();
			File[] files = dir.listFiles();
			if(files.length == 0) {
				return;
			}
			else {
				for(int i = 0; i < files.length; i++) {
					fileCopy(files[i], path + "\\"  + file.getName());
				}
			}
		}
		
		else if(file.isFile()) {
			FileInputStream is = new FileInputStream(file);
			File dfile = new File(path);
			dfile.createNewFile();
			
			FileOutputStream os = new FileOutputStream(dfile);
			byte[] b = new byte[1024];
			int len;
			while((len = is.read(b)) != -1) {
				os.write(b, 0, len);
			}
			is.close();
			os.close();
		}
	}
}
