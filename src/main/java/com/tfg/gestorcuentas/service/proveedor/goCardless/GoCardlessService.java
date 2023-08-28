package com.tfg.gestorcuentas.service.proveedor.goCardless;

import com.tfg.gestorcuentas.data.entity.ParametroEntity;
import com.tfg.gestorcuentas.data.entity.ProveedorEntity;
import com.tfg.gestorcuentas.data.repository.IParametroJPARepository;
import com.tfg.gestorcuentas.data.repository.IProveedorJPARepository;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.balance.AccountBalance;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.details.AccountDetails;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.transaction.AccountTransaction;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.userAccounts.AccountList;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.bank.Bank;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.bank.BankLink;
import com.tfg.gestorcuentas.service.proveedor.goCardless.model.user.UserGoCardlessAccess;
import com.tfg.gestorcuentas.utils.MessageErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoCardlessService {

    @Value("${application.url}")
    private String applicationURL;
    private final IProveedorJPARepository iProveedorJPARepository;
    private final IParametroJPARepository iParametroJPARepository;

    @Autowired 
    public GoCardlessService(IProveedorJPARepository iProveedorJPARepository, IParametroJPARepository iParametroJPARepository) {
        this.iProveedorJPARepository = iProveedorJPARepository;
        this.iParametroJPARepository = iParametroJPARepository;
    }

    public UserGoCardlessAccess getUserToken() {
        Optional<ProveedorEntity> entity1 = iProveedorJPARepository.findByName("GoCardless");
        ProveedorEntity proveedor = entity1.orElseThrow(() -> new NoSuchElementException(MessageErrors.PROVIDER_CREDENTIALS_NOT_FOUND.getErrorCode()));

        List<ParametroEntity> paramsList = iParametroJPARepository.findByProveedorId(proveedor.getId());
        if(paramsList.isEmpty()) throw new NoSuchElementException(MessageErrors.PARAM_NOT_FOUND.getErrorCode());

        String url = "https://bankaccountdata.gocardless.com/api/v2/token/new/";
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> params = paramsList.stream().collect(Collectors.toMap(ParametroEntity::getAtributo, ParametroEntity::getValor));

        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);

        return template.postForObject(url, request, UserGoCardlessAccess.class);
    }

    public List getAvailableBanks(String userToken) {
        if (userToken == null) throw new IllegalArgumentException(MessageErrors.USER_TOKEN_NULL.getErrorCode());

        String url = "https://bankaccountdata.gocardless.com/api/v2/institutions/?country=es";
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", userToken);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

        return template.exchange(url, HttpMethod.GET, request, List.class).getBody();
    }

    public BankLink getBankLink(String userToken, Bank bank) {
        if (userToken == null) throw new IllegalArgumentException(MessageErrors.USER_TOKEN_NULL.getErrorCode());
        if (bank == null) throw new IllegalArgumentException(MessageErrors.BANK_SELECTED_NULL.getErrorCode());

        String url = "https://bankaccountdata.gocardless.com/api/v2/requisitions/";
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", userToken);

        Map<String, String> data = new HashMap<>();
        data.put("redirect", applicationURL);
        data.put("institution_id", bank.getId());

        HttpEntity<Map<String, String>> request = new HttpEntity<>(data, headers);

        return template.exchange(url, HttpMethod.POST, request, BankLink.class).getBody();
    }

    public AccountList getAccountList(String userToken, BankLink bankLink) {
        if (userToken == null) throw new IllegalArgumentException(MessageErrors.USER_TOKEN_NULL.getErrorCode());
        if (bankLink == null) throw new IllegalArgumentException(MessageErrors.BANKLINK_NULL.getErrorCode());

        String url = "https://bankaccountdata.gocardless.com/api/v2/requisitions/" + bankLink.getId();
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", userToken);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

        return template.exchange(url, HttpMethod.GET, request, AccountList.class).getBody();
    }

    public AccountDetails getAccountDetails(String userToken, String account) {
        if (account == null || account.isEmpty() || account.isBlank())
            throw new IllegalArgumentException(MessageErrors.ACCOUNT_EMPTY_OR_NULL.getErrorCode());

        String url = "https://bankaccountdata.gocardless.com/api/v2/accounts/" + account + "/details/";
        RestTemplate template = new RestTemplate();

        return template.exchange(url, HttpMethod.GET, getHttpEntity(userToken), AccountDetails.class).getBody();
    }

    public AccountBalance getAccountBalances(String userToken, String account) {
        if (account == null || account.isEmpty() || account.isBlank())
            throw new IllegalArgumentException(MessageErrors.ACCOUNT_EMPTY_OR_NULL.getErrorCode());

        String url = "https://bankaccountdata.gocardless.com/api/v2/accounts/" + account + "/balances/";
        RestTemplate template = new RestTemplate();

        return template.exchange(url, HttpMethod.GET, getHttpEntity(userToken), AccountBalance.class).getBody();
    }

    public AccountTransaction getAccountTransactions(String userToken, String account) {
        if (account == null || account.isEmpty() || account.isBlank())
            throw new IllegalArgumentException(MessageErrors.ACCOUNT_EMPTY_OR_NULL.getErrorCode());

        String url = "https://bankaccountdata.gocardless.com/api/v2/accounts/" + account + "/transactions/";
        RestTemplate template = new RestTemplate();

        return template.exchange(url, HttpMethod.GET, getHttpEntity(userToken), AccountTransaction.class).getBody();
    }

    private HttpEntity<Map<String, String>> getHttpEntity(String userToken) {
        if (userToken == null) throw new IllegalArgumentException(MessageErrors.USER_TOKEN_NULL.getErrorCode());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", userToken);

        return new HttpEntity<>(headers);
    }
}
