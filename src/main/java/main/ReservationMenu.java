package main;

import exception.InvalidReservationDateException;
import exception.RoomNotFoundException;
import exception.RoomUnavailableException;
import utils.DateUtils;
import utils.InputUtils;

import java.time.LocalDate;

public class ReservationMenu {
    public static void createReservation() {
        try{
            RoomMenu.afficherRoomsAvailable();

            String roomNumber = InputUtils.readString("Entrer RoomNumber : ");

            LocalDate checkIn = DateUtils.readDate("Entrer  Date d'arrivée ex (2026-09-15) : ");

            LocalDate checkout = DateUtils.readDate("Entrer Checkout ex (2026-09-15) : ");

            int numberOfGuests = InputUtils.readInt("Entrer numbre des persone ");

            Main.reservationService.creetReservation(roomNumber, checkIn, checkout, numberOfGuests);

        }catch (InvalidReservationDateException | RoomNotFoundException | RoomUnavailableException e){
            System.out.println("Erreur :" + e.getMessage());

        }
    }
}
