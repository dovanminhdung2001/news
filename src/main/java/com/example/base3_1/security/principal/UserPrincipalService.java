package com.example.base3_1.security.principal;

import com.example.base3_1.entity.User;
import com.example.base3_1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserPrincipalService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    /* Lấy ra user đã đang nhập */
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userRepository.findUserByPhone(phone);

        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

        UserPrincipal accountDTO = new UserPrincipal(user.getPhone(),user.getPassword(),true,
                true,true, true,authorities);
        accountDTO.setId(user.getId());
        accountDTO.setName(user.getName());
        accountDTO.setRoleId(user.getRole());
        accountDTO.setEmail(user.getEmail());
        accountDTO.setPhone(user.getPhone());
        accountDTO.setActive(user.getIsActive());
        accountDTO.setGender(user.getGender());
        accountDTO.setPassword(user.getPassword());

        return accountDTO;
    }
}
