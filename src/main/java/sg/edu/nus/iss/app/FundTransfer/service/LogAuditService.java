package sg.edu.nus.iss.app.FundTransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.app.FundTransfer.model.Account;

@Service
public class LogAuditService {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    
    public JsonObject generateJsonData(Account from, Account to, float amount, String transactionId){

        String date = java.time.LocalDate.now().toString();
        System.out.println(String.valueOf(amount));

        JsonObject value = Json.createObjectBuilder()
        .add("transactionId", transactionId)
        .add("date", date)
        .add("from_account", from.getAccountId())
        .add("to_account", to.getAccountId())
        .add("amount", String.valueOf(amount))
        .build();

        return value ;
    }

    public void saveToRedis(String transactionId,JsonObject o){

        // // if send to string us this methos
        // System.out.println("getbody to string"+resp.getBody().toString());
        // System.out.println("get body"+resp.getBody());
        // System.out.println("resp"+resp);
        // redisTemplate.opsForValue().set(c.getId(),resp.getBody().toString());

        redisTemplate.opsForValue().set(transactionId, o.toString());
        String result = (String) redisTemplate.opsForValue().get(transactionId);
        System.out.println("  RESULT >>> " + result);
    }
}
