package com.myservices.customer;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableAutoConfiguration
public record CustomerService(CustomerRepository customerRepository) {

    public void registerCustomer(CustomerRegistrationRequest request){
        Customer customer = Customer.builder()
                .first_name(request.first_name())
                .last_name(request.last_name())
                .email(request.email())
                .build();
        customerRepository.save(customer);
    }
}
