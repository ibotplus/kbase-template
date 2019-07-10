package com.eastrobot.kbs.template.dao.repository;

import com.eastrobot.kbs.template.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface UserRepository extends JpaRepository<User, Serializable> {
}
