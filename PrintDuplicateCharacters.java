package com.sample;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class PrintDuplicateCharacters {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Set<Character> set = new HashSet<>();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the String");
		String input = sc.nextLine().toUpperCase();
		String temp = "";
		for (int i = 0; i < input.length(); i++) {
			if (temp.indexOf(input.charAt(i)) == -1) {
				temp = temp + input.charAt(i);

			} else {
				set.add(input.charAt(i));
			}
		}
		System.out.print(set);
	}

}
