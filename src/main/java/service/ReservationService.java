package service;

import Repository.jdbc.JdbcReservationRepository;
import dto.AvailableRoomDTO;
import dto.ReservationDTO;
import exception.InvalidReservationDateException;
import exception.RoomNotFoundException;
import exception.RoomUnavailableException;
import model.Reservation;
import model.Room;
import model.enums.ReservationStatus;
import model.enums.RoomStatus;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class ReservationService {

    private JdbcReservationRepository jdbcReservation;
    private RoomService roomService ;

    public ReservationService(RoomService roomService){
        this.jdbcReservation = new JdbcReservationRepository();
        this.roomService = roomService;
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



    public void creetReservation(String roomNumber, LocalDate checkIn, LocalDate checkOut,
                                 int numberOfGuests) throws InvalidReservationDateException , RoomNotFoundException, RoomUnavailableException {

        this.validateDates(checkIn, checkOut);

        if (numberOfGuests <= 0) {
            throw new InvalidReservationDateException("Le nombre de personnes invalide.");
        }

        Room room = this.roomService.findRoom(roomNumber);
        if (numberOfGuests > room.getCapacity()) {
            throw new InvalidReservationDateException("Le nombre de personnes dépasse la capacité de la chambre.");
        }

        if (room.getStatus() != RoomStatus.AVAILABLE) {
            throw new RoomUnavailableException("La chambre n'est pas disponible.");
        }

        if (!checkDate(room, checkIn, checkOut)) {
            throw new RoomUnavailableException("La chambre est déja réservée dans " + checkIn);
        }

        int days =(int) ChronoUnit.DAYS.between(checkIn, checkOut);

        BigDecimal totalPrice = this.calculerTotalPrice(room.getPrice(),checkIn,checkOut);

//        System.out.println(totalPrice);
//        System.exit(0);

        Reservation reservation = new Reservation(UUID.randomUUID().toString(),
                AuthService.getUserLogin().getId(), room, checkIn, checkOut,
                numberOfGuests, days, totalPrice, ReservationStatus.CONFIRMED);

        this.jdbcReservation.save(reservation);

        System.out.println("Reservation crée.");

    }

    public List<ReservationDTO> userReservation(){
        UUID userId = AuthService.getUserLogin().getId();
        List<Reservation>  reservations =  this.jdbcReservation.findByUserId(userId);
        List<ReservationDTO> Reservationdto = reservations.stream().map(reservation ->
                        new ReservationDTO(reservation.getCode(),
                        reservation.getRoom().getRoomNumber(),reservation.getCheckIn(),reservation.getCheckOut(),
                        reservation.getGuests() ,reservation.getTotalPrice(),reservation.getStatus()))
                        .toList();
        return Reservationdto;
    }



}
