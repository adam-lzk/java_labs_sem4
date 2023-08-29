package bsu.rfe.java.teacher.tag;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import bsu.rfe.java.teacher.entity.Ad;
import bsu.rfe.java.teacher.entity.AdList;
import bsu.rfe.java.teacher.entity.User;
import bsu.rfe.java.teacher.helper.AdListHelper;

public class UpdateAd extends SimpleTagSupport {
	private Ad ad;
	public void setAd(Ad ad) {
		this.ad = ad;
	}

	public void doTag() throws JspException, IOException {
		String errorMessage = null;
		AdList adList = (AdList) getJspContext().getAttribute("ads",
				PageContext.APPLICATION_SCOPE);
		User currentUser = (User) getJspContext().getAttribute("authUser",
				PageContext.SESSION_SCOPE);
		if (ad.getSubject()==null || ad.getSubject().equals("")) {
			errorMessage = "Hey, slave, enter the title!";
		} else {
			if (currentUser==null || (ad.getId()>0 &&
					ad.getAuthorId()!=currentUser.getId())) {
				errorMessage = "Hey, slave, you shoude be a dungen master to edit this ad!";
			}
		}
		if (errorMessage==null) {
			ad.setLastModified(Calendar.getInstance().getTimeInMillis());
			if (ad.getId()==0) {
				adList.addAd(currentUser, ad);
			} else {
				adList.updateAd(ad);
			}
			AdListHelper.saveAdList(adList);
		}
		getJspContext().setAttribute("errorMessage", errorMessage,
				PageContext.SESSION_SCOPE);
	}
}
