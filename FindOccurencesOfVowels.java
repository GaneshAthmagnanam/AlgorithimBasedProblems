package com.sample;

public class FindOccurencesOfVowels {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String myString="java";
		int count = 0;
		char[] myArray=myString.toCharArray();
		for(Character c:myArray) {
			switch(c) {
			
			case 'a':
				++count;
				continue;
			case 'e':
				++count;
				continue;
			case 'i':
				++count;
				continue;
			case 'o':
				++count;
				continue;
			case 'u':	
				++count;
				
				
				break;
			default:	
			}
		}
		System.out.println(count);
	}

}
