package com.appspot.gardemallorie.repository;
import com.appspot.gardemallorie.domain.Location;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

@RooJpaRepository(domainType = Location.class)
public interface LocationRepository {

}
