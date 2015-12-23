package de.mazdermind.urlaub.model;

import java.util.List;

public class Employee {
	private String name;
	private int id;
	private int yearOfEntrance;
	private int availableDaysInYearOfEntrance;
	private int availableDays;
	private FederalState state;
	private List<EmployeeHolidays> holidays;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<EmployeeHolidays> getHolidays() {
		return holidays;
	}

	public int getAvailableDays() {
		return availableDays;
	}

	public int getAvailableDays(int year) {
		if (year < this.yearOfEntrance)
			return 0;
		else if (year == this.yearOfEntrance)
			return this.availableDaysInYearOfEntrance;
		else
			return availableDays;
	}

	public void setAvailableDays(int availableDays) {
		this.availableDays = availableDays;
	}

	public int getAvailableDaysInYearOfEntrance() {
		return availableDaysInYearOfEntrance;
	}

	public void setAvailableDaysInYearOfEntrance(int availableDaysInYearOfEntrance) {
		this.availableDaysInYearOfEntrance = availableDaysInYearOfEntrance;
	}

	public int getYearOfEntrance() {
		return yearOfEntrance;
	}

	public void setYearOfEntrance(int yearOfEntrance) {
		this.yearOfEntrance = yearOfEntrance;
	}

	public FederalState getState() {
		return state;
	}

	public void setState(FederalState state) {
		this.state = state;
	}
}
