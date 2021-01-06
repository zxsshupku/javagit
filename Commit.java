public class Commit {
	private String sha;
	private String rootsha;
	private String content;
	
	public Commit(Tree root) throws Exception {
		setRootsha(root.getSha());
		setContent("tree " + root.getSha());
		setSha(CalHash.struHash(getContent()));
		kvstorage.createFile(sha, content);
	}
	
	public Commit(Tree root, String precommitkey) throws Exception {
		if(root.getSha() != precommitkey) {
		//为了方便解析取消了换行符
		setContent("tree " + root.getSha() + " parent " + precommitkey);
		setSha(CalHash.struHash(getContent()));
		kvstorage.createFile(sha, content);
		}
	}
	
	public String getSha() {
		return sha;
	}
	
	public void setSha(String sha) {
		this.sha = sha;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getRootsha() {
		return rootsha;
	}

	public void setRootsha(String rootsha) {
		this.rootsha = rootsha;
	}
}