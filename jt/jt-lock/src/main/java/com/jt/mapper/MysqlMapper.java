package com.jt.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

public interface MysqlMapper {
	@Insert("insert into mysqllock(id) values(1)")
	void insert();
	@Delete("delete from mysqllock where id = 1")
	void delete();
}
