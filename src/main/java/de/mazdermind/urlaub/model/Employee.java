package de.mazdermind.urlaub.model;

import java.util.Collections;
import java.util.List;

public class Employee {
	private String name;
	private Integer id;
	private Integer yearOfEntrance;
	private Integer availableDaysInYearOfEntrance;
	private Integer availableDays;
	private FederalState federalState;
	private List<EmployeeHoliday> holidays;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<EmployeeHoliday> getHolidays() {
		return Collections.unmodifiableList(holidays);
	}

	public Integer getAvailableDays() {
		return availableDays;
	}

	public Integer getAvailableDays(int year) {
		if(this.yearOfEntrance == null)
			return this.availableDays;

		if (year < this.yearOfEntrance)
			return 0;
		else if (year == this.yearOfEntrance)
			return this.availableDaysInYearOfEntrance;
		else
			return this.availableDays;
	}

	public void setAvailableDays(Integer availableDays) {
		this.availableDays = availableDays;
	}

	public Integer getAvailableDaysInYearOfEntrance() {
		return availableDaysInYearOfEntrance;
	}

	public void setAvailableDaysInYearOfEntrance(Integer availableDaysInYearOfEntrance) {
		this.availableDaysInYearOfEntrance = availableDaysInYearOfEntrance;
	}

	public Integer getYearOfEntrance() {
		return yearOfEntrance;
	}

	public void setYearOfEntrance(Integer yearOfEntrance) {
		this.yearOfEntrance = yearOfEntrance;
	}

	public FederalState getFederalState() {
		return federalState;
	}

	public void setFederalState(FederalState state) {
		this.federalState = state;
	}

	// Calculation Methods
	public int calculateUsedDays(int year) {
		return 0;
	}

	public int calculatePlannedDays(int year) {
		return 0;
	}

	public int calculateRemainingDays(int year) {
		return this.getAvailableDays(year) - this.calculateUsedDays(year);
	}

	public int calculatePlannedRemainingDays(int year) {
		return this.getAvailableDays(year) - this.calculatePlannedDays(year);
	}
	
	public void addHoliday(EmployeeHoliday holiday) {
		if(!this.canAddHoliday(holiday))
			throw new UnsupportedOperationException();
		
		this.holidays.add(holiday);
	}
	
	public boolean canAddHoliday(EmployeeHoliday holiday) {
		return false;
	}
}
