package com.moovapp.riderapp.main.upcomingRides;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.ornolfr.ratingview.RatingView;
import com.moovapp.riderapp.R;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewCurrentRideResponseModel;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewPreviousRidesResponseModel;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Lijo Mathew Theckanal on 18-Jul-18.
 */
public class UpcominRidesListAdapter extends ArrayAdapter<ViewCurrentRideResponseModel.DataEntity> {

    private static class ViewHolder {
        public final LinearLayout mainLayout;
        public final LinearLayout llMain;
        public final TextView tvRiderName;
        public final ImageView imgRiderImage;
        public final TextView tvCarModel;
        public final RatingView rating1;
        public final TextView tvRiderPhone;
        public final TextView tvDistance;
        public final TextView tvCarNumber;
        public final TextView tvEta;
        public final TextView tvCancelRide;
        public final TextView tvNoTrips;

        private ViewHolder(LinearLayout mainLayout, LinearLayout llMain, TextView tvRiderName, ImageView imgRiderImage, TextView tvCarModel, RatingView rating1,
                           TextView tvRiderPhone, TextView tvDistance, TextView tvCarNumber, TextView tvEta, TextView tvCancelRide, TextView tvNoTrips) {
            this.mainLayout = mainLayout;
            this.llMain = llMain;
            this.tvRiderName = tvRiderName;
            this.imgRiderImage = imgRiderImage;
            this.tvCarModel = tvCarModel;
            this.rating1 = rating1;
            this.tvRiderPhone = tvRiderPhone;
            this.tvDistance = tvDistance;
            this.tvCarNumber = tvCarNumber;
            this.tvEta = tvEta;
            this.tvCancelRide = tvCancelRide;
            this.tvNoTrips = tvNoTrips;
        }

        public static ViewHolder create(LinearLayout mainLayout) {
            LinearLayout llMain = (LinearLayout) mainLayout.findViewById(R.id.llMain);
            TextView tvRiderName = (TextView) mainLayout.findViewById(R.id.tvRiderName);
            ImageView imgRiderImage = (ImageView) mainLayout.findViewById(R.id.imgRiderImage);
            TextView tvCarModel = (TextView) mainLayout.findViewById(R.id.tvCarModel);
            RatingView rating1 = (RatingView) mainLayout.findViewById(R.id.rating1);
            TextView tvRiderPhone = (TextView) mainLayout.findViewById(R.id.tvRiderPhone);
            TextView tvDistance = (TextView) mainLayout.findViewById(R.id.tvDistance);
            TextView tvCarNumber = (TextView) mainLayout.findViewById(R.id.tvCarNumber);
            TextView tvEta = (TextView) mainLayout.findViewById(R.id.tvEta);
            TextView tvCancelRide = (TextView) mainLayout.findViewById(R.id.tvCancelRide);
            TextView tvNoTrips = (TextView) mainLayout.findViewById(R.id.tvNoTrips);
            return new ViewHolder(mainLayout, llMain, tvRiderName, imgRiderImage, tvCarModel, rating1, tvRiderPhone, tvDistance, tvCarNumber, tvEta, tvCancelRide, tvNoTrips);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.current_rider, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final ViewCurrentRideResponseModel.DataEntity item = getItem(position);

        vh.tvRiderName.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));
        vh.tvCarModel.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));
        vh.tvRiderPhone.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));
        vh.tvDistance.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));
        vh.tvCarNumber.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));
        vh.tvEta.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));
        vh.tvCancelRide.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));
        vh.tvNoTrips.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));

        vh.tvCarModel.setText(item.getDriverDetails().getCarModel());
        vh.rating1.setRating(Integer.parseInt(item.getDriverDetails().getRatings()+""));
        vh.tvRiderPhone.setText(item.getDriverDetails().getPhone());
//        tvNoTrips.setText("No of trips: " + data.getDriver_details().getTotal_rides());
//        tvDistance.setText(data.getDistance_to_drive_details().getDistance());
        vh.tvNoTrips.setVisibility(View.GONE);
        vh.tvDistance.setText("15 Km");
        vh.tvCarNumber.setText(item.getDriverDetails().getVehicleNo());
//        tvEta.setText(data.getDistance_to_drive_details().getTime());
        vh.tvEta.setText("15 Minutes");
        try {
            if (item.getDriverDetails().getImage().length() > 3) {
                Picasso.get().load(item.getDriverDetails().getImage()).placeholder(R.mipmap.user_placeholder).error(R.mipmap.user_placeholder).into(vh.imgRiderImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (item.getRideStatus().equals("booked")) {
            vh.tvCancelRide.setVisibility(View.VISIBLE);
        } else {
            vh.tvCancelRide.setVisibility(View.GONE);
        }

        vh.tvCancelRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelRideInterface.onCancelRide(item.getRideId()+"");
            }
        });

        return vh.mainLayout;
    }

    private LayoutInflater mInflater;
    private Context context;
    private CancelRideInterface cancelRideInterface;

    // Constructors
    public UpcominRidesListAdapter(Context context, List<ViewCurrentRideResponseModel.DataEntity> objects, CancelRideInterface cancelRideInterface) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.cancelRideInterface = cancelRideInterface;
    }

    public UpcominRidesListAdapter(Context context, ViewCurrentRideResponseModel.DataEntity[] objects, CancelRideInterface cancelRideInterface) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.cancelRideInterface = cancelRideInterface;
    }
}