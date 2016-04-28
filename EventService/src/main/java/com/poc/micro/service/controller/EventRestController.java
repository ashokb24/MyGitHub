package com.poc.micro.service.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poc.micro.service.EventAPIProducer;
import com.poc.micro.service.EventMessageResponse;
import com.poc.micro.service.dao.EventDAOService;
import com.poc.micro.service.model.Event;
import com.poc.micro.service.model.EventMessageRequest;

@RestController
public class EventRestController {

	@Autowired
	EventAPIProducer eventAPIProducer;

	@Autowired
	EventDAOService eventDaoService;

	@RequestMapping(path = "/produceEvent", method = RequestMethod.POST)
	public EventMessageResponse produceEvent(@RequestBody EventMessageRequest msg)
			throws ExecutionException, InterruptedException {
		return eventAPIProducer.sendEvent(msg);
	}

	@RequestMapping(path = "/fetchAllEvents", method = RequestMethod.GET)
	public List<Event> fetchAllEvents() {
		return eventDaoService.getAllEvents();
	}
	
	@RequestMapping(path = "/fetchAllEvents/{eventSourceName}", method = RequestMethod.GET)
	public List<Event> fetchAllEvents(@RequestHeader("eventSourceName") String eventSournceName) {
		return eventDaoService.getAllEventsByEventSourceName(eventSournceName);
	}
	
	@RequestMapping(path = "/fetchAllEventsByDuration", method = RequestMethod.GET)
	public List<Event> fetchAllEventsByTime(@RequestHeader("eventStartTime") String eventStartTime,@RequestHeader("eventEndTime") String eventEndTime){
		return eventDaoService.getAllEventsByDuration(eventStartTime, eventEndTime);
	}

	@RequestMapping(path = "/deleteAllEvents", method = RequestMethod.DELETE)
	public String deleteAllEvents() {
		eventDaoService.deleteAllEvents();
		return "Events Deleted Successfully";
	}
  
}
