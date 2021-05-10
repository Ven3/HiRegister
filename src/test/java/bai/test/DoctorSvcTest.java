package bai.test;

import bai.service.DoctorService;
import bai.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DoctorSvcTest {

    @Autowired
    UserService userService;
    @Autowired
    DoctorService doctorService;

    @Test
    public void getRbInfo(){
        List list = doctorService.getRbEps("rb-b445a46e413a-20180513231626");
        System.out.println(list.size());

    }
}
