package com.zxh;

import com.zxh.entity.Admin;
import com.zxh.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
class SnackmallApplicationTests {
    @Autowired
    AdminServiceImpl adminService;
    @Test
    void contextLoads() {

        Admin byId = adminService.getById(1);
        System.out.println(byId.getAdminName());
    }

}
