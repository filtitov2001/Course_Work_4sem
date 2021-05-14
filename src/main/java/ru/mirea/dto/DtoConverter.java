package ru.mirea.dto;

import org.springframework.stereotype.Component;
import ru.mirea.models.Customer;
import ru.mirea.models.Order;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoConverter {
    public List<OrderResponse> toOrderResponseList(List<Order> orders){
        return orders.stream().map(order -> {
            OrderResponse response = new OrderResponse();
            response.setId(order.getId());
            response.setCakeName(order.getCakeName());
            response.setCreamName(order.getCreamName());
            response.setFillerName(order.getFillerName());
            response.setDate(order.getDate());
            response.setComment(order.getComment());
            return response;
        }).collect(Collectors.toList());
    }

    public CustomerResponse toCustomerResponse(Customer customer){
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setCustomerName(customer.getCustomerName());
        response.setCustomerEmail(customer.getCustomerEmail());
        response.setCustomerNumber(customer.getCustomerNumber());
        response.setOrders(toOrderResponseList(customer.getOrders()));
        return response;
    }

    public List<CustomerResponse> toCustomerResponseList(List<Customer> customers){
        return customers.stream()
                .map(this::toCustomerResponse)
                .collect(Collectors.toList());
    }

}
