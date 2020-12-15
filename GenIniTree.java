package javagit;

import java.io.File;

public class GenIniTree {
	public static void dfs(String path, Tree t) throws Exception {
		File file = new File(path);
		File[] files = file.listFiles();
		for(int i = 0; i < files.length; i++) {
			if(files[i].isFile()) {
				t.addblob(new Blob(files[i].getPath()), files[i].getName());
			}
			if(files[i].isDirectory()) {
				Tree t2 = new Tree();
				dfs(path + File.separator + files[i].getName(), t2);
				t.addtree(t2, files[i].getName());
			}
		}
 	}
	
	public static void genInitialTree(String path) throws Exception {
		Tree t = new Tree();
		dfs(path, t);
		t.genTreeFile();
	}
}
