package com.poc.micro.service.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.poc.micro.service.model.Event;
import com.poc.micro.service.utils.DateUtil;
import com.poc.micro.service.utils.EventServiceConstants;

@Service
public class EventDAOService {
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	public void saveEvent(String eventSourceNode, String eventMessage, String applicationId) {
		EventDAOModel eventDao = new EventDAOModel();
		eventDao.setEventSourceName(eventSourceNode);
		eventDao.setEventMsg(eventMessage);
		eventDao.setEventCretTs(new Date());
		eventDao.setApplicationId(applicationId);
		eventRepository.insert(eventDao);
	}

	public List<Event> getAllEvents() {
		List<Event> eventsList = new ArrayList<Event>();
		List<EventDAOModel> eventsDaoList = eventRepository.findAll();
		for (int i = 0; i < eventsDaoList.size(); i++) {
			Event event = new Event();
			event.setEventSourceName(eventsDaoList.get(i).getEventSourceName());
			event.setEventMsg(eventsDaoList.get(i).getEventMsg());
			event.setId(eventsDaoList.get(i).getId());
			event.setEventCretTs(DateUtil.dateToString(eventsDaoList.get(i).getEventCretTs(),
					EventServiceConstants.FORMAT_YYYY_MM_DD_HH_mm_ss));
			event.setApplicationId(eventsDaoList.get(i).getApplicationId());
			eventsList.add(event);
		}
		return eventsList;
	}

	/**
	 * 
	 * @param eventSourceName
	 * @return
	 */
	public List<Event> getAllEventsByEventSourceName(String eventSourceName) {
		List<Event> eventsList = new ArrayList<Event>();
		List<EventDAOModel> eventsDaoList = eventRepository.findEventsByEventSourceName(eventSourceName);
		for (int i = 0; i < eventsDaoList.size(); i++) {
			Event event = new Event();
			event.setEventSourceName(eventsDaoList.get(i).getEventSourceName());
			event.setEventMsg(eventsDaoList.get(i).getEventMsg());
			event.setId(eventsDaoList.get(i).getId());
			event.setEventCretTs(DateUtil.dateToString(eventsDaoList.get(i).getEventCretTs(),
					EventServiceConstants.FORMAT_YYYY_MM_DD_HH_mm_ss));
			event.setApplicationId(eventsDaoList.get(i).getApplicationId());
			eventsList.add(event);
		}
		return eventsList;
	}

	/**
	 * 
	 * @param eventStartTime
	 * @param eventEndTime
	 * @return
	 */
	public List<Event> getAllEventsByDuration(String eventStartTime, String eventEndTime) {
		List<Event> eventsList = new ArrayList<Event>();
		Criteria criteria = Criteria.where("eventCretTs").gt(DateUtil.stringToDate(eventStartTime))
				.andOperator(Criteria.where("eventCretTs").lt(DateUtil.stringToDate(eventEndTime)));
		List<EventDAOModel> eventsDaoList = mongoTemplate.find(Query.query(criteria), EventDAOModel.class);
		for (int i = 0; i < eventsDaoList.size(); i++) {
			Event event = new Event();
			event.setEventSourceName(eventsDaoList.get(i).getEventSourceName());
			event.setEventMsg(eventsDaoList.get(i).getEventMsg());
			event.setId(eventsDaoList.get(i).getId());
			event.setEventCretTs(DateUtil.dateToString(eventsDaoList.get(i).getEventCretTs(),
					EventServiceConstants.FORMAT_YYYY_MM_DD_HH_mm_ss));
			event.setApplicationId(eventsDaoList.get(i).getApplicationId());
			eventsList.add(event);
		}
		return eventsList;
	}

	public void deleteAllEvents() {
		eventRepository.deleteAll();
	}
}