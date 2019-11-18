# webstock使用过程 
## maven依赖
```
<dependency>  
     <groupId>org.springframework.boot</groupId>  
     <artifactId>spring-boot-starter-websocket</artifactId>  
</dependency> 
```
## 相关代码
```
package com.beijin.limengya.mobile.controller;

import com.beijin.limengya.config.SocketSessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/checkcenter")
public class CheckCenterController {
	/**
	 * session操作类
	 */
	@Autowired
	SocketSessionRegistry webAgentSessionRegistry;
	/**
	 * 消息发送工具
	 */
	@Autowired
	private SimpMessagingTemplate template;

	@RequestMapping(value = "/index")
	public String index() {
		template.convertAndSendToUser("userId", "/topic/greetings", "我的消息");
		return "/index";
	}

	/**
	 * 用户广播
	 * 发送消息广播  用于内部发送使用
	 *
	 * @param request
	 * @return
	 */
	@MessageMapping("/change-notice1")
	public void greeting1(String value) {
		this.template.convertAndSend("/topic/notice", value);
	}
	@MessageMapping("/change-notice")
	@SendTo("/topic/notice")
	public String greeting(String value) {
		return value;
	}

	/**
	 * 同样的发送消息   只不过是ws版本  http请求不能访问
	 *
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@MessageMapping("/msg/hellosingle")
	public void greeting2(String userId) throws Exception {
		//这里没做校验
		template.convertAndSendToUser(userId, "/topic/greetings", "我的消息");
	}
}
```