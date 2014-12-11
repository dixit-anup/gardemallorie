package com.appspot.gardemallorie.repository;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

import com.appspot.gardemallorie.domain.BabySitter;

@RooJpaRepository(domainType = BabySitter.class)
public interface BabySitterRepository {
}
