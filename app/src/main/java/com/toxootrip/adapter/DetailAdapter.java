package com.toxootrip.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toxootrip.R;
import com.toxootrip.activity.CommonBaseActivity;
import com.toxootrip.model.AcModel;
import com.toxootrip.model.AnotherModel;
import com.toxootrip.model.BusinessModel;
import com.toxootrip.model.GenralModel;
import com.toxootrip.model.HourModel;
import com.toxootrip.model.PaymentModel;
import com.toxootrip.model.RoomModel;

/**
 * Created by himanshu on 28-01-2018.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder>  {
    private Context context;
    private AnotherModel anotherModelList = new AnotherModel();
    public static final String IMAGE_URL_BASE_PATH = "http://toxotrip.com.cp-in-2.webhostbox.net/api/";
    private String hotel_id;
    private String phone,phonee;




    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView mTextView;

        private TextView textView;

        private TextView address;
        private TextView recyclerView;
        private TextView frecyclerView;
        private TextView grecyclerView;
        private TextView hrecyclerView;
        private TextView irecyclerView;
        private TextView hour;
        private TextView te;
        private TextView tele;
        private TextView telee;

        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextView = (TextView) v.findViewById(R.id.tv_text);
            textView = (TextView)v.findViewById(R.id.tv_text1);

            address = (TextView)v.findViewById(R.id.tv_text2);
            recyclerView = (TextView)v.findViewById(R.id.card_recycler_view);
            frecyclerView = (TextView)v.findViewById(R.id.card_recycler_view1);
            grecyclerView = (TextView) v.findViewById(R.id.card_recycler_view2);
            hrecyclerView = (TextView)v.findViewById(R.id.card_recycler_view3);
            irecyclerView =(TextView)v.findViewById(R.id.card_recycler_view4);
            hour = (TextView)v.findViewById(R.id.card_recycler_view5);
            te = (TextView)v.findViewById(R.id.tv_t);
            tele = (TextView)v.findViewById(R.id.tv);
            telee = (TextView)v.findViewById(R.id.t);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DetailAdapter(Context context,AnotherModel anotherModelList) {
        this.context = context;
        this.anotherModelList = anotherModelList;


    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_details, parent, false);
        hotel_id = ((Activity) context).getIntent().getExtras().getString("hotel_id");


        // set the view's size, margins, paddings and layout parameters
       /* ClusterAdapter.MyViewHolder vh = new ClusterAdapter.MyViewHolder(v);*/
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DetailAdapter.MyViewHolder holder, final int position) {

        //final AnotherModel constant = anotherModelList.get(position);
  /*String image_url = IMAGE_URL_BASE_PATH + anotherModelList.getImage();


        Picasso.with(context)
                .load(image_url)
                .into(holder.mImageView);
*/        holder.mTextView.setText(anotherModelList.getHotleDetail().get(position).getHotel_name());
        holder.textView.setText(anotherModelList.getHotleDetail().get(position).getHotel_phone());
        holder.te.setText(anotherModelList.getHotleDetail().get(position).getHotel_phone());
        holder.address.setText(anotherModelList.getHotleDetail().get(position).getHotel_address());
        phone = anotherModelList.getHotleDetail().get(position).getHotel_phone();
        String myContactNo[] = phone.split(",");
        holder.textView.setText(myContactNo[0]+",");
            holder.te.setText(myContactNo[1]+",");

        final String phone=myContactNo[0];
        holder.textView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                      Intent callIntent = new Intent(Intent.ACTION_DIAL);
                      callIntent.setData(Uri.parse("tel:" + phone));

                  context.startActivity(callIntent);


              }
          });

        final String phonee=myContactNo[1];
        holder.te.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phonee));

                context.startActivity(callIntent);


            }
        });

       holder.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commonActivity = new Intent(context, CommonBaseActivity.class);
                commonActivity.putExtra("flowType", CommonBaseActivity.ALLMAP);

                commonActivity.putExtra("hotel_id", hotel_id);



                context.startActivity(commonActivity);
            }
        });




        StringBuilder str = new StringBuilder();

        for(AcModel model:anotherModelList.getAcBanquet())
        {
            str.append(model.getName()+"\n");
        }

        holder.recyclerView.setText(str);

        StringBuilder strn = new StringBuilder();

        for(BusinessModel modell:anotherModelList.getBusiness())
        {
            strn.append(modell.getName()+"\n");
        }

        holder.frecyclerView.setText(strn);

        StringBuilder strnn = new StringBuilder();

        for(RoomModel modelll:anotherModelList.getRoom())
        {
            strnn.append(modelll.getName()+"\n");
        }

        holder.grecyclerView.setText(strnn);



        StringBuilder strnnn = new StringBuilder();

        for(GenralModel modellll:anotherModelList.getGeneral())
        {
            strnnn.append(modellll.getName()+"\n");
        }

        holder.hrecyclerView.setText(strnnn);

        StringBuilder strng = new StringBuilder();

        for(PaymentModel modellg:anotherModelList.getPayment())
        {
            strng.append(modellg.getName()+"\n");
        }

        holder.irecyclerView.setText(strng);




        StringBuilder strngg = new StringBuilder();

        for(HourModel modellgg:anotherModelList.getHour())
        {
            strngg.append(modellgg.getName()+"\n");
        }

        holder.hour.setText(strngg);

    }
    @Override
    public int getItemCount() {
        return 1;///anotherModelList.size()
    }



}



