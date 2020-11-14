package com.coral.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class AlphaDAOmybatis implements  AlphaDao{
    @Override
    public String select() {
        return "mybatis";
    }
}
