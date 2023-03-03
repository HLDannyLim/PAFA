package sg.edu.nus.iss.app.FundTransfer.exception;

public class AccountException extends Exception {
    public AccountException(){
        super("Transfer unsuccessful");
    }
    public AccountException(String msg){
        super(msg);
    }
    
}
