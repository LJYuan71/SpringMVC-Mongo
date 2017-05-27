package tk.ljyuan71.mongo.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;

import tk.ljyuan71.mongo.user.model.User;
import tk.ljyuan71.mongo.user.service.UserService;
import tk.ljyuan71.utils.ResponseWithPage;

@Service
public class UserServiceImpl implements UserService{
	private static Logger log = Logger.getLogger(UserServiceImpl.class);
	
	/**
	 *  使用Dao层会降低灵活性，所以放弃Dao层
	 *	@Resource
	 *	private UserDao userDao;
	 */
	//只要传入对应的Class<User> entityClass, String collectionName就可以实现类似Dao的操作
	//比如之前有：ADao,BDao。在某个DServiceImpl中调用A表的插入方法B表的查询方法需要这样，ADao.insert(A) BDao.find(query)实现解耦合
	//现在可以：在某个DServiceImpl中调用A集合的插入方法B集合的查询方法可以这样，mongoTemplate.insert(A.class)
	//mongoTemplate.find(query,B.class) 可以达到同样的需求
	@Resource  
    private MongoTemplate mongoTemplate; 
	//@Resource 
	private Morphia  morphia; 
	private Datastore datastore;
	private MongoClient mongoClient;
    public UserServiceImpl(){  
      morphia= new Morphia();  
      morphia.map(User.class);  
      mongoClient = new MongoClient("localhost");
      datastore = morphia.createDatastore(mongoClient, "LJY");
    }
	@Override
	public void insert(User user, String collName) throws Exception{
		try {
			mongoTemplate.insert(user,collName);
		} catch (Exception e) {
			log.error("插入User信息出错!",e);
		}
	}

	@Override
	public void insert(User user) throws Exception{
		try {
			mongoTemplate.insert(user);
		} catch (Exception e) {
			log.error("插入User信息出错!",e);
		}
	}

	@Override
	public User findOne(Map<String, Object> params, String collName) throws Exception{
		User user = new User();
		Query query = new Query(new Criteria().andOperator(Criteria.where("age").gte(1),Criteria.where("gender").is(1)));//
		try {
			user = mongoTemplate.findOne(query,User.class,collName);
		} catch (Exception e) {
			log.error("查询User信息出错!",e);
		}
		return user;
	}

	@Override
	public User findOne(Map<String, Object> params) throws Exception{
		User user = new User();
		Query query = new Query(new Criteria().andOperator(Criteria.where("age").gte(1),Criteria.where("gender").is(1)));//
		try {
			user = mongoTemplate.findOne(query,User.class);
		} catch (Exception e) {
			log.error("查询User信息出错!",e);
		}
		return user;
	}

	@Override
	public List<User> findAll(String collName) throws Exception{
		List<User> list = new ArrayList<User>();
		try {
			list = mongoTemplate.findAll(User.class,collName);
		} catch (Exception e) {
			log.error("查询所有User信息出错!",e);
		}
		return list;
	}

	@Override
	public List<User> findAll() throws Exception{
		List<User> list = new ArrayList<User>();
		try {
			list = mongoTemplate.findAll(User.class);
		} catch (Exception e) {
			log.error("查询所有User信息出错!",e);
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
			mongoTemplate.createCollection(collName);
			log.info("创建"+collName+"数据库集合成功!");
		} catch (Exception e) {
			log.error("创建"+collName+"数据库集合失败!",e);
		}
	}

	@Override
	public void remove(Map<String, Object> params, String collName) throws Exception{
		try {
			mongoTemplate.remove(params, collName);
		} catch (Exception e) {
			log.error("根据条件删除"+collName+"数据失败!",e);
		}
	}

	@Override
	public void remove(Map<String, Object> params) throws Exception{
		try {
			mongoTemplate.remove(params);
		} catch (Exception e) {
			log.error("删除数据库集合失败!",e);
		}
	}

	@Override
	public User findById(Object id, String collName) throws Exception{
		User user = new User();
		try {
			user = mongoTemplate.findById(id,User.class, collName);
		} catch (Exception e) {
			log.error("根据id查找"+collName+"集合中的User用户失败!",e);
		}
		return user;
	}

	@Override
	public User findById(Object id) throws Exception{
		User user = new User();
		try {
			user = mongoTemplate.findById(id,User.class);
		} catch (Exception e) {
			log.error("根据id查找User用户失败!",e);
		}
		return user;
	}
	
