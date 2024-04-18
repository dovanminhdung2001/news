package com.example.base3_1.security.login;

import com.example.base3_1.dto.MessageResponseDTO;
import com.example.base3_1.dto.UserDTO;
import com.example.base3_1.entity.User;
import com.example.base3_1.security.jwt.JwtResponseDTO;
import com.example.base3_1.security.jwt.JwtTokenService;
import com.example.base3_1.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class LoginApi {
    @Autowired
    JwtTokenService jwtTokenService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @PostMapping("/api/register/user")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        User user = userService.createUser(userDTO);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestParam("phone") String phone,
                                   @RequestParam("password") String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));
            User user = userService.findByPhone(phone);

            String accessToken = jwtTokenService.createToken(phone);
            String refreshToken = jwtTokenService.createRefreshToken(phone);

            return ResponseEntity.ok(new JwtResponseDTO(accessToken, refreshToken, user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage(), e));
        }
    }

    @PostMapping("/api/refresh")
    public ResponseEntity<?> refreshToken(
            @RequestParam String accessToken,
            @RequestParam String refreshToken
    ) {
        try {
            if (jwtTokenService.validateRefreshToken(refreshToken)) {
                String email = jwtTokenService.getEmailFromJwt(accessToken);

                String a = jwtTokenService.createToken(email);
                String r = jwtTokenService.createRefreshToken(email);

                return ResponseEntity.ok(new JwtResponseDTO(a, r));
            }
        }  catch (ExpiredJwtException ex){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/user/demo")
    public ResponseEntity<?> demo () {
        return ResponseEntity.ok(new MessageResponseDTO("ok"));
    }
}
