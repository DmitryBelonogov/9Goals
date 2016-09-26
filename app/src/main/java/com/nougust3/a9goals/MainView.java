package com.nougust3.a9goals;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

interface MainView extends TiView {
    //@CallOnMainThread
    void updateGoalsList();

    //@CallOnMainThread
    void hideAddButton();

    //@CallOnMainThread
    void showAddButton();

    void makeToast(String message);
}
