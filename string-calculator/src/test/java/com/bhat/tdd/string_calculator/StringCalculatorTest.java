package com.bhat.tdd.string_calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringCalculatorTest {

	StringCalculator stringCalculator = new StringCalculator();
	
	@Test
	public void emptyNumber() {
		assertEquals(0, stringCalculator.add(""));
	}
	
	@Test
	public void singleNumber() {
		assertEquals(1, stringCalculator.add("1"));
	}
	
	@Test
	public void twoNumbers() {
		assertEquals(3, stringCalculator.add("1,2"));
	}
	
	@Test
	public void multipleNumbers() {
		assertEquals(6, stringCalculator.add("1,2,3"));
	}
	
	@Test
	public void multipleNumbersWithNewlineDelimiter() {
		assertEquals(6, stringCalculator.add("1\n2,3"));
	}
	
	@Test
	public void multipleNumbersWithCustomDelimiter() {
		assertEquals(6, stringCalculator.add("//;\n1;2;3"));
	}
}