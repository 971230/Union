package com.ztesoft.inf.communication.client;

public final class InfCloed {

	private static InfCloed instance = new InfCloed();

	private InfCloed() {
	}

	public static InfCloed getInstance() {
		return instance;
	}
}
