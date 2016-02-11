package cpsc319.team3.com.plurilockitup.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by kelvinchan on 16-02-05.
 */
public class Customer implements Serializable{
    private static final String DEFAULT_ID_TOKEN = "abcd1234";
    private HashMap<String, Double> accounts;
    private static Customer thisInstance;
    private String[] dayAccountNames;
    private String[] creditAcctNames;

    public static Customer rebuild(){
        thisInstance = new Customer();
        return thisInstance;
    }

    public static Customer rebuild(String tokenID){
        thisInstance = new Customer(tokenID);
        return thisInstance;
    }


    public static Customer getInstance(){
        if(thisInstance == null) {
            thisInstance = new Customer();
        }
        return thisInstance;
    }
    private Random balanceGenerate;
    public Customer(String idToken){
        accounts = new HashMap<>();
        this.balanceGenerate = new Random();
        populateAccounts(accounts, balanceGenerate);
    }
    public Customer (){
        this(DEFAULT_ID_TOKEN);
    }

    private void populateAccounts(HashMap<String, Double> accounts, Random balanceGenerate) {
        String[] dayAcctList = new String[]{"Sunny Day CHQ", "Rainy Day SAV", "Snowy Day SAV"};
        this.dayAccountNames = dayAcctList;
        this.addAcct(dayAcctList, 10000);
        String[] creditAcctList = new String[]{"Loan2U", "Ca$hUPFront", "Y.U.Broke"};
        this.creditAcctNames = creditAcctList;
        this.addAcct(creditAcctList, 2500);
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
     * Turns the double value of account into a decimal currency format
     * @param acctName of the balance query
     * @return dollar amount of account
     */

    public String getBalanceString(String acctName){
        DecimalFormat amtString = new DecimalFormat("#.##");
        amtString.setMinimumFractionDigits(2);

        return "$ " + amtString.format(getBalance(acctName));
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

    public String[] getDayAccountNames(){
        String[] toReturn = new String[this.dayAccountNames.length];
        for(int i = 0; i<dayAccountNames.length; i++){
            toReturn[i] = dayAccountNames[i];
        }
        return toReturn;
    }

    public String[] getCreditAcctNames(){
        String[] toReturn = new String[this.creditAcctNames.length];
        for(int i = 0; i<creditAcctNames.length; i++){
            toReturn[i] = creditAcctNames[i];
        }

        return toReturn;
    }


    public HashMap<String, Double> getAccounts(){
        return accounts;
    }

    public void setAccounts(HashMap<String, Double> accounts){
        this.accounts = accounts;
    }
}
