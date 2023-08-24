package com.tfg.gestorcuentas.controller;

import com.tfg.gestorcuentas.service.nordigen.model.*;
import com.tfg.gestorcuentas.service.proveedor.goCardless.GoCardlessService;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.balance.AccountBalance;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.details.AccountDetails;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.transaction.AccountTransaction;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.userAccounts.AccountList;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.bank.BankLink;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.bank.BankList;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.user.UserGoCardlessAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/goCardless")
public class GoCardlessController {

    public GoCardlessService goCardlessService;

    @Autowired
    public GoCardlessController(GoCardlessService goCardlessService) {
        this.goCardlessService = goCardlessService;
    }

    @ResponseBody
    @PostMapping(value = "/access")
    public ResponseEntity<?> getAccessToken() {
        UserGoCardlessAccess userGoCardlessAccess = goCardlessService.getUserToken();
        if (userGoCardlessAccess != null) return new ResponseEntity<>(userGoCardlessAccess, HttpStatus.OK);

        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @PostMapping(value = "/availableBanks")
    public ResponseEntity<?> getAvailableBanks(@RequestBody Token token) {
        List<BankList> availableBanks = goCardlessService.getAvailableBanks(token.getToken());
        if (availableBanks != null) return new ResponseEntity<>(availableBanks, HttpStatus.OK);

        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @PostMapping(value = "/bank")
    public ResponseEntity<?> getBankLink(@RequestBody BankLinkRequest request) {
        BankLink bankLink = goCardlessService.getBankLink(request.getToken(), request.getBank());
        if (bankLink != null) return new ResponseEntity<>(bankLink, HttpStatus.OK);

        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @PostMapping(value = "/accountList")
    public ResponseEntity<?> getAccountList(@RequestBody AccountListRequest request) {
        AccountList accountList = goCardlessService.getAccountList(request.getToken(), request.getBankLink());
        if (accountList != null) return new ResponseEntity<>(accountList, HttpStatus.OK);

        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @PostMapping(value = "/accountDetails")
    public ResponseEntity<?> getAccountDetails(@RequestBody AccountDetailRequest request) {
        AccountDetails accountDetails = goCardlessService.getAccountDetails(request.getToken(), request.getAccountId());
        if (accountDetails != null) return new ResponseEntity<>(accountDetails, HttpStatus.OK);

        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @PostMapping(value = "/accountBalance")
    public ResponseEntity<?> getAccountBalances(@RequestBody AccountBalanceRequest request) {
        AccountBalance accountBalance = goCardlessService.getAccountBalances(request.getToken(), request.getAccountId());
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
