package cpsc319.team3.com.plurilockitup.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import cpsc319.team3.com.plurilockitup.R;
import cpsc319.team3.com.plurilockitup.model.CreditStatement;

public class StatementFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statement, container, false);
        TableLayout statementTable = (TableLayout) view.findViewById(R.id.statementTable);

        //Get month list and set frag month title
        String month = getArguments().getString("month");
        ((TextView)view.findViewById(R.id.purchase_month)).setText(month);
        String[] monthList = getArguments().getStringArray("monthList");

        //create table view for each fragment
        for(int i = 0;i<monthList.length;i++){
            View row = getLayoutInflater(savedInstanceState).inflate(R.layout.statement_row, null);

            CreditStatement creditStatement = new CreditStatement(5);
            HashMap<String, String> purchaseList = creditStatement.getPurchaseList();
            for(Map.Entry<String, String> purchase: purchaseList.entrySet()){
                ((TextView) row.findViewById(R.id.purchase_name)).setText(purchase.getKey());
                ((TextView) row.findViewById(R.id.purchase_amount)).setText(purchase.getValue());
            }

            statementTable.addView(row);
        }

        return view;
    }
}
