package com.bhat.tdd.string_calculator;

import java.util.ArrayList;
import java.util.List;
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
		
		int tokens[] = splitNumbers(delimiter, numbers);
		List<Integer> negativeNums = getNegatives(tokens);
		if(negativeNums.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<negativeNums.size()-1; i++) {
				sb.append(negativeNums.get(i));
				sb.append(",");
			}
			sb.append(negativeNums.get(negativeNums.size() - 1));
			throw new RuntimeException("Negatives not allowed - " + sb);
		}
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
	private int[] splitNumbers(String delimiter, String numbers) {
		String tokens[] = numbers.split(delimiter);
		int nums[] = new int[tokens.length];
		int i = 0;
		for(String num: tokens) {
			nums[i++] = Integer.parseInt(num);
		}
		return nums;
	}
	
	// Created a method to avoid code duplication
	private int calculateSum(int[] tokens) {
		int sum = 0;
		for(int i=0; i<tokens.length; i++) {
			sum += tokens[i];
		}
		return sum;
	}
	
	private List<Integer> getNegatives(int[] numbers) {
		List<Integer> nums = new ArrayList<Integer>();
		for(int num: numbers) {
			if(num < 0) {
				nums.add(num);
			}
		}
		return nums;
	}
}
