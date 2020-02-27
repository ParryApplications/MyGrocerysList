package com.nested.paras.mygroc.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nested.paras.mygroc.Data.DataBase;
import com.nested.paras.mygroc.Model.Grocery;
import com.nested.paras.mygroc.R;
import com.nested.paras.mygroc.UI.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public SearchView search_id;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Grocery> groceries;
    private List<Grocery> groceryList;
    private DataBase db;
    private AlertDialog alertDialog;
    private AlertDialog.Builder dialogbuilder;
    private EditText item_id,qty_id;
    private Button save_id;
    private TextView heading;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               createpopup();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

            }
        });

        db = new DataBase(this);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerviedID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceries = new ArrayList<>();
        groceryList = new ArrayList<>();

        groceries = db.getallgrocery();

        for (Grocery c : groceries)
        {
            Grocery  grocery = new Grocery();
            grocery.setId(c.getId());
            grocery.setGrocery_item(c.getGrocery_item());
            grocery.setGetGrocery_qty("Quantity: " + c.getGrocery_qty);
            grocery.setDate("Added on: " + c.getDate());

            groceryList.add(grocery);
        }

        recyclerViewAdapter = new RecyclerViewAdapter(this,groceryList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
               return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.feedback_id) {
            dialogbuilder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().from(this).inflate(R.layout.feedback,null);
            dialogbuilder.setView(view);
            alertDialog=dialogbuilder.create();
            alertDialog.show();
            return true;
        }
        else if(id==R.id.exit_id)
        {
            dialogbuilder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.confirmation, null);
            Button no = (Button)view.findViewById(R.id.no);
            Button yes = (Button)view.findViewById(R.id.yes);
            yes.setBackgroundResource(R.drawable.exit);
            dialogbuilder.setView(view);
            alertDialog = dialogbuilder.create();
            alertDialog.show();



            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    finish();
                }
            });
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    private void createpopup () {

        dialogbuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);

        heading = (TextView)view.findViewById(R.id.heading);
        heading.setText(" Add More Groceries! ");
        item_id = (EditText)view.findViewById(R.id.item_id);
        qty_id = (EditText)view.findViewById(R.id.qty_id);


        dialogbuilder.setView(view);
        alertDialog = dialogbuilder.create();
        alertDialog.show();

        save_id = (Button) view.findViewById(R.id.save_id);

        save_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!item_id.getText().toString().isEmpty() && !qty_id.getText().toString().isEmpty()) {
                    savingtoDB(v);
                } else {
                    Snackbar.make(v,"Invalid!",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }








    private void savingtoDB (View v)
    {
        Grocery grocery = new Grocery();

        String item = item_id.getText().toString();
        String qty = qty_id.getText().toString();

        grocery.setGrocery_item(item);
        grocery.setGetGrocery_qty(qty);

        db.addgrocery(grocery);
        Snackbar.make(v, "Item Saved!", Snackbar.LENGTH_SHORT).show();

        //        Log.d("Item Added ID:",String.valueOf(db.getgrocerycount()));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                alertDialog.dismiss();
                startActivity(new Intent(ListActivity.this, ListActivity.class));
                finish();
            }
        }, 1000);    // 1 sec

    }

}
