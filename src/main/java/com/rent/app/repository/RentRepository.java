package com.rent.app.repository;

import com.rent.app.models.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {
    Optional<List<Rent>> findByHouseNumber(Long houseNumber);

}
