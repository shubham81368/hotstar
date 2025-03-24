package com.driver.services;


import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.model.WebSeries;
import com.driver.repository.UserRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSeriesRepository webSeriesRepository;


    public Integer addUser(User user){

        //Jut simply add the user to the Db and return the userId returned by the repository
        userRepository.save(user);
        return user.getId();
    }

    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){

        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
        //Hint: Take out all the Webseries from the WebRepository
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User is invalid"));
        SubscriptionType type=user.getSubscription().getSubscriptionType();
        int age=user.getAge();
        List<WebSeries>web =webSeriesRepository.findAll();
        int basic=0;
        int pro=0;
        int elite=0;

        for(WebSeries webSeries:web){
            if(webSeries.getAgeLimit()<=age &&  (webSeries.getSubscriptionType().equals(SubscriptionType.ELITE))){
                elite++;
            }
            if(webSeries.getAgeLimit()<=age &&  (webSeries.getSubscriptionType().equals(SubscriptionType.PRO))){
                pro++;
            }
            if(webSeries.getAgeLimit()<=age &&  (webSeries.getSubscriptionType().equals(SubscriptionType.BASIC))){
                basic++;
            }
        }
        if(type.equals(SubscriptionType.BASIC)){
            return basic;
        }
        else if(type.equals(SubscriptionType.PRO)){
            return basic+pro;
        }

        return basic+pro+elite;
    }


}
