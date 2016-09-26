package com.nougust3.a9goals;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class GoalsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<Goal> goals;
    private Context context;

    GoalsAdapter(Context context, ArrayList<Goal> goals) {
        this.context = context;
        this.goals = goals;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return goals.size();
    }

    @Override
    public Goal getItem(int position) {
        return goals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            view = inflater.inflate(R.layout.goal_item, parent, false);
        }

        TextView textView = (TextView) view.findViewById(R.id.textView);

        Goal goal = goals.get(position);

        textView.setText(goal.getName());

        if(goal.getType() == 1) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.MainGoal));
            textView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        }

        if(goal.getType() == 2) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.SecongaryGoal));
            textView.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        }

        if(goal.getType() == 3) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.OverGoal));
            textView.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        }

        if(goal.getState()) {
            (view.findViewById(R.id.item_layout)).setAlpha(.3f);
        }
        else {
            (view.findViewById(R.id.item_layout)).setAlpha(1.f);
        }

        return view;
    }
}
