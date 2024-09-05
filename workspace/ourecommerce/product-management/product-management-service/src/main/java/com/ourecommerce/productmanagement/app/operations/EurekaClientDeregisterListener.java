package com.ourecommerce.productmanagement.app.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EurekaClientDeregisterListener{
    @Autowired
    private EurekaRegistration eurekaRegistration;
    
    @EventListener
    public void onApplicationEvent(ContextClosedEvent event) {
        // Unregister the service instance from Eureka Server
        eurekaRegistration.getApplicationInfoManager().setInstanceStatus(com.netflix.appinfo.InstanceInfo.InstanceStatus.DOWN);
    }
}
