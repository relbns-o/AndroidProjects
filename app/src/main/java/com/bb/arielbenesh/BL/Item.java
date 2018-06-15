package com.bb.arielbenesh.BL;

/**
 * Item object that holds specific wanted json attributes
 */
public class Item {
	private String fullName;
	private int level;
	private boolean identified ;

	// default constructor
	public Item() {}
	
	public Item(String fullName, int level, boolean identified) {
		this.fullName = fullName;
		this.level = level;
		this.identified = identified;
	}

	public String getFullName() {
		return fullName;
	}

	public int getLevel() {
		return level;
	}

	public boolean isIdentified() {
		return identified;
	}

}
