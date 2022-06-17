package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Reservation;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

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
}
