package com.km.zhc.weight.sys.service;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class BaseService {

    SqlSessionFactory sqlSessionFactory = null;

    public SqlSessionFactory getSqlSessionFactory(){
        if(sqlSessionFactory!=null){
            return sqlSessionFactory;
        }
        synchronized (BaseService.class){
            if(sqlSessionFactory==null){
                String resource = "mybatis-config.xml";
                InputStream resourceAsStream = null;
                try {
                    resourceAsStream = Resources.getResourceAsStream(resource);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
                /*
                SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。
                使用 SqlSessionFactory 的最佳实践是在应用运行期间不要重复创建多次，
                多次重建 SqlSessionFactory 被视为一种代码“坏习惯”。
                最简单的就是使用单例模式或者静态单例模式。
                 */
                sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream);
            }
        }
        return sqlSessionFactory;
    }

    public SqlSession getSqlSession(){

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		/*
		每个线程都应该有它自己的 SqlSession 实例。
		SqlSession 的实例不是线程安全的，因此是不能被共享的每个线程都应该有它自己的 SqlSession 实例。
		SqlSession 的实例不是线程安全的，因此是不能被共享的
		 */
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }
}
