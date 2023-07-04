package com.luv2code.springcoredemo.rest;

import com.luv2code.springcoredemo.common.Coach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class  DemoController {
    private Coach myCoach;
    private Coach anotherCoach;

    @Autowired
    public DemoController(
            @Qualifier("circketCoach") Coach theCoach,
//            @Qualifier("circketCoach") Coach theAnotherCoach
            @Qualifier("equatic") Coach theAnotherCoach
    ) {

        // nếu scope là SINGLETON(mặc đinh) theCoach và theAnotherCoach sẽ cùng tham chiếu đến cùng 1 bean
        // nếu scope là protorype thì theCoach và theAnotherCoach sẽ là 2 bean khác nhau
//        System.out.println(theCoach == theAnotherCoach);
        this.myCoach = theCoach;
        this.anotherCoach = theAnotherCoach;
    }

//    @Autowired
//    public void setCoach(@Qualifier("trackCoach") Coach theCoach) {
//        this.myCoach = theCoach;
//    }

    @GetMapping("/dailyworkout")
    public String getDailyWorkout() {
        return myCoach.getDailyWorkout();
    }

    @GetMapping("/checking")
    public String check() {
        return "Comparing beans: myCoach == anotherCoach " + (myCoach == anotherCoach);
    }

}


//Spring FrameWork sẽ làm thế này
//Coach theCoach = new CircketCoach();
//DemoController demoController = new DemoController(theCoach);
