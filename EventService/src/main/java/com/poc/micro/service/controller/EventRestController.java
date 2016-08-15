package com.poc.micro.service.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poc.micro.service.EventMessageResponse;
import com.poc.micro.service.EventService;
import com.poc.micro.service.dao.EventDAOService;
import com.poc.micro.service.model.Event;
import com.poc.micro.service.model.EventMessageRequest;

@RestController
public class EventRestController {

	@Autowired
	EventDAOService eventDaoService;
	
	@Autowired
	EventService eventService;

	@RequestMapping(path = "/events", method = RequestMethod.POST)
	public EventMessageResponse produceEvent(@RequestBody EventMessageRequest msg)
			throws ExecutionException, InterruptedException {
		return eventService.triggerEvent(msg);
	}

	@RequestMapping(path = "/events", method = RequestMethod.GET)
	public List<Event> fetchAllEvents() {
		return eventService.getAllEvents();
	}
	
	@RequestMapping(path = "/events/{eventSourceName}", method = RequestMethod.GET)
	public List<Event> fetchAllEvents(@RequestHeader("eventSourceName") String eventSournceName) {
		return eventService.getAllEventsByEventSourceName(eventSournceName);
	}
	
	@RequestMapping(path = "/events/{eventStartTime}/{eventEndTime}", method = RequestMethod.GET)
	public List<Event> fetchAllEventsByTime(@RequestHeader("eventStartTime") String eventStartTime,@RequestHeader("eventEndTime") String eventEndTime){
		return eventService.getAllEventsByDuration(eventStartTime, eventEndTime);
	}

  
}
