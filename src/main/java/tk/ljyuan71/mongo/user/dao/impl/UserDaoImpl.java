package tk.ljyuan71.mongo.user.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mongodb.morphia.Morphia;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.sun.org.apache.bcel.internal.generic.NEW;

import tk.ljyuan71.mongo.ResponseWithPage;
import tk.ljyuan71.mongo.user.dao.UserDao;
import tk.ljyuan71.mongo.user.model.User;


@Repository
public class UserDaoImpl implements UserDao{
	@Resource  
    private MongoTemplate mongoTemplate; 
	
	private Morphia  morphia;  
    
    public UserDaoImpl(){  
      morphia= new Morphia();  
      morphia.map(User.class);  
    } 
	   
	@Override
	public void insert(User object, String collName) {
		mongoTemplate.insert(object, collName);
	}

	@Override
	public void insert(User object) {
		mongoTemplate.insert(object);
	}

	@Override
	public User findOne(Map<String, Object> params, String collName) {
		String key = "";
		Object value = "";
		for(Map.Entry<String, Object> entry: params.entrySet()){
			key = entry.getKey();
			value = entry.getValue();
		}
		return mongoTemplate.findOne(new Query(Criteria.where(key).is(value)), User.class,collName);
	}

	@Override
	public User findOne(Map<String, Object> params) {
		String key = "";
		Object value = "";
		for(Map.Entry<String, Object> entry: params.entrySet()){
			key = entry.getKey();
			value = entry.getValue();
		}
		return mongoTemplate.findOne(new Query(Criteria.where(key).is(value)), User.class);
	}

	@Override
	public List<User> findAll(String collName) {
		
		return mongoTemplate.findAll(User.class, collName);
	}

	@Override
	public List<User> findAll() {
		return mongoTemplate.findAll(User.class);
	}

	@Override
	public void update(Map<String, Object> params, String collName) {
		// TODO Auto-generated method stub
	}


	@Override
	public void createCollection(String collName) {
		 mongoTemplate.createCollection(collName);
	}

	@Override
	public void remove(Map<String, Object> params, String collName) {
		String key = "";
		Object value = "";
		for(Map.Entry<String, Object> entry: params.entrySet()){
			key = entry.getKey();
			value = entry.getValue();
		}
		mongoTemplate.remove(new Query(Criteria.where(key).is(value)),User.class,collName); 
	}

	@Override
	public void remove(Map<String, Object> params) {
		String key = "";
		Object value = "";
		for(Map.Entry<String, Object> entry: params.entrySet()){
			key = entry.getKey();
			value = entry.getValue();
		}
		mongoTemplate.remove(new Query(Criteria.where(key).is(value)),User.class); 
	}

	@Override
	public User findById(Object id, String collName) {
		return mongoTemplate.findById(id, User.class, collName);
	}

	@Override
	public User findById(Object id) {
		return mongoTemplate.findById(id, User.class);
	}

	@Override
	public void save(User user, String collName) {
		mongoTemplate.save(user, collName);
	}

	@Override
	public void save(User user) {
		mongoTemplate.save(user);
	}

	@Override
	public void insertAll(List<User> userList) {
		mongoTemplate.insertAll(userList);
	}

