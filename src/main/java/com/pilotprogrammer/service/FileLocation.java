package com.pilotprogrammer.service;

public enum FileLocation {
	CLASS_LOADER_ABSOLUTE_FOUND("/com/pilotprogrammer/class-loader-test.txt", true),
	CLASS_LOADER_RELATIVE_FOUND("com/pilotprogrammer/class-loader-test.txt", true),
	CLASS_LOADER_ABSOLUTE_NOT_FOUND("class-loader-test.txt", true),
	CLASS_LOADER_RELATIVE_NOT_FOUND("/class-loader-test.txt", true),

	CLASS_ABSOLUTE_FOUND("/com/pilotprogrammer/class-loader-test.txt", false),
	CLASS_RELATIVE_FOUND("class-test.txt", false),
	CLASS_ABSOLUTE_NOT_FOUND("/class-test.txt", false),
	CLASS_RELATIVE_NOT_FOUND("com/pilotprogrammer/class-test.txt", false),
	
	CLASS_RELATIVE_DEEP_FOUND("com/pilotprogrammer/deep-class-test.txt", false),

	NEW_CLASSPATH_FOUND("/new-class-path.txt", true),
	NEW_CLASSPATH_NOT_FOUND("/pilot/programmer/new-class-path.txt", true),
	;
	
	private String filePath;
	private boolean useClassLoader;
	
	public String getFilePath() {
		return filePath;
	}
	
	public boolean shouldUseClassLoader() {
		return useClassLoader;
	}

	FileLocation(String filePath, boolean useClassLoader) {
		this.filePath = filePath;
		this.useClassLoader = useClassLoader;
	}
}
