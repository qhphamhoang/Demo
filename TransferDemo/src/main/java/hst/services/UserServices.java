package hst.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hst.models.User;
import hst.models.UserDao;
import scala.annotation.meta.getter;
import utils.GsonUtils;

@Component
public class UserServices {

	private final static Logger logger = LoggerFactory.getLogger(UserServices.class);
	
	@Autowired
	private UserDao _userDao;
	
	public List<User> selectAll() {
		List<User> listUser = null;
		try {
			listUser = _userDao.selectAll();
		} catch (Exception ex) {
			logger.info("selectAll: " + ex.getMessage());
			ex.printStackTrace() ;
			return null;
		}
		return listUser;
	}
	
	
	public List<User> getAll() {
		List<User> listUser = null;
		try {
			// User user = new User(id);
			listUser = _userDao.getAll();
		} catch (Exception ex) {
			logger.info("getAll: " + ex.getMessage());
			ex.printStackTrace() ;
			return null;
		}
		
		return listUser;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(User user) throws Exception {
		try {			
			_userDao.delete(user);
		} catch (Exception ex) {
			logger.info("delete: " + ex.getMessage());
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteAll() throws Exception {
		try {			
			_userDao.deleteAll();;
		} catch (Exception ex) {
			logger.info("delete: " + ex.getMessage());
		}
	}

	public User getByEmail(String email) {
		try {
			return _userDao.getByEmail(email);
			
		} catch (Exception ex) {
			logger.info("getByEmail: " + ex.getMessage());
			return null;
		}
	}
	
	public User getUserById(long id) {
		try {
			return _userDao.getById(id);
			
		} catch (Exception ex) {
			logger.info("getUserById: " + ex.getMessage());
			return null;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public synchronized String create(User user) throws Exception {
		String result = "";
		try {
			result = _userDao.save(user);
		} catch (Exception ex) {
			logger.info("create: " + ex.getMessage());
			return ex.getMessage();
		}
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public String createWithoutCheck(User user) throws Exception {
		String result = "";
		try {
			result = _userDao.saveWithoutCheck(user);
		} catch (Exception ex) {
			logger.info("create: " + ex.getMessage());
			return ex.getMessage();
		}
		return result;
	}
	

	@Transactional(rollbackFor = Exception.class)
	public String update(User user) throws Exception {
		String result = "";
		try {
			return result = _userDao.update(user);
		} catch (Exception ex) {
			result += ex.getMessage();
			logger.info("update: " + ex.getMessage());
		}
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public synchronized String updateBalance(long id, long balance) throws Exception {
		String sts = "";
		try {			
			sts = _userDao.updateBalance(id, balance);
		} catch (Exception ex) {
			logger.info("updateBalance: " + ex.getMessage());
			return ex.getMessage();
		}
		return "User succesfully updated!" + sts;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public synchronized String transferBalance(long fromID, long toID, long balance) throws Exception{
		String sts;
		try {			
			sts = _userDao.transferBalance(fromID, toID, balance);
		} catch (Exception ex) {
			logger.info("transferBalance: " + ex.getMessage());
			return ex.getMessage();
		}
		return sts;
	}
}
