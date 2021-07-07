package com.moovapp.riderapp.main.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.moovapp.riderapp.R;
import com.moovapp.riderapp.main.HomeActivity;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewCurrentRideResponseModel;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Manuel Chris-Ogar on 2/20/2019.
 */
public class UpcomingRidesAdapter extends RecyclerView.Adapter<UpcomingRidesAdapter.UpcomingRidesViewholder> {
    List<ViewCurrentRideResponseModel.DataEntity> objects;

    public UpcomingRidesAdapter(List<ViewCurrentRideResponseModel.DataEntity> objects) {
        this.objects = objects;
    }


    //    private ViewCurrentRideResponseModel.DataEntity rideData;
    @NonNull
    @Override
    public UpcomingRidesAdapter.UpcomingRidesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from((parent.getContext())).inflate(R.layout.upcoming_trips_viewholder, parent, false);
       return new UpcomingRidesViewholder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingRidesAdapter.UpcomingRidesViewholder holder, int position) {
//        bind all views
        ViewCurrentRideResponseModel.DataEntity currentUpcomingTrip = objects.get(position);

        holder.tvTripDestination.setText(currentUpcomingTrip.getRideTo());
        holder.tvTtipLocation.setText(currentUpcomingTrip.getRideFrom());
        holder.tvTimeofTrip.setText(currentUpcomingTrip.getFutureTime());
        holder.tvDateofTrip.setText(currentUpcomingTrip.getFutureDate());
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class UpcomingRidesViewholder extends RecyclerView.ViewHolder {

        TextView tvDateofTrip;
        TextView tvTimeofTrip;
        TextView tvTtipLocation;
        TextView tvTripDestination;
        public UpcomingRidesViewholder(View itemView) {
            super(itemView);
            tvDateofTrip = itemView.findViewById(R.id.tvDateofTrip);
            tvTimeofTrip = itemView.findViewById(R.id.tvTimeofTrip);
            tvTtipLocation = itemView.findViewById(R.id.tvTtipLocation);
            tvTripDestination = itemView.findViewById(R.id.tvTripDestination);
        }
    }
}
