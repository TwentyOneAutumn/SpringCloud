package com.generator.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TableMapper {

    @Select("SELECT table_name FROM information_schema.tables WHERE table_schema = #{dbName}")
    List<String> getTableNameAll(@Param("dbName") String dbName);
}
