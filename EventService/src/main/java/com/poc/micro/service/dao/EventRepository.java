package com.poc.micro.service.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<EventDAOModel, String> {
	public List<EventDAOModel> findEventsByEventSourceName(String eventSourceName);
}
