package com.hyb.dao;

import com.hyb.pojo.IndexProduct;
import com.hyb.pojo.IndexProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IndexProductMapper {
    int countByExample(IndexProductExample example);

    int deleteByExample(IndexProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IndexProduct record);

    int insertSelective(IndexProduct record);

    List<IndexProduct> selectByExample(IndexProductExample example);

    IndexProduct selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IndexProduct record, @Param("example") IndexProductExample example);

    int updateByExample(@Param("record") IndexProduct record, @Param("example") IndexProductExample example);

    int updateByPrimaryKeySelective(IndexProduct record);

    int updateByPrimaryKey(IndexProduct record);
    
    List<IndexProduct> selectNewestProduct(int num);
    
    List<IndexProduct> selectHotestProduct(int num);
}