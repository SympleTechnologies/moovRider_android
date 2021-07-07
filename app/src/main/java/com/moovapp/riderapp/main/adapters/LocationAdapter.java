package com.moovapp.riderapp.main.adapters;

/**
 * Created by Manuel Chris-Ogar on 1/23/2019.
 */
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.moovapp.riderapp.R;

import java.util.HashMap;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private List<HashMap<String, String>> result;
    private ListItemClickListener listItemClickListener;

    public interface ListItemClickListener{
        void OnLocationClicked( HashMap<String, String> selectedLocation);
//        voud onTouchItem()
    }

    public LocationAdapter(List<HashMap<String, String>> result, ListItemClickListener listItemClickListener ) {
        this.result = result;
        this.listItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_results_viewholder,parent,false);
        return  new LocationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        final HashMap<String, String> location = result.get(position);
        holder.loadResults.setText(location.get("description"));
        holder.loadResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItemClickListener.OnLocationClicked(location);
            }
        });

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        public CheckedTextView loadResults;
        public LocationViewHolder(View itemView) {
            super(itemView);
            loadResults = itemView.findViewById(R.id.loadResults);
        }
    }
}

