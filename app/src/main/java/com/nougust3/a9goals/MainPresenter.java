package com.nougust3.a9goals;

import android.content.Context;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.util.ArrayList;

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

        if(goalsModel.getGoalsCount() > 8) {
            getView().hideAddButton();
        }
    }

    void selectGoal(int id) {
        selectedId = id;
    }

    void removeGoal() {
        goalsModel.remove(selectedId);
        getView().updateGoalsList();

        if(goalsModel.getGoalsCount() <= 9) {
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