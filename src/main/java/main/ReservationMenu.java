package main;

import dto.InvoiceDTO;
import dto.ReservationDTO;
import exception.*;
import model.Room;
import utils.DateUtils;
import utils.InputUtils;

import java.math.BigDecimal;
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
            Room room = Main.roomService.findRoom(roomNumber);
            BigDecimal roomPrice = room.getPrice();
            int choix = PaymentMenu.menuPayment();
            InvoiceDTO invoice = Main.reservationService.creetReservation(roomNumber, checkIn, checkout, numberOfGuests,choix);
            afficherInvoice(invoice);

        }catch (InvalidReservationDateException | RoomNotFoundException | RoomUnavailableException |
                InvalidBalanceException e){
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

    public static void afficherInvoice(InvoiceDTO invoice) {

        System.out.println("===============FACTURE===============");
        System.out.println("Date : " + invoice.getIssuedAt());
        System.out.println("Subtotal HT : " + invoice.getSubtotalHT() + " DH");
        System.out.println("TVA (20%) : " + invoice.getVat() + " DH");
        System.out.println("Total TTC : " + invoice.getTotalTTC() + " DH");
        System.out.println("=====================================");
    }

    public static void cancelReservation() {
        afficherReservation(Main.reservationService.userReservation());
        String code = InputUtils.readString("Entrer Reservation Code : ");
        try{
        Main.reservationService.cancelReservation(code);
        System.out.println("Reservation Cancel");
        }catch (ReservationNotFoundException e){
            System.out.println("Erreur : "+e.getMessage());
        }
    }
}
