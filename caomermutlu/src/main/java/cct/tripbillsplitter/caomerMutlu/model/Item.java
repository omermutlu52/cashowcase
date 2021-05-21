package cct.tripbillsplitter.caomerMutlu.model;

public class Item {

	
	private String tripID;
	private String description;
	private Integer price;
    private String name;

	
	public Item() {
		super();
	}


	public Item(String description, Integer price) {
		super();
		this.description = description;
		this.price = price;
	}

	

	public Item(String tripID, String description, Integer price, String name) {
		super();
		this.tripID = tripID;
		this.description = description;
		this.price = price;
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getTripID() {
		return tripID;
	}


	public void setTripID(String tripID) {
		this.tripID = tripID;
	}


	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}

}
