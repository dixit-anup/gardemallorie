package com.appspot.gardemallorie.service.impl;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;
import static org.springframework.data.domain.Sort.Direction.ASC;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.repository.BabySittingRepository;
import com.appspot.gardemallorie.service.BabySittingService;

@Service
public class BabySittingServiceImpl implements BabySittingService {

	static private final String DAY_PROPERTY = "day";
	static private final Sort SORT_BY_DAY_ASC = new Sort(ASC, DAY_PROPERTY);
	
    @Autowired
    BabySittingRepository babySittingRepository;
    
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	@Transactional
	public void copyBabySittingUntil(Date date, Long id) {
		
    	BabySitting babySitting = findBabySitting(id);
        Calendar currentDay = findNextBabySittingDay();
        Calendar endDay = Calendar.getInstance();
        
    	endDay.setTime(date);
    	
    	logger.debug("copying babySitting {} from {} to {}", babySitting, currentDay, endDay);
        
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
	@Transactional
	public void deleteBabySitting(Long id) {
		babySittingRepository.delete(id);
	}

	@Override
	public Page<BabySitting> findAllBabySittings(Pageable pageable) {
		return babySittingRepository.findAll(pageable);
	}
	
	@Override
    public BabySitting findBabySitting(Long id) {
        return babySittingRepository.findOne(id);
    }
    
	@Override
	@SuppressWarnings("deprecation")
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
	public Calendar findNextBabySittingDay() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(babySittingRepository.findLastDay());
		calendar.add(DAY_OF_MONTH, 1);
    	return calendar;
	}
	
	@Override
	public Page<BabySitting> findNextBabySittings(Pageable pageable) {
		return babySittingRepository.findByDayGreaterThanEqual(new Date(), pageable);
	}

	@Override
	@Transactional
    public void saveBabySitting(BabySitting babySitting) {
        babySittingRepository.save(babySitting);
    }
    
	@Override
	@Transactional
    public BabySitting updateBabySitting(BabySitting babySitting) {
        return babySittingRepository.save(babySitting);
    }

}
