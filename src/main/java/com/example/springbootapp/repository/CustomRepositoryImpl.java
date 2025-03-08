package com.example.springbootapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.springbootapp.model.EmployeeDetails;
import com.mongodb.client.result.UpdateResult;

public class CustomRepositoryImpl implements CustomRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @SuppressWarnings("unused")
    @Override
    public void updateEmployeeAge(String name, float newAge) {

		Query query = new Query(Criteria.where("name").is(name));
		Update update = new Update();
		update.set("age", newAge);
		
		UpdateResult result = mongoTemplate.updateFirst(query, update, EmployeeDetails.class);		
		if(result == null)
			System.out.println("No documents updated");
		else
			System.out.println(result.getModifiedCount() + " document(s) updated..");
	}
}