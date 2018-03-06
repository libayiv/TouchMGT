package com.security.modules.sys.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.common.utils.R;

/**
 * 
 */
@RestController
@RequestMapping("/sys/permitted")
public class SysPermittedController extends AbstractController {
	
	@RequestMapping("hasPermission")
	public R hasPermission(@RequestParam String permission) {
		Map<String, Object> map = new HashMap<String, Object>();
		Long userId = getUserId();
		if(userId != null){
			Subject subject = SecurityUtils.getSubject();
			boolean hasPermission = subject != null && subject.isPermitted(permission);
			map.put("hasPermission", hasPermission);
		}
		return  R.ok(map);
	}
}
