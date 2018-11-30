package com.moovapp.riderapp.main.paymentHistory;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moovapp.riderapp.R;
import com.moovapp.riderapp.utils.retrofit.responseModels.ViewPreviousRidesResponseModel;

import java.util.List;


/**
 * Created by Lijo Mathew Theckanal on 18-Jul-18.
 */
public class PaymentHistoryListAdapter extends ArrayAdapter<ViewPreviousRidesResponseModel.DataEntity> {

    private static class ViewHolder {
        public final LinearLayout mainLayout;
        public final LinearLayout llMain;
        public final TextView tvFrom;
        public final TextView tvTo;
        public final TextView tvDate;
        public final TextView tvTime;
        public final TextView tvSeats;
        public final TextView tvAmount;
        public final TextView tvStatus;

        private ViewHolder(LinearLayout mainLayout, LinearLayout llMain, TextView tvFrom, TextView tvTo, TextView tvDate, TextView tvTime, TextView tvSeats, TextView tvAmount, TextView tvStatus) {
            this.mainLayout = mainLayout;
            this.llMain = llMain;
            this.tvFrom = tvFrom;
            this.tvTo = tvTo;
            this.tvDate = tvDate;
            this.tvTime = tvTime;
            this.tvSeats = tvSeats;
            this.tvAmount = tvAmount;
            this.tvStatus = tvStatus;
        }

        public static ViewHolder create(LinearLayout mainLayout) {
            LinearLayout llMain = (LinearLayout) mainLayout.findViewById(R.id.llMain);
            TextView tvFrom = (TextView) mainLayout.findViewById(R.id.tvFrom);
            TextView tvTo = (TextView) mainLayout.findViewById(R.id.tvTo);
            TextView tvDate = (TextView) mainLayout.findViewById(R.id.tvDate);
            TextView tvTime = (TextView) mainLayout.findViewById(R.id.tvTime);
            TextView tvSeats = (TextView) mainLayout.findViewById(R.id.tvSeats);
            TextView tvAmount = (TextView) mainLayout.findViewById(R.id.tvAmount);
            TextView tvStatus = (TextView) mainLayout.findViewById(R.id.tvStatus);
            return new ViewHolder(mainLayout,llMain, tvFrom, tvTo, tvDate, tvTime, tvSeats, tvAmount, tvStatus);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.payment_history_list_item, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final ViewPreviousRidesResponseModel.DataEntity item = getItem(position);

        vh.tvFrom.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));
        vh.tvTo.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));
        vh.tvDate.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));
        vh.tvTime.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));
        vh.tvSeats.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));
        vh.tvAmount.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));
        vh.tvStatus.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));

        try {
            vh.tvFrom.setText(item.getRide_from().split(",")[0]);
        } catch (Exception e) {
            e.printStackTrace();
            vh.tvFrom.setText(item.getRide_from());
        }
        try {
            vh.tvTo.setText(item.getRide_to().split(",")[0]);
        } catch (Exception e) {
            e.printStackTrace();
            vh.tvTo.setText(item.getRide_to());
        }
        vh.tvDate.setText(item.getRide_booked_on_date());
        vh.tvTime.setText(item.getRide_booked_on_time());
        vh.tvSeats.setText(item.getRide_seats()+" Seats");
        vh.tvAmount.setText("$ "+ item.getRide_amount());
        vh.tvStatus.setText(item.getRide_payment_status().toUpperCase());

        if(item.getRide_payment_status().equalsIgnoreCase("paid")){
            vh.llMain.setBackgroundResource(R.mipmap.paid_bg);
            vh.tvFrom.setTextColor(context.getResources().getColor(R.color.black));
            vh.tvTo.setTextColor(context.getResources().getColor(R.color.black));
            vh.tvDate.setTextColor(context.getResources().getColor(R.color.black));
            vh.tvTime.setTextColor(context.getResources().getColor(R.color.black));
            vh.tvSeats.setTextColor(context.getResources().getColor(R.color.black));
            vh.tvAmount.setTextColor(context.getResources().getColor(R.color.black));
            vh.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else{
            vh.llMain.setBackgroundResource(R.mipmap.unpaid_bg);
            vh.tvFrom.setTextColor(context.getResources().getColor(R.color.white));
            vh.tvTo.setTextColor(context.getResources().getColor(R.color.white));
            vh.tvDate.setTextColor(context.getResources().getColor(R.color.white));
            vh.tvTime.setTextColor(context.getResources().getColor(R.color.white));
            vh.tvSeats.setTextColor(context.getResources().getColor(R.color.white));
            vh.tvAmount.setTextColor(context.getResources().getColor(R.color.white));
            vh.tvStatus.setTextColor(context.getResources().getColor(R.color.yellow));
        }

        return vh.mainLayout;
    }

    private LayoutInflater mInflater;
    private Context context;

    // Constructors
    public PaymentHistoryListAdapter(Context context, List<ViewPreviousRidesResponseModel.DataEntity> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public PaymentHistoryListAdapter(Context context, ViewPreviousRidesResponseModel.DataEntity[] objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }
}