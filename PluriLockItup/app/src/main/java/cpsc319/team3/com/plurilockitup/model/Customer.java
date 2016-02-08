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

    /**
     * Takes a list of account names and adds them to the accounts list of customer.
     * Balance is randomly generated: (Double) [randomInt(dollar) + randomDouble(cents)]
     * @param acctNameList all accounts that should be added to the accounts list
     * @param maxBalance the max value of the randomInt for the dollar amount
     */
    public void addAcct(String[] acctNameList, int maxBalance){
        for(String acct: acctNameList){
            accounts.put(acct, (balanceGenerate.nextInt(maxBalance)+balanceGenerate.nextDouble()));
        }
    }

    /**
     * Subtracts the amt from the fromAcct and adds it to the deposit toAcct
     * @param fromAcct acct name to withdraw from
     * @param toAcct acct name to deposit to
     * @param amt amount to transfer
     */
    public void transferFund (String fromAcct, String toAcct, Double amt){
        //Withdraw
        accounts.put(fromAcct, accounts.get(fromAcct)-amt);

        //Deposit
        accounts.put(toAcct, accounts.get(toAcct)+amt);
    }

    /**
     * Given an account name, return the current balance
     * @param acctName account name of balance query
     * @return  the current value of the account
     */
    public Double getBalance(String acctName){
        return accounts.get(acctName);
    }

    /**
     * Gets all the account names
     * @return  All key names in accounts map
     */
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
