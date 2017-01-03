package com.warsztat.springhibernate.model;

public enum ActionType {
    NAPRAWA("NAPRAWA"),
    WYMIANA("WYMIANA"),
    SPRZEDAZ("SPRZEDAZ");
     
    String actionType;
     
    private ActionType(String actionType){
        this.actionType = actionType;
    }
     
    public String getActionType(){
        return actionType;
    }
     
}