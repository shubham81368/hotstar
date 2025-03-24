package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay
        User user=userRepository.findById(subscriptionEntryDto.getUserId()).orElseThrow(()->new RuntimeException("User not found"));
       int totalAmount=0;
       if(subscriptionEntryDto.getSubscriptionType()==SubscriptionType.ELITE){
           totalAmount+=1000+350*subscriptionEntryDto.getNoOfScreensRequired();
       }
       else if(subscriptionEntryDto.getSubscriptionType()==SubscriptionType.BASIC){
            totalAmount+=500+200* subscriptionEntryDto.getNoOfScreensRequired();
       }else if(subscriptionEntryDto.getSubscriptionType()==SubscriptionType.PRO){
          totalAmount+=800+250*subscriptionEntryDto.getNoOfScreensRequired();
       }
       Subscription subscriptionObj=new Subscription();
       subscriptionObj.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
       subscriptionObj.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensRequired());
       subscriptionObj.setTotalAmountPaid(totalAmount);
       subscriptionObj.setUser(user);
       subscriptionObj.setStartSubscriptionDate(LocalDateTime.now());
       subscriptionRepository.save(subscriptionObj);
        return totalAmount;
    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not Found"));
        Subscription subscription=user.getSubscription();

        int remainingAmount=0;
        if(subscription.getSubscriptionType()==SubscriptionType.PRO){
            subscription.setSubscriptionType(SubscriptionType.ELITE);
            int currPrice=subscription.getTotalAmountPaid();
            int currNumberOfScreen=subscription.getNoOfScreensSubscribed();
            int totalAmount=1000+350*currNumberOfScreen;
            remainingAmount=totalAmount-currPrice;
            subscription.setTotalAmountPaid(totalAmount);
            subscription.setStartSubscriptionDate(LocalDateTime.now());
        }
        else if(subscription.getSubscriptionType()==SubscriptionType.BASIC){

            subscription.setSubscriptionType(SubscriptionType.PRO);
            int currPrice=subscription.getTotalAmountPaid();
            int currNumberOfScreen=subscription.getNoOfScreensSubscribed();
            int totalAmount=800+250*currNumberOfScreen;
            remainingAmount=totalAmount-currPrice;
            subscription.setTotalAmountPaid(totalAmount);
            subscription.setStartSubscriptionDate(LocalDateTime.now());
        }
        else if(subscription.getSubscriptionType()==SubscriptionType.ELITE){
            throw  new RuntimeException("User is already an elite member");
        }
        subscriptionRepository.save(subscription);

        return remainingAmount;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb
        List<User>user=userRepository.findAll();
        int totalRevenue=0;
        for(User p:user){
           totalRevenue+= p.getSubscription().getTotalAmountPaid();
        }



        return totalRevenue;
    }

}
