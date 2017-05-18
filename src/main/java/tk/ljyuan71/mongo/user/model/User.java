package tk.ljyuan71.mongo.user.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * java类转换为mongodb的文档,它有以下几种注释：
 * 1.@Id - 文档的唯一标识，在mongodb中为ObjectId，它是唯一的，通过时间戳+机器标识+进程ID+自增计数器（确保同一秒内产生的Id不会冲突）构成。
 * 2.@Document - 把一个java类声明为mongodb的文档，可以通过collection参数指定这个类对应的文档。
 * 3.@Indexed - 声明该字段需要索引，建索引可以大大的提高查询效率。
 * 4.@Transient - 映射忽略的字段，该字段不会保存到MongoDB
 * 5.@CompoundIndex - 复合索引的声明，建复合索引可以有效地提高多字段的查询效率。
 * 6.@PersistenceConstructor - 声明构造函数，作用是把从数据库取出的数据实例化为对象。该构造函数传入的值为从DBObject中取出的数据。
 * @author LJY
 *
 */
@Document(collection = "user")
public class User implements Serializable {
	private static final long serialVersionUID = 7134892021464822105L;
	@Id
	@org.mongodb.morphia.annotations.Id
	private String id;  
	private String account;
    private String name;  
    private Integer age;  
    private Integer gender;
    private String password;
    private String address;
    private Date createdate; 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", account=" + account + ", name=" + name + ", age=" + age + ", gender=" + gender
				+ ", password=" + password + ", address=" + address + ", createdate=" + createdate + "]";
	}
    
}
