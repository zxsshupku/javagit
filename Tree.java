package javagit;

import java.util.ArrayList;
import javagit.hash;

public class Tree {
	private String sha;
	private ArrayList<String> fname;
	private ArrayList<String> fhash;
	
	public Tree() {}
	
	public void addblob(Blob b, String name){
		fname.add(name);
		fhash.add(b.getSha());
		String s = "";
		for(int i = 0; i < fname.size(); i++) {
			s += fname.get(i);
		}
		for(int i = 0; i < fhash.size(); i++) {
			s += fhash.get(i);
		}
		hash hs = new hash();
		setSha(hs.stringhash(s));
	}
	
	public void addtree(Tree t, String name) throws Exception {
		fname.add(name);
		fhash.add(t.getSha());
		String s = "";
		for(int i = 0; i < fname.size(); i++) {
			s += fname.get(i);
		}
		for(int i = 0; i < fhash.size(); i++) {
			s += fhash.get(i);
		}
		hash hs = new hash();
		setSha(hs.stringhash(s));
	}

	public String getSha() {
		return sha;
	}

	public void setSha(String sha) {
		this.sha = sha;
	}
	
}
