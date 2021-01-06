package com.bhat.tdd.string_calculator;

import java.util.ArrayList;
import java.util.Arrays;
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
			Matcher singleDelimiterMatcher = Pattern.compile("//(.)\n(.*)").matcher(numbers);
			Matcher multipleDelimiterMatcher = Pattern.compile("//(\\[.+\\])\n(.*)").matcher(numbers);
			if(singleDelimiterMatcher.matches()) {
				delimiter = escapeMetaChars(singleDelimiterMatcher.group(1));
				numbers = singleDelimiterMatcher.group(2);
			}
			else if(multipleDelimiterMatcher.matches()) {
				List<String> delimiters = parseDelimiters(multipleDelimiterMatcher.group(1));
				numbers = multipleDelimiterMatcher.group(2);
				delimiter = "";
				for(int i=0; i<delimiters.size()-1; i++) {
					delimiter += delimiters.get(i) + "|";
				}
				delimiter += delimiters.get(delimiters.size()-1);
				
			}
		}
		
		int nums[] = splitNumbers(delimiter, numbers);
		
		// Check whether input contains any negative numbers
		List<Integer> negativeNums = getNegatives(nums);
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
		int sum = calculateSum(nums);
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
	
	private List<String> parseDelimiters(String text) {
		List<String> delimiters = new ArrayList<String>();
		int start = 0;
		for(int i=0; i<text.length(); i++) {
			if(text.charAt(i) == '[') {
				start = i+1;
			} else if(text.charAt(i) == ']') {
				String delimiter = escapeMetaChars(text.substring(start, i));
				delimiters.add(delimiter);
			}
		}
		return delimiters;
	}
	
	private String escapeMetaChars(String s) {
		StringBuilder sb = new StringBuilder();
		List<Character> metaChars = Arrays.asList('*', '+', '?', '^', '$', '.');
		for(int i=0; i<s.length(); i++) {
			if(metaChars.contains(s.charAt(i))) {
				sb.append("\\");
			}
			sb.append(s.charAt(i));
		}
		return sb.toString();
	}
}
