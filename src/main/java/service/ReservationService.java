package service;

import Repository.jdbc.JdbcReservationRepository;
import db.DatabaseConnection;
import dto.InvoiceDTO;
import dto.ReservationDTO;
import exception.*;
import model.*;
import model.enums.ReservationStatus;
import model.enums.RoomStatus;
import utils.MoneyUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import Strategy.payment.BalancePaymentStrategy;
import Strategy.payment.CardPaymentStrategy;
import Strategy.payment.PaymentContext;
import Strategy.payment.PaymentStrategy;

public class ReservationService {

    private JdbcReservationRepository jdbcReservation;
    private RoomService roomService ;
    private PaymentService paymentService;
    private InvoiceService invoiceService;


    public ReservationService(RoomService roomService){
        this.jdbcReservation = new JdbcReservationRepository();
        this.roomService = roomService;
        this.paymentService = new PaymentService();
        this.invoiceService = new InvoiceService();
    }

    public void validateDates(LocalDate checkIn, LocalDate checkOut
    ) throws InvalidReservationDateException {

        if (checkIn.isBefore(LocalDate.now())) {
            throw new InvalidReservationDateException("Date checkin invalide.");
        }

        if (!checkOut.isAfter(checkIn)) {
            throw new InvalidReservationDateException("Date checkout invalide.");
        }
    }

    public boolean checkDate(Room room, LocalDate checkIn, LocalDate checkOut){

        List<Reservation> listReservations = this.jdbcReservation.findByRoomNumber(room.getRoomNumber());

        for (Reservation reservation : listReservations) {

            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {

                if (checkIn.isBefore(reservation.getCheckOut())
                        && checkOut.isAfter(reservation.getCheckIn())) {

                    return false;
                }
            }
        }

        return true;
    }

    public BigDecimal calculerTotalPrice(BigDecimal roomPrice,LocalDate chickin , LocalDate chickout){
         BigDecimal total = BigDecimal.ZERO;
         long nombreNuit = ChronoUnit.DAYS.between(chickin,chickout);
         LocalDate date = chickin;
         while (date.isBefore(chickout)){
             BigDecimal nightPrice = roomPrice;
             if(date.getMonthValue() == 7 || date.getMonthValue() == 8 )
             {
                 nightPrice = nightPrice.multiply(new BigDecimal("1.30"));
             }
             if(date.getMonthValue() == 11 || date.getMonthValue() == 12 ||
                     date.getMonthValue() == 1 || date.getMonthValue() == 2 )
             {
                 nightPrice = nightPrice.multiply(new BigDecimal("0.85"));
             }
             if(date.getDayOfWeek() == DayOfWeek.FRIDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY ){
                 nightPrice = nightPrice.multiply(new BigDecimal("1.15"));
             }

             total = total.add(nightPrice);

             date = date.plusDays(1);

         }
            if (nombreNuit >= 14) {

                total = total.multiply(new BigDecimal("0.85"));

            } else if (nombreNuit >= 7) {

                total = total.multiply(new BigDecimal("0.90"));
            }

            long daysBefCheckIn = ChronoUnit.DAYS.between(LocalDate.now(),chickin);
            if(daysBefCheckIn >= 30){
                total = total.multiply(new BigDecimal("0.95"));
            }
            else if(daysBefCheckIn <= 3){
                total = total.multiply(new BigDecimal("1.10"));
            }

            return total;

    }


    private PaymentStrategy getPaymentStrategy(int choix) {


        switch (choix) {

            case 1:
                return new BalancePaymentStrategy();

            case 2:
                return new CardPaymentStrategy();

            default:
                throw new IllegalArgumentException(
                        "Choix invalide."
                );
        }
    }


