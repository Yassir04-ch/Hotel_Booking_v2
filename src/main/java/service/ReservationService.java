package service;

import Repository.jdbc.JdbcReservationRepository;
import exception.InvalidReservationDateException;
import exception.RoomNotFoundException;
import exception.RoomUnavailableException;
import model.Reservation;
import model.Room;
import model.enums.ReservationStatus;
import model.enums.RoomStatus;

import java.math.BigDecimal;
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

        BigDecimal totalPrice = room.getPrice().multiply(BigDecimal.valueOf(days));

        Reservation reservation = new Reservation(UUID.randomUUID().toString(),
                AuthService.getUserLogin().getId(), room, checkIn, checkOut,
                numberOfGuests, days, totalPrice, ReservationStatus.CONFIRMED);

        this.jdbcReservation.save(reservation);

        System.out.println("Reservation crée.");

    }



}
