package com.priv.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author fenghen
 * @Entity 对实体注释。任何Hibernate映射对象都要有这个注释
 */
@Entity
public class User implements UserDetails, Serializable {

	/**
	 * @Id 标注用于声明一个实体类的属性映射为数据库的主键列。
	 * @GeneratedValue 用于标注主键的生成策略，通过strategy 属性指定。
	 * *–IDENTITY：采用数据库ID自增长的方式来自增主键字段，Oracle 不支持这种方式；
	 * *–AUTO： JPA自动选择合适的策略，是默认选项；
	 * *–SEQUENCE：通过序列产生主键，通过@SequenceGenerator 注解指定序列名，MySql不支持这种方式
	 * *–TABLE：通过表产生主键，框架借由表模拟序列产生主键，使用该策略可以使应用更易于数据库移植。
	 * 通常两者一起使用，也可一起放在getter方法上
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * @Column 用来标识实体类中属性与数据表中字段的对应关系
	 * -nullable：允许为null
	 * -unique:列的值是否唯一
	 * https://blog.csdn.net/TuxedoLinux/article/details/80876087?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase
	 */
	@Column(nullable = false,  unique = true)
	private String username;

	@Column
	private String password;

	/**
	 * @ManyToMany 多对多
	 * @JoinTable 描述了多对多关系的数据表关系。name 属性指定中间表名称
	 */
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> authorities;


	public User() {
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 权限信息也可能是其他信息，不一定是角色信息
	 * @return
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Role> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * UserDetails接口方法，返回的不一定是username,也可能是其他的用户信息
	 * @return
	 */
	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 下面几个方法一般返回是true
	 * @return
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


}
