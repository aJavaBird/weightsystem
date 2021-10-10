package com.km.zhc.weight.sys.service;

import com.km.zhc.weight.sys.mapper.WeightMapper;
import com.km.zhc.weight.sys.util.GeneralUtil;
import org.apache.ibatis.session.SqlSession;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class WeightService extends BaseService{

    private static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /** 注册体重记录 */
    public void addWeight(String userName,Double weight,Double waistline) throws Exception{
        SqlSession sqlSession = getSqlSession();
        try{
            WeightMapper mapper = sqlSession.getMapper(WeightMapper.class);
            long num = mapper.updateTodayInvalid(userName);
            if(num>=1){
                System.out.println("本日已存在记录，已将本日旧记录清除");
            }
            int addNum = mapper.addWeight(userName,weight,waistline);
            sqlSession.commit();
        }catch(Exception e){
            e.printStackTrace();
            sqlSession.rollback();
            throw new Exception("系统出错！");
        }
    }

    public List<Map<String,Object>> getRecent90DaysList(String userName){
        SqlSession sqlSession = getSqlSession();
        WeightMapper mapper = sqlSession.getMapper(WeightMapper.class);
        String statisticsDate = DAY_FORMAT.format(GeneralUtil.getDayLater(new Date(),-90));
        List<Map<String,Object>> resultList = mapper.getRecent90DaysList(userName,statisticsDate);
        return resultList;
    }

}
