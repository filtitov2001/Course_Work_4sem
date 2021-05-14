package ru.mirea.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByCustomerNameAndCustomerEmailAndCustomerNumber(String customerName, String email, String number);
}
