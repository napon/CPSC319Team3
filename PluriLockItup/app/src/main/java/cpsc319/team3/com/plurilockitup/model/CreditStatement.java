package cpsc319.team3.com.plurilockitup.model;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by kelvinchan on 16-02-29.
 */
public class CreditStatement {
    HashMap<String, String> purchaseList;
    int numPurchases;
    String[] purchaseName;

    Random random;

    public CreditStatement(int numPurchases){
        this.numPurchases = numPurchases;
        random = new Random();

        purchaseName = new String[] {"Geralds General store",
                "Puppy4U Pet Store", "BC Hydro", "Visa Infinite-InDebt",
                "UBC Course Fee", "UBC Fees", "UBC Hidden Fees", "UBC IOU",
                "SolarDollar Coffee"};
        purchaseList = new HashMap<>();
        populatePurchaseList();
    }

    private void populatePurchaseList(){
        while(numPurchases > 0){
            purchaseList.put(purchaseName(), purchaseAmt(500));
            numPurchases--;
        }
    }

    private String purchaseName(){
        int pos = random.nextInt(purchaseName.length);
        return purchaseName[pos];
    }

    private String purchaseAmt(int maxBalance){
        Double amt = random.nextInt(maxBalance)+random.nextDouble();

        DecimalFormat amtString = new DecimalFormat("#.##");
        amtString.setMinimumFractionDigits(2);

        return "$ " + amtString.format(amt);
    }

    public HashMap<String, String> getPurchaseList(){
        return purchaseList;
    }


}
