package com.sample;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class PrintFirstNonRepatedCharacter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the String");
		String input = sc.nextLine().toUpperCase();
		String temp = "";
		Map<Character, Integer> ht = new LinkedHashMap<>();
		for (int i = 0; i < input.length(); i++) {
			int count = 0;
			if (temp.indexOf(input.charAt(i)) == -1) {
				temp = temp + input.charAt(i);
				ht.put(input.charAt(i), ++count);
			} else {

				ht.replace(input.charAt(i), ht.get(input.charAt(i)) + 1);
			}
		}
		System.out.println("Map Details");
		System.out.println("----------------------------------");
		for (Map.Entry map : ht.entrySet()) {

			System.out.println(map.getKey() + "::" + map.getValue());
		}
		for (Map.Entry<Character, Integer> m : ht.entrySet()) {
			if (m.getValue() == 1) {
				System.out.println("----------------------------------");
				System.out.println("FirstNonRepatedCharacter is " + m.getKey());
				break;
			}
		}
		System.out.println("----------------------------------");
		for (Map.Entry<Character, Integer> m : ht.entrySet()) {
			if (m.getValue() > 1) {

				System.out.println("RepatedCharacter in the given Strings is/are " + m.getKey() + ":" + m.getValue());
			}
		}
	}

}
