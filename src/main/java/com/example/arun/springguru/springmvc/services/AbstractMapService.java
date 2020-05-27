package com.example.arun.springguru.springmvc.services;

import com.example.arun.springguru.springmvc.domain.DomainObject;
import com.example.arun.springguru.springmvc.domain.Product;

import java.util.*;

public abstract class AbstractMapService {

    protected Map<Integer, DomainObject> domainMap;

    public AbstractMapService() {
        domainMap = new HashMap<>();
        loadDomainObjects();
    }

    public List<DomainObject> listAll() {
        return new ArrayList<>(domainMap.values());
    }

    protected DomainObject getById(Integer id) {
        return domainMap.get(id);
    }

    protected DomainObject saveOrUpdate(DomainObject domainObject) {
        if(null != domainObject){
            if(null==domainObject.getId()){
                domainObject.setId(getNextId());
            }
            domainMap.put(domainObject.getId(),domainObject);
        }else{
            throw new RuntimeException("Product cannot be null");
        }
        return domainObject;
    }

    protected void delete(Integer id) {
        domainMap.remove(id);
    }

    private Integer getNextId(){
        return Collections.max(domainMap.keySet())+1;
    }

    protected abstract void loadDomainObjects();


}
