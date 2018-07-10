package com.sample;

import java.util.ArrayList;
import java.util.List;

public class UniqueStrings {

	public static void main(String[] args) {
		List<String> myList = new ArrayList<>();
		myList.add("pit");
		myList.add("clok");
		myList.add("lock");
		myList.add("pit");
		myList.add("jal");
		myList.add("pit");
		myList.add("laj");
		myList.add("two");
		int length = myList.size();
		boolean flag = true;
		for (int i = 0; i < length; i++) {
			flag = true;
			for (int j = 0; j < i; j++) {
				if (myList.get(i).equalsIgnoreCase(myList.get(j))) {
					flag = false;
					break;
				} else if (myList.get(i).length() == myList.get(j).length()) {
					String tempi = myList.get(i);
					String tempj = myList.get(j);
					int count = 0;
					for (int k = 0; k < myList.get(i).length(); k++) {
						if (tempi.indexOf(tempj.charAt(k)) != -1) {
							++count;
						}
						if (count == myList.get(i).length()) {
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
