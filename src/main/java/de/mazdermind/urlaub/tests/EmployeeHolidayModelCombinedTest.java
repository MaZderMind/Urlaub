package de.mazdermind.urlaub.tests;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.mazdermind.urlaub.model.Employee;
import de.mazdermind.urlaub.model.EmployeeHoliday;
import de.mazdermind.urlaub.model.HolidayPlanningState;

public class EmployeeHolidayModelCombinedTest {
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	public Employee generateEmployeeMock() {
		Employee employee = new Employee();
		employee.setId(5);
		employee.setCountry("GERMANY");
		employee.setName("Donald");
		employee.setFederalState("he");

		return employee;
	}

	@Test
	public void testHolidayCountsAsUsed() {
		Employee employee = this.generateEmployeeMock();
		EmployeeHoliday h = new EmployeeHoliday(employee);

		employee.setAvailableDays(30);

		// a work-week (monday - friday)
		h.setStart(LocalDate.of(2015, 12, 07));
		h.setEnd(LocalDate.of(2015, 12, 11));

		assertEquals(30, (int) employee.calculateRemainingDays(2015));
		assertEquals(0, (int) employee.calculateUsedDays(2015));

		employee.addHoliday(h);

		assertEquals(25, (int) employee.calculateRemainingDays(2015));
		assertEquals(5, (int) employee.calculateUsedDays(2015));
	}

	@Test
	public void testWeekendsDoNotCount() {
		Employee employee = this.generateEmployeeMock();
		EmployeeHoliday h = new EmployeeHoliday(employee);

		employee.setAvailableDays(30);

		// a complete week (monday - sunday)
		h.setStart(LocalDate.of(2015, 12, 07));
		h.setEnd(LocalDate.of(2015, 12, 13));

		assertEquals(30, (int) employee.calculateRemainingDays(2015));
		assertEquals(0, (int) employee.calculateUsedDays(2015));

		employee.addHoliday(h);

		assertEquals(25, (int) employee.calculateRemainingDays(2015));
		assertEquals(5, (int) employee.calculateUsedDays(2015));
	}

	@Test
	public void testFederalHolidaysDoNotCount() {
		Employee employee = this.generateEmployeeMock();
		EmployeeHoliday h = new EmployeeHoliday(employee);

		employee.setAvailableDays(30);

		// a work-week (monday - friday) with a federal holiday in it
		// (2015-12-25)
		h.setStart(LocalDate.of(2015, 12, 21));
		h.setEnd(LocalDate.of(2015, 12, 25));

		assertEquals(30, (int) employee.calculateRemainingDays(2015));
		assertEquals(0, (int) employee.calculateUsedDays(2015));

		employee.addHoliday(h);

		assertEquals(26, (int) employee.calculateRemainingDays(2015));
		assertEquals(4, (int) employee.calculateUsedDays(2015));
	}

	@Test
	public void testPlannedHolidayCountsOnlyAsPlanned() {
		Employee employee = this.generateEmployeeMock();
		EmployeeHoliday h = new EmployeeHoliday(employee);

		employee.setAvailableDays(30);

		// a work-week (monday - friday)
		h.setStart(LocalDate.of(2015, 12, 07));
		h.setEnd(LocalDate.of(2015, 12, 11));

		assertEquals(30, (int) employee.calculateRemainingDays(2015));
		assertEquals(0, (int) employee.calculateUsedDays(2015));

		assertEquals(30, (int) employee.calculatePlannedRemainingDays(2015));
		assertEquals(0, (int) employee.calculatePlannedDays(2015));

		h.setPlanningState(HolidayPlanningState.PLANNED);
		employee.addHoliday(h);

		assertEquals(30, (int) employee.calculateRemainingDays(2015));
		assertEquals(0, (int) employee.calculateUsedDays(2015));

		assertEquals(25, (int) employee.calculatePlannedRemainingDays(2015));
		assertEquals(5, (int) employee.calculatePlannedDays(2015));
	}

	@Test
	public void testCantAddHolidayExceedingRemainingDays() {
		Employee employee = this.generateEmployeeMock();
		EmployeeHoliday h;

		employee.setAvailableDays(7);

		// a work-week (monday - friday)
		h = new EmployeeHoliday(employee);
		h.setStart(LocalDate.of(2015, 12, 07));
		h.setEnd(LocalDate.of(2015, 12, 11));

		assertEquals(7, (int) employee.calculateRemainingDays(2015));
		assertEquals(0, (int) employee.calculateUsedDays(2015));

		employee.addHoliday(h);

		assertEquals(2, (int) employee.calculateRemainingDays(2015));
		assertEquals(5, (int) employee.calculateUsedDays(2015));

		// another work-week (monday - friday)
		h = new EmployeeHoliday(employee);
		h.setStart(LocalDate.of(2015, 12, 14));
		h.setEnd(LocalDate.of(2015, 12, 18));

		exception.expect(UnsupportedOperationException.class);
		employee.addHoliday(h);

		assertEquals(2, (int) employee.calculateRemainingDays(2015));
		assertEquals(5, (int) employee.calculateUsedDays(2015));
	}

