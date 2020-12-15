package javagit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javagit.CalHash;

public class Tree {
	private String sha;
	private String content;
	
	public Tree() {}
	
	public void addblob(Blob b, String name) throws Exception{
		setContent(getContent() + "blob" + b.getSha() + name + "/n");
		setSha(CalHash.struHash(getContent()));
	}
	
	public void addtree(Tree t, String name) throws Exception {
		setContent(getContent() + "tree" + t.getSha() + name + "/n");
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
	
	public void genTreeFile() throws Exception {
		File f = new File(".\\object\\");
		Tree t = new Tree();
		f.createNewFile();
		FileWriter newFile = new FileWriter(f);
		newFile.write(t.getContent());
	}
}
