package com.nested.paras.mygroc.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nested.paras.mygroc.R;

public class DetailsActivity extends AppCompatActivity{

    private TextView item,qty,date;
    private Button edit,dlt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        item=(TextView)findViewById(R.id.grocery_item_id);
        qty=(TextView)findViewById(R.id.grocery_qty_id);
        date=(TextView)findViewById(R.id.date_id);

        edit = (Button)findViewById(R.id.edit_id);
        dlt = (Button)findViewById(R.id.dlt_id);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            item.setText(bundle.getString("name"));
            qty.setText(bundle.getString("quantity"));
            date.setText(bundle.getString("date"));

        }

    }



}
