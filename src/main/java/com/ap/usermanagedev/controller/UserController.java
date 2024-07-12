package com.ap.usermanagedev.controller;

import com.ap.usermanagedev.common.BaseResponse;
import com.ap.usermanagedev.common.ErrorCode;
import com.ap.usermanagedev.common.ResultUtils;
import com.ap.usermanagedev.exception.BusinessException;
import com.ap.usermanagedev.model.domain.User;
import com.ap.usermanagedev.model.domain.request.UserLoginRequest;
import com.ap.usermanagedev.model.domain.request.UserRegisterRequest;
import com.ap.usermanagedev.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ap.usermanagedev.contant.UserConstant.ADMIN_ROLE;
import static com.ap.usermanagedev.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	@PostMapping("/register")
	public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
		if (userRegisterRequest == null) {
			// return ResultUtils.error(ErrorCode.PARAMS_ERROR);
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "");
		}
		String userAccount = userRegisterRequest.getUserAccount();
		String userPassword = userRegisterRequest.getUserPassword();
		String checkPassword = userRegisterRequest.getCheckPassword();
		if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
			// return null;
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号密码为空！");
		}
		Long result = userService.userRegister(userAccount, userPassword, checkPassword);
		return ResultUtils.success(result);
	}

	@PostMapping("/login")
	public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
		if (userLoginRequest == null) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "");
		}
		String userAccount = userLoginRequest.getUserAccount();
		String userPassword = userLoginRequest.getUserPassword();
		if (StringUtils.isAnyBlank(userAccount, userPassword)) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号密码为空！");
		}
		User result = userService.userLogin(userAccount, userPassword, request);
		return ResultUtils.success(result);
	}

	@PostMapping("/logout")
	public BaseResponse<Integer> userLogout(HttpServletRequest request) {
		if (request == null) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "");
		}
		Integer result = userService.userLogout(request);
		return ResultUtils.success(result);
	}

	@GetMapping("/current")
	public BaseResponse<User> getCurrent(HttpServletRequest request) {
		Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
		User currentUser = (User) userObj;
		if (currentUser == null) {
			return null;
		}
		User user = userService.getById(currentUser.getId());
		User result = userService.getSafetyUser(user);
		return ResultUtils.success(result);
	}

	@GetMapping("/search")
	public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
		if (!isAdmin(request)) {
			// List<User> result = new ArrayList<>();
			// return ResultUtils.success(result);
			throw new BusinessException(ErrorCode.NO_AUTH, "");
		}
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(username)) {
			queryWrapper.like("username", username);
		}
		// return userService.list(queryWrapper);
		List<User> userList = userService.list(queryWrapper);
		List<User> result = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
		return ResultUtils.success(result);
	}

	@PostMapping("/delete")
	public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
		if (!isAdmin(request)) {
			throw new BusinessException(ErrorCode.NO_AUTH, "");
		}
		if (id <= 0) {
			// return null;
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "");
		}
		Boolean result = userService.removeById(id);
		return ResultUtils.success(result);
	}

	/**
	 * 是否为管理员
	 *
	 * @param request
	 * @return
	 */
	private boolean isAdmin(HttpServletRequest request) {
		//鉴权 只有管理员才能操作
		Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
		User user = (User) userObj;
		return user != null && user.getUserRole() == ADMIN_ROLE;
	}
}