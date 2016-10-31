package hst.models;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserDao {
	private final static Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	@Autowired
	private SessionFactory _sessionFactory;

	private Session getSession() {
		return _sessionFactory.getCurrentSession();
	}

	public String save(User user) {
		String result = "";
		BigInteger count = (BigInteger) getSession().createSQLQuery("Select count(*) from User where email = :email for update").setParameter("email", user.getEmail()).uniqueResult();
		if(count.longValue() == 0L){
			getSession().saveOrUpdate(user);
			result += user.getId() + " / " + user.getEmail();
		}
		else{
			result += "Duplicate: " + user.getEmail();
		}
		return result;
	}
	
	public String saveWithoutCheck(User user) {
		getSession().saveOrUpdate(user);
		return "Insert Successful";
	}

	public void delete(User user) {
		getSession().delete(user);
		return;
	}
	
	public void deleteAll() {
		SQLQuery query;
		String sqlFrom = "delete from user";
		query = getSession().createSQLQuery(sqlFrom);
		query.executeUpdate();
		return;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAll() {
		return getSession().createQuery("from User").list();
	}

	@SuppressWarnings("unchecked")
	public List<User> selectAll() {
		String sql = "select * from user";

		SQLQuery query = getSession().createSQLQuery(sql);
		query.addEntity(User.class);
//		query.setLockMode("R", LockMode.READ);
		return query.list();
	}

	public User getByEmail(String email) {
		return (User) getSession().createQuery("from User where email = :email").setParameter("email", email)
				.uniqueResult();
	}

	public User getById(long id) {
		String sql = "select * from user where id = :id";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("id", id);
		query.addEntity(User.class);
//		query.setLockMode("R", LockMode.READ);
		
//		return (User) getSession().load(User.class, id);
		return (User)query.uniqueResult();
	}

	public String update(User user) {
		SQLQuery query;
		String result;
		try {
			String sql = "update user set email = :email, name = :name, balance = :balance where id = :id";
			query = getSession().createSQLQuery(sql);
			query.setParameter("id", user.getId());
			query.setParameter("email", user.getEmail());
			query.setParameter("name", user.getName());
			query.setParameter("balance", user.getBalance());
			
			query.executeUpdate();
			
			return "Update Sucessful: " + user.getId();
//			getSession().update(user);
		} catch (Exception e) {
			// TODO: handle exception
			getSession().getTransaction().rollback();
			System.out.println("message: " + e.getMessage());
		}
		
		return "Update fault";
	}
//	public void update(User user) {
//		try {
//			getSession().update(user);
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("message: " + e.getMessage());
//		}
//	}
	
	public synchronized long getBalance(long id){
		SQLQuery query;
		try{
			String sql = "select balance from user where id = :id for update";
			query = getSession().createSQLQuery(sql);
			query.setParameter("id", id);
			BigInteger balance = (BigInteger) query.uniqueResult();
			
			return balance.longValue();
		}
		catch(Exception e){
			System.out.println("error: " + e.getMessage());
		}
		
		return 0L;
	}
	public String updateBalance(long id, long balance) {
		String result = "";
		try {
			System.out.println("Start updateBalance ================== ");
			SQLQuery query;
//			String sqlFrom = "select balance from user where id = :id for update";
//			query = getSession().createSQLQuery(sqlFrom);
//			query.setParameter("id", id);
			long fromBalance = getBalance(id);
			long finalBalance = fromBalance + balance;
			result += "id: " + id + " finalBalance" + finalBalance;

			System.out.println("id: " + id);
			System.out.println("fromBalance: " + fromBalance);
			System.out.println("balance: " + balance);
			System.out.println("finalBalance: " + finalBalance);

			String sql = "update user set balance = :finalBalance where id =:id";
			query = getSession().createSQLQuery(sql);
			query.setParameter("id", id);
			query.setParameter("finalBalance", finalBalance);
			query.executeUpdate();

			System.out.println("Finish updateBalance ================== ");
			System.out.println("");
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			getSession().getTransaction().rollback();
			result += e.getMessage();
			System.out.println("message: " + e.getMessage());
		}
		getSession().getTransaction().rollback();
		return result;
	}

	public String transferBalance(long fromID, long toID, long balance) {
		String result = "";
		try {

			SQLQuery query;

			// Update From ID
			long fromBalance = getBalance(fromID);
			System.out.println("fromID: " + fromID);
			System.out.println("fromBalance: " + fromBalance);
			System.out.println("balance: " + balance);
			
			long finalFromBalance = fromBalance - balance;
			System.out.println("finalFromBalance: " + finalFromBalance);
			result += " \n FromID: " + fromID + "\n beginFromBalance:" + fromBalance + " \n finalFromBalance: " + finalFromBalance;
			
			String sqlUpdateFrom = "update user set balance = :finalFromBalance where id =:fromID";
			query = getSession().createSQLQuery(sqlUpdateFrom);
			query.setParameter("fromID", fromID);
			query.setParameter("finalFromBalance", finalFromBalance);
			query.executeUpdate();

			// Update TO ID
			long toBalance = getBalance(toID);
			System.out.println("toID: " + toID);
			System.out.println("toBalance: " + toBalance);
			System.out.println("balance: " + balance);
			
			long finalToBalance = toBalance + balance;
			System.out.println("finalToBalance: " + finalToBalance);
			result += " \n ToID: " + toID +  "\n beginToBalance:" + toBalance +" \n finalToBalance: " + finalToBalance;

			String sqlUpdate = "update user set balance = :finalToBalance where id =:toID";
			query = getSession().createSQLQuery(sqlUpdate);
			query.setParameter("toID", toID);
			query.setParameter("finalToBalance", finalToBalance);
			query.executeUpdate();

			System.out.println("=================================================");
			System.out.println("");

			result += " \n Transfer successfull";
			
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			getSession().getTransaction().rollback();
			result += e.getMessage();
			System.out.println("message: " + e.getMessage());
		}
		getSession().getTransaction().rollback();
		return " \n Fault: " + result;
	}

} // class UserDao
