package com.appspot.gardemallorie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.service.CalendarService;

privileged aspect BabySittingServiceImpl_Calendar {
    
	declare precedence: com.appspot.gardemallorie.service.impl.BabySittingServiceImpl_Roo_Service;

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
	
	void around(BabySittingServiceImpl babySittingService, BabySitting babySitting):
        args(babySitting)
        && execution( public void BabySittingServiceImpl.deleteBabySitting(BabySitting) )
		&& target(babySittingService)
    {
		babySittingService.calendarService.deleteEvents(babySitting);

		proceed(babySittingService, babySitting);
	}
	
}
