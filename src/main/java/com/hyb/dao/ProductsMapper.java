package com.hyb.dao;

import com.hyb.pojo.Products;
import com.hyb.pojo.ProductsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductsMapper {
    int countByExample(ProductsExample example);

    int deleteByExample(ProductsExample example);

    int deleteByPrimaryKey(String pid);

    int insert(Products record);

    int insertSelective(Products record);

    List<Products> selectByExampleWithBLOBs(ProductsExample example);

    List<Products> selectByExample(ProductsExample example);

    Products selectByPrimaryKey(String pid);

    int updateByExampleSelective(@Param("record") Products record, @Param("example") ProductsExample example);

    int updateByExampleWithBLOBs(@Param("record") Products record, @Param("example") ProductsExample example);

    int updateByExample(@Param("record") Products record, @Param("example") ProductsExample example);

    int updateByPrimaryKeySelective(Products record);

    int updateByPrimaryKeyWithBLOBs(Products record);

    int updateByPrimaryKey(Products record);
    
    //查询首页所需的最新商品
    List<Products> getNewestProducts(int num);
  //查询首页所需的最新商品
    List<Products> getHotProducts(int num);
}