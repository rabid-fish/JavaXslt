package com.github.rabid_fish.web;

import static org.junit.Assert.*;

import org.junit.Test;

public class RequestParametersCheckTest {

	@Test
	public void testConstructor() {
		RequestParametersCheck check = new RequestParametersCheck("1", "list");
		assertTrue(!check.requestParametersAreInvalid());
	}

	@Test
	public void testConstructorWithInvalidParameters() {
		RequestParametersCheck check = new RequestParametersCheck("100", "gibberish");
		assertTrue(check.requestParametersAreInvalid());
	}
	
}
