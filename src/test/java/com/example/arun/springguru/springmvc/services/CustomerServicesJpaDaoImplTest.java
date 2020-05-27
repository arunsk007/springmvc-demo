package com.example.arun.springguru.springmvc.services;

import com.example.arun.springguru.springmvc.config.JpaIntegrationConfig;
import com.example.arun.springguru.springmvc.domain.Customer;
import com.example.arun.springguru.springmvc.domain.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(JpaIntegrationConfig.class)
@ActiveProfiles("jpadao")
public class CustomerServicesJpaDaoImplTest {

    @Autowired
    private CustomerService customerService;

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Test
    public void testListAll() {
        List<Customer> customers = (List<Customer>) customerService.listAll();
        assert customers.size()==3;
        Assert.assertEquals(customers.size(),3);
    }
}
