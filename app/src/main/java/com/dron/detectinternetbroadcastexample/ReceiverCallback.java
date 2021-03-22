package com.dron.detectinternetbroadcastexample;

public interface ReceiverCallback {
    /**
     * Method to change the text and toast and change background
     * Callback Method
     * @param internetStatus = (true/false)
     */
    void changeUI(boolean internetStatus);

}
