package de.mazdermind.urlaub.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import de.mazdermind.urlaub.model.Employee;

public class EmployeeModelTest {
	@Test
	public void testCreateEmployee() {
		Employee e = new Employee();
		e.setId(42);
		e.setName("Donald");
		e.setCountry("DE");
		e.setFederalState("he");
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

		assertEquals(30, (int)e.getAvailableDays());
		assertEquals(0, (int)e.getAvailableDays(1999));
		assertEquals(0, (int)e.getAvailableDays(2004));
		assertEquals(15, (int)e.getAvailableDays(2005));
		assertEquals(30, (int)e.getAvailableDays(2006));
		assertEquals(30, (int)e.getAvailableDays(2007));
		assertEquals(30, (int)e.getAvailableDays(2015));
	}

	@Test
	public void testDaysWithoutEntranceYear() {
		Employee e = new Employee();

		// The Employee year the employee entered is unknown.
		e.setAvailableDaysInYearOfEntrance(15);
		e.setAvailableDays(30);

		assertEquals(30, (int)e.getAvailableDays());
		assertEquals(30, (int)e.getAvailableDays(1999));
		assertEquals(30, (int)e.getAvailableDays(2004));
		assertEquals(30, (int)e.getAvailableDays(2005));
		assertEquals(30, (int)e.getAvailableDays(2006));
		assertEquals(30, (int)e.getAvailableDays(2007));
		assertEquals(30, (int)e.getAvailableDays(2015));
	}
}
