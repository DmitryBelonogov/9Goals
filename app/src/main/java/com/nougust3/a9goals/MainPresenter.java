package com.nougust3.a9goals;

import android.content.Context;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.util.ArrayList;
import java.util.Calendar;

class MainPresenter extends TiPresenter<MainView> {
    private Context context;
    private Model goalsModel;
    private int selectedId = 0;

    void setContext(Context context) {
        this.context = context;
    }

    ArrayList<Goal> getGoals() {
        return goalsModel.getGoals();
    }

    void addGoal(String goal, int type) {
        goalsModel.appendGoal(goal, type);
        goalsModel.loadGoals();
        getView().updateGoalsList();

        if(goalsModel.countGoals() > 8) {
            getView().hideAddButton();
        }
    }

    int countByType(int type) {
        return goalsModel.countByType(type);
    }

    Boolean checkGoalsDate() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day == goalsModel.getDate();
    }

    void selectGoal(int id) {
        selectedId = id;
    }

    void removeGoal() {
        goalsModel.remove(selectedId);
        getView().updateGoalsList();

        if(goalsModel.countGoals() <= 9) {
            getView().showAddButton();
        }
    }

    void changeGoalState() {
        getGoals().get(selectedId).changeState();
        goalsModel.saveGoals();
        getView().updateGoalsList();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        goalsModel = new Model(context);
        goalsModel.loadGoals();
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        goalsModel.saveGoals();
    }
}