package com.kghdev.bean;

import com.kghdev.bean.vo.Car;
import com.kghdev.bean.vo.Company;
import com.kghdev.thread.ThreadController;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/tst1")
    @ResponseBody
    public void tst1()  {
        /*normaly DI*/
        Company bmw = new Company("BMW");
        Car x6 = new Car("x6", bmw);
    }
}
