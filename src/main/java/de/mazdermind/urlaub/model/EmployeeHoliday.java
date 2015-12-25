package de.mazdermind.urlaub.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import de.jollyday.ManagerParameters;

public class EmployeeHoliday {
	private LocalDate start;
	private LocalDate end;
	private HolidayPlanningState planningState;
	private Employee parent;

	public EmployeeHoliday() {

	}

	public EmployeeHoliday(Employee parent) {
		this.parent = parent;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
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
		String country = this.getParent().getCountry();
		String federalState = this.getParent().getFederalState();
		HolidayManager holidayManager = HolidayManager
				.getInstance(ManagerParameters.create(HolidayCalendar.valueOf(country)));

		List<Holiday> allHolidays = new ArrayList<>();

		for (int year = this.start.getYear(); year <= this.end.getYear(); year++) {
			Set<Holiday> holidaysThisYear = holidayManager.getHolidays(year, federalState);

			Comparator<Holiday> byDate = (h1, h2) -> h1.getDate().compareTo(h2.getDate());

			holidaysThisYear.stream()
				.filter(h -> h.getDate().isAfter(this.start))
				.filter(h -> h.getDate().isBefore(this.end))
				.sorted(byDate)
				.forEach(h -> allHolidays.add(h));
		}

		return allHolidays;
	}
}
