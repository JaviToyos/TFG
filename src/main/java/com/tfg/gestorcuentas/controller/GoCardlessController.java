package com.tfg.gestorcuentas.controller;

import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.service.proveedor.goCardless.GoCardlessService;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.balance.AccountBalance;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.details.AccountDetails;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.transaction.AccountTransaction;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.userAccounts.AccountList;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.bank.Bank;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.bank.BankLink;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.bank.BankList;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.user.UserGoCardlessAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/nordigen")
public class GoCardlessController {

    public GoCardlessService goCardlessService;

    @Autowired
    public GoCardlessController(GoCardlessService goCardlessService) {
        this.goCardlessService = goCardlessService;
    }

    @ResponseBody
    @PostMapping(value = "/access")
    public ResponseEntity<?> getAccessToken(@RequestBody UsuarioEntity user) {
        UserGoCardlessAccess userGoCardlessAccess = goCardlessService.getUserToken(user);
        if (userGoCardlessAccess != null) return new ResponseEntity<>(userGoCardlessAccess, HttpStatus.OK);

        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/availableBanks")
    public ResponseEntity<?> getAvailableBanks(@RequestBody String userToken) {
        List<BankList> availableBanks = goCardlessService.getAvailableBanks(userToken);
        if (availableBanks != null) return new ResponseEntity<>(availableBanks, HttpStatus.OK);

        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/bank")
    public ResponseEntity<?> getBankLink(@RequestBody String userToken, @RequestBody Bank bank) {
        BankLink bankLink = goCardlessService.getBankLink(userToken, bank);
        if (bankLink != null) return new ResponseEntity<>(bankLink, HttpStatus.OK);

        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/accountList")
    public ResponseEntity<?> getAccountList(@RequestBody String userToken, @RequestBody Bank bank, @RequestBody BankLink bankLink) {
        AccountList accountList = goCardlessService.getAccountList(userToken, bank, bankLink);
        if (accountList != null) return new ResponseEntity<>(accountList, HttpStatus.OK);

        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/accountDetails")
    public ResponseEntity<?> getAccountDetails(@RequestBody String userToken, @RequestBody String accountID) {
        AccountDetails accountDetails = goCardlessService.getAccountDetails(userToken, accountID);
        if (accountDetails != null) return new ResponseEntity<>(accountDetails, HttpStatus.OK);

        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/accountBalance")
    public ResponseEntity<?> getAccountBalances(@RequestBody String userToken, @RequestBody String accountID) {
        AccountBalance accountBalance = goCardlessService.getAccountBalances(userToken, accountID);
        if (accountBalance != null) return new ResponseEntity<>(accountBalance, HttpStatus.OK);

        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/accountTransactions")
    public ResponseEntity<?> getAccountTransactions(@RequestBody String userToken, @RequestBody String accountID) {
        AccountTransaction accountTransaction = goCardlessService.getAccountTransactions(userToken, accountID);
        if (accountTransaction != null) return new ResponseEntity<>(accountTransaction, HttpStatus.OK);

        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }
}
