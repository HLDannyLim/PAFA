package sg.edu.nus.iss.app.FundTransfer.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.util.MultiValueMap;

import sg.edu.nus.iss.app.FundTransfer.exception.AccountException;
import sg.edu.nus.iss.app.FundTransfer.model.Account;
import sg.edu.nus.iss.app.FundTransfer.repo.AccountsRepository;
import sg.edu.nus.iss.app.FundTransfer.service.AccountService;

@Controller
@RequestMapping(path = {"/","/index.html"})
public class FundsTransferController {
    @Autowired
    AccountsRepository accRepo;

    @Autowired
    AccountService accSvc;

    @GetMapping
    public String getForm(Model model,HttpSession sess){
        System.out.println("inside getform getmapping");
        List<Account> result = new LinkedList<>();
        result = accRepo.findAllAccount();
        String error = (String) sess.getAttribute("error");

        model.addAttribute("result", result);
        model.addAttribute("error", error);
        return "view0";
    }

    @PostMapping(path = "/transfer")
    public String transfer (@RequestBody MultiValueMap<String, String> form, Model model,HttpSession sess) throws AccountException{
        // Account fromAcc = new Account();
        // fromAcc.setName(fromacc);
        // Account toAcc = new Account();
        // toAcc.setName(toacc);
        Float amount = Float.parseFloat(form.getFirst("amount"));
        String fromacc = form.getFirst("faccount");
        String toacc = form.getFirst("taccount");


        //check both account 
        boolean checkAccount = accSvc.checkAccount(fromacc,toacc);

        //check amount
        boolean checkAmount = accSvc.checkAmount(fromacc,toacc,amount);

        if(checkAccount == false){
            model.addAttribute("error", "Please check your Account");
            sess.setAttribute("error", "Please check your Account");
            return "redirect:/";
        }
        if(checkAmount == false){
            model.addAttribute("error", "Please check your Amount");
            sess.setAttribute("error", "Please check your Amount");
            return "redirect:/";
        }

        String transactionId = accSvc.fundsTransferService(fromacc,toacc, amount);




        Account facc = new Account();
        facc = accSvc.getAccountDetails(fromacc);
        Account tacc = new Account();
        tacc = accSvc.getAccountDetails(toacc);

        String line1 = "Your transfer of $" + amount + " from"; 
        String line2 = fromacc + " (" + facc.getAccountId() + ") to"; 
        String line3 = toacc + " (" + tacc.getAccountId() +")"; 
        String line4 = "is successful"; 
        String line5 = "Transaction id is " + transactionId; 


        model.addAttribute("line1", line1);
        model.addAttribute("line2", line2);
        model.addAttribute("line3", line3);
        model.addAttribute("line4", line4);
        model.addAttribute("line5", line5);


        System.out.println("@@@@@@@@@"+"     "+ amount + "    " + checkAccount+checkAmount );
        return "view1";
    }

    // @GetMapping(path = "/getaccount")
    // public String getAccount(Model model){
    //     List<Account> result = new LinkedList<>();
    //     result = accRepo.findAllAccount();
    //     model.addAttribute("result", result);

    //     return "view9";
    // }



    
}
