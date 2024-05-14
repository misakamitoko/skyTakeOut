package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);


    @Insert("insert into category (id, name, create_time, update_time, create_user, update_user, status)" +
    "values"+
    "(#{id} ,#{name}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser}, #{status})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Category category);

    @Delete("delete from category where id = #{id}")
    void delete(Long id);

    @Select("select * from category where type = #{type}")
    List<Category> list(Integer type);
}
