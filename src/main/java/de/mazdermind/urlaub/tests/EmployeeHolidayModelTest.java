package de.mazdermind.urlaub.tests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.jollyday.Holiday;
import de.jollyday.HolidayType;
import de.mazdermind.urlaub.model.Employee;
import de.mazdermind.urlaub.model.EmployeeHoliday;

public class EmployeeHolidayModelTest {
	private Employee employee;

	@Before
	public void setupEmployeeMock() {
		this.employee = new Employee();
		this.employee.setId(5);
		this.employee.setCountry("GERMANY");
		this.employee.setName("Donald");
		this.employee.setAvailableDays(30);
		this.employee.setFederalState("he");
	}

	@Test
	public void testTotalNumberOfDaysCalculation() throws ParseException {
		EmployeeHoliday h = new EmployeeHoliday(this.employee);

		// a work-week (monday - friday)
		h.setStart(LocalDate.of(2015, 12, 07));
		h.setEnd(LocalDate.of(2015, 12, 11));
		assertEquals(5, h.calculateTotalNumberOfDays());

		// a complete week (monday - sunday)
		h.setStart(LocalDate.of(2015, 12, 07));
		h.setEnd(LocalDate.of(2015, 12, 13));
		assertEquals(7, h.calculateTotalNumberOfDays());

		// two complete weeks (monday - sunday)
		h.setStart(LocalDate.of(2015, 12, 07));
		h.setEnd(LocalDate.of(2015, 12, 20));
		assertEquals(14, h.calculateTotalNumberOfDays());

		// two complete weeks across a month-border (monday - sunday)
		h.setStart(LocalDate.of(2015, 11, 23));
		h.setEnd(LocalDate.of(2015, 12, 06));
		assertEquals(14, h.calculateTotalNumberOfDays());

		// three complete weeks across a year-border (monday - sunday)
		h.setStart(LocalDate.of(2015, 12, 21));
		h.setEnd(LocalDate.of(2016, 01, 10));
		assertEquals(21, h.calculateTotalNumberOfDays());
	}

	@Test
	public void testNumberOfWorkdaysCalculation() throws ParseException {
		EmployeeHoliday h = new EmployeeHoliday(this.employee);

		// a work-week (monday - friday)
		h.setStart(LocalDate.of(2015, 12, 07));
		h.setEnd(LocalDate.of(2015, 12, 11));
		assertEquals(5, h.calculateNumberOfWorkdays());

		// a complete week (monday - sunday)
		h.setStart(LocalDate.of(2015, 12, 07));
		h.setEnd(LocalDate.of(2015, 12, 13));
		assertEquals(5, h.calculateNumberOfWorkdays());

		// two complete weeks (monday - sunday)
		h.setStart(LocalDate.of(2015, 12, 07));
		h.setEnd(LocalDate.of(2015, 12, 20));
		assertEquals(10, h.calculateNumberOfWorkdays());
	}

	@Test
	public void testNumberOfWorkdaysCalculationAcrossFederalHolidays() throws ParseException {
		EmployeeHoliday h = new EmployeeHoliday(this.employee);

		// a work-week (monday - friday) with a federal holiday in it
		// (2015-12-25)
		h.setStart(LocalDate.of(2015, 12, 21));
		h.setEnd(LocalDate.of(2015, 12, 25));
		assertEquals(4, h.calculateNumberOfWorkdays());

		// a complete week (monday - sunday) with multitple federal holidays in
		// it (fr 2015-12-25, sa 2015-12-26) which partially fall on a weekend
		h.setStart(LocalDate.of(2015, 12, 21));
		h.setEnd(LocalDate.of(2015, 12, 27));
		assertEquals(4, h.calculateNumberOfWorkdays());

		// two complete weeks (monday - sunday) with multiple federal holidays
		// in it (fr 2015-12-25, sa 2015-12-26, fr 2015-01-01) which partially
		// fall on a weekend
		h.setStart(LocalDate.of(2015, 12, 21));
		h.setEnd(LocalDate.of(2016, 01, 03));
		assertEquals(8, h.calculateNumberOfWorkdays());
	}

	@Test
	public void testNumberOfWorkdaysCalculationAcrossMultipleFederalHolidaysOnTheSameDay() throws ParseException {
		// https://de.wikipedia.org/wiki/Feiertage_in_Deutschland#Zusammenfallen_zweier_Feiertage
		EmployeeHoliday h = new EmployeeHoliday(this.employee);

		// a work-week (monday - friday) with two federal holidays in
		// it which are both on the same date (th 2008-05-01)
		h.setStart(LocalDate.of(2008, 04, 28));
		h.setEnd(LocalDate.of(2008, 05, 02));
		assertEquals(4, h.calculateNumberOfWorkdays());
	}

	@Test
	public void testListFederalHolidays() throws ParseException {
		EmployeeHoliday h = new EmployeeHoliday(this.employee);
		
		List<Holiday> expected;

		// a work-week (monday - friday) with a federal holiday in it
		// (2015-12-25)
		h.setStart(LocalDate.of(2015, 12, 21));
		h.setEnd(LocalDate.of(2015, 12, 25));
		expected = Arrays.asList(
			new Holiday(LocalDate.of(2015, 12, 25), "CHRISTMAS", HolidayType.OFFICIAL_HOLIDAY));

		assertEquals(expected, h.listFederalHolidays());

		// a complete week (monday - sunday) with multitple federal holidays in
		// it (fr 2015-12-25, sa 2015-12-26) which partially fall on a weekend
		h.setStart(LocalDate.of(2015, 12, 21));
		h.setEnd(LocalDate.of(2015, 12, 27));
		expected = Arrays.asList(
				new Holiday(LocalDate.of(2015, 12, 25), "CHRISTMAS", HolidayType.OFFICIAL_HOLIDAY),
				new Holiday(LocalDate.of(2015, 12, 26), "STEPHENS", HolidayType.OFFICIAL_HOLIDAY));

		assertEquals(expected, h.listFederalHolidays());

		// two complete weeks (monday - sunday) with multiple federal holidays
		// in it (fr 2015-12-25, sa 2015-12-26, fr 2015-01-01) which partially
		// fall on a weekend
		h.setStart(LocalDate.of(2015, 12, 21));
		h.setEnd(LocalDate.of(2016, 01, 03));
		expected = Arrays.asList(
				new Holiday(LocalDate.of(2015, 12, 25), "CHRISTMAS", HolidayType.OFFICIAL_HOLIDAY),
				new Holiday(LocalDate.of(2015, 12, 26), "STEPHENS", HolidayType.OFFICIAL_HOLIDAY),
				new Holiday(LocalDate.of(2016, 01, 01), "NEW_YEAR", HolidayType.OFFICIAL_HOLIDAY));

		assertEquals(expected, h.listFederalHolidays());

		// a work-week (monday - friday) with two federal holidays in
		// it which are both on the same date (th 2008-05-01)
		h.setStart(LocalDate.of(2008, 04, 28));
		h.setEnd(LocalDate.of(2008, 05, 02));
		expected = Arrays.asList(
				new Holiday(LocalDate.of(2008, 05, 01), "christian.ASCENSION_DAY", HolidayType.OFFICIAL_HOLIDAY),
				new Holiday(LocalDate.of(2008, 05, 01), "LABOUR_DAY", HolidayType.OFFICIAL_HOLIDAY));

		assertEquals(expected, h.listFederalHolidays());
	}
}
