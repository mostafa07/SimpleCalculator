package com.mx3.thirdwayvsimplecalculator.data.model;

public class Message {

    private int messageResourceId;
    private Object[] params;

    // Constructor
    public Message(int messageResourceId, Object... params) {
        this.messageResourceId = messageResourceId;
        this.params = params;
    }


    // Getters and Setters

    public int getMessageResourceId() {
        return messageResourceId;
    }

    public void setMessageResourceId(int messageResourceId) {
        this.messageResourceId = messageResourceId;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
