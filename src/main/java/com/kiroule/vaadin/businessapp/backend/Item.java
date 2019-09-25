package com.kiroule.vaadin.businessapp.backend;

public class Item {

	private Category category;
	private String name;
	private String desc;
	private double price;
	private String vendor;
	private int stock;
	private int sold;

	public enum Category {
		HEALTHCARE("Healthcare"), DENTAL("Dental"), CONSTRUCTION(
				"Construction");

		private String name;

		Category(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public Item(Category category, String name, String desc, String vendor,
	            double price, int stock, int sold) {
		this.category = category;
		this.name = name;
		this.desc = desc;
		this.price = price;
		this.vendor = vendor;
		this.stock = stock;
		this.sold = sold;
	}

	public Category getCategory() {
		return category;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public double getPrice() {
		return price;
	}

	public String getVendor() {
		return vendor;
	}

	public int getStock() {
		return stock;
	}

	public int getSold() {
		return sold;
	}

}