	@Override
	public void update(Map<String, Object> params, User object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Map<String, Object> params, User object, String collName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFirst(Map<String, Object> params, String collName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFirst(Map<String, Object> params, User object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFirst(Map<String, Object> params, User object, String collName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMulti(Map<String, Object> params, String collName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMulti(Map<String, Object> params, User object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMulti(Map<String, Object> params, User object, String collName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void upsert(Map<String, Object> params, String collName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void upsert(Map<String, Object> params, User object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void upsert(Map<String, Object> params, User object, String collName) {
		// TODO Auto-generated method stub
		
	}
	
	public ResponseWithPage queryAll(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResponseWithPage responseWithPage = new ResponseWithPage();
		String collectionName = "LJY";
		DBObject queryObject = new BasicDBObject();//查询条件
		DBObject projection = new BasicDBObject();//返回字段
		projection.put("_id", 0);  //0不返回，1返回，没有显示设置0的都返回
		projection.put("cname",1);  
		projection.put("onumber",1); 
		DBCursor dbCursor = mongoTemplate.getCollection(collectionName).find(queryObject,projection);
		//排序  
        DBObject sortDBObject=new BasicDBObject();  
        sortDBObject.put("onumber",1); //1升序,-1降序
        dbCursor.sort(sortDBObject);  
        //分页查询  
        dbCursor.skip(responseWithPage.getPage()).limit(responseWithPage.getRows());  
       
        //总数  
        int count=dbCursor.count();
        //循环指针  
        List<User> userList = new ArrayList<User>();  
        while (dbCursor.hasNext()) {  
        	//userList.add(morphia.fromDBObject(User.class, dbCursor.next()));  
        }
		return responseWithPage;
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
                                  在一条语句中不能同时存在0和1的情况
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
	public List<User> find(Map<String, Object> params, String collName) {
		//一、查询语句第一种Query query=new Query(...)
		//Query(CriteriaDefinition criteriaDefinition)接受的参数是Criteria -->CriteriaDefinition
		//条件age >=1 and gender=1
		Query query1 = new Query(new Criteria().andOperator(Criteria.where("age").gte(1),Criteria.where("gender").is(1)));//
		
		//二、查询语句第二种Query query=new BasicQuery(...);
		BasicDBObject fieldsObject = new BasicDBObject(); //BasicQuery支持查询语句可以指定返回字段
		// 在一条语句中不能同时存在0和1的情况
		fieldsObject.put("password", 0);
		fieldsObject.put("gender", 0);
		//fieldsObject.put("_id", 0);
		//fieldsObject.put("account", true);
		//fieldsObject.put("name", 1);
		//fieldsObject.put("age", 1);
		//二、1.BasicDBObject   BasicQuery(DBObject queryObject)
		DBObject obj2= new BasicDBObject();//DBObject obj2= new BasicDBObject("age", 17);
		//查询条件 age=17
		obj2.put("age", 17);
		Query query2 = new BasicQuery(obj2,fieldsObject);
		Query query2_1 = new BasicQuery(obj2);
		Query query = new BasicQuery(obj2);//调用this(queryObject, null);
		//二、2.BasicDBList与BasicDBObject   BasicQuery(DBObject queryObject)
		BasicDBList basicDBList = new BasicDBList();
		basicDBList.add(new BasicDBObject("age",17));
		basicDBList.add(new BasicDBObject("gender",1));
		DBObject obj3 = new BasicDBObject();
		//查询条件 age=17 and gender=1
		obj3.put("$and", basicDBList);
		Query query3 = new BasicQuery(obj3,fieldsObject);
		
		//二、3.QueryBuilder与BasicDBObject  BasicQuery(DBObject queryObject)
		QueryBuilder queryBuilder= new QueryBuilder();
		//查询条件 age=17 and gender=1
		queryBuilder.and(new BasicDBObject("age",11),new BasicDBObject("gender",1));
		DBObject obj4 = queryBuilder.get();
		Query query4 = new BasicQuery(obj4,fieldsObject);
		
		return mongoTemplate.find(query4, User.class, collName);
	}

	@Override
	public List<User> find(Map<String, Object> params) {
		Query query1 = new Query(new Criteria().andOperator(Criteria.where("age").gte(1),Criteria.where("gender").is(1)));//
		
		BasicDBObject fieldsObject = new BasicDBObject(); //BasicQuery支持查询语句可以指定返回字段
		fieldsObject.put("password", 0);
		fieldsObject.put("account", 1);
		fieldsObject.put("name", 1);
		DBObject obj2= new BasicDBObject();//DBObject obj2= new BasicDBObject("age", 17);
		obj2.put("age", 17);
		Query query2 = new BasicQuery(obj2,fieldsObject);
		BasicDBList basicDBList = new BasicDBList();
		basicDBList.add(new BasicDBObject("age",17));
		basicDBList.add(new BasicDBObject("gender",1));
		DBObject obj3 = new BasicDBObject();
		obj3.put("$and", basicDBList);
		Query query3 = new BasicQuery(obj3,fieldsObject);
		
		QueryBuilder queryBuilder= new QueryBuilder();
		queryBuilder.and(new BasicDBObject("age",17),new BasicDBObject("gender",1));
		DBObject obj4 = queryBuilder.get();
		Query query4 = new BasicQuery(obj4,fieldsObject);
		
		return mongoTemplate.find(query2, User.class);
	}

}
