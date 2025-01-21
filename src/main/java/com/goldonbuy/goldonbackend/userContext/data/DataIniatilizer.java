package com.goldonbuy.goldonbackend.userContext.data;

import com.goldonbuy.goldonbackend.userContext.entity.Role;
import com.goldonbuy.goldonbackend.userContext.entity.User;
import com.goldonbuy.goldonbackend.userContext.repository.RoleRepository;
import com.goldonbuy.goldonbackend.userContext.repository.UserRepository;
import com.goldonbuy.goldonbackend.userContext.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataIniatilizer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleService roleService;



    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_USER");
        createDefaultUserIfNotExists();
        createDefaultRoleIfNotExists(defaultRoles);
        createDefaultAdminIfNotExists();
    }

    private void createDefaultUserIfNotExists() {

        Role userRole = this.roleRepository.findByName("ROLE_USER");

        for(int i=1; i<=5; i++) {
            String defaultEmail = "user"+i+"@email.com";
            if(userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User " + i);
            user.setEmail(defaultEmail);
            user.setPassword(this.passwordEncoder.encode("000"+i));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default vet user "+i+" created successfully" );
        }
    }

    private void createDefaultAdminIfNotExists() {

        Role adminRole = this.roleRepository.findByName("ROLE_ADMIN");

        for(int i=1; i<=2; i++) {
            String defaultEmail = "admin"+i+"@email.com";
            if(userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("The Admin");
            user.setLastName("Admin " + i);
            user.setEmail(defaultEmail);
            user.setPassword(this.passwordEncoder.encode("000"+i));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default admin "+i+" created successfully" );
        }
    }

    private void createDefaultRoleIfNotExists(Set<String> roles) {

        roles.stream()
                .filter(role -> {
                    return this.roleService.getRoleByName(role).getName().isEmpty();
                })
                .map(Role::new).forEach(roleRepository::save);
    }
}
