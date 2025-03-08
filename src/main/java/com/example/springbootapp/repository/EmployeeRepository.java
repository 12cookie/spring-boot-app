package com.example.springbootapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.springbootapp.model.EmployeeDetails;

public interface EmployeeRepository extends MongoRepository <EmployeeDetails, String>  {

    @Query("{name:'?0'}")
	EmployeeDetails findEmployeeByName(String name);
	
	@Query(value = "{education:'?0'}", fields = "{'name' : 1, 'age' : 1}")
	List<EmployeeDetails> findAll(String education);
	
	public long count();
}