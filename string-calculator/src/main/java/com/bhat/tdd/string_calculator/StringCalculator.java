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
			Matcher matcher1 = Pattern.compile("//(.)\n(.*)").matcher(numbers);
			Matcher matcher2 = Pattern.compile("//\\[(.)\\]\\[(.)\\]\n(.*)").matcher(numbers);
			Matcher matcher3 = Pattern.compile("//\\[(.*)\\]\\[(.*)\\]\n(.*)").matcher(numbers);
			Matcher matcher4 = Pattern.compile("//\\[(.*)\\]\n(.*)").matcher(numbers);
			Matcher matcher5 = Pattern.compile("//(\\[.+\\])\n(.*)").matcher(numbers);
			if (matcher1.matches()) {
				System.out.println("Matched 1");
				delimiter = escapeMetaChars(matcher1.group(1));
				numbers = matcher1.group(2);
				// System.out.println(delimiter);
			}
//			} else if(matcher2.matches()){
//				System.out.println("Matched 2");
//				delimiter = Pattern.quote(matcher2.group(1)) + "|" + Pattern.quote(matcher2.group(2));
//				numbers = matcher2.group(3);
//			} else if(matcher3.matches()){
//				System.out.println("Matched 3 " + matcher3.group(1) + " " +  matcher3.group(2));
//				delimiter = Pattern.quote(matcher3.group(1)) + "|" + Pattern.quote(matcher3.group(2));
//				numbers = matcher3.group(3);
//			} else if(matcher4.matches()){
//				System.out.println("Matched 4 " + matcher4.group(1));
//				delimiter = Pattern.quote(matcher4.group(1));
//				numbers = matcher4.group(2);
//			}
			else if(matcher5.matches()) {
				// System.out.print("Matched 5");
				List<String> delimiters = parseDelimiters(matcher5.group(1));
				numbers = matcher5.group(2);
				delimiter = "";
				for(int i=0; i<delimiters.size()-1; i++) {
					// System.out.print(delimiters.get(i) + " ");
					delimiter += delimiters.get(i) + "|";
				}
				delimiter += delimiters.get(delimiters.size()-1);
				
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
		//delimiter = delimiter.replace("\\Q", "");
		//delimiter = delimiter.replace("\\E", "");
		//delimiter = delimiter.replace("*", "\\*");
		System.out.println(delimiter);

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
		List<String> delimiters = new ArrayList();
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
		List<Character> metaChars = Arrays.asList('*', '+', '?', '^', '$');
		for(int i=0; i<s.length(); i++) {
			if(metaChars.contains(s.charAt(i))) {
				sb.append("\\");
			}
			sb.append(s.charAt(i));
		}
		return sb.toString();
	}
}
