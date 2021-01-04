package javagit;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public final class Diff {
	
	             /* test */
	 /* 
	  * public static void main(String[] args) {
	  *
	  *		diff("D:\\old.txt", "D:\\new.txt");
	  * }
	  */
		
	
	//读取文件内容
	public static String[] readFile(String name) throws Exception {
		FileReader fr = new FileReader(name);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> fileTmp = new ArrayList<>();
		
		String tmp = null;
		while ((tmp = br.readLine()) != null) {
			fileTmp.add(tmp.trim());
		}
		br.close();
		
		String[] File = (String[]) fileTmp.toArray(new String[0]);
		return File;
	}
	
	//比对文件内容并将结果输出到控制台
	public static void diff(String name1, String name2) {
		try {
			String[] oldFile = readFile(name1);
			String[] newFile = readFile(name2);
			MatchPair[] mp = LCS(oldFile, newFile);
			
			String[] recLCS = new String[mp.length];
			
			//给两个数组位于LCS位置的内容赋标记值，并记录其原有内容
			for (int i = 0; i < mp.length; i++) {
				recLCS[i] = oldFile[mp[i].x];
				oldFile[mp[i].x] = "F//G";
				newFile[mp[i].y] = "F//G";
			}
			
			
			/* 基本思路是将两个数组中属于LCS的内容做特定标记，剩余的内容如果属于旧数组则带-输出
			 * 内容，属于新数组则带+输出，如果碰到标记内容，则只输出一次，进行新旧数组交替输出
			 */
			int index = 0;
			int indexL = 0;
			while (index < oldFile.length || index < newFile.length) {
				if (index < oldFile.length && index < newFile.length) {
					//如果新旧数组当前下标对应内容都不是标记内容，则全部输出
					if (newFile[index] != "F//G" && oldFile[index] != "F//G") {
						System.out.println("+ " + newFile[index]);
						System.out.println("- " + oldFile[index]);
						index++;
					//如果新数组当前下标对应内容是标记内容，旧数组不是，则输出LCS的内容和旧数组内容
					} else if (newFile[index] == "F//G" && oldFile[index] != "F//G") {
						System.out.println("  " + recLCS[indexL]);
						System.out.println("- " + oldFile[index]);
						indexL++;
						index++;
					//如果旧数组当前下标对应内容是标记内容，新数组不是，则只输出新数组内容
					} else if (newFile[index] != "F//G" && oldFile[index] == "F//G") {
						System.out.println("+ " + newFile[index]);
						index++;
					//如果都是标记内容则只输出recLCS内容
					} else {
						System.out.println("  " + recLCS[indexL]);
						index++;
						indexL++;
					}
				//如果旧数组内容已全部输出	
				} else if (index >= oldFile.length) {
					if (newFile[index] != "F//G") {
						System.out.println("+ " + newFile[index]);
						index++;
					} else {
						System.out.println("  " + recLCS[indexL]);
						index++;
						indexL++;
					}
				//如果新数组内容已全部输出
				} else {
					if (oldFile[index] != "F//G") {
						System.out.println("- " + oldFile[index]);
						index++;
					} else {
						System.out.println("  " + recLCS[indexL]);
						index++;
						indexL++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static class MatchPair {
		public int x;
		public int y;
		public int len;
		
		public MatchPair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	//输出LCS的方法
	public static MatchPair[] LCS(String[] txt1, String[] txt2) {
		ArrayList list = new ArrayList();
		
		int M = txt1.length;
		int N = txt2.length;
		int MAX = M + N;
		
		int[] cur = null;
		int[] pre = new int[1];
		
		pre[0] = 0;
		
		int x, y;
		for (int D = 0; D <= MAX; ++D) {
			list.add(pre);
			cur = new int[D+1];
			
			for (int k = -1 * D; k <= D; k += 2) {
				if (k == -1 * D	|| k != D && pre[(k - 1 + D - 1) >> 1] < pre[(k + 1 + D - 1) >> 1]) {
					x = pre[(k + 1 + D - 1) >> 1];
				} else {
					x = pre[(k - 1 + D - 1) >> 1] + 1;
				}
				y = x - k;
				while (x < txt1.length && y < txt2.length && txt1[x].equals(txt2[y])) {
					++x;
					++y;
				}
				cur[(k + D) >> 1] = x;
				
				if (x == txt1.length && y == txt2.length) {
					int x1, k1;
					ArrayList ret = new ArrayList();
					
					do {
						pre = (int[]) list.get(D);
						if (k == -1 * D	|| k != D && pre[(k - 1 + D - 1) >> 1] < pre[(k + 1 + D - 1) >> 1]) {
							x1 = pre[(k + 1 + D - 1) >> 1];
							k1 = k + 1;
						} else {
							x1 = pre[(k - 1 + D - 1) >> 1];
							k1 = k - 1;
						}
						while (x > x1 && (x - k) > (x1 - k1)) {
							--x;
							//System.out.println("(" + x + "," + (x - k) + ")");
							ret.add(new MatchPair(x, x - k));
						}
						k = k1;
						x = x1;
					} while (--D >= 0);
					Collections.reverse(ret);
					return (MatchPair[]) ret.toArray(new MatchPair[0]);
				}
			}
			pre = cur;
		}
		return null;
	}
}
