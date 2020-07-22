package com.priv.dao;

import com.priv.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JpaRepository实现了大多数单表查询的操作
 */
public interface UserDao extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
