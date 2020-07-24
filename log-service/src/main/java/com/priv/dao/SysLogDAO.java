package com.priv.dao;

import com.priv.entity.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author fenghen
 */
public interface SysLogDAO extends JpaRepository<SysLog, Long> {
}
