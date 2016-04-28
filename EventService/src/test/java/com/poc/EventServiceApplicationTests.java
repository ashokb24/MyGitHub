package com.poc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.poc.micro.service.EventServiceApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EventServiceApplication.class)
@WebAppConfiguration
public class EventServiceApplicationTests {

	@Test
	public void contextLoads() {
	}

}
