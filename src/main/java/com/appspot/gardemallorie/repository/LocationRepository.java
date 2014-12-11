package com.appspot.gardemallorie.repository;

import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

import com.appspot.gardemallorie.domain.Location;

@RooJpaRepository(domainType = Location.class)
public interface LocationRepository {
}
