package com.github.rabid_fish.web;

import static org.junit.Assert.*;

import org.junit.Test;

public class ServletActionTest {

	@Test
	public void testTranslateFromString() {
		
		ServletAction servletAction = ServletAction.translateFromString(ServletAction.LIST.toString());
		assertEquals(servletAction, ServletAction.LIST);
	}

	@Test
	public void testTranslateFromStringWithLowercaseActionString() {
		
		ServletAction servletAction = ServletAction.translateFromString(ServletAction.LIST.toString().toLowerCase());
		assertEquals(servletAction, ServletAction.LIST);
	}
	
	@Test
	public void testTranslateFromStringCheckForNull() {
		
		ServletAction servletAction = ServletAction.translateFromString(null);
		assertEquals(servletAction, ServletAction.LIST);
	}
	
	@Test
	public void testTranslateFromStringCheckForGibberish() {
		
		ServletAction servletAction = ServletAction.translateFromString("gibberish");
		assertEquals(servletAction, null);
	}


}
