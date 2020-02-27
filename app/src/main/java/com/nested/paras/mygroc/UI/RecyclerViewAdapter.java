package com.nested.paras.mygroc.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.nested.paras.mygroc.Activities.DetailsActivity;
import com.nested.paras.mygroc.Data.DataBase;
import com.nested.paras.mygroc.Model.Grocery;
import com.nested.paras.mygroc.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{

    public TextView item,qty,date,heading;
    public EditText item_id,qty_id;
    public Button edit,dlt,no,yes,save_id;

    public AlertDialog alertDialog;
    public AlertDialog.Builder dialogbuilder;

    public Context context;
    public List<Grocery> groceryList;


    public RecyclerViewAdapter(Context context, List<Grocery> groceryList) {
        this.context = context;
        this.groceryList = groceryList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position)
    {
        Grocery grocery = groceryList.get(position);
        holder.name.setText(grocery.getGrocery_item());
        holder.qty.setText(grocery.getGrocery_qty);
        holder.date.setText(grocery.getDate());
    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{




        public TextView name,qty,date;
        public Button edit,dlt;

        public ViewHolder(@NonNull View view, Context ctx) {
            super(view);
            context = ctx;

            name = (TextView)view.findViewById(R.id.grocery_item_id);
            qty = (TextView)view.findViewById(R.id.grocery_qty_id);
            date= (TextView)view.findViewById(R.id.date_id);

            edit = (Button)view.findViewById(R.id.edit_id);
            dlt = (Button)view.findViewById(R.id.dlt_id);

            edit.setOnClickListener(this);
            dlt.setOnClickListener(this);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    call();
                }
            });


        }
        public void call()
        {
            int position = getAdapterPosition();
            Grocery grocery = groceryList.get(position);
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("name",grocery.getGrocery_item());
            intent.putExtra("quantity",grocery.getGetGrocery_qty());
            intent.putExtra("date",grocery.getDate());
            intent.putExtra("id",grocery.getId());
            context.startActivity(intent);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.edit_id:
                    {
                        int position = getAdapterPosition();
                        Grocery grocery=groceryList.get(position);
                        edit(grocery);

                        break;
                    }
                case R.id.dlt_id:
                    {
                        int position = getAdapterPosition();
                        //imp to pass the postion
                        Grocery grocery = groceryList.get(position);

                        delete(grocery.getId());

                        break;
                    }
            }

        }
        public void delete(final int id)
        {
            dialogbuilder=new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.confirmation,null);
            no = (Button)view.findViewById(R.id.no);
            yes = (Button)view.findViewById(R.id.yes);

            yes.setBackgroundResource(R.drawable.delete);

            dialogbuilder.setView(view);
            alertDialog=dialogbuilder.create();
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
                    DataBase dataBase= new DataBase(context);
                    dataBase.deletegrocery(id);

                    groceryList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    alertDialog.dismiss();
                }
            });

        }
        public void edit(final Grocery grocery)
        {
            dialogbuilder = new AlertDialog.Builder(context);
            final View view = LayoutInflater.from(context).inflate(R.layout.popup,null);

            item_id=(EditText) view.findViewById(R.id.item_id);
            qty_id=(EditText) view.findViewById(R.id.qty_id);
            save_id=(Button) view.findViewById(R.id.save_id);
            heading=(TextView)view.findViewById(R.id.heading);
            heading.setText("Edit Grocery!");
            dialogbuilder.setView(view);
            alertDialog=dialogbuilder.create();
            alertDialog.show();



            save_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataBase db = new DataBase(context);
                    grocery.setGrocery_item(item_id.getText().toString());
                    grocery.setGetGrocery_qty(qty_id.getText().toString());

                    if(!item_id.getText().toString().isEmpty() && !qty_id.getText().toString().isEmpty())
                    {
                        db.updategrocery(grocery);
                        notifyItemChanged(getAdapterPosition(),grocery);
                        Toast.makeText(context," Changed Successfully ",Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                    else
                        {
                            Snackbar.make(view,"Add Grocery And Quantity Both !",Snackbar.LENGTH_SHORT).show();
                        }
                }
            });
        }

    }

}
