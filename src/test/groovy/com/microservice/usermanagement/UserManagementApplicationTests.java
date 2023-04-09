package com.microservice.usermanagement;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(classes = UserManagementApplication.class)
@WebAppConfiguration
@SpringBootTest
class UserManagementApplicationTests {

    void contextLoads() {}

}
