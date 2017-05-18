package tk.ljyuan71.mongo;

import java.util.List;
import java.util.Map;


public interface MongoBase<T> {
	/**
	 * 根据指定集合名称insert添加  
	 * @param object
	 * @param collName
	 */
    public void insert(T object,String collName);
    /**
     * 默认集合insert添加
     * @param object
     */
    public void insert(T object);
    /**
     * 根据条件和指定集合名称查找  
     * @param params
     * @param collName
     * @return
     */
    public T findOne(Map<String,Object> params,String collName);  
    /**
     * 根据条件查找  
     * @param params
     * @return
     */
    public T findOne(Map<String,Object> params);
    /**
     * 根据条件查询
     * @param params
     * @param collName
     * @return
     */
    public List<T> find(Map<String,Object> params, String collName);
    /**
     * 根据条件查询
     * @param params
     * @param collName
     * @return
     */
    public List<T> find(Map<String,Object> params);
    /**
     * 根据指定集合名称查找所有  
     * @param params
     * @param collName
     * @return
     */
    
    public List<T> findAll(String collName); 
    /**
     * 默认集合名称查找所有  
     * @param params
     * @return
     */
    public List<T> findAll();
    /**
     * 根据指定集合修改  
     * @param params
     * @param collName
     */
    public void update(Map<String,Object> params,String collName);  
    /**
     * 根据条件修改 默认集合
     * @param params
     */
    public void update(Map<String,Object> params,T object);
    /**
     * 根据条件修改 指定集合
     * @param params
     */
    public void update(Map<String,Object> params,T object,String collName);
    /**
     * 根据指定集合修改第一条数据  
     * @param params
     * @param collName
     */
    public void updateFirst(Map<String,Object> params,String collName);  
    /**
     * 根据条件修改 默认集合第一条数据
     * @param params
     */
    public void updateFirst(Map<String,Object> params,T object);
    /**
     * 根据条件修改 指定集合第一条数据
     * @param params
     */
    public void updateFirst(Map<String,Object> params,T object,String collName);
    /**
     * 根据指定集合修改所有符合数据  
     * @param params
     * @param collName
     */
    public void updateMulti(Map<String,Object> params,String collName);  
    /**
     * 根据条件修改 默认集合所有符合数据  
     * @param params
     */
    public void updateMulti(Map<String,Object> params,T object);
    /**
     * 根据条件修改 指定集合所有符合数据  
     * @param params
     */
    public void updateMulti(Map<String,Object> params,T object,String collName);
    /**
     * 根据指定集合修改所有符合数据  
     * @param params
     * @param collName
     */
    public void upsert(Map<String,Object> params,String collName);  
    /**
     * 根据条件修改 默认集合所有符合数据  
     * @param params
     */
    public void upsert(Map<String,Object> params,T object);
    /**
     * 根据条件修改 指定集合所有符合数据  
     * @param params
     */
    public void upsert(Map<String,Object> params,T object,String collName);
    /**
     * 创建集合  
     * @param collName
     */
    public void createCollection(String collName);  
    /**
     * 根据条件和指定集合删除  
     * @param params
     * @param collName
     */
    public void remove(Map<String,Object> params,String collName); 
    /**
     * 根据条件删除 默认集合
     * @param params
     */
    public void remove(Map<String,Object> params);
    /**
     * 根据Id和指定集合查找
     * @param id
     * @param collName
     * @return
     */
    public T findById(Object id,String collName);
    /**
     * 根据Id查找默认集合
     * @param id
     * @return
     */
    public T findById(Object id);
    /**
     * 根据指定集合save添加  
     * @param object
     * @param collName
     */
    public void save(T object,String collName);  
    /**
     * 默认集合save添加  
     * @param object
     */
    public void save(T object); 
    /**
     * 批量添加  
     * @param object
     */
    public void insertAll(List<T> object); 

}
