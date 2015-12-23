package de.mazdermind.urlaub.model;

public class Employee {
	private String name;
	private Integer id;

	public Employee(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}
}
