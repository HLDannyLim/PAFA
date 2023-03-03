package sg.edu.nus.iss.app.FundTransfer.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.app.FundTransfer.model.Account;
import sg.edu.nus.iss.app.FundTransfer.repo.AccountsRepository;
import sg.edu.nus.iss.app.FundTransfer.exception.AccountException;

@Service
public class AccountService {
    @Autowired
    AccountsRepository accRepo;

    @Autowired
    LogAuditService logAuditService;



    public boolean checkAccount(String fromName, String toName){
        List<Account> acc = new LinkedList<>();
        acc = accRepo.findAllAccount();
        boolean fname = false;
        boolean tname = false;
        boolean result = false;
        if(fromName.equals(toName))
        return false ;
        for (Account account : acc) {
            if(fromName.equals(account.getName()))
            fname = true;
            if(account.getAccountId().length() == 8)
            fname = true;


            if(toName.equals(account.getName()))
            tname = true;
            if(account.getAccountId().length() == 8)
            fname = true;
        }
        if(fname && tname == true)
        result = true;

        return result;
    }


    public boolean checkAmount(String fromName, String toName, float amount){
        List<Account> acc = new LinkedList<>();
        acc = accRepo.findAllAccount();
        boolean result = true;

        if(amount < 10)
        return false;

        for (Account account : acc) {
            if(fromName.equals(account.getName())){
                if(account.getBalance() < amount)
                return false;
            }
        }
        return result;
    }

    public Account getAccountDetails(String name){
        Account result = new Account();
        List<Account> acc = new LinkedList<>();
        acc = accRepo.findAllAccount();
        for (Account account : acc) {
            if(name.equals(account.getName())){
                result.setAccountId(account.getAccountId());
                result.setName(account.getName());
                result.setBalance(account.getBalance());
            }
        }
        return result;
    }

    @Transactional(rollbackFor = AccountException.class)
    public String fundsTransferService(String fromName, String toName, float amount) throws AccountException{
        Account facc = new Account();
        facc = getAccountDetails(fromName);
        Account tacc = new Account();
        tacc = getAccountDetails(toName);

        boolean from = accRepo.fundTransferFrom(facc, amount);
        boolean to = accRepo.fundTransferTo(tacc, amount);
        String transactionId=UUID.randomUUID().toString().substring(0, 8);
        JsonObject json = logAuditService.generateJsonData(facc,tacc,amount,transactionId);
        logAuditService.saveToRedis(transactionId,json);

        return transactionId;
    }



}
