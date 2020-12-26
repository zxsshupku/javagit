package javagit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Commit {
	private String sha;
	private String rootsha;
	private String content;
	
	public Commit(Tree root) throws Exception {
		setRootsha(root.getSha());
		setContent("tree" + root.getSha());
		CalHash hs = new CalHash();
		setSha(hs.blobHash(getContent()));
		genCommitFile();
	}
	
	public Commit(Tree root, Commit precommit) throws Exception {
		if(root.getSha() != precommit.getRootsha()) {
		setContent("tree" + root.getSha() + "/n");
		setContent(getContent() + "parent" + precommit.getSha());
		CalHash hs = new CalHash();
		setSha(hs.blobHash(getContent()));
		genCommitFile();
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

	public void genCommitFile() throws Exception {
		File f = new File(".\\object\\");
		Tree t = new Tree();
		Commit cmt = new Commit(t);
		f.createNewFile();
		FileWriter newFile = new FileWriter(f);
		newFile.write(t.getContent());
	}
	
}