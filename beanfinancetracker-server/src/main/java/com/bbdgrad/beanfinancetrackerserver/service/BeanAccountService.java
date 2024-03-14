package com.bbdgrad.beanfinancetrackerserver.service;

import com.bbdgrad.beanfinancetrackerserver.controller.beanaccount.BeanAccountRequest;
import com.bbdgrad.beanfinancetrackerserver.model.Batch;
import com.bbdgrad.beanfinancetrackerserver.model.BeanAccount;
import com.bbdgrad.beanfinancetrackerserver.model.User;
import com.bbdgrad.beanfinancetrackerserver.repository.BeanAccountRepository;
import com.bbdgrad.beanfinancetrackerserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BeanAccountService {
    private final BeanAccountRepository beanAccountRepository;
    private final UserRepository userRepository;

    public ResponseEntity<List<BeanAccount>> getBeanAccounts() {
        Optional<List<BeanAccount>> beanAccountList = Optional.of(beanAccountRepository.findAll());
        return ResponseEntity.ok().body(beanAccountList.orElseGet(List::of));
    }

    public ResponseEntity<BeanAccount> registerBeanAccount(BeanAccountRequest beanAccountRequest) {
        Optional<User> userExists = userRepository.findById(beanAccountRequest.getUser_id());
        if (userExists.isEmpty()) {
            return new ResponseEntity("User does not Exists", HttpStatus.NOT_FOUND);
        }
        var newBeanAccount = BeanAccount.builder()
                .account_name(beanAccountRequest.getAccount_name())
                .user_id(beanAccountRequest.getUser_id())
                .current_balance(beanAccountRequest.getCurrent_balance()).build();
        beanAccountRepository.save(newBeanAccount);
        return ResponseEntity.ok().body(newBeanAccount);
    }

    public ResponseEntity<BeanAccount> getBeanAccount(Integer id) {
        Optional<BeanAccount> beanAccount = beanAccountRepository.findById(id);
        if (beanAccount.isEmpty()) {
            return new ResponseEntity("BeanAccount does not exist", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(beanAccount.orElse(null));
    }
    public ResponseEntity<String> removeBeanAccount(Integer id) {
        Optional<BeanAccount> beanAccountExist = beanAccountRepository.findById(id);
        if (beanAccountExist.isEmpty()) {
            return new ResponseEntity<>("BeanAccount does not Exists", HttpStatus.NOT_FOUND);
        }
        beanAccountRepository.deleteById(id);
        return ResponseEntity.ok().body("BeanAccount removed successfully");
    }

    public ResponseEntity<BeanAccount> updateBeanAccount(Integer id, Optional<Integer> user_id, Optional<String> account_name, Optional<BigDecimal> current_balance){
        Optional<BeanAccount> beanAccount = beanAccountRepository.findById(id);
        if(beanAccount.isEmpty()){
            return new ResponseEntity("BeanAccount does not exist", HttpStatus.NOT_FOUND);
        }
        if(user_id.isPresent()){
            Optional<User> user = userRepository.findById(user_id.get());
            if(user.isEmpty()){
                return new ResponseEntity("User does not exist", HttpStatus.NOT_FOUND);
            }

            beanAccount.get().setUser_id(user_id.get());
        }
        account_name.ifPresent(s -> beanAccount.get().setAccount_name(s));
        current_balance.ifPresent(s -> beanAccount.get().setCurrent_balance(s));
        beanAccountRepository.save(beanAccount.get());
        return ResponseEntity.ok().body(beanAccount.get());
    }

}
