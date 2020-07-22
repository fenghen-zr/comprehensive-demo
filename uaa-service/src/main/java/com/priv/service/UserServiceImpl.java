package com.priv.service;


import com.priv.dao.UserDao;
import com.priv.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	//private final Logger log = LoggerFactory.getLogger(getClass());

	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	private UserDao userDao;

	@Override
	public void create(User user) {

		User existing = userDao.findByUsername(user.getUsername());

		String hash = encoder.encode(user.getPassword());
		user.setPassword(hash);
		userDao.save(user);

	}
}
