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
		
		// Check for custom delimiters
		if(numbers.startsWith("//")) {
			Matcher matcher = Pattern.compile("//(.)\n(.*)").matcher(numbers);
			if (matcher.matches()) {
				delimiter = Pattern.quote(matcher.group(1));
				numbers = matcher.group(2);
				// System.out.println(delimiter);
			}
		}
		
		int tokens[] = splitNumbers(delimiter, numbers);
		
		// Check whether input contains any negative numbers
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
		
		// No negative numbers, so calculate sum
		int sum = calculateSum(tokens);
		return sum;
	}

	private int[] splitNumbers(String delimiter, String numbers) {
		String tokens[] = numbers.split(delimiter);
		int nums[] = new int[tokens.length];
		int i = 0;
		for(String num: tokens) {
			nums[i++] = Integer.parseInt(num);
		}
		return nums;
	}

	private int calculateSum(int[] tokens) {
		int sum = 0;
		for(int i=0; i<tokens.length; i++) {
			sum += tokens[i] > 1000 ? 0 : tokens[i];
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
