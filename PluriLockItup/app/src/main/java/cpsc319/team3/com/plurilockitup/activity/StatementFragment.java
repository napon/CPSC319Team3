package cpsc319.team3.com.plurilockitup.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import cpsc319.team3.com.plurilockitup.R;

public class StatementFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statement, container, false);

        TableLayout statementTable = (TableLayout) view.findViewById(R.id.statementTable);

        for(int i = 0;i<5;i++){
            View row = getLayoutInflater(savedInstanceState).inflate(R.layout.statement_row, null);
            statementTable.addView(row);
        }

        return view;
    }
}
