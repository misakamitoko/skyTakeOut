package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PutMapping
    @ApiOperation("修改分类")
    public Result update(@RequestBody CategoryDTO categoryDTO){
        log.info("修改分类：{}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("查询分类列表：{}", categoryPageQueryDTO);
        PageResult page = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(page);
    }

    @PostMapping
    @ApiOperation("新增员工")
    public Result save(@RequestBody CategoryDTO categoryDTO){
        log.info("新增员工：{}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("删除分类")
    public Result delete( Long id){
        log.info("删除分类：{}", id);
        Boolean successed  = categoryService.delete(id);
        return successed ? Result.success() : Result.error("分类下面有关联套餐或菜品");
    }

    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用分类")
    public Result enableOrDisable(@PathVariable Integer status, Long id){
        log.info("启用禁用分类：{}", id);
        categoryService.enableOrDisable(id, status);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据类型查询套餐分类")
    public Result<List<Category>> list(Integer type){
        log.info("根据类型查询分类列表");
        List<Category> categories = categoryService.list(type);
        return Result.success(categories);
    }
}
