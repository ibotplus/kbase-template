package com.eastrobot.kbs.template.service;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Resource;

public abstract class BaseService<R extends JpaRepository> {
    @Resource
    protected R repo;

}
