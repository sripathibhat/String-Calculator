package com.bhat.tdd.string_calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

	int add(String numbers) {
		if(numbers.equals("")) {
			return 0;
		}
		String delimiter = ",|\n";
		if(numbers.startsWith("//")) {
			Matcher matcher = Pattern.compile("//(.)\n(.*)").matcher(numbers);
			if (matcher.matches()) {
				// System.out.println(matcher.groupCount() + "\n" + matcher.group(0) + "\n" + matcher.group(1) + "\n" + matcher.group(2));
				delimiter = matcher.group(1);
				numbers = matcher.group(2);
//				String[] tokens = numbers.split(delimiter);
//				int sum = 0;
//				if(tokens.length != 0) {
//					for(int i=0; i<tokens.length; i++) {
//						sum += Integer.parseInt(tokens[i]);
//					}
//				}
//				return sum;
			}
		}
		
		String tokens[] = splitNumbers(delimiter, numbers);
		int sum = calculateSum(tokens);
		
//		String[] tokens = numbers.split(",|\n");
//		int sum = 0;
//		if(tokens.length != 0) {
//			for(int i=0; i<tokens.length; i++) {
//				sum += Integer.parseInt(tokens[i]);
//			}
//		}
		return sum;
	}
	
	// Created a method to avoid code duplication
	private String[] splitNumbers(String delimiter, String numbers) {
		String tokens[] = numbers.split(delimiter);
		return tokens;
	}
	
	// Created a method to avoid code duplication
	private int calculateSum(String[] tokens) {
		int sum = 0;
		for(int i=0; i<tokens.length; i++) {
			sum += Integer.parseInt(tokens[i]);
		}
		return sum;
	}
}
