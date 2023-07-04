package com.luv2code.springcoredemo.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CircketCoach implements Coach {

    public CircketCoach() {
        System.out.println("In contructor: " + getClass().getSimpleName());
    }


    // PostContruct sẽ chạy khi đối tượng được khởi tạo
    @PostConstruct
    public void doMyStartupStuff() {
        System.out.println("In doMyStartupStuff(): " + getClass().getSimpleName()) ;
    }


    //PreDestroy chạy trước khi tắt chương trình
    @PreDestroy
    public void doMyCleanupStuff() {
        System.out.println("In doMyCleanupStuff(): " + getClass().getSimpleName());
    }



    @Override
    public String getDailyWorkout() {
        return "Practice fast blowing for 15 minutes";
    }
}
