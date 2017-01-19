package com.mbgen.model;

import org.apache.ibatis.annotations.Param;

public interface TestInterfaceDao {

    public int testMethod(@Param("number123123123") int number123123123);

}