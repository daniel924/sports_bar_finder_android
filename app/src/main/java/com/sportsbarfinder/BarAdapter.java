package com.sportsbarfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dan on 1/13/15.
 */
public class BarAdapter extends ArrayAdapter<Bar> {

    private Context context;
    private List<Bar> bars;

    public BarAdapter(Context context, List<Bar> bars) {
        super(context, R.layout.fragment_bar, bars);
        this.context = context;
        this.bars = bars;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_bar, null);
        }

        Bar bar = bars.get(position);

        TextView barText = (TextView) convertView.findViewById(R.id.bar_result);
        TextView teamsText = (TextView) convertView.findViewById(R.id.teams_result);
        TextView cityText = (TextView) convertView.findViewById(R.id.city_result);
        TextView addressText = (TextView) convertView.findViewById(R.id.address_result);

        setTextView(barText, bar.name);
        setTextView(teamsText, bar.prettyPrintTeams());
        setTextView(cityText, bar.city);
        setTextView(addressText, bar.address);

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
