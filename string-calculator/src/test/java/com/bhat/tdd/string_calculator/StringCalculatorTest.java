package com.bhat.tdd.string_calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
		assertEquals(6, stringCalculator.add("//*\n1*2*3"));
	}
	
	@Test
	public void exceptionWithNegativeNumbers() {
		try {
			stringCalculator.add("-1,-2,3");
			fail("Exception to be thrown");
		} catch(RuntimeException exception) {
			assertEquals("Negatives not allowed - -1,-2", exception.getMessage());
		}
	}
	
	@Test
	public void ignoreNumbersGreaterThan1000() {
		assertEquals(2, stringCalculator.add("2,1001"));
		assertEquals(2999, stringCalculator.add("1000,1000,3000,999"));
	}

	@Test
	public void differentFormatForCustomDelimiters() {
		assertEquals(6, stringCalculator.add("//[***]\n1***2***3"));
		assertEquals(5, stringCalculator.add("//[;;;]\n1;;;4"));
	}
	
	@Test
	public void multipleCustomDelimiters() {
		assertEquals(6, stringCalculator.add("//[*][%]\n1*2%3"));
		assertEquals(8, stringCalculator.add("//[/][.]\n1/2.3.2"));
	}

	@Test
	public void multipleCustomDelimitersWithMoreCharacters() {
		assertEquals(6, stringCalculator.add("//[**][%%]\n1**2%%3"));
		assertEquals(10, stringCalculator.add("//[???][^^^][+++]\n1???2+++3^^^4"));
	}

}
