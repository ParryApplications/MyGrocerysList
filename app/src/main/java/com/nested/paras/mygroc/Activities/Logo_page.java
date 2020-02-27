package com.nested.paras.mygroc.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.nested.paras.mygroc.R;
import com.nested.paras.mygroc.Data.DataBase;

public class Logo_page extends AppCompatActivity {
DataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_page);

        db = new DataBase(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {

                if(db.getgrocerycount() > 0)
                {
                    startActivity(new Intent(Logo_page.this, com.nested.paras.mygroc.Activities.ListActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(Logo_page.this, com.nested.paras.mygroc.Activities.MainActivity.class));
                    finish();
                }
            }
        }, 3000);    // 1 sec

    }


}
