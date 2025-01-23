package com.goldonbuy.goldonbackend.userContext.security.authentication;

import com.goldonbuy.goldonbackend.catalogContext.exceptions.AlreadyExistingException;
import com.goldonbuy.goldonbackend.catalogContext.response.ApiResponse;
import com.goldonbuy.goldonbackend.userContext.dto.UserDTO;
import com.goldonbuy.goldonbackend.userContext.entity.User;
import com.goldonbuy.goldonbackend.userContext.repository.UserRepository;
import com.goldonbuy.goldonbackend.userContext.request.AddUserRequest;
import com.goldonbuy.goldonbackend.userContext.request.LoginRequest;
import com.goldonbuy.goldonbackend.userContext.security.authentication.service.AuthService;
// import com.goldonbuy.goldonbackend.userContext.security.jwt.JwtUtils;
import com.goldonbuy.goldonbackend.userContext.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("${api.prefix-2}/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    private final IUserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AddUserRequest request) {
        try {
            if(this.userRepository.findByEmail(request.getEmail()) != null) {
                return ResponseEntity.badRequest().body("Username is already in use");
            }

            User user = this.userService.createUser(request);
            UserDTO userDTO = this.userService.convertToDTO(user);
            return ResponseEntity.ok(new ApiResponse("Create User success !", userDTO));
        } catch (AlreadyExistingException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequest request) {

        String token = this.authService.login(request);

        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setAccessToken(token);

        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }
}
