package com.abelium.INATrace.components.user;

import java.util.List;

import com.abelium.INATrace.components.common.CommonApiTools;
import com.abelium.INATrace.components.user.api.ApiUser;
import com.abelium.INATrace.components.user.api.ApiUserBase;
import com.abelium.INATrace.components.user.api.ApiUserGet;
import com.abelium.INATrace.components.user.types.UserAction;
import com.abelium.INATrace.db.entities.User;

public class UserApiTools {

	private static void updateApiUserBase(ApiUserBase apiUser, User user) {
		apiUser.email = user.getEmail();
		apiUser.name = user.getName();
		apiUser.surname = user.getSurname();
		apiUser.status = user.getStatus();
		apiUser.role = user.getRole();
		apiUser.language = user.getLanguage();
	}
	
	public static ApiUserBase toApiUserBase(User user) {
		if (user == null) return null;
		
		ApiUserBase apiUser = new ApiUserBase();
		CommonApiTools.updateApiBaseEntity(apiUser, user);
		updateApiUserBase(apiUser, user);
		return apiUser;
	}
	
	public static ApiUser toApiUser(User user) {
		if (user == null) return null;
		
		ApiUser apiUser = new ApiUser();
		CommonApiTools.updateApiBaseEntity(apiUser, user);
		updateApiUserBase(apiUser, user);
		// Add more
		return apiUser;
	}
	
	public static ApiUserGet toApiUserGet(User user, 
			List<UserAction> actions,
			List<Long> companyIds) {
		if (user == null) return null;
		
		ApiUserGet apiUser = new ApiUserGet();
		CommonApiTools.updateApiBaseEntity(apiUser, user);
		updateApiUserBase(apiUser, user);
		// Add more
		apiUser.actions = actions;
		apiUser.companyIds = companyIds;
		return apiUser;
	}
	
}
