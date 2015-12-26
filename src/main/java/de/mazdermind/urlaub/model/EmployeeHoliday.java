package de.mazdermind.urlaub.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
		return (int) this.start.until(this.end.plusDays(1), ChronoUnit.DAYS);
	}

	public int calculateNumberOfWorkdays() {
		int days = 0;

		// list of holidays in this employee holiday's span
		List<Holiday> holidays = this.listFederalHolidays();

		// map those to only their dates
		List<LocalDate> dates = holidays.stream()
				.map(h -> h.getDate())
				.collect(Collectors.toList());

		// TODO model with a stream … somehow?
		// iterate over all days in this employee-holiday's span
		for (LocalDate date = this.start; date.isBefore(this.end.plusDays(1)); date = date.plusDays(1)) {
			// if it's a weekend -> skip it
			// TODO: make free days configurable
			if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
				continue;

			// if it is a holiday -> skip it
			if (dates.contains(date))
				continue;

			// count remaining days
			days++;
		}

		return days;
	}

	public List<Holiday> listFederalHolidays() {
		// fetch country and state from the employee model
		String country = this.getParent().getCountry();
		String federalState = this.getParent().getFederalState();

		// create a holiday-manager instance for the selected country
		HolidayManager holidayManager = HolidayManager
				.getInstance(ManagerParameters.create(HolidayCalendar.valueOf(country)));

		// target list of holidays
		List<Holiday> allHolidays = new ArrayList<>();

		// iterate over all years this holiday employee-spans ;)
		for (int year = this.start.getYear(); year <= this.end.getYear(); year++) {
			Set<Holiday> holidaysThisYear = holidayManager.getHolidays(year, federalState);

			Comparator<Holiday> byDate = (h1, h2) -> h1.getDate().compareTo(h2.getDate());

			// filter relevant holidays, sort them and add them to the target list
			holidaysThisYear.stream()
				.filter(h -> h.getDate().isAfter(this.start) || h.getDate().isEqual(this.start))
				.filter(h -> h.getDate().isBefore(this.end) || h.getDate().isEqual(this.end))
				.sorted(byDate)
				.forEachOrdered(allHolidays::add);
		}

		return allHolidays;
	}
}
