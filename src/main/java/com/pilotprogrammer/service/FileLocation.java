package com.pilotprogrammer.service;

public enum FileLocation {
	// for these, file is placed in <project_root>/src/main/java/com/pilotprogrammer/class-loader-test.txt
	CLASS_LOADER_ABSOLUTE_FOUND("/com/pilotprogrammer/class-loader-test.txt"),
	CLASS_LOADER_RELATIVE_FOUND("com/pilotprogrammer/class-loader-test.txt"),
	CLASS_LOADER_ABSOLUTE_NOT_FOUND("class-loader-test.txt"),
	CLASS_LOADER_RELATIVE_NOT_FOUND("/class-loader-test.txt"),

	// for these, file is placed in <project_root>/src/main/java/com/pilotprogrammer/service/adjacent-class-test.txt
	// and is placed in <project_root>/src/main/java/com/pilotprogrammer/level-above-class-test.txt
	CLASS_ABSOLUTE_FOUND("/adjacent-class-test.txt"),
	CLASS_RELATIVE_FOUND("adjacent-class-test.txt"),
	CLASS_ABSOLUTE_NOT_FOUND(""),
	CLASS_RELATIVE_NOT_FOUND(""),

	NEW_CLASSPATH_FOUND("/pilot/programmer/new-class-path.txt"),
	NEW_CLASSPATH_NOT_FOUND("/pilot/programmer/not-found.txt"),
	;
	
	private String filePath;
	FileLocation(String path) {
		filePath = path;
	}
	
	public String getFilePath() {
		return filePath;
	}
}
