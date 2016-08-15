package com.poc.micro.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.micro.service.dao.EventDAOService;
import com.poc.micro.service.model.Event;
import com.poc.micro.service.model.EventMessageRequest;

@Service
public class EventService {
	@Autowired
	EventDAOService eventDaoService;
	
	@Autowired
	EventAPIProducer eventAPIProducer;
	
	@Autowired
	EventAPIConsumer eventAPIConsumer;
	
	public EventMessageResponse triggerEvent(EventMessageRequest msg)
			throws ExecutionException, InterruptedException {
		EventMessageResponse eventResponse = eventAPIProducer.sendEvent(msg);
		eventAPIConsumer.run();
		return eventResponse;
	}
	
	public List<Event> getAllEvents() {
		return eventDaoService.getAllEvents();
	}
	
	public List<Event> getAllEventsByEventSourceName(String eventSournceName) {
		return eventDaoService.getAllEventsByEventSourceName(eventSournceName);
	}
	
	public List<Event> getAllEventsByDuration( String eventStartTime,String eventEndTime){
		return eventDaoService.getAllEventsByDuration(eventStartTime, eventEndTime);
	}
	
	public String deleteAllEvents() {
		eventDaoService.deleteAllEvents();
		return "Events Deleted Successfully";
	}
}
