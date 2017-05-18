package tk.ljyuan71.mongo.user.service;

import java.util.List;
import java.util.Map;

import tk.ljyuan71.mongo.user.model.User;

public interface UserService {
	/**
	 * 根据指定集合名称添加  
	 * @param object
	 * @param collName
	 */
    public void insert(User user,String collName) throws Exception;
    /**
     * 默认集合[user集合，不存在则创建]添加
     * @param object
     */
    public void insert(User user) throws Exception;
    /**
     * 根据条件和指定集合名称查找  
     * @param params
     * @param collName
     * @return
     */
    public User findOne(Map<String,Object> params,String collName) throws Exception;  
    /**
     * 根据条件查找  
     * @param params
     * @return
     */
    public User findOne(Map<String,Object> params) throws Exception;
    /**
     * 根据条件查询
     * @param params
     * @param collName
     * @return
     */
    public List<User> find(Map<String,Object> params, String collName);
    /**
     * 根据条件查询
     * @param params
     * @param collName
     * @return
     */
    public List<User> find(Map<String,Object> params);
    /**
     * 根据指定集合名称查找所有  
     * @param params
     * @param collName
     * @return
     */
    public List<User> findAll(String collName) throws Exception; 
    /**
     * 默认集合名称查找所有  
     * @param params
     * @return
     */
    public List<User> findAll() throws Exception;
    /**
     * 根据指定集合修改  
     * @param params
     * @param collName
     */
    public void update(Map<String,Object> params,String collName) throws Exception;  
    /**
     * 根据条件修改 默认集合
     * @param params
     */
    public void update(Map<String,Object> params) throws Exception;
    /**
     * 创建集合  
     * @param collName
     */
    public void createCollection(String collName) throws Exception;  
    /**
     * 根据条件和指定集合删除  
     * @param params
     * @param collName
     */
    public void remove(Map<String,Object> params,String collName) throws Exception; 
    /**
     * 根据条件删除 默认集合
     * @param params
     */
    public void remove(Map<String,Object> params) throws Exception;
    /**
     * 根据Id和指定集合查找
     * @param id
     * @param collName
     * @return
     */
    public User findById(Object id,String collName) throws Exception;
    /**
     * 根据Id查找默认集合
     * @param id
     * @return
     */
    public User findById(Object id) throws Exception;


}
