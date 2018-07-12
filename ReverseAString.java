package com.sample;

public class ReverseAString {

	static String myString = "Ganesh";
	static char[] myArray = myString.toCharArray();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("The Given String is");
		System.out.println("---------------------------------");
		for (int i = 0; i < myArray.length; i++) {
			System.out.print(myArray[i]);
		}
		System.out.println();
		System.out.println();
		System.out.println("Reverse of Given String is");
		System.out.println("---------------------------------");
		for (int i = myArray.length - 1; i >= 0; i--) {
			System.out.print(myArray[i]);
		}
		System.out.println();
		System.out.println();
		System.out.println("Reverse of Given String using Recursion ");
		System.out.println("---------------------------------");
		iterateMe(myArray.length-1);
	}

	private static void iterateMe(int arrayLength) {
		if(arrayLength>=0) {
			System.out.print(myArray[arrayLength]);
			iterateMe(arrayLength-1);
		}
		
	}

}
