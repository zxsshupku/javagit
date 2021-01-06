import java.io.IOException;

public class branch {

	private String bname;
	private String content;
	
	public String getbname() {
		return bname;
	}
	
	public void setbname(String bname) {
		this.bname = bname;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public branch(String name, String commitkey) throws IOException {
		bname = name;
		content = commitkey;
		kvstorage.createBranch(name, content);
	}
	
	public static void createMaster(String commitkey) throws IOException {
		kvstorage.createBranch("master", commitkey);
	}
	
	public static void commitChange(String commitkey) throws Exception {
		String branchname = kvstorage.getBValue("head");
		kvstorage.createBranch(branchname, commitkey);
	}
}
