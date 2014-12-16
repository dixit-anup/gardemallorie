package com.appspot.gardemallorie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.service.CalendarService;

privileged aspect BabySittingServiceImpl_Calendar {
    
	@Autowired
	private CalendarService BabySittingServiceImpl.calendarService;

	Object around(BabySittingServiceImpl babySittingService, BabySitting babySitting):
        args(babySitting)
        && (
    		execution( public void BabySittingServiceImpl.saveBabySitting(BabySitting) ) ||
    		execution( public BabySitting BabySittingServiceImpl.updateBabySitting(BabySitting) )
		)
		&& target(babySittingService)
    {
		Object result = proceed(babySittingService, babySitting);

		babySittingService.calendarService.saveEvents(babySitting);
		
		return result;
	}
	
	void around(BabySittingServiceImpl babySittingService, Long id):
        args(id)
        && execution( public void BabySittingServiceImpl.deleteBabySitting(Long) )
		&& target(babySittingService)
    {
		BabySitting babySitting = babySittingService.findBabySitting(id);
		
		babySittingService.calendarService.deleteEvents(babySitting);

		proceed(babySittingService, id);
	}
	
}
