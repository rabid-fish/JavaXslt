package com.github.rabid_fish.web;

public enum ServletAction {
	LIST,
	UPDATE,
	DELETE
	;
	
	public static ServletAction translateFromString(String servletActionString) {

		ServletAction servletAction = null;
		
		if (servletActionString == null) {
			// Set default action to LIST
			servletAction = LIST;
		} else {
			try {
				servletAction = ServletAction.valueOf(servletActionString.toUpperCase());
			} catch (IllegalArgumentException e) {
				// do nothing, will return null and cause 404
			}
		}
		
		return servletAction;
	}

}