    public InvoiceDTO creetReservation(String roomNumber, LocalDate checkIn, LocalDate checkOut,
                                 int numberOfGuests,int choix ) throws InvalidReservationDateException, RoomNotFoundException, RoomUnavailableException, InvalidBalanceException {

        this.validateDates(checkIn, checkOut);

        if (numberOfGuests <= 0) {
            throw new InvalidReservationDateException("Le nombre de personnes invalide.");
        }
        User user = AuthService.getUserLogin();
        Room room = this.roomService.findRoom(roomNumber);
        if (numberOfGuests > room.getCapacity()) {
            throw new InvalidReservationDateException("Le nombre de personnes dépasse la capacité de la chambre.");
        }

        if (room.getStatus() != RoomStatus.AVAILABLE) {
            throw new RoomUnavailableException("La chambre n'est pas disponible.");
        }

        BigDecimal totalPrice = this.calculerTotalPrice(room.getPrice(),checkIn,checkOut);

        BigDecimal totalTTC = MoneyUtils.calculateTTC(totalPrice);
//        System.out.println(user.getBalance());
//        System.exit(0);
        if (!checkDate(room, checkIn, checkOut)) {
            throw new RoomUnavailableException("La chambre est déja réservée dans " + checkIn);
        }

        int days =(int) ChronoUnit.DAYS.between(checkIn, checkOut);


//        System.out.println(totalPrice);
//        System.exit(0);
        Reservation reservation = new Reservation(UUID.randomUUID().toString(),
                user.getId(), room, checkIn, checkOut,
                numberOfGuests, days, totalPrice, ReservationStatus.CONFIRMED);

        PaymentStrategy strategy = this.getPaymentStrategy(choix);
        PaymentContext paymentContext = new PaymentContext(strategy);

        Connection connection = DatabaseConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            this.jdbcReservation.save(reservation);
            paymentContext.pay(user, totalTTC);
            Payment payment = this.paymentService.createPaiment( reservation, totalPrice );
            Invoice invoice = this.invoiceService.createInvoice(payment);
            connection.commit();
            System.out.println("Reservation succes");
            return this.mapInvoice(invoice);

        }catch (Exception e){
            try{
                connection.rollback();
            }catch (SQLException roolback){
                roolback.printStackTrace();
            }
            throw new RuntimeException("erreur dans creation reservation : " +e.getMessage());
        }finally {
            try{
               connection.setAutoCommit(true);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

    public InvoiceDTO mapInvoice(Invoice invoice){
        InvoiceDTO invoicedto = new InvoiceDTO(invoice.getSubtotalHT(),invoice.getVat(),
                                invoice.getTotalTTC(),invoice.getIssuedAt());
        return invoicedto;
    }

    public List<ReservationDTO> mapReservations(List<Reservation>  reservations){
        List<ReservationDTO> Reservationdto = reservations.stream().map(reservation ->
                             new ReservationDTO(reservation.getCode(),
                                reservation.getRoom().getRoomNumber(),reservation.getCheckIn(),reservation.getCheckOut(),
                                reservation.getGuests() ,reservation.getNumberOfNights(),reservation.getTotalPrice()
                                ,reservation.getStatus(),reservation.getCreatedAt()))
                                .toList();
        return Reservationdto;

    }

    public  List<ReservationDTO> allReservation(){
        List<Reservation>  reservations =  this.jdbcReservation.findAll();
        return this.mapReservations(reservations);
    }

    public List<ReservationDTO> userReservation(){
        UUID userId = AuthService.getUserLogin().getId();
        List<Reservation>  reservations =  this.jdbcReservation.findByUserId(userId);
        return this.mapReservations(reservations);
    }

    public void updateReservation(String code, String roomNumber, LocalDate checkIn, LocalDate checkout, int numberGuest) throws ReservationNotFoundException,
            InvalidReservationDateException , RoomNotFoundException {
        Reservation reservation = this.jdbcReservation.findByCode(code).orElseThrow(() ->
                new ReservationNotFoundException("Reservation not found"));

        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new IllegalArgumentException("Cette réservation n'est pas confirmée.");
        }

        if (reservation.getCheckIn().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("impossible de modifier une réservation déja commencée.");
        }

        Room room = this.roomService.findRoom(roomNumber);

        if (numberGuest > room.getCapacity()) {
            throw new InvalidReservationDateException("Le nombre de personnes dépasse la capacité de la chambre");
        }

        this.jdbcReservation.updateStatus(reservation, ReservationStatus.CANCELLED);
        boolean valid = this.checkDate(room, checkIn, checkout);
        if (!valid) {
            this.jdbcReservation.updateStatus(reservation, ReservationStatus.CONFIRMED);
            throw new InvalidReservationDateException("La chambre est déjà réservée dans cette période.");
        }

        BigDecimal totalPrix = this.calculerTotalPrice(room.getPrice() , checkIn ,checkout);
        int numberOfNights =(int) ChronoUnit.DAYS.between(checkIn,checkout);
        reservation.setRoom(room);
        reservation.setCheckIn(checkIn);
        reservation.setCheckOut(checkout);
        reservation.setGuests(numberGuest);
        reservation.setTotalPrice(totalPrix);
        reservation.setNumberOfNights(numberOfNights);
        reservation.setCreatedAt(LocalDate.now());
        this.jdbcReservation.update(reservation);
        System.out.println("Reservation updated");
      }

      public void cancelReservation(String code) throws ReservationNotFoundException {
          Reservation reservation = this.jdbcReservation.findByCode(code).orElseThrow(()->
                  new ReservationNotFoundException("Reservation not found"));

          if(reservation.getStatus() != ReservationStatus.CONFIRMED){
              throw new IllegalArgumentException("Cette réservation est déja annulée ou Terminée");
          }

          if (reservation.getCheckIn().isBefore(LocalDate.now())) {

//              BigDecimal remboursement = this.calculerTotalPrice(reservation.getRoom().getPrice() ,LocalDate.now(),reservation.getCheckOut());
//              User user =  AuthService.getUserLogin();
          }

          this.jdbcReservation.updateStatus(reservation, ReservationStatus.CANCELLED);

      }

    }
