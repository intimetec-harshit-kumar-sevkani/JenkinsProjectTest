package com.example.springEmployee.jwt;

import com.example.springEmployee.entity.Employee;
import com.example.springEmployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailService implements UserDetailsService {


    @Autowired
   private EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       Employee employee =  employeeRepository.findByUserName(username).orElseThrow(() -> new RuntimeException("User not found !!"));

        return new User(
                employee.getUsername(),
                employee.getPassword(),
                this.getAuthorities(employee)
        );
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Employee employee){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+employee.getRole().getRoleName()));
        return authorities;
    }
}
