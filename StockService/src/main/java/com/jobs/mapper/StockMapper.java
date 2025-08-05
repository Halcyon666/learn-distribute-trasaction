package com.jobs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jobs.pojo.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StockMapper extends BaseMapper<Stock> {

    //建库存量
    @Update("update tb_stock set `count` = `count` - #{count} where goods_id = #{gid}")
    int minusCount(@Param("gid") String gid, @Param("count") int count);

    //加库存量
    @Update("update tb_stock set `count` = `count` + #{count} where goods_id = #{gid}")
    int addCount(@Param("gid") String gid, @Param("count") int count);
}
