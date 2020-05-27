package com.example.arun.springguru.springmvc.controllers;

import com.example.arun.springguru.springmvc.domain.Product;
import com.example.arun.springguru.springmvc.services.ProductService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductControllerTest {

    @Mock //Mockito Mock object
    private ProductService productService;

    @InjectMocks //setups up controller, and injects mock objects into it.
    private ProductController productController;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this); //initilizes controller and mocks
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testList() throws Exception{

        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());

        //specific Mockito interaction, tell stub to return list of products
        Mockito.when(productService.listAll()).thenReturn((List) products); //need to strip generics to keep Mockito happy.

        mockMvc.perform(MockMvcRequestBuilders.get("/product/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("product/list"))
                .andExpect(MockMvcResultMatchers.model().attribute("products", Matchers.hasSize(2)));

    }

    @Test
    public void testShow() throws Exception{
        Integer id = 1;

        //Tell Mockito stub to return new product for ID 1
        Mockito.when(productService.getById(id)).thenReturn(new Product());

        mockMvc.perform(MockMvcRequestBuilders.get("/product/show/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("product/show"))
                .andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.instanceOf(Product.class)));
    }

    @Test
    public void testEdit() throws Exception{
        Integer id = 1;

        //Tell Mockito stub to return new product for ID 1
        Mockito.when(productService.getById(id)).thenReturn(new Product());

        mockMvc.perform(MockMvcRequestBuilders.get("/product/edit/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("product/productform"))
                .andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.instanceOf(Product.class)));
    }

    @Test
    public void testNewProduct() throws Exception {
        Integer id = 1;

        //should not call service
        Mockito.verifyNoInteractions(productService);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("product/productform"))
                .andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.instanceOf(Product.class)));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        // set up object for return comparison
        Integer id = 1;
        String description = "Test Description";
        BigDecimal price = new BigDecimal("12.00");
        String imageUrl = "example.com";

        Product returnProduct = new Product();
        returnProduct.setId(id);
        returnProduct.setDescription(description);
        returnProduct.setPrice(price);
        returnProduct.setImageUrl(imageUrl);

        Mockito.when(productService.saveOrUpdate(ArgumentMatchers.any())).thenReturn(returnProduct);

        mockMvc.perform(MockMvcRequestBuilders.post("/product")
                // mimic form parameter bindings
                .param("id", "1")
                .param("description", description)
                .param("price", "12.00")
                .param("imageUrl", "example.com"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/product/show/1"))
                .andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.instanceOf(Product.class)))
                .andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("id", Matchers.is(id))))
                .andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("description", Matchers.is(description))))
                .andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("price", Matchers.is(price))))
                .andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("imageUrl", Matchers.is(imageUrl))));

        //verify properties of bound object
        // test for form - java object unmarshalling in controller
        ArgumentCaptor<Product> boundProduct = ArgumentCaptor.forClass(Product.class);
        Mockito.verify(productService).saveOrUpdate(boundProduct.capture());

        assertEquals(id, boundProduct.getValue().getId());
        assertEquals(description, boundProduct.getValue().getDescription());
        assertEquals(price, boundProduct.getValue().getPrice());
        assertEquals(imageUrl, boundProduct.getValue().getImageUrl());
    }
}
