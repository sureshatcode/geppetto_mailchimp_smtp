package com.geppetto.mailchimp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.geppetto.mailchimp.dto.Subscriber;
import com.geppetto.mailchimp.service.SubscriberService;

/**
 * @author Suresh Palanisamy<br>
 *         <p>
 *         Date Created: 28-July-2015
 *         </p>
 *
 */

@Controller
@RequestMapping(value = "/subscriber")
public class SubscriberController {

	private static final Logger LOG = LoggerFactory.getLogger(SubscriberController.class);

	@Autowired
	private SubscriberService subscriberService;

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	@RequestMapping(value = "/findAllSubscribers", method = RequestMethod.GET)
	@ResponseBody
	public List<Subscriber> findAllSubscribers() throws Exception {
		LOG.debug("Find all subscribers method has been initialized in the controller layer!");
		return this.subscriberService.findAllSubscribers();
	}
	
	@RequestMapping(value = "/createSubscribers", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public List<Subscriber> createSubscribers(@RequestBody List<Subscriber> subscribers) throws Exception {
		LOG.debug("Create subscribers method has been initialized in the controller layer!");
		return this.subscriberService.createSubscribers(subscribers);
	}

	@RequestMapping(value = "/updateSubscriber", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public Subscriber updateSubscriber(@RequestBody Subscriber subscriber) throws Exception {
		LOG.debug("Update subscribers method has been initialized in the controller layer!");
		return this.subscriberService.updateSubscriber(subscriber);
	}

	@RequestMapping(value = "/deleteSubscriber", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteSubscriber(@RequestParam("subscriberSno") long subscriberSno) throws Exception {
		LOG.debug("Delete subscriber method has been initialized in the controller layer!");
		return this.subscriberService.deleteSubscriber(subscriberSno);
	}
}