package com.buzzmove.placesapiexample.search_screen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buzzmove.placesapiexample.R;
import com.buzzmove.placesapiexample.data.entities.Result;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.MyViewHolder> {

    private Context context;

    private SearchViewFragment mFragment;

    private SearchContract.Presenter mPresenter;

    private List<Result> places;

    public static final int DEFAULT_CLOSE_ZOOM = 15;

    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout container;
        TextView name;
        TextView address;
        ImageView icon;
        LinearLayout types_container;

        MyViewHolder(View view) {
            super(view);
            container = view.findViewById(R.id.container);
            name = view.findViewById(R.id.name);
            address = view.findViewById(R.id.address);
            icon = view.findViewById(R.id.icon);
            types_container = view.findViewById(R.id.types_container);
        }
    }

    PlacesAdapter(Context context, SearchViewFragment fragment) {
        this.context = context;
        this.mFragment = fragment;
        places = new ArrayList<>();
    }

    private int selectedPos = RecyclerView.NO_POSITION;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public void setPresenter(@NonNull SearchContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.itemView.setSelected(selectedPos == position);

        final Result location = places.get(position);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notifyItemChanged(selectedPos);
                selectedPos = holder.getAdapterPosition();
                notifyItemChanged(selectedPos);

                LatLng coordinates = new LatLng(location.getGeometry().getLocation().getLat(),
                        location.getGeometry().getLocation().getLng());
                //mFragment.getMap().addMarker(new MarkerOptions().position(london));
                int padding = 10; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(coordinates, DEFAULT_CLOSE_ZOOM);
                mFragment.getMap().animateCamera(cu);
                //mFragment.getMap().moveCamera(CameraUpdateFactory.newLatLng(london));

            }
        });

        if(selectedPos == position){
            holder.container.setBackgroundColor(ContextCompat.getColor(context, R.color.recyclerview_item_selected));
        } else {
            holder.container.setBackgroundColor(ContextCompat.getColor(context, R.color.recyclerview_item_unselected));
        }


        holder.name.setText(location.getName());
        holder.address.setText(location.getFormatted_address());

        holder.types_container.removeAllViews();


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (String type : location.getTypes()) {
            LinearLayout type_container = (LinearLayout) inflater.inflate(R.layout.place_type_item, null);
            TextView type_textview = type_container.findViewById((R.id.location_type_text));
            type_textview.setText(type.replace("_", " "));
            holder.types_container.addView(type_container);
        }

        Picasso.with(context)
                .load(location.getIcon())
                .into(holder.icon);

    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void setData(List<Result> results) {
        places.clear();
        places.addAll(results);
        notifyDataSetChanged();
    }

    public Result getItem(int index){
        return places.get(index);
    }

    public void clear(){
        places.clear();
    }

}
