package com.org.spemajorbackend.service;

import com.org.spemajorbackend.dto.AmountBalancesResponse;
import com.org.spemajorbackend.entity.Customer;
import com.org.spemajorbackend.entity.Mess;
import com.org.spemajorbackend.repository.CustomerRepository;
import com.org.spemajorbackend.repository.MessRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CalculatorService {

    private final MessRepository messRepository;
    private final CustomerRepository customerRepository;
    public CalculatorService(MessRepository messRepository, CustomerRepository customerRepository) {
        this.messRepository = messRepository;
        this.customerRepository = customerRepository;
    }

//    calculate amount to be paid by the customer
    public float calculateAmount(String messId, Date startDate, Date endDate, String customerID)
    {
        if(startDate.after(endDate)){
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        else{
            long diff = endDate.getTime() - startDate.getTime();
            long days =  TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            Mess mess  = messRepository.findById(messId).orElseThrow(
                    () -> new UsernameNotFoundException("No mess exist owned by: "+messId)
            );

            Customer customer  = customerRepository.findById(customerID).orElseThrow(
                    () -> new UsernameNotFoundException("No customer exist by name of: "+ customerID)
            );

            int pricing = Integer.parseInt(mess.getPricing());
            float perDayPrice = (float) pricing / 30;

            float total = days * perDayPrice;
            return Math.round(total);
        }
    }

//    calculate total balances and remaining
    public AmountBalancesResponse calculateTotal(String ownerId)
    {
        Mess mess  = messRepository.findById(ownerId).orElseThrow(
                () -> new UsernameNotFoundException("No mess exist owned by:"+ownerId)
        );
        List<Customer> customers = mess.getCustomers();

        if(customers.isEmpty())
            return new AmountBalancesResponse(0.0, 0.0, 0.0);
        else
        {
            int totalCustomers = customers.size();
            int pricingPerMonth = Integer.parseInt(mess.getPricing());

            Double total = (double) (totalCustomers*pricingPerMonth);
            Double paid=0.0;
            for(Customer c: customers)
            {
//                if(c.getPaid())
                    paid = paid + (double) pricingPerMonth;
//                else
//                    paid = paid + 0.0;
            }

            Double rem = total - paid;
            return new AmountBalancesResponse(total, paid, rem);
        }
    }

//    BMI Calculator
    public Double calculateBMI(Double height, Double weight)
    {
        if(height<=0 || weight<=0)
            throw new IllegalArgumentException("Only positive values are expected as weight and height");
        Double bmi = weight / (height*height);
        return bmi;
    }
}
