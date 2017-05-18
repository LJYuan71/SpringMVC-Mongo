package tk.ljyuan71.mongo.user.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Morphia;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.ljyuan71.mongo.Respond;
import tk.ljyuan71.mongo.ResponseWithPage;
import tk.ljyuan71.mongo.user.model.User;
import tk.ljyuan71.mongo.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private static Logger log = Logger.getLogger(UserController.class);
	@Resource
	private UserService userService;
	
	/**
	 * 说明：
    	（1）save ：我们在新增文档时，如果有一个相同_ID的文档时，会覆盖原来的。
    	（2）insert：我们在新增文档时，如果有一个相同的_ID时，就会新增失败。
     	（3）insertAll批量添加，可以一次性插入一个列表，效率比较高，save则需要一个一个的插入文档，效率比较低。
	 */
	
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
        mongoTemplate.findOne(newQuery(newCriteria().orOperator(Criteria.where("onumber").is("002"),Criteria.where("cname").is("zcy"))),entityClass); 
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
       DBObjectobj =newBasicDBObject();
       obj.put("$or", basicDBList);
       Query query = new BasicQuery(obj);
       
       3.QueryBuilder默认构造函数，是初始化BasicDBObject，QueryBuilder多个方法标准和查询连接起来，
       	方便我们操作查询语句。跟Criteria是标准查询的接口一样, QueryBuilder和BasicDBObject配合使用
       	QueryBuilder帮我们实现了  $and等操作符
                   例如：我们查询onumber=002 OR cname=zcy1
       	QueryBuilder和BasicDBObject配合使用
        QueryBuilder queryBuilder= newQueryBuilder(); 
        queryBuilder.or(new BasicDBObject("onumber","002"),new BasicDBObject("cname","zcy1")); 
        Query query=new BasicQuery(queryBuilder.get());
       	
      4.BasicQuery查询语句可以指定返回字段：节省传输数据量，减少了内存消耗，提高了性能。
                构造函数BasicQuery(DBObject queryObject, DBObject fieldsObject)
      fieldsObject 这个字段可以指定返回字段 fieldsObject.put(key,value)
       key：字段
       value：
          	1或者true表示返回字段
          	0或者false表示不返回该字段
                     除非设置为0或false，不返回该字段,否则默认会返回该字段
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
	
	/**
	 * 进阶的查询分页
	 * 可以随意修改查询限制、跳跃、和排序顺序的功能,我们这边对指针返回的结果，我用到Morphia框架。morphia-1.3.0.jar包
	 * Morphia是一个开放源代码的对象关系映射框架，它对MongoDB数据库 Java版驱动进行了非常轻量级的对象封装。
	 * 我们需要通过DBCurosr获取的DBObject转换成我们对应的实体对象，方便我们操作实体。
     * DBCurosr 是 DBCollection 的 find 方法返回的对象，可以设置 skip、limit 、sot等属性执行分页查询
	 * 初始化Morphia，并往里面添加我们需要转换的实体类CLASS
     * Morphia  morphia =new Morphia();
     * morphia.map(Orders.class);
     * dbCursor.hasNext()判断是否还有下一个文档（DBObject），  dbCursor.Next()获取DBObject
	 */
	
	
	@RequestMapping(value="/saveUserInfo", produces="application/json;charset=UTF-8")
	@ResponseBody
	public Respond saveUserInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Respond respond = new Respond();
		User user = new User();
		try {
			user.setId("1234567890");//如果没有主动设置，则会默认使用数据库自带id(有索引)
			user.setAccount("xin");
			user.setAddress("北京市 丰台区南四环");
			user.setAge(11);
			user.setGender(1);
			user.setName("新用户");
			user.setPassword("123456");
			user.setCreatedate(new Date());
			userService.insert(user,"LJY");//向LJY集合中添加数据
			//userService.insert(user);//向默认集合（实体名小写user）中添加数据，没有则创建后添加
		} catch (Exception e) {
			log.error("插入用户信息出错",e);
			respond.setStatus(200);
			respond.setCause(e.getMessage());
		}
		//CriteriaDefinition criteriaDefinition = new CriteriaDefinition;
		Query query = new Query(Criteria.where("account").is("LJYuan71"));
		respond.setStatus(202);
		respond.setCause("插入"+user.getAccount()+"用户成功!");
		return respond;
	}
	
	
	@RequestMapping(value="/findAll", produces="application/json;charset=UTF-8")
	@ResponseBody
	public ResponseWithPage findAll(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResponseWithPage respond = new ResponseWithPage();
		List<User> userList = new ArrayList<>();
		try {
			//userList = userService.findAll("LJY");//带参数的表示根据指定集合查询，查询返回第一条符合记录
			userList = userService.findAll();//查询默认集合[user]
		} catch (Exception e) {
			log.error("查询所有用户信息出错",e);
			respond.setStatus(202);
			respond.setCause(e.getMessage());
		}
		respond.setStatus(200);
		respond.setCause("查询所有用户成功!");
		respond.setData(userList);
		return respond;
	}
	
	@RequestMapping(value="/find", produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object find(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> map = new HashMap<>();
		return userService.find(map,"LJY");
	}
	
	@RequestMapping(value="/findOne", produces="application/json;charset=UTF-8")
	@ResponseBody
	public Respond findOne(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Respond respond = new Respond();
		Map<String,Object> queryMap = new HashMap<>();
		List<User> userList = new ArrayList<>();
		queryMap.put("account", "LJYuan71");
		try {
			User user = userService.findOne(queryMap, "LJY");//带第二个参数的表示根据指定集合查询，查询返回第一条符合记录
			//User user = userService.findOne(queryMap);//查询默认集合[user]
			userList.add(user);
		} catch (Exception e) {
			log.error("查询用户信息出错",e);
			respond.setStatus(202);
			respond.setCause(e.getMessage());
		}
		respond.setStatus(200);
		respond.setCause("查询到的第一个用户成功!");
		respond.setData(userList);
		return respond;
	}
	
	@RequestMapping(value="/findById", produces="application/json;charset=UTF-8")
	@ResponseBody
	public Respond findById(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Respond respond = new Respond();
		List<User> userList = new ArrayList<>();
		String id = "1234567890";//1234567890  590c1730da663507a031277c
		try {
			User user = userService.findById(id, "LJY");//带第二个参数的表示根据指定集合查询，查询返回第一条符合记录
			//User user = userService.findOne(id);//查询默认集合[user]
			userList.add(user);
		} catch (Exception e) {
			log.error("根据id查询用户信息出错",e);
			respond.setStatus(200);
			respond.setCause(e.getMessage());
		}
		respond.setStatus(202);
		respond.setCause("根据id查询用户成功!");
		respond.setData(userList);
		return respond;
	}
	
	public ResponseWithPage getAllUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Morphia  morphia = new Morphia();
		morphia.map(User.class);
		
		ResponseWithPage responseWithPage = new ResponseWithPage();
		
		return responseWithPage;
	}
}
