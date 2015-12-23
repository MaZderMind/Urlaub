package de.mazdermind.urlaub.model;

import java.util.Date;

public class EmployeeHolidays {
	private Date start;
	private Date end;
	private HolidayPlanningState planningState;
	
	public EmployeeHolidays() {
		
	}
	
	public EmployeeHolidays(Date start, Date end)
	{
		this.start = start;
		this.end = end;
	}
	
	public EmployeeHolidays(Date start, Date end, HolidayPlanningState planningState)
	{
		this.start = start;
		this.end = end;
		this.planningState = planningState;
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
}
