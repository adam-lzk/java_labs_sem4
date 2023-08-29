package bsu.rfe.java.teacher.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import bsu.rfe.java.teacher.entity.User;
import bsu.rfe.java.teacher.entity.UserList;
import bsu.rfe.java.teacher.entity.UserList.UserExistsException;
import bsu.rfe.java.teacher.helper.UserListHelper;

public class AddUser extends SimpleTagSupport {
	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public void doTag() throws JspException, IOException {
		String errorMessage = null;
		UserList userList = (UserList)getJspContext().getAttribute("users", PageContext.APPLICATION_SCOPE);
		if (user.getLogin()==null || user.getLogin().equals("")) {
			errorMessage = "Hey, slave, Enter name!!";
		} else {
			if (user.getName()==null || user.getName().equals("")) {
				errorMessage = "Hey, slave, Enter name!!";
			}
		}
		if (errorMessage==null) {
			try {
				userList.addUser(user);
				UserListHelper.saveUserList(userList);
			} catch (UserExistsException e) {
				errorMessage = "Slave, " + user.getLogin() + " has allready play with dick!";
			}
		}
		getJspContext().setAttribute("errorMessage", errorMessage, PageContext.SESSION_SCOPE);
	}
}
