package com.ford.sast.jenkins

enum ProjectType {
	
	WebApp('WebApp'), 
	WebService('WebService'), 
	Mobile('Mobile')
	
	private static final String PREFIX = 'Ford - '
	
	public ProjectType(String preset) {
		this.preset = PREFIX + preset
	}
	
	private final String preset;
	String getPreset() {
		this.preset
	}
}
