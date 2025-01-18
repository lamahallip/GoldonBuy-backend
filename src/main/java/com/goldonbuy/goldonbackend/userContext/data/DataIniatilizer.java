package com.goldonbuy.goldonbackend.userContext.data;

import com.goldonbuy.goldonbackend.userContext.entity.User;
import com.goldonbuy.goldonbackend.userContext.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataIniatilizer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserIfNotExists();
    }

    private void createDefaultUserIfNotExists() {

        for(int i=1; i<=5; i++) {
            String defaultEmail = "user"+i+"@email.com";
            if(userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User " + i);
            user.setEmail(defaultEmail);
            user.setPassword("000"+i);
            userRepository.save(user);
            System.out.println("Default vet user "+i+" created successfully" );
        }
    }
}
