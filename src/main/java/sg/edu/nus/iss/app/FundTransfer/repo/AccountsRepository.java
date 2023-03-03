package sg.edu.nus.iss.app.FundTransfer.repo;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.app.FundTransfer.model.Account;

@Repository
public class AccountsRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;


        public List<Account> findAllAccount(){
            String findAllSQL = "select * from accounts ";
            List<Account> result = new LinkedList<Account>();

            result = jdbcTemplate.query(findAllSQL, BeanPropertyRowMapper.newInstance(Account.class));
            for (Account account : result) {
                account.setText(account.getName() + " (" + account.getAccountId() + " )");
            }
            return result;
        }

        public boolean fundTransferFrom(Account acc,float amount){
            String updateSQL="update accounts set balance = ?, account_id = ? where account_id = ?";
            String accountId=UUID.randomUUID().toString().substring(0, 8);
            float balance = acc.getBalance() - amount ;

            Integer result = jdbcTemplate.update(updateSQL,balance,accountId,acc.getAccountId());

            return result>0?true:false;
        }

        public boolean fundTransferTo(Account acc,float amount){
            String updateSQL="update accounts set balance = ? where account_id = ?";

            float balance = acc.getBalance() + amount ;

            Integer result = jdbcTemplate.update(updateSQL,balance,acc.getAccountId());

            return result>0?true:false;
        }
        
}
