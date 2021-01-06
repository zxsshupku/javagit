import java.io.IOException;

public class head {

	public head() throws IOException{
		kvstorage.createBranch("head", "master");
	}
	
	public static void createHead(String bname) throws IOException {
		kvstorage.createBranch("head", bname);
	}
	
	public static void branchChange(String bname) throws Exception {
		String commitkey = kvstorage.getBValue(bname);
		loadback.loadBack(commitkey, ".\\workspace");
		createHead(bname);
	}
	
	public static String getCurrentCommit() throws Exception {
		String currentbranch = kvstorage.getBValue("head");
		String commitkey = kvstorage.getBValue(currentbranch);
		return commitkey;
	}
}
