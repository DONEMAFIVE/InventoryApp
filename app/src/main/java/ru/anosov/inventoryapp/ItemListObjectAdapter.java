package ru.anosov.inventoryapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class ItemListObjectAdapter extends RecyclerView.Adapter<ItemListObjectAdapter.ItemListHolder> {
    private Context mContext;
    private ArrayList<ItemListViewObject> mItemListViews;
    private onItemClickListener mListener;

    private String translate(String str){
        switch (str){
            case "Moved":
                return "Перемещно";
            case "EmptyNumber":
                return "Номер пуст";
            case "EmptyStatus": //Поправить в на сервере
                return "Не найдено";
            case "None":
                return "Не найдено";
            case "Ok":
                return "Всё найдено";
        }
        return str;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListHolder itemListHolder, int i) {
        ItemListViewObject currentItem = mItemListViews.get(i);

        String name = currentItem.getmName();
        String number = currentItem.getmNumber();
        String status = currentItem.getmStatus();

        itemListHolder.mTextName.setText(name);
        itemListHolder.mTextNumber.setText(translate(number));
        itemListHolder.mTextStatus.setText(translate(status));
    }
    @Override
    public int getItemCount() {
        return mItemListViews.size();
    }
    public interface onItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public ItemListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_list_view_obj, viewGroup, false);
        return new ItemListHolder(v);
    }
    public ItemListObjectAdapter(Context context, ArrayList<ItemListViewObject> aList){
        mContext = context;
        mItemListViews = aList;
    }
    public class ItemListHolder extends RecyclerView.ViewHolder {
        public TextView mTextName;
        public TextView mTextNumber;
        public TextView mTextStatus;

        public ItemListHolder(@NonNull View itemView) {
            super(itemView);
            mTextName = itemView.findViewById(R.id.tv_obj);
            mTextNumber = itemView.findViewById(R.id.tv_inum);
            mTextStatus = itemView.findViewById(R.id.tv_stat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}