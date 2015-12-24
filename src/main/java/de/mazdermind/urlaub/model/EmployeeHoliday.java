package de.mazdermind.urlaub.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.jollyday.Holiday;

public class EmployeeHoliday {
	private Date start;
	private Date end;
	private HolidayPlanningState planningState;
	private Employee parent;

	public EmployeeHoliday() {

	}

	public EmployeeHoliday(Employee parent) {
		this.parent = parent;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public HolidayPlanningState getPlanningState() {
		return planningState;
	}

	public void setPlanningState(HolidayPlanningState planningState) {
		this.planningState = planningState;
	}

	public Employee getParent() {
		return parent;
	}

	public void setParent(Employee parent) {
		this.parent = parent;
	}

	// Calculation Methods
	public int calculateTotalNumberOfDays() {
		return 0;
	}

	public int calculateNumberOfWorkdays() {
		return 0;
	}

	public List<Holiday> listFederalHolidays() {
		return new ArrayList<Holiday>();
	}
}
