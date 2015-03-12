package com.sportsbarfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dan on 1/13/15.
 */



public class BarAdapter extends ArrayAdapter<Bar> {

    private Context context;
    private List<Bar> bars;

    private static class BarViewHolder {
        Bar bar;
        TextView name;
        TextView teams;
        TextView city;
        TextView address;
        Button button;
        int position;
    }

    public BarAdapter(Context context, List<Bar> bars) {
        super(context, R.layout.fragment_bar, bars);
        this.context = context;
        this.bars = bars;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_bar, null);
        }

        Bar bar = bars.get(position);
        BarViewHolder holder = new BarViewHolder();
        holder.bar = bars.get(position);
        holder.name = (TextView) convertView.findViewById(R.id.bar_result);
        holder.teams = (TextView) convertView.findViewById(R.id.teams_result);
        holder.city = (TextView) convertView.findViewById(R.id.city_result);
        holder.address = (TextView) convertView.findViewById(R.id.address_result);
        holder.button = (Button) convertView.findViewById(R.id.map_result);
        holder.button.setTag(holder.bar);
        setTextView(holder.name, bar.name);
        setTextView(holder.teams, bar.prettyPrintTeams());
        setTextView(holder.city, bar.city);
        setTextView(holder.address, bar.address);

        convertView.setTag(holder);

//        TextView barText = (TextView) convertView.findViewById(R.id.bar_result);
//        TextView teamsText = (TextView) convertView.findViewById(R.id.teams_result);
//        TextView cityText = (TextView) convertView.findViewById(R.id.city_result);
//        TextView addressText = (TextView) convertView.findViewById(R.id.address_result);
//        Button mapButton = (Button) convertView.findViewById(R.id.map_result);
//
//        setTextView(barText, bar.name);
//        setTextView(teamsText, bar.prettyPrintTeams());
//        setTextView(cityText, bar.city);
//        setTextView(addressText, bar.address);

//
//        holder.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent mapIntent = new Intent(
//                        Intent.ACTION_VIEW, geoLocation, view.getContext(), FindBarActivity.class);
//                mapIntent.setPackage("com.google.android.apps.maps");
//
//            }
//        });

        return convertView;
    }

    private void setTextView(TextView tv, String text) {
        if(text.equals("")) {
            tv.setVisibility(View.GONE);
        }
        else {
            tv.setText(text);
            tv.setVisibility(View.VISIBLE);
        }

    }

    public void setList(List<Bar> bars) {
        this.bars = bars;
        notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        return bars.size();
    }

    @Override
    public Bar getItem(int position){
        return bars.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

}
