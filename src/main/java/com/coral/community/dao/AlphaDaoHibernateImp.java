package com.coral.community.dao;

import org.springframework.stereotype.Repository;

@Repository("alphaHibernate")
public class AlphaDaoHibernateImp implements  AlphaDao {
    @Override
    public String select() {
        return "Hibernate";
    }
}
