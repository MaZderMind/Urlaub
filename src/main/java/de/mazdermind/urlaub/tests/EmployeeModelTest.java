package de.mazdermind.urlaub.tests;

import org.junit.Test;

import de.mazdermind.urlaub.model.Employee;
import de.mazdermind.urlaub.model.FederalState;

public class EmployeeModelTest {

	@Test
	public void testCreateEmployee() {
		Employee e = new Employee();
		e.setId(42);
		e.setName("Donald");
		e.setState(FederalState.HE);
	}

	@Test
	public void testDaysInYearOfEntrance() {
		Employee e = new Employee();

		// The Employee entered the Company somewhere in the middle of 2005
		// Because the Year was already half over, he only had 14 Days left.
		// (the calculation of this number is out of the scope of this test)
		// in the Years after that he'll have 30 days available.
		e.setYearOfEntrance(2005);
		e.setAvailableDaysInYearOfEntrance(15);
		e.setAvailableDays(30);

		assert e.getAvailableDays() == 30;
		assert e.getAvailableDays(1999) == 0;
		assert e.getAvailableDays(2004) == 0;
		assert e.getAvailableDays(2005) == 15;
		assert e.getAvailableDays(2006) == 30;
		assert e.getAvailableDays(2007) == 30;
		assert e.getAvailableDays(2015) == 30;
	}

	@Test
	public void testDaysWithoutEntranceYear() {
		Employee e = new Employee();

		// The Employee year the employee entered is unknown.
		e.setAvailableDaysInYearOfEntrance(15);
		e.setAvailableDays(30);

		assert e.getAvailableDays() == 30;
		assert e.getAvailableDays(1999) == 30;
		assert e.getAvailableDays(2004) == 30;
		assert e.getAvailableDays(2005) == 30;
		assert e.getAvailableDays(2006) == 30;
		assert e.getAvailableDays(2007) == 30;
		assert e.getAvailableDays(2015) == 30;
	}
}
