package com.janan.service;

import com.janan.model.PlanType;
import com.janan.model.Subscription;
import com.janan.model.User;

public interface SubscriptionService {

    Subscription createSubscription(User user);

    Subscription getUsersSubscription(Long userId);

    Subscription upgradeSubscription(Long userId, PlanType planType);

    boolean isValid(Subscription subscription);


}
