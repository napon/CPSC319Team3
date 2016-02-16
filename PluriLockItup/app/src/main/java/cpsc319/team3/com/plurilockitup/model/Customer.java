package cpsc319.team3.com.plurilockitup.model;

//import org.json.JSONException;
import android.content.Context;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.net.URI;
import java.net.URISyntaxException;
import javax.json.Json;

import cpsc319.team3.com.biosense.utils.PluriLockNetworkUtil;
//import org.json.JSONObject;

/**
 * Created by kelvinchan on 16-02-05. Modified by Noah later than that.
 * Singleton class; will only ever have one use logged in to the app at any time.
 * This lets us grab customer information from other views and re-create views without
 * changing the logged in customer information.
 */

public class Customer implements Serializable{
    private HashMap<String, Double> accounts;
    private String uId;
    private static Customer thisInstance;

    //These need to be under customer instead of main activity because customers could
    //name their accounts differently.
    private String[] dayAccountNames; //the names of the chequing accounts.
    private String[] creditAcctNames; //the names of the credit accounts
    private Random balanceGenerate; //RNG to generate account balances

    /**
     * Gets the active customer instance. If there isn't one, it will generate a
     * default one.
     * @return the active customer instance
     */
    public static Customer getInstance(){
        if(thisInstance == null) {
            thisInstance = new Customer();
        }
        return thisInstance;
    }

    /**
     * Generates a new default customer
     * @return the new active customer
     */
    public static Customer rebuild(){
        thisInstance = new Customer();
        return thisInstance;
    }

    /**
     * Generates a new customer with that particular tokenID
     * Was thinking that this would be useful once we authenticate them with
     * Plurilock and we can stash their token somewhere.
     * @param tokenID - Plurilock (or the bank's) unique user token.
     * @return the new customer
     */
    public static Customer rebuild(String tokenID){
        thisInstance = new Customer(tokenID);
        return thisInstance;
    }

    /**
     * Constructor to build a default customer with a particular tokenID.
     * @param idToken
     */
    private Customer(String idToken){
        accounts = new HashMap<>();
        this.uId = idToken;
        this.balanceGenerate = new Random();
        populateAccounts();
    }

    /**
     * Default constrcutor, builds a customer with the default token ID.
     */
    private Customer (){
        this(Utils.DEFAULT_ID_TOKEN);
    }

    /**
     * This helper method generates the accounts with the default values.
     */
    private void populateAccounts() {
        this.dayAccountNames = Utils.DEFAULT_DAY_ACCOUNT_NAMES;
        this.addAcct(Utils.DEFAULT_DAY_ACCOUNT_NAMES, 10000);
        this.creditAcctNames = Utils.DEFAULT_CRDT_ACCOUNT_NAMES;
        this.addAcct(Utils.DEFAULT_CRDT_ACCOUNT_NAMES, 2500);
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
    public void transferFund (String fromAcct, String toAcct, Double amt, Context c) throws Exception {
        //Withdraw
        accounts.put(fromAcct, accounts.get(fromAcct) - amt);

        //Send data package to PluriLock
        try {
            // open websocket
            final PluriLockNetworkUtil plnu = new PluriLockNetworkUtil(new URI("wss://localhost:8080"), c);

            if (plnu.preNetworkCheck()) {
                // add listener
                plnu.addMessageHandler(new PluriLockNetworkUtil.MessageHandler() {
                    public void handleMessage(String message) {
//                    JsonObject jsonObject = Json.createReader(new StringReader(message)).readObject();
//                    String userName = jsonObject.getString("user");
                        System.out.println(message);
                    }
                });

                // send message to websocket every 5 seconds
                for (int i = 0; i <= 25; i++) {
                    plnu.sendMessage(getMessage(i + " Hi There!!"));
                    Thread.sleep(5000);
                }
            }
        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }

        //Deposit
        accounts.put(toAcct, accounts.get(toAcct)+amt);
    }

    /**
     * Create a json representation.
     *
     * @param message
     * @return json string
     */
    private static String getMessage(String message) {
        return Json.createObjectBuilder()
                .add("user", "bot")
                .add("message", message)
                .build()
                .toString();
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

    /**
     * Returns the customer's credit account names
     * @return dayAccountNames[]
     */
    public String[] getDayAccountNames(){
        String[] toReturn = new String[this.dayAccountNames.length];
        for(int i = 0; i<dayAccountNames.length; i++){
            toReturn[i] = dayAccountNames[i];
        }
        return toReturn;
    }

    /**
     * Returns the customer's credit account names
     * @return creditAcctNames[]
     */
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

    public String getuId() { return uId; }

    public void setuId(String tokenID){
        this.uId = tokenID;
    }
}
