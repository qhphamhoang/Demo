package hst.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hst.models.User;
import hst.services.UserServices;
import utils.GsonUtils;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserServices _userService;

	@RequestMapping(value = "/selectAll")
	@ResponseBody
	public String selectAll() {
		List<User> l;
		try {
			// User user = new User(id);
			l = _userService.selectAll();
		} catch (Exception ex) {
			return ex.getMessage();
		}
		String result = "nothing";
		if (l != null & l.size() > 0)
			result = GsonUtils.toJson(l);

		return "Get succesfully !" + result;
	}
	
	@RequestMapping(value = "/getAll")
	@ResponseBody
	public String getAll() {
		List<User> l;
		try {
			// User user = new User(id);
			l = _userService.getAll();
		} catch (Exception ex) {
			return ex.getMessage();
		}
		String result = "nothing";
		if (l != null & l.size() > 0)
			result = GsonUtils.toJson(l);

		return "Get succesfully !" + result;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(long id) {
		try {
			User user = new User(id);
			_userService.delete(user);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return "User succesfully deleted!";
	}
	
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public String deleteAll() {
		String result;
		try {
			_userService.deleteAll();
			result = "User succesfully deleted!";
		} catch (Exception ex) {
			result = ex.getMessage();
			return ex.getMessage();
		}
		return result;
	}

	@RequestMapping(value = "/get-by-email")
	@ResponseBody
	public String getByEmail(String email) {
		String userId;
		try {
			User user = _userService.getByEmail(email);
			userId = String.valueOf(user.getId());
		} catch (Exception ex) {
			return "User not found";
		}
		return "The user id is: " + userId;
	}
	
	@RequestMapping(value = "/getUserById")
	@ResponseBody
	public String getUserById(long id) {
		String userId;
		try {
			User user = _userService.getUserById(id);
			userId = String.valueOf(user.getId());
		} catch (Exception ex) {
			return "User not found";
		}
		return "The user id is: " + userId;
	}

	@RequestMapping(value = "/create")
	@ResponseBody
	public String create(String email, String name, long balance) {
		User user;
		String result = "";
		try {
			user = new User(email, name, balance);
			result = _userService.create(user);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return "create! " + result;
	}
	
	@RequestMapping(value = "/createWithoutCheck")
	@ResponseBody
	public String createWithoutCheck(String email, String name, long balance) {
		User user;
		String result = "";
		try {
			user = new User(email, name, balance);
			result = _userService.createWithoutCheck(user);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return "createWithoutCheck! " + result;
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public String update(long id, String email, String name, long balance) {
		User user;
		String result = "";
		try {
			user = new User(id, email, name, balance);
			result = _userService.update(user);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return "updated! -- " + result;
	}
	
	@RequestMapping(value = "/updateBalance")
	@ResponseBody
	public String updateBalance(long id, long balance) {
		String sts = "";
		try {			
			sts = _userService.updateBalance(id, balance);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return "updateBalance! -- " + sts;
	}
	
	@RequestMapping(value = "/transferBalance")
	@ResponseBody
	public String transferBalance(long fromID, long toID, long balance) {
		String sts;
		try {			
			sts = _userService.transferBalance(fromID, toID, balance);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		return "Transfered Process ! --- " + sts;
	}

} // class UserController
