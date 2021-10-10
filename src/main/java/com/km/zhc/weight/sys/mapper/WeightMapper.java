package com.km.zhc.weight.sys.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface WeightMapper {

    @Insert("insert into weight(user_name,weight,waistline,statistics_date)values(#{userName},#{weight},#{waistline},curdate())")
    int addWeight(@Param("userName") String userName, @Param("weight") Double weight, @Param("waistline") Double waistline);

    @Update("update weight set is_delete='1' where user_name=#{userName} and statistics_date=curdate()")
    int updateTodayInvalid(@Param("userName") String userName);

    @Select("select user_name,date_format(statistics_date,'%Y-%m-%d') statistics_date,weight,waistline from weight where user_name=#{userName} and is_delete='0' and statistics_date>=#{statisticsDate} order by id desc limit 100")
    List<Map<String,Object>> getRecent90DaysList(@Param("userName") String userName,@Param("statisticsDate") String statisticsDate);

}
