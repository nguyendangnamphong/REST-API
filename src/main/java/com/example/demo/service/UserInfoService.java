package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByEmail(username); 
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }

    public List<UserInfo> getAllUsers() {
        return repository.findAll();
    }

    public String updateUser(int id, UserInfo userInfo) {
        Optional<UserInfo> existingUser = repository.findById(id);
        if (existingUser.isPresent()) {
            UserInfo userToUpdate = existingUser.get();
            userToUpdate.setName(userInfo.getName());
            userToUpdate.setEmail(userInfo.getEmail());
            userToUpdate.setPassword(encoder.encode(userInfo.getPassword()));
            userToUpdate.setRoles(userInfo.getRoles());
            repository.save(userToUpdate);
            return "User Updated Successfully";
        } else {
            return "User Not Found";
        }
    }

    public String deleteUser(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "User Deleted Successfully";
        } else {
            return "User Not Found";
        }
    }
}