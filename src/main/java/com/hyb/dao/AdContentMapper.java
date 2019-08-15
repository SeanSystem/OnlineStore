package com.hyb.dao;

import com.hyb.pojo.AdContent;
import com.hyb.pojo.AdContentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdContentMapper {
    int countByExample(AdContentExample example);

    int deleteByExample(AdContentExample example);

    int deleteByPrimaryKey(String id);

    int insert(AdContent record);

    int insertSelective(AdContent record);

    List<AdContent> selectByExample(AdContentExample example);

    AdContent selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AdContent record, @Param("example") AdContentExample example);

    int updateByExample(@Param("record") AdContent record, @Param("example") AdContentExample example);

    int updateByPrimaryKeySelective(AdContent record);

    int updateByPrimaryKey(AdContent record);
}