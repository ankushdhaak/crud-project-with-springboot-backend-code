package net.javaguides.springboot.controller;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.ListEmp;
import net.javaguides.springboot.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin( origins = "http://localhost:4200")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//1-get all employee Api
	@RequestMapping("/employees")
	public List<Employee> getAllEmployees(){
		List<Employee> empList = employeeRepository.findAll();
		return empList;
	}
	
	//2- Insert employees Api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {	
		Employee emp= employeeRepository.save(employee);
		return emp;	
	}
	
	//3-save all employee
	@PostMapping("/employee")
	public List<Employee> saveAllEmp(@RequestBody  ListEmp listEmp){
		List<Employee> saveEmployeeList =employeeRepository.saveAll(listEmp.getListEmp());
		return saveEmployeeList;	
	}
	
	//- 4get employee by id api
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
		Employee employee=employeeRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Eemploye not exit with id" +  id));
		return ResponseEntity.ok(employee);
			
	}
	//- 5 update employee api
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable long id ,@RequestBody  Employee employee1){
		Employee employee=employeeRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Eemploye not exit with id" +id));
		employee.setFirstName(employee1.getFirstName());
		employee.setLastName(employee1.getLastName());
		employee.setEmail(employee1.getEmail());
		Employee employeeDetails=employeeRepository.save(employee);
		return ResponseEntity.ok(employeeDetails);
	}
	
	//- 6 Delete api by id
	@DeleteMapping("/employees/{id}")
	public String deleteEmployeeById(@PathVariable long id)
	{
		Employee getEmp=employeeRepository.getById(id);
		employeeRepository.delete(getEmp);	
		return  "record deleted successfully";
	}
	
	//-7 Delete all employee
	@DeleteMapping("/employees")
	public String deleteAllEmployes() {
		this.employeeRepository.deleteAll();
		return "Deleted all employees";
	}
	
	
}
	

