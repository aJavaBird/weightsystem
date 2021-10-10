package com.km.zhc.weight.sys.service;

import com.km.zhc.weight.sys.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;

public class UserService extends BaseService{

    /** 注册添加用户 */
    public void registerUser(String userName,String password) throws Exception{
        SqlSession sqlSession = getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        long num = mapper.countUserByUserName(userName);
        if(num>=1){
            throw new RuntimeException("用户名已存在！");
        }
        try{
            int addNum = mapper.addUser(userName,password);
            sqlSession.commit();
        }catch(Exception e){
            e.printStackTrace();
            sqlSession.rollback();
            throw new Exception("系统出错！");
        }
    }

    /** 判断用户名、密码是否正确 */
    public Boolean checkUser(String userName,String password){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            long num = mapper.countUserByUserNameAndPassword(userName,password);
            sqlSession.commit();
            if(num>=1){
                return Boolean.TRUE;
            }
        }catch(Exception e){
            e.printStackTrace();
            sqlSession.rollback();
        }
        return Boolean.FALSE;
    }

}
