package tk.ljyuan71.mongo.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import tk.ljyuan71.mongo.user.dao.UserDao;
import tk.ljyuan71.mongo.user.model.User;
import tk.ljyuan71.mongo.user.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	private static Logger log = Logger.getLogger(UserServiceImpl.class);
	
	@Resource
	private UserDao userDao;
	
	@Override
	public void insert(User user, String collName) throws Exception{
		try {
			userDao.insert(user,collName);
		} catch (Exception e) {
			log.error("插入信息出错!",e);
		}
	}

	@Override
	public void insert(User user) throws Exception{
		try {
			userDao.insert(user);
		} catch (Exception e) {
			log.error("插入信息出错!",e);
		}
	}

	@Override
	public User findOne(Map<String, Object> params, String collName) throws Exception{
		User user = null;
		try {
			user = userDao.findOne(params,collName);
		} catch (Exception e) {
			log.error("查询信息出错!",e);
		}
		return user;
	}

	@Override
	public User findOne(Map<String, Object> params) throws Exception{
		User user = null;
		try {
			user = userDao.findOne(params);
		} catch (Exception e) {
			log.error("查询信息出错!",e);
		}
		return user;
	}

	@Override
	public List<User> findAll(String collName) throws Exception{
		List<User> list = null;
		try {
			list = userDao.findAll(collName);
		} catch (Exception e) {
			log.error("查询所有信息出错!",e);
		}
		return list;
	}

	@Override
	public List<User> findAll() throws Exception{
		List<User> list = null;
		try {
			list = userDao.findAll();
		} catch (Exception e) {
			log.error("查询所有信息出错!",e);
		}
		return list;
	}

	@Override
	public void update(Map<String, Object> params, String collName) throws Exception{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Map<String, Object> params) throws Exception{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createCollection(String collName) throws Exception{
		try {
			userDao.createCollection(collName);
		} catch (Exception e) {
			log.error("创建数据库集合失败!",e);
		}
	}

	@Override
	public void remove(Map<String, Object> params, String collName) throws Exception{
		try {
			userDao.remove(params, collName);
		} catch (Exception e) {
			log.error("删除数据库集合失败!",e);
		}
	}

	@Override
	public void remove(Map<String, Object> params) throws Exception{
		try {
			userDao.remove(params);
		} catch (Exception e) {
			log.error("删除数据库集合失败!",e);
		}
	}

	@Override
	public User findById(Object id, String collName) throws Exception{
		User user = null;
		try {
			user = userDao.findById(id, collName);
		} catch (Exception e) {
			log.error("根据id查找用户失败!",e);
		}
		return user;
	}

	@Override
	public User findById(Object id) throws Exception{
		User user = null;
		try {
			user = userDao.findById(id);
		} catch (Exception e) {
			log.error("根据id查找用户失败!",e);
		}
		return user;
	}

	@Override
	public List<User> find(Map<String, Object> params, String collName) {
		List<User> userList = null;
		try {
			userList = userDao.find(params, collName);
		} catch (Exception e) {
			log.error("查找用户列表失败!",e);
		}
		return userList;
	}

	@Override
	public List<User> find(Map<String, Object> params) {
		List<User> userList = null;
		try {
			userList = userDao.find(params);
		} catch (Exception e) {
			log.error("查找用户列表失败!",e);
		}
		return userList;
	}

}