	@Test
	public void testHolidaySpanningYearchangeCountForEachYearIndividually() {
		Employee employee = this.generateEmployeeMock();
		EmployeeHoliday h = new EmployeeHoliday(employee);

		employee.setAvailableDays(30);

		// three complete weeks (monday - sunday) with multiple federal holidays
		// in it (fr 2015-12-25, sa 2015-12-26, fr 2015-01-01) which partially
		// fall on a weekend
		// counting days should be
		// - 21.12. to 24.12. (4)
		// - 28.12. to 31.12. (4)
		// - 04.01. to 08.01. (5)
		h.setStart(LocalDate.of(2015, 12, 21));
		h.setEnd(LocalDate.of(2016, 01, 10));

		assertEquals(30, (int) employee.calculateRemainingDays(2015));
		assertEquals(0, (int) employee.calculateUsedDays(2015));
		assertEquals(30, (int) employee.calculateRemainingDays(2016));
		assertEquals(0, (int) employee.calculateUsedDays(2016));

		employee.addHoliday(h);

		assertEquals(22, (int) employee.calculateRemainingDays(2015));
		assertEquals(8, (int) employee.calculateUsedDays(2015));
		assertEquals(25, (int) employee.calculateRemainingDays(2015));
		assertEquals(5, (int) employee.calculateUsedDays(2015));
	}

	@Test
	public void testCantAddHolidayExceedingRemainingDaysInOneYear() {
		Employee employee = this.generateEmployeeMock();
		EmployeeHoliday h = new EmployeeHoliday(employee);

		employee.setAvailableDays(5);

		// three complete weeks (monday - sunday) with a federal holiday in it
		// (fr 2015-01-01)
		// counting days should be
		// - 28.12. to 31.12. (4)
		// - 04.01. to 17.01. (10) -> exceed available days
		h.setStart(LocalDate.of(2015, 12, 28));
		h.setEnd(LocalDate.of(2016, 01, 17));

		assertEquals(5, (int) employee.calculateRemainingDays(2015));
		assertEquals(0, (int) employee.calculateUsedDays(2015));
		assertEquals(5, (int) employee.calculateRemainingDays(2016));
		assertEquals(0, (int) employee.calculateUsedDays(2016));

		exception.expect(UnsupportedOperationException.class);
		employee.addHoliday(h);

		assertEquals(5, (int) employee.calculateRemainingDays(2015));
		assertEquals(0, (int) employee.calculateUsedDays(2015));
		assertEquals(5, (int) employee.calculateRemainingDays(2016));
		assertEquals(0, (int) employee.calculateUsedDays(2016));
	}

	@Test
	public void testHolidayCountingForYearOfEntrance() {
		Employee employee = this.generateEmployeeMock();
		EmployeeHoliday h = new EmployeeHoliday(employee);

		employee.setAvailableDaysInYearOfEntrance(20);
		employee.setAvailableDays(30);
		employee.setYearOfEntrance(2015); // 15 days in 2015, 30 days in 2016

		// three complete weeks (monday - sunday) with multiple federal holidays
		// in it (fr 2015-12-25, sa 2015-12-26, fr 2015-01-01) which partially
		// fall on a weekend
		// counting days should be
		// - 21.12. to 24.12. (4)
		// - 28.12. to 31.12. (4)
		// - 04.01. to 08.01. (5)
		h.setStart(LocalDate.of(2015, 12, 21));
		h.setEnd(LocalDate.of(2016, 01, 10));

		assertEquals(20, (int) employee.calculateRemainingDays(2015));
		assertEquals(0, (int) employee.calculateUsedDays(2015));
		assertEquals(30, (int) employee.calculateRemainingDays(2016));
		assertEquals(0, (int) employee.calculateUsedDays(2016));

		employee.addHoliday(h);

		assertEquals(12, (int) employee.calculateRemainingDays(2015));
		assertEquals(8, (int) employee.calculateUsedDays(2015));
		assertEquals(25, (int) employee.calculateRemainingDays(2016));
		assertEquals(5, (int) employee.calculateUsedDays(2016));
	}

	@Test
	public void testCantAddHolidayExceedingRemainingDaysInYearOfEntry() {
		Employee employee = this.generateEmployeeMock();
		EmployeeHoliday h = new EmployeeHoliday(employee);

		employee.setAvailableDaysInYearOfEntrance(5);
		employee.setAvailableDays(30);
		employee.setYearOfEntrance(2015); // 5 days in 2015, 30 days in 2016

		// three complete weeks (monday - sunday) with multiple federal holidays
		// in it (fr 2015-12-25, sa 2015-12-26, fr 2015-01-01) which partially
		// fall on a weekend
		// counting days should be
		// - 21.12. to 24.12. (4)
		// - 28.12. to 31.12. (4)
		// - 04.01. to 08.01. (5)
		h.setStart(LocalDate.of(2015, 12, 21));
		h.setEnd(LocalDate.of(2016, 01, 10));

		assertEquals(5, (int) employee.calculateRemainingDays(2015));
		assertEquals(0, (int) employee.calculateUsedDays(2015));
		assertEquals(30, (int) employee.calculateRemainingDays(2016));
		assertEquals(0, (int) employee.calculateUsedDays(2016));

		exception.expect(UnsupportedOperationException.class);
		employee.addHoliday(h);

		assertEquals(5, (int) employee.calculateRemainingDays(2015));
		assertEquals(0, (int) employee.calculateUsedDays(2015));
		assertEquals(30, (int) employee.calculateRemainingDays(2016));
		assertEquals(0, (int) employee.calculateUsedDays(2016));
	}
}
