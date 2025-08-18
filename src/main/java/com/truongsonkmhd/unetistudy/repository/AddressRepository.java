package com.truongsonkmhd.unetistudy.repository;

import com.truongsonkmhd.unetistudy.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    Address findByUserIdAndAddressType(UUID userId, Integer addressType);
}