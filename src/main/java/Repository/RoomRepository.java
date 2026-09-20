package Repository;

import model.Room;
import model.enums.RoomStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepository {

    void save(Room room);

    Optional<Room> findById(UUID id);

    Optional<Room> findByRoomNumber(String roomNumber);

    List<Room> findAll();

    List<Room> findAvailable();

    void update(Room room);

    void updateStatus(Room room , RoomStatus status);

    void delete(Room room);

}