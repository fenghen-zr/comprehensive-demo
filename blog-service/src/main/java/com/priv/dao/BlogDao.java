package com.priv.dao;

import com.priv.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



/**
 * @author fenghen
 */
public interface BlogDao extends JpaRepository<Blog, Long> {

    List<Blog> findByUsername(String username);

}
