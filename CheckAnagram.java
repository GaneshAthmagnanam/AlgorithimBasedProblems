package com.sample;

import java.util.Scanner;

public class CheckAnagram {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter first string");
		String input1 = sc.nextLine();
		System.out.println("Enter second string");
		String input2 = sc.nextLine();
		String one = input1.toUpperCase();
		String two = input2.toUpperCase();
		int count = 0;
		if (one.length() == two.length()) {
			for (int i = 0; i < one.length(); i++) {
				if (one.indexOf(two.charAt(i)) != -1) {
					++count;
				}
			}
			if (count == one.length()) {
				System.out.println("The Given strings are Anagram");
			} else {
				System.out.println("The Given strings are not Anagram");
			}
		} else {
			System.out.println("The Given strings are not Anagram");
		}

	}

}
