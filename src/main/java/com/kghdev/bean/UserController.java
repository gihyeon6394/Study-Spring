package com.kghdev.bean;

import com.kghdev.bean.config.AppConfig;
import com.kghdev.bean.vo.Car;
import com.kghdev.bean.vo.Company;
import com.kghdev.thread.ThreadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/tst1")
    @ResponseBody
    public void tst1()  {
        /*normaly DI*/
        Company bmw = new Company("BMW");
        Car x1 = new Car("x6", bmw);
        Car x2 = new Car("x6", bmw);
        Car x3 = new Car("x6", bmw);
        Car x4 = new Car("x6", bmw);
        Car x5 = new Car("x6", bmw);
        Car x6 = new Car("x6", bmw);
//        ....

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Car bmw_ = context.getBean("car", Car.class);
        bmw_.getCompany().getName();  // BMW

    }


    @RequestMapping("/tst2")
    @ResponseBody
    public void tst2()  {
//        UserRepository userRepository =  new UserRepository();
//        UserService userService = new UserService(userRepository);
//        UserService userService = new UserService();
//        userService.getMember();


    }


}
