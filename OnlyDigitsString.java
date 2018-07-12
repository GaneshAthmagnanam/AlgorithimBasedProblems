package com.sample;

public class OnlyDigitsString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String myString="123w4";
		String regex = "[0-9]+";
		if(myString.matches(regex)) {
			System.out.println("Given String matches our Regex");
		}
		else {
			System.out.println("Given String not matches our Regex");
		}
	}

}
