package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.impl.DepartmentDaoJDBC;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();	
		
		System.out.println("==== Test 1: Department FindById =====");
		
		Department dep = departmentDao.findById(2);
		
		System.out.println(dep);
		System.out.println();
		
		System.out.println("==== Test 2: Department FindAll =====");
		
		List<Department> list = departmentDao.findAll();
		
		for (Department department : list) {
			System.out.println(department);
		}
		
		System.out.println("==== Test 3: Department Insert =====");
		System.out.printf("Name new Department: ");
		String name = sc.nextLine();
		Department depinsert = new Department(null, name);
		departmentDao.insert(depinsert);
		
		System.out.println("Inserted! New Department Id " + depinsert.getId());
		
		System.out.println("==== Test 3: Department Update =====");
		dep = departmentDao.findById(2);
		dep.setName("Marketing");
		departmentDao.update(dep);
		
		System.out.println("Updade completed!");
		
		System.out.println("==== Test 4: Department Delete =====");
		
		departmentDao.deleteById(10);
		
		System.out.println("Delete Completed");
		
		
	}

}
