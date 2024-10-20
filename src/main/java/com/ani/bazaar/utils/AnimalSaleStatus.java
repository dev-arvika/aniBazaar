package com.ani.bazaar.utils;

public enum AnimalSaleStatus {

	PENDING("Pending"), ACTIVE("Active"), SOLD("Sold");

	private final String displayName;

	AnimalSaleStatus(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

}
