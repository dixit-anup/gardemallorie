package com.appspot.gardemallorie.repository;
import com.appspot.gardemallorie.domain.BabySitter;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

@RooJpaRepository(domainType = BabySitter.class)
public interface BabySitterRepository {
}
