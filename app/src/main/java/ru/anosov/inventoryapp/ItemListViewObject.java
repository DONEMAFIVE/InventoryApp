package ru.anosov.inventoryapp;

public class ItemListViewObject {
    private String mName;
    private String mNumber;
    private String mStatus;



    public ItemListViewObject(String mName, String mNumber, String mStatus ) {
        this.mName = mName;
        this.mNumber = mNumber;
        this.mStatus = mStatus;
    }

    public String getmName() {
        return mName;
    }

    public String getmNumber() {
        return mNumber;
    }

    public String getmStatus() {
        return mStatus;
    }
}
