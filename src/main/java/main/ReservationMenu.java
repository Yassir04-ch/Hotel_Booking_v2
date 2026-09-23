package main;

import dto.ReservationDTO;
import exception.InvalidReservationDateException;
import exception.ReservationNotFoundException;
import exception.RoomNotFoundException;
import exception.RoomUnavailableException;
import model.Reservation;
import utils.DateUtils;
import utils.InputUtils;

import java.time.LocalDate;
import java.util.List;

public class ReservationMenu {

    public  static void afficherReservation(List<ReservationDTO> reservations){
        if(reservations.isEmpty()){
            System.out.println("Auccune reservation");
            return;
        }

        System.out.println("===== Réservation =====");

        for (ReservationDTO reservation : reservations){
            System.out.println("Code de réservation : " + reservation.getCode());
            System.out.println("Numéro de chambre : " + reservation.getRoomNumber());
            System.out.println("Date de départ : " + reservation.getCheckIn());
            System.out.println("Date d'arrivée : " + reservation.getCheckOut());
            System.out.println("Nombre de personnes : " + reservation.getGuests());
            System.out.println("Nombre de nuits : " + reservation.getNumberNight());
            System.out.println("Prix total : " + reservation.getTotalPrice() + " DH");
            System.out.println("Statut : " + reservation.getStatus());
            System.out.println("Date de création : " + reservation.getCreatedAt());
            System.out.println("======================");

        }
    }

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

    public static void updateReservation(){
        try{
            String reservationCode = InputUtils.readString("Entrer code de reservation");

            LocalDate checkIn = DateUtils.readDate("Entrer Date de départ ");

            LocalDate checkout = DateUtils.readDate("Entrer Date d'arrivée ");

            String roomNumber = InputUtils.readString("Entrer nombre de room");

            int numberGuest = InputUtils.readInt("Entrer nombre des persones");

            Main.reservationService.updateReservation(reservationCode, roomNumber, checkIn, checkout, numberGuest);

        }catch (ReservationNotFoundException e){
            System.out.println("Erreur : "+e.getMessage());

        }catch (InvalidReservationDateException e){
            System.out.println("Erreur : "+e.getMessage());

        }catch (RoomNotFoundException e){
            System.out.println("Erreur : "+e.getMessage());
        }
    }
}
