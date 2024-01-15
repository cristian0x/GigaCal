package com.gigacal.mock;

import java.io.IOException;

public class SmsNotificationServiceMock {

    public static void main(String[] args) {

        final String key = ""; // API key
        final String password = ""; // web service password
        final String sender = "GigaCal";

        final String message = "To jest test. Pozdrawiam, DevOps.";
        final String[] receivers = new String[] {}; // phone numbers

        SmsPlanetRequest request = SmsPlanetRequest.sendSMS(key, password, sender, message, receivers);
        try {
            SmsPlanetResponse response = request.execute();
            if (response.getErrorCode() == 0) {
                System.out.println("Wiadomosc zostala wyslana. Nadany identyfikator: " + response.getMessageId());
            } else {
                System.err.println("Blad przetwarzania: " + response.getErrorMessage());
            }
        } catch (IOException e) {
            System.err.println("Nie udalo sie nawiazac polaczenia.");
        }

    }

}
