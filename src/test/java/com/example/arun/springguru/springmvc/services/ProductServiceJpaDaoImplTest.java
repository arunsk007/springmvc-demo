package com.example.arun.springguru.springmvc.services;

import com.example.arun.springguru.springmvc.config.JpaIntegrationConfig;
import com.example.arun.springguru.springmvc.domain.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(JpaIntegrationConfig.class)
@ActiveProfiles("jpadao")
public class ProductServiceJpaDaoImplTest {

    @Autowired
    private ProductService productService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Test
    public void testListAll() {
        List<Product> products = (List<Product>) productService.listAll();
        assert products.size()==5;
        Assert.assertEquals(products.size(),5);
    }

    @Test
    public void testGetById() {


    }
}
