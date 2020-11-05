package com.ust.shbay.service;

/**
 * 用于独立测试Service层的启动类
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value ={"com.ust.shbay.manager.dao"})
public class ApplicationTest {
}
