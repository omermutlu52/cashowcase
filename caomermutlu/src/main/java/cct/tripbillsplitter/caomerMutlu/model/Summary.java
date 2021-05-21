package cct.tripbillsplitter.caomerMutlu.model;

public class Summary {

	private String name;
	private String description;
	private int value;	
	
	public Summary() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Summary(String name, String description, int value) {
		super();
		this.name = name;
		this.description = description;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
	
	
}
