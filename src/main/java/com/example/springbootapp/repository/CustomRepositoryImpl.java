package com.example.springbootapp.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.springbootapp.model.EmployeeDetails;
import com.mongodb.client.result.UpdateResult;
import org.springframework.stereotype.Repository;

@Repository
public class CustomRepositoryImpl implements CustomRepository {

    final MongoTemplate mongoTemplate;

	public CustomRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

    @Override
    public void updateEmployeeAge(String name, float newAge) {

		Query query = new Query(Criteria.where("name").is(name));
		Update update = new Update();
		update.set("age", newAge);
		
		UpdateResult result = mongoTemplate.updateFirst(query, update, EmployeeDetails.class);
        System.out.println(result.getModifiedCount() + " document(s) updated..");
	}
}