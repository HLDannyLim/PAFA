package sg.edu.nus.iss.app.FundTransfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String accountId;
    private String name;
    private float balance;
    private String text;



}
