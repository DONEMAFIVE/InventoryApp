package ru.anosov.inventoryapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;


public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListHolder> implements Filterable {
    private Context mContext;
    private ArrayList<ItemListView> mItemListViews;
    private ArrayList<ItemListView> mItemListViewsFull;
    private onItemClickListener mListener;

    @Override
    public void onBindViewHolder(@NonNull ItemListHolder itemListHolder, int i) {
        ItemListView currentItem = mItemListViews.get(i);

        String mol = currentItem.getmMol();
        itemListHolder.mTextMol.setText(mol);
    }
    @Override
    public int getItemCount() {
        return mItemListViews.size();
    }

    public void filterList(ArrayList<ItemListView> filteredList){
        mItemListViews = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ItemListView> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() ==0){
                filteredList.addAll(mItemListViewsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ItemListView item : mItemListViewsFull) {
                    if(item.getmMol().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mItemListViews.clear();
            mItemListViews.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    public interface onItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public ItemListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_list_view, viewGroup, false);
        return new ItemListHolder(v);
    }
    public ItemListAdapter(Context context, ArrayList<ItemListView> aList){
        mContext = context;
        this.mItemListViews = aList;
        mItemListViewsFull = new ArrayList<>(aList);
    }
    public class ItemListHolder extends RecyclerView.ViewHolder {

        public TextView mTextMol;
        public ItemListHolder(@NonNull View itemView) {
            super(itemView);
            mTextMol = itemView.findViewById(R.id.tv_mol);

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