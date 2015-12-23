package de.mazdermind.urlaub.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import de.mazdermind.urlaub.model.Employee;

@Path("/employee")
public class EmployeeResource {
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Employee getEmployee(@PathParam("id") Integer id) {
		Employee employee = new Employee(id);
		employee.setName("Marc");
		return employee;
	}
}
