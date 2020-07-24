package com.priv.dao;


import com.priv.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author fenghen
 */

public interface UserDao extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
