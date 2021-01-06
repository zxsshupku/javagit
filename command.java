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
	
	//��Ŀ��·��ĳ�ļ����Ƶ��ݴ��� Ȼ����commitʱ�������ݴ����ļ���������
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
	
	//Ҫ���������key���ļ��ָ�·��
	public static void reset(String commitkey, String path) throws Exception{
		kvstorage.fileDelete(".\\javagit\\workspace");
		kvstorage.fileDelete(".\\javagit\\buffer");
		loadback.loadBack(commitkey, ".\\javagit\\workspace");
		File file = new File(".\\javagit\\workspace");
		kvstorage.fileCopy(file, ".\\javagit\\buffer");
		branch.commitChange(commitkey);
	}
	
	//�Ƚ�master���ļ��ع����ݴ������ٽ���һ��commit���ļ��ع����ݴ���������ͬ���ļ������ǣ��ٶ�master��֧����һ��commit
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
	


