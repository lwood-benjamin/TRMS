package com.revature.project_one.models;

public enum EventType {
	UCOURSE(80), SEMINAR(60), CERTPREP(75),
	CERT(100), TECHTRAIN(90), OTHER(30);

	public final int label;

	private EventType(int i) {
		this.label = i;
	}

}
