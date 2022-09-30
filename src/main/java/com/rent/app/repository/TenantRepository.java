package com.rent.app.repository;

import com.rent.app.models.Rent;
import com.rent.app.models.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Optional<List<Tenant>> findByHouseOwnerId(Long houseOwnerId);
}
