package com.bbdgrad.beanfinancetrackerserver.controller.beanaccount;

import com.bbdgrad.beanfinancetrackerserver.model.BeanAccount;
import com.bbdgrad.beanfinancetrackerserver.service.BeanAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/beanAccount")
public class BeanAccountController {
    private final BeanAccountService beanAccountService;

    @GetMapping()
    public ResponseEntity<List<BeanAccount>> getBeanAccounts() {
        return beanAccountService.getBeanAccounts();
    }

    @PostMapping
    public ResponseEntity<BeanAccount> registerBeanAccount(@RequestBody BeanAccountRequest beanAccountRequest) {
        return beanAccountService.registerBeanAccount(beanAccountRequest);
    }

    @GetMapping("/{beanAccountId}")
    public ResponseEntity<BeanAccount> getBeanAccount(@PathVariable("beanAccountId") Integer beanAccountId) {
        return beanAccountService.getBeanAccount(beanAccountId);
    }

    @DeleteMapping("/delete/{beanAccountId}")
    public ResponseEntity<String> removeBeanAccount(@PathVariable("beanAccountId") int beanAccountId) {
        return beanAccountService.removeBeanAccount(beanAccountId);
    }

    @PutMapping("/update/{beanAccountId}")
    public ResponseEntity<BeanAccount> updateBeanAccount(@PathVariable("beanAccountId") Integer beanAccountId,
                                                      @RequestParam(required = false) Optional<Integer> user_id,
                                                      @RequestParam(required = false) Optional<String> account_name,
                                                      @RequestParam(required = false) Optional<BigDecimal> current_balance) {
        return beanAccountService.updateBeanAccount(beanAccountId, user_id, account_name, current_balance);
    }
}
