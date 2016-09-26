package com.nougust3.a9goals;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import net.grandcentrix.thirtyinch.TiActivity;

public class MainActivity
        extends TiActivity<MainPresenter, MainView>
        implements MainView {

    static final private int RESULT = 0;

    private View fab;
    private FABToolbarLayout layout;
    private GoalsAdapter adapter;
    private MainPresenter presenter;

    @NonNull
    @Override
    public MainPresenter providePresenter() {
        presenter = new MainPresenter();
        presenter.setContext(getApplicationContext());
        return presenter;
    }

    @Override
    public void updateGoalsList() {
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

        intent.putExtra("MAIN_COUNT", presenter.countByType(1));
        intent.putExtra("SECONDRY_COUNT", presenter.countByType(2));
        intent.putExtra("OVER_COUNT", presenter.countByType(3));

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

    @Override
    protected void onResume() {
        super.onResume();
        updateGoalsList();
    }
}
