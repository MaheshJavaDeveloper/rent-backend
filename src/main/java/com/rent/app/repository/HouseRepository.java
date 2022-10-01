package com.rent.app.repository;

import com.rent.app.models.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    Optional<List<House>> findByHouseOwnerId(Long houseOwnerId);
}
