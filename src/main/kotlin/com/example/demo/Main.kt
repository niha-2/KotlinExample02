package com.example.demo

import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder

class DBManipulate {
    fun createSessionFactory(): SqlSessionFactory {
        val resource = "mybatis-config.xml"
        val inputStream = Resources.getResourceAsStream(resource)
        return SqlSessionFactoryBuilder().build(inputStream)
    }
}