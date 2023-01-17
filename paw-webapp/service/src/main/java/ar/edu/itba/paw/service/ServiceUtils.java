package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Reservation;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceUtils {

    public static String generateReservationSecurityCode(Reservation reservation) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        String toSeed = reservation.getStartedAtTime().toString() + reservation.getCustomer().getCustomerName() + reservation.getId();
        byte[] seed = toSeed.getBytes(StandardCharsets.UTF_8);
        SecureRandom random = new SecureRandom(seed);

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static LocalDateTime CustomDateParser(String maybeDate) {
        if (maybeDate.length() != 10) { // format: 1999-03-17
            return null;
        }
        int year;
        int month;
        int day;

        try {
            year = Integer.parseInt(maybeDate.substring(0, 4));
        } catch (NumberFormatException e) {
            return null;
        }
        try {
            month = Integer.parseInt(maybeDate.substring(5, 7));
        } catch (NumberFormatException e) {
            return null;
        }
        try {
            day = Integer.parseInt(maybeDate.substring(8, 10));
        } catch (NumberFormatException e) {
            return null;
        }

        return LocalDateTime.of(year, month, day, 23, 59, 59, 990000000);
    }
}
