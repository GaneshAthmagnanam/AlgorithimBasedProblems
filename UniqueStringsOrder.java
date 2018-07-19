package com.sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UniqueStringsOrder {

	public static void main(String[] args) {
		List<String> myList = new ArrayList<>();
		myList.add("pit");
		myList.add("laja");
		myList.add("clok");
		myList.add("lock");
		myList.add("pit");
		myList.add("jal");
		myList.add("pit");
		myList.add("lajl");
		myList.add("two");
		int length = myList.size();
		boolean flag = true;
		for (int i = 0; i < length; i++) {
			flag = true;
			for (int j = 0; j < i; j++) {
				
				
				if (myList.get(i).equalsIgnoreCase(myList.get(j))) {
					flag = false;
					break;
				} 
				
				for (int j1 = 0; j1 < i; j1++) {
				 if (myList.get(i).length() == myList.get(j1).length()) {
					char[] tempi = myList.get(i).toCharArray();
					char[] tempj = myList.get(j1).toCharArray();
					Arrays.sort(tempi);
					Arrays.sort(tempj);
					String tempiString=new String(tempi);
					String tempjString=new String(tempj);
					if (tempiString.equalsIgnoreCase(tempjString)) {
						flag = false;
						break;
					} 
					
					
					
				}
				}
			}
			if (flag) {
				System.out.println(myList.get(i));
			}
		}
	}
}