	/**
	 * 
                    一.Query query=new Query(...)
        Query构造方法
        Query()
        Query(CriteriaDefinition criteriaDefinition)接受的参数是Criteria -->CriteriaDefinition
         Criteria是标准查询的接口，可以引用静态的Criteria.where的把多个条件组合在一起，就可以轻松地将多个方法标准和查询连接起来，方便我们操作查询语句。
                     例如： 查询条件onumber="002"
        mongoTemplate.find (new Query(Criteria.where("onumber").is("002")),entityClass)
                    多个条件组合查询时：
                    例如：onumber="002" and cname="zcy"
        mongoTemplate.find (new Query(Criteria.where("onumber").is("002").and("cname").is("zcy")),entityClass)
                     例如：onumber="002" or cname="zcy"
        mongoTemplate.findOne(new Query(new Criteria().orOperator(Criteria.where("onumber").is("002"),Criteria.where("cname").is("zcy"))),entityClass); 
	 */
	/**
	 * 二.Query query=new BasicQuery(...);
	 *  BasicQuery继承Query类
	 *  BasicQuery构造方法
         BasicQuery(DBObject queryObject)
         **BasicQuery(DBObject queryObject, DBObject fieldsObject)
         BasicQuery(java.lang.String query)
         **BasicQuery(java.lang.String query, java.lang.String fields)
                     我们比较经常使用的比较底层子类，扩展了自己的方法和继承父类
                     
       1.BasicDBObject
       BasicDBObject和DBObject关系：BasicDBObject -->BasicBSONObject[LinkedHashMap,BSONObject],DBObject
                    例如：查询条件onumber="002"
       DBObject obj = new BasicDBObject();
       obj.put( "onumber","002" );
       Query query = new BasicQuery(obj);
       
       2.BasicDBList
       BasicDBObject和DBObject关系：BasicDBList -->BasicBSONList[ArrayList,BSONObject],DBObject
       BasicDBList可以存放多个BasicDBObject条件
                    例如：我们查询onumber=002 OR cname=zcy1
       BasicDBList basicDBList=new BasicDBList();
       basicDBList.add(new BasicDBObject("onumber","002"));
       basicDBList.add(new BasicDBObject("cname","zcy1"));
       DBObject obj =new BasicDBObject();
       obj.put("$or", basicDBList);
       Query query = new BasicQuery(obj);
       
       3.QueryBuilder默认构造函数，是初始化BasicDBObject，QueryBuilder多个方法标准和查询连接起来，
       	方便我们操作查询语句。跟Criteria是标准查询的接口一样, QueryBuilder和BasicDBObject配合使用
       	QueryBuilder帮我们实现了  $and等操作符
                   例如：我们查询onumber=002 OR cname=zcy1
       	QueryBuilder和BasicDBObject配合使用
        QueryBuilder queryBuilder= new QueryBuilder(); 
        queryBuilder.or(new BasicDBObject("onumber","002"),new BasicDBObject("cname","zcy1")); 
        Query query=new BasicQuery(queryBuilder.get());
       	
      4.BasicQuery查询语句可以指定返回字段：节省传输数据量，减少了内存消耗，提高了性能。
                构造函数BasicQuery(DBObject queryObject, DBObject fieldsObject)
      fieldsObject 这个字段可以指定返回字段 fieldsObject.put(key,value)
       key：字段
       value：
          	1或者true表示返回字段
          	0或者false表示不返回该字段
            _id:默认就是1 除非设置为0或false，不返回该字段,否则默认会返回该字段
                                  在一条语句中不能同时设置0和1的情况
                例如：
     QueryBuilder queryBuilder = new QueryBuilder();   
	 queryBuilder.or(new BasicDBObject("onumber", "002"), new BasicDBObject("cname","zcy1"));   
	 BasicDBObject fieldsObject=new BasicDBObject();  
	 fieldsObject.put("onumber", 1);  
	 fieldsObject.put("cname", 1);  
	 query query=new BasicQuery(queryBuilder.get(),fieldsObject);
	  相当于MongoDB
     db.orders.find({"$or" : [ { "onumber" : "002"} , {"cname" : "zcy1"}]},{"onumber":1,"cname":1})  
	 * 
	 */
	@Override
	public ResponseWithPage find(Map<String, Object> params, String collName) throws Exception{
		
		//一、查询语句第一种Query query=new Query(...)
		//Query(CriteriaDefinition criteriaDefinition)接受的参数是Criteria -->CriteriaDefinition
		//条件age >=1 and gender=1
		Query query1 = new Query(new Criteria().andOperator(Criteria.where("age").gte(1),Criteria.where("gender").is(1)));//
		
		//二、查询语句第二种Query query=new BasicQuery(...);
		BasicDBObject fieldsObject = new BasicDBObject(); //BasicQuery支持查询语句可以指定返回字段
		// 在一条语句中不能同时存在设置0和1的情况
		fieldsObject.put("password", 0);
		//fieldsObject.put("gender", 0);
		fieldsObject.put("_id", 0);
		//fieldsObject.put("account", true);
		//fieldsObject.put("name", 1);
		//fieldsObject.put("age", 1);
		//二、1.BasicDBObject   BasicQuery(DBObject queryObject)
		DBObject obj2= new BasicDBObject();//DBObject obj2= new BasicDBObject("age", 17);
		//查询条件 age=17
		obj2.put("age", 17);
		Query query2 = new BasicQuery(obj2,fieldsObject);
		Query query = new BasicQuery(obj2);//调用this(queryObject, null);
		//二、2.BasicDBList与BasicDBObject   BasicQuery(DBObject queryObject)
		BasicDBList basicDBList = new BasicDBList();
		basicDBList.add(new BasicDBObject("age",17));
		basicDBList.add(new BasicDBObject("gender",1));
		DBObject obj3 = new BasicDBObject();
		//查询条件 age=17 or gender=1
		obj3.put("$or", basicDBList);
		Query query3 = new BasicQuery(obj3,fieldsObject);
		
		//二、3.QueryBuilder与BasicDBObject  BasicQuery(DBObject queryObject)
		QueryBuilder queryBuilder= new QueryBuilder();
		//查询条件 age=11 and gender=1 or account=xin
		//queryBuilder.and(new BasicDBObject("password","123456"),new BasicDBObject("address","北京市 丰台区南四环"))
		//.or(new BasicDBObject("account", "xin"));
		queryBuilder.put("age");
		queryBuilder.lessThan(20);//age<20
		queryBuilder.put("gender");
		queryBuilder.greaterThanEquals(2);//gender>=2
		queryBuilder.and(new BasicDBObject("password","123456")); //age<20 and password = 123456
		DBObject obj4 = queryBuilder.get();
		Query query4 = new BasicQuery(obj4,fieldsObject);
		List<User> users = new ArrayList<User>();
		ResponseWithPage responseWithPage = new ResponseWithPage();
		try {
			
			//查询所有find(new BasicDBObject(),new BasicDBObject())
			DBCursor dbCursor = mongoTemplate.getCollection(collName).find(obj4,fieldsObject);
			//排序  
	        DBObject sortDBObject=new BasicDBObject();  
	        sortDBObject.put("age",-1); //1升序,-1降序
	        sortDBObject.put("gender",1);
	        dbCursor.sort(sortDBObject);  
	        //分页查询  
	        dbCursor.skip(responseWithPage.getPage()).limit(responseWithPage.getRows());  
	       
	        //总数  
	        int count = dbCursor.count();
	        //循环指针  
	        while (dbCursor.hasNext()) {  
	        	users.add(morphia.fromDBObject(datastore,User.class, dbCursor.next()));  
	        }
	        responseWithPage.setData(users);
	        responseWithPage.setTotal(count);
			//users = mongoTemplate.find(query4, User.class, collName);//不分页查询
		} catch (Exception e) {
			log.error("查找User用户列表失败!",e);
			throw e;
		}
		return responseWithPage;
	}

	@Override
	public ResponseWithPage find(Map<String, Object> params) {
		List<User> userList = new ArrayList<User>();
		ResponseWithPage responseWithPage = new ResponseWithPage();
		Query query = new Query(new Criteria().andOperator(Criteria.where("age").gte(1),Criteria.where("gender").is(1)));//
		try {
			userList = mongoTemplate.find(query,User.class);
		} catch (Exception e) {
			log.error("查找User用户列表失败!",e);
		}
		responseWithPage.setData(userList);
		return responseWithPage;
	}

}
