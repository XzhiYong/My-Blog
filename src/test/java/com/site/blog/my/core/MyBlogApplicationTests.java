package com.site.blog.my.core;

import com.site.blog.my.core.webSocket.WebSocketServer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class MyBlogApplicationTests {

	@Test
	public void contextLoads() {

        try {
            WebSocketServer.sendInfo("message", "100");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
