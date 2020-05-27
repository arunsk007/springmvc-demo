package com.example.arun.springguru.springmvc.services;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public class BaseJpaServiceImpl {

    @PersistenceUnit
    protected EntityManagerFactory emf;

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }
}
