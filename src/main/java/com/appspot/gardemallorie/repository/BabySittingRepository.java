package com.appspot.gardemallorie.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

import com.appspot.gardemallorie.domain.BabySitting;

@RooJpaRepository(domainType = BabySitting.class)
public interface BabySittingRepository {

	@Query("SELECT babySitting FROM BabySitting babySitting")
	Page<BabySitting> pageAll(Pageable pageable);

	Page<BabySitting> findByDayGreaterThanEqual(Date day, Pageable pageable);

}
