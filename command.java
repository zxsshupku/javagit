import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class command {

	public static void getCommand(String command) throws Exception {
		String[] split = command.split(" ");
		if(split[0].equals("git") && split[1].equals("add")) {
			gitAdd(split[2]);
		}
		
		else if(split[0].equals("git") && split[1].equals("init")) {
			gitInit();
		}
		
		else if(split[0].equals("git") && split[1].equals("log")) {
			gitLog();
		}
		
		else if(split[0].equals("git") && split[1].equals("diff")) {
			Diff.diff(".\\javagit\\workspace\\" + split[2], ".\\javagit\\buffer\\" + split[2]);
		}
		
		else if(split[0].equals("reset")) {
			reset(split[1], split[2]);
		}
		
		else if(split[0].equals("commit")) {
			commit();
		}
		
		else if(split[0].equals("git") && split[1].equals("diff")) {
			gitdiff(split[2]);
		}
		
		else if(split[0].equals("checkout")) {
			head.branchChange(split[1]);
		}
		
		else if(split[0] == "branch") {
			new branch(split[1], head.getCurrentCommit());
		}
		
		else if(split[0] == "merge") {
			merge();
		}
		
		else 
			System.out.print("input error");
			
	}
	
	private static void gitdiff(String string) {
		// TODO Auto-generated method stub
		
	}

	public static void gitInit() throws Exception {
		Tree t = GenIniTree.genInitialTree(".\\javagit\\workspace");
		Commit c = new Commit(t);
		String commitkey = c.getSha();
		branch.createMaster(commitkey);
	}
	
	public static void gitLog() throws Exception {
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
	public static void gitAdd(String name) throws Exception {
		File file = new File(".\\javagit\\workspace"+ "\\" + name);
		kvstorage.fileCopy(file, ".\\javagit\\buffer");
	}
	
	public static void commit() throws Exception{
		Tree t = GenIniTree.genInitialTree(".\\javagit\\buffer");
		kvstorage.fileDelete(".\\javagit\\buffer");
		String precommit = head.getCurrentCommit();
		Commit c = new Commit(t, precommit);
		branch.commitChange(c.getSha());
	}
	
	//要求参数输入key和文件恢复路径
	public static void reset(String commitkey, String path) throws Exception{
		kvstorage.fileDelete(".\\javagit\\workspace");
		kvstorage.fileDelete(".\\javagit\\buffer");
		loadback.loadBack(commitkey, ".\\javagit\\workspace");
		File file = new File(".\\javagit\\workspace");
		kvstorage.fileCopy(file, ".\\javagit\\buffer");
		branch.commitChange(commitkey);
	}
	
	//先将master的文件回滚到暂存区，再将上一次commit的文件回滚到暂存区，其中同名文件将覆盖，再对master分支进行一次commit
	public static void merge() throws Exception {
		String mastercommit = kvstorage.getBValue("master");
		String masterkey = kvstorage.getBValue(mastercommit);
		String headkey = head.getCurrentCommit();
		loadback.loadBack(masterkey, ".\\javagit\\buffer");
		loadback.loadBack(headkey, ".\\javagit\\buffer");
		head.branchChange("master");
		commit();
	}
}
	


