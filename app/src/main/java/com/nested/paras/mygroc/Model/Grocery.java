package com.nested.paras.mygroc.Model;

public class Grocery
{
    public int id;
    public String grocery_item;
    public String getGrocery_qty;
    public String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrocery_item() {
        return grocery_item;
    }

    public void setGrocery_item(String grocery_item) {
        this.grocery_item = grocery_item;
    }

    public String getGetGrocery_qty() {
        return getGrocery_qty;
    }

    public void setGetGrocery_qty(String getGrocery_qty) {
        this.getGrocery_qty = getGrocery_qty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public Grocery()
    {

    }
    public Grocery(int id, String grocery_item, String getGrocery_qty, String date) {
        this.id = id;
        this.grocery_item = grocery_item;
        this.getGrocery_qty = getGrocery_qty;
        this.date = date;
    }
}
