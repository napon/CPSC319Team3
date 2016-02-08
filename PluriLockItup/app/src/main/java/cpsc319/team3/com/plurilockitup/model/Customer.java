package cpsc319.team3.com.plurilockitup.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by kelvinchan on 16-02-05.
 */
public class Customer implements Serializable{

    public HashMap<String, Double> accounts;

    private Random balanceGenerate;

    public Customer (){
        accounts = new HashMap<>();
        this.balanceGenerate = new Random();
    }

    public Customer (HashMap <String, Double> accounts){
        this.accounts = accounts;
        this.balanceGenerate = new Random();
    }

    public void addAcct(String[] acctNameList, int maxBalance){
        for(String acct: acctNameList){
            accounts.put(acct, (balanceGenerate.nextInt(maxBalance)+balanceGenerate.nextDouble()));
        }
    }

    public void transferFund (String fromAcct, String toAcct, Double amt){
        //Withdraw
        accounts.put(fromAcct, accounts.get(fromAcct)-amt);

        //Deposit
        accounts.put(toAcct, accounts.get(toAcct)+amt);
    }

    public Double getBalance(String acctName){
        return accounts.get(acctName);
    }

    public ArrayList<String> getAccountNameList(){
        ArrayList<String> acctNames = new ArrayList<>();
        for (String key : accounts.keySet()){
            acctNames.add(key);
        }
        return acctNames;
    }

    public HashMap<String, Double> getAccounts(){
        return accounts;
    }

    public void setAccounts(HashMap<String, Double> accounts){
        this.accounts = accounts;
    }
}
