package com.ekolodey.spring_attestation.repositories;

import com.ekolodey.spring_attestation.models.Order;
import com.ekolodey.spring_attestation.models.OrderStatus;
import com.ekolodey.spring_attestation.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
    OrderStatus findByName(String name);
}
