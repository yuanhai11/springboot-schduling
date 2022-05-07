package com.example.schedule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleApplicationTests {

    @Test
    public void contextLoads() {

    }

    @Test
    public void test1() {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(1);
        objects.add(1);
        objects.add(1);
        System.out.println(objects.size() / 2+ 2);
    }

}
