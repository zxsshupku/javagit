public class Tree {
	private String sha;
	private String content;
	
	public Tree() {}
	
	public void addblob(Blob b, String name) throws Exception{
		//为了方便解析取消了换行符
		if(getContent() == null)
			setContent("blob " + b.getSha() + " " + name);
		else
			setContent(getContent() + " blob " + b.getSha() + " " + name);
		setSha(CalHash.struHash(getContent()));
	}
	
	public void addtree(Tree t, String name) throws Exception {
		if(getContent() == null)
			setContent("tree " + t.getSha() + " " + name);
		else
			setContent(getContent() + " tree " + t.getSha() + " " + name);
		setSha(CalHash.struHash(getContent()));
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
	
}
