import java.io.IOException;

public class head {

	public void createHead(String bname) throws IOException {
		kvstorage.createBranch("head", bname);
	}
	
	public void branchChange(String bname, String path) throws Exception {
		String commitkey = kvstorage.getBValue(bname);
		loadback.loadBack(commitkey, path);
		createHead(bname);
	}
	
	public static String getCurrentCommit() throws Exception {
		String currentbranch = kvstorage.getBValue("head");
		String commitkey = kvstorage.getBValue(currentbranch);
		return commitkey;
	}
}
