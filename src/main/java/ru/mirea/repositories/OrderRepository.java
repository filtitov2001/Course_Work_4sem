package ru.mirea.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.models.Customer;
import ru.mirea.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    void deleteAllByCustomer(Customer customer);
}
