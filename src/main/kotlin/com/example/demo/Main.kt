package com.example.demo

import com.example.demo.database.*
import com.example.demo.database.UserDynamicSqlSupport.User.age
import com.example.demo.database.UserDynamicSqlSupport.User.id
import com.example.demo.database.UserDynamicSqlSupport.User.name
import com.example.demo.database.UserDynamicSqlSupport.User.profile
import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.mybatis.dynamic.sql.SqlBuilder.isEqualTo
import org.mybatis.dynamic.sql.SqlBuilder.isGreaterThanOrEqualTo

fun main() {
    list5_4_20()
}
fun createSessionFactory(): SqlSessionFactory {
    val resource = "mybatis-config.xml"
    val inputStream = Resources.getResourceAsStream(resource)
    return SqlSessionFactoryBuilder().build(inputStream)
}

//select
fun list5_4_3() {
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val user = mapper.selectByPrimaryKey(100)
        println(user)
    }
}

fun list5_4_4() {
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val userList = mapper.select {
            // isEqualTo:mybatis-dynamic-sql
            where(name, isEqualTo("Jiro"))
        }
        println(userList)
    }
}

fun list5_4_6() {
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val userList = mapper.select {
            where(age, isGreaterThanOrEqualTo(25))
        }
        println(userList)
    }
}

fun list5_4_8() {
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.count {
            where(age, isGreaterThanOrEqualTo(25))
        }
        println(count)
    }
}

fun list5_4_9() {
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.count { allRows() }
        println(count)
    }
}

// insert
fun list5_4_10() {
    val user = UserRecord(103, "Shiro", 18, "Hello")
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.insert(user)
        session.commit()
        println("${count}行のレコードを挿入しました")
    }
}

fun list5_4_12() {
    val userList = listOf(UserRecord(104, "Goro", 15, "Hello"), UserRecord(105, "Rokuro", 13, "Hello"))
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.insertMultiple(userList)
        session.commit()
        println("${count}行のレコードを挿入しました")
    }
}

// update
// 主キー
fun list5_4_14() {
    val user = UserRecord(id = 105, profile = "Bye")
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.updateByPrimaryKeySelective(user)
        session.commit()
        println("${count}行のレコードを更新しました")
    }
}

// 主キー以外のカラムを検索条件としたレコード更新(Recordオブジェクトを使わない場合)
fun list5_4_16() {
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.update {
            set(profile).equalTo("Hey")
            where(id, isEqualTo(104))
        }
        session.commit()
        println("${count}行のレコードを更新しました")
    }
}

// 主キー以外のカラムを検索条件としたレコード更新(Recordオブジェクトを使う場合)
fun list5_4_18() {
    val user = UserRecord(profile = "Good Morning")
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.update {
            updateSelectiveColumns(user)
            where(name, isEqualTo("Shiro"))
        }
        session.commit()
        println("${count}行のレコードを更新しました")
    }
}

// delete
fun list5_4_20() {
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.deleteByPrimaryKey(102)
        session.commit()
        println("${count}行のレコードを削除しました")
    }
}