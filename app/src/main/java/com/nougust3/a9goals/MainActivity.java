package com.nougust3.a9goals;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import net.grandcentrix.thirtyinch.TiActivity;

public class MainActivity
        extends TiActivity<MainPresenter, MainView>
        implements MainView {

    private FABToolbarLayout layout;
    private GoalsAdapter adapter;
    private View fab;

    private MainPresenter presenter;

    static final private int RESULT = 0;

    @NonNull
    @Override
    public MainPresenter providePresenter() {
        presenter = new MainPresenter();
        presenter.setContext(getApplicationContext());
        return presenter;
    }

    @Override
    public void updateGoalsList() {
        if(adapter == null){
            Log.i("G", "adapter is null");
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideAddButton() {
        fab.setVisibility(View.GONE);
    }

    @Override
    public void showAddButton() {
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        View one = findViewById(R.id.one);
        View two = findViewById(R.id.two);
        View three = findViewById(R.id.three);
        fab = findViewById(R.id.fabtoolbar_fab);
        ListView list = (ListView) findViewById(R.id.list);
        layout = (FABToolbarLayout) findViewById(R.id.fabtoolbar);

        adapter = new GoalsAdapter(this, getPresenter().getGoals());

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getPresenter().selectGoal(i);
                getPresenter().changeGoalState();
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                getPresenter().selectGoal(i);
                layout.show();
                return true;
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().removeGoal();
                updateGoalsList();
                layout.hide();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().changeGoalState();
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });
    }

    @Override
    public void onBackPressed() {
        layout.hide();
    }

    private void createDialog() {
        Intent intent = new Intent(this, GoalActivity.class);
        startActivityForResult(intent, RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT) {
            if (resultCode == RESULT_OK) {
                addGoal(data.getStringExtra(GoalActivity.GOAL),
                        data.getIntExtra(GoalActivity.TYPE, 1));
            }
        }
    }

    void addGoal(String goalName, int goalType) {
        presenter.addGoal(goalName, goalType);
    }
}
