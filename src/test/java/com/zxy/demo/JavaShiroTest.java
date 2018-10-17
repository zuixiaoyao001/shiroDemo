package com.zxy.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @Title JavaShiroTest.java
 * @author 醉逍遥
 * @date 2018年10月13日
 * @version 1.0
 */
public class JavaShiroTest extends ApplicationTests{
	/**
	 * 4.指定realm，并在realm中添加一个认证数据来进行测试使用
	 */
	SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
	@Before
	public void add() {
		simpleAccountRealm.addAccount("张三", "123456","admin","user");
	}
	/**
	 * 创建securityManager，主题提交认证，
	 * securityManager认证，realm与数据源交互做ren
	 */
	@Test
	public void testAuthenticate() {
		//1.构建securityManager环境,添加好数据源的realm
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		securityManager.setRealm(simpleAccountRealm);
		//2.提交主体认证请求
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		//3.主题发送认证请求前需要带上token的认证数据,同时也需要制定realm,返回去指定realm
		UsernamePasswordToken token = new UsernamePasswordToken("张三", "123456");
		subject.login(token);
		System.out.println("是否认证："+subject.isAuthenticated());
//	     校验是否授予了管理员角色权限
		subject.checkRoles("admin","user");
//		退出
		subject.logout();
		System.out.println("是否认证："+subject.isAuthenticated());
	}
}
