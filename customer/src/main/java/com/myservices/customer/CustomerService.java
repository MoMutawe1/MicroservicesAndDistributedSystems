package com.myservices.customer;

import com.myservices.clients.fraud.FraudCheckResponse;
import com.myservices.clients.fraud.FraudClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService{

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;

    public void registerCustomer(CustomerRegistrationRequest request){
        Customer customer = Customer.builder()
                .first_name(request.first_name())
                .last_name(request.last_name())
                .email(request.email())
                .build();

        // todo: check if email valid
        // todo: check if email not taken

        customerRepository.saveAndFlush(customer);
        // todo: send a notification
        // check if fraudster without using Feign
        // FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
        // "http://FRAUD/api/v1/fraud-check/{customerId}",
        // FraudCheckResponse.class,
        // customer.getId()
        // );

        // check if fraudster without using Feign
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster user");
        }
    }
}
