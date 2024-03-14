package com.bbdgrad.beanfinancetrackerserver.service;

import com.bbdgrad.beanfinancetrackerserver.controller.country.CountryRequest;
import com.bbdgrad.beanfinancetrackerserver.controller.user.UserRequest;
import com.bbdgrad.beanfinancetrackerserver.model.Country;
import com.bbdgrad.beanfinancetrackerserver.model.User;
import com.bbdgrad.beanfinancetrackerserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<List<User>> getUsers() {
        Optional<List<User>> userList = Optional.of(userRepository.findAll());
        return ResponseEntity.ok().body(userList.orElseGet(List::of));
    }

    public ResponseEntity<User> registerUser(UserRequest userRequest) {
        Optional<User> userExist = userRepository.findByEmail(userRequest.getEmail());
        if (userExist.isPresent()){
            return new ResponseEntity("Email is taken", HttpStatus.CONFLICT);
        }
        var newUser = User.builder().name(userRequest.getName()).email(userRequest.getEmail()).build();
        userRepository.save(newUser);
        return ResponseEntity.ok().body(newUser);
    }

    public ResponseEntity<String> removeUser(Integer id) {
        Optional<User> userExist = userRepository.findById(id);
        if (userExist.isEmpty()) {
            return new ResponseEntity<>("User does not Exists", HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);

        return ResponseEntity.ok().body("User remove successfully");
    }

    public ResponseEntity<User> getUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return new ResponseEntity("User does not exist", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(user.orElse(null));
    }

    public ResponseEntity<User> updateUser(Integer id, Optional<String> name, Optional<String> email ) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            return new ResponseEntity("User does not Exists", HttpStatus.NOT_FOUND);
        }
        name.ifPresent(s -> user.get().setName(s));
        email.ifPresent(s -> user.get().setEmail(s));
        userRepository.save(user.get());

        return ResponseEntity.ok().body(user.get());

    }


}
