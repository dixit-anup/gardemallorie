package com.appspot.gardemallorie.service.impl;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.service.BabySittingService;

public class BabySittingServiceImpl implements BabySittingService {

	static private final String DAY_PROPERTY = "day";
	static private final Sort SORT_BY_DAY_ASC = new Sort(ASC, DAY_PROPERTY);
	static private final Sort SORT_BY_DAY_DESC = new Sort(DESC, DAY_PROPERTY);
	
	@Override
	public void copyBabySittingUntil(Date date, Long id) {
		
    	BabySitting babySitting = findBabySitting(id);
        List<BabySitting> babySittings = babySittingRepository.findAll(SORT_BY_DAY_DESC);
        Calendar currentDay = Calendar.getInstance();
        Calendar endDay = Calendar.getInstance();
        
        currentDay.setTime(babySittings.get(0).getDay());
    	currentDay.add(DAY_OF_MONTH, 1);

    	endDay.setTime(date);
        
        while (currentDay.compareTo(endDay) <= 0) {
        	
        	currentDay.add(DAY_OF_MONTH, 1);
        	int currentDayOfWeek = currentDay.get(DAY_OF_WEEK);

        	// Skip saturdays and sundays
        	if (currentDayOfWeek == SATURDAY || currentDayOfWeek == SUNDAY) {
        		continue;
        	}
        	
        	BabySitting copy = new BabySitting();
        	copy.setBabySitter(babySitting.getBabySitter());
        	copy.setBack(babySitting.getBack());
        	copy.setDay(currentDay.getTime());
        	copy.setGo(babySitting.getGo());
        	copy.setLocation(babySitting.getLocation());
        	copy.setPlannedBeginning(babySitting.getPlannedBeginning());
        	copy.setPlannedEnd(babySitting.getPlannedEnd());
        	saveBabySitting(copy);
        }
        
	}
	

	@Override
	@Transactional(propagation = NOT_SUPPORTED)
	public Page<BabySitting> findAllBabySittings(Pageable pageable) {
		return babySittingRepository.findAll(pageable);
	}
	
	@Override
	@Transactional(propagation = NOT_SUPPORTED)
	public List<BabySitting> findExtraCharges() {

		List<BabySitting> babySittings = babySittingRepository.findAll(new PageRequest(0, Integer.MAX_VALUE, SORT_BY_DAY_ASC)).getContent();
        List<BabySitting> extraChargesList = new ArrayList<BabySitting>();
        BabySitting extraCharges = null;
        
        for (int i = 0; i < babySittings.size(); i++) {
        
        	BabySitting babySitting = babySittings.get(i);
            Date babySittingDay = babySitting.getDay();
            
            if (i == 0 || babySittingDay.getYear() != extraCharges.getDay().getYear() || babySittingDay.getMonth() != extraCharges.getDay().getMonth()) {
                extraCharges = new BabySitting();
                extraCharges.setDay((Date) babySittingDay.clone());
                extraCharges.getDay().setDate(1);
                extraChargesList.add(extraCharges);
                // Childcare vouchers
                extraCharges.setChildcareVouchers(babySitting.getChildcareVouchers());
                // Extra hours
                extraCharges.setExtraHours(babySitting.getExtraHours());
            } else {
                // Childcare vouchers
                extraCharges.setChildcareVouchers(extraCharges.getChildcareVouchers() + babySitting.getChildcareVouchers());
                // Extra hours
                extraCharges.setExtraHours(extraCharges.getExtraHours() + babySitting.getExtraHours());
            }
        }

		return extraChargesList;
	}
	
	
	@Override
	@Transactional(propagation = NOT_SUPPORTED)
	public Page<BabySitting> findNextBabySittings(Pageable pageable) {
		return babySittingRepository.findByDayGreaterThanEqual(new Date(), pageable);
	}

}
