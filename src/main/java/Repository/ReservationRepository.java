package Repository;

import model.Reservation;
import model.enums.ReservationStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {
    void save(Reservation reservation);

    void update(Reservation reservation);

    Optional<Reservation> findByCode(String code);

    List<Reservation> findByUserId(UUID userId);

    List<Reservation> findAll();

    void updateStatus(Reservation reservation, ReservationStatus status);

    List<Reservation> findByRoomNumber(String roomNumber);


}
