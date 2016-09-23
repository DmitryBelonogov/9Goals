package com.nougust3.a9goals;

class Goal {
    private String name;
    private int type;
    private Boolean state;

    Goal(String name, int type, Boolean state) {
        this.name = name;
        this.type = type;
        this.state = state;
    }

    void changeState() {
        state = !state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    Boolean getState() {
        return state;
    }
}
