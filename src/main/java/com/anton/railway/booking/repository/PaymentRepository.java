package com.anton.railway.booking.repository;

import com.anton.railway.booking.entity.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

}
