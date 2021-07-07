package com.moovapp.riderapp.main.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moovapp.riderapp.R;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewPreviousRidesResponseModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Manuel Chris-Ogar on 2/14/2019.
 */
public class PreviousRidesAdapter extends RecyclerView.Adapter<PreviousRidesAdapter.PreviousRidesViewHolder> {

    List<ViewPreviousRidesResponseModel.DataEntity> objects;
    private ListItemClick listItemClickListener;

    public interface ListItemClick{
        void clickedTrip( ViewPreviousRidesResponseModel.DataEntity tripDetails );
    }


    public PreviousRidesAdapter(List<ViewPreviousRidesResponseModel.DataEntity> object,ListItemClick listItemClickListeners ){
        this.objects = object;
        this.listItemClickListener = listItemClickListeners;
    }
    @NonNull
    @Override
    public PreviousRidesAdapter.PreviousRidesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from((parent).getContext()).inflate(R.layout.previous_rides_viewholder, parent,false);
        return new PreviousRidesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousRidesAdapter.PreviousRidesViewHolder holder, int position) {
        Log.d("ride", "onBindViewHolder: "+objects.size());
        final ViewPreviousRidesResponseModel.DataEntity currentTrip = objects.get(position);
        holder.previousRideDestination.setText(currentTrip.getRide_to());
        Log.d("ride", "onBindViewHolder: "+currentTrip.getRide_booked_on_time());
        holder.previousRideLocation.setText(currentTrip.getRide_from());
        holder.previousRideTime.setText(currentTrip.getRide_booked_on_time());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatMonth = new SimpleDateFormat("MM");
        DateFormat formatDay=new SimpleDateFormat("dd");
        DateFormat formatYear = new SimpleDateFormat("yyyy");
        try{
            Date years = simpleDateFormat.parse(currentTrip.getRide_booked_on_date());
            String day = formatDay.format(years);
            String month = formatMonth.format(years);
            String year = formatYear.format(years);
            holder.prevRideDateNumber.setText(day);
            holder.prevRideMonth.setText(month);
            holder.prevRideYear.setText(year);

        }catch (Exception e){
            e.printStackTrace();
        }


        holder.rateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItemClickListener.clickedTrip(currentTrip);
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class PreviousRidesViewHolder extends RecyclerView.ViewHolder {
        public  TextView prevRideDateNumber;
        public TextView prevRideMonth;
        public  TextView  prevRideYear;
        public TextView  previousRideLocation;
        public TextView  previousRideDestination;
        public TextView  previousRideTime;
        private CardView rateTrip;

        public PreviousRidesViewHolder(View itemView) {
            super(itemView);
            prevRideDateNumber = itemView.findViewById(R.id.prevRideDateNumber);
            prevRideMonth = itemView.findViewById(R.id.prevRideMonth);
            prevRideYear = itemView.findViewById(R.id.prevRideYear);
            previousRideLocation = itemView.findViewById(R.id.previousRideLocation);
            previousRideDestination = itemView.findViewById(R.id.previousRideDestination);
            previousRideTime = itemView.findViewById(R.id.previousRideTime);
            rateTrip = itemView.findViewById(R.id.rateTrip);
        }
    }
}
