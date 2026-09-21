package Repository.jdbc;

import Repository.RoomRepository;
import db.DatabaseConnection;
import model.Room;
import model.enums.RoomStatus;
import model.enums.RoomType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcRoomRepository  implements RoomRepository {

    private final Connection connection;

    public JdbcRoomRepository() {
        connection = DatabaseConnection.getInstance().getConnection();
    }


    @Override
    public void save(Room room) {
        String sql = "INSERT INTO rooms(room_number,type,capacity,price,status) VALUES (?,?::room_type,?,?,?::room_status)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,room.getRoomNumber());
            statement.setString(2,room.getType().name());
            statement.setInt(3,room.getCapacity());
            statement.setBigDecimal(4,room.getPrice());
            statement.setString(5,room.getStatus().name());
            statement.executeUpdate();
        }catch (SQLException e){
         throw  new RuntimeException(e);
        }
    }

    @Override
    public Optional<Room> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Room> findByRoomNumber(String roomNumber) {

        String sql = "SELECT * FROM rooms WHERE room_number = ? ";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, roomNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Room room = new Room(resultSet.getString("room_number"), RoomType.valueOf(resultSet.getString("type")), resultSet.getInt("capacity"),
                        resultSet.getBigDecimal("price"), RoomStatus.valueOf(resultSet.getString("status")));

                return Optional.of(room);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();

        String sql = "SELECT * FROM rooms WHERE is_deleted = FALSE";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Room room = new Room(resultSet.getString("room_number"),
                        RoomType.valueOf(resultSet.getString("type")),
                        resultSet.getInt("capacity"),
                        resultSet.getBigDecimal("price"),
                        RoomStatus.valueOf(resultSet.getString("status")));

                rooms.add(room);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rooms;
    }

    @Override
    public List<Room> findAvailable() {
        String sql = "SELECT * FROM rooms WHERE status = ? AND is_deleted = FALSE";
        List<Room> rooms = new ArrayList<>();

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,RoomStatus.AVAILABLE.name());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Room room = new Room(resultSet.getString("room_number"),
                    RoomType.valueOf(resultSet.getString("type")),
                    resultSet.getInt("capacity"),
                    resultSet.getBigDecimal("price"),
                    RoomStatus.valueOf(resultSet.getString("status")));
            rooms.add(room);
        }

        }catch (SQLException e){
         throw new RuntimeException(e);
        }

        return rooms;
    }

    @Override
    public void update(Room room) {
        String sql = "UPDATE rooms SET  type = ?::room_type, capacity = ?, price = ? , status = ?::room_status WHERE room_number = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1,room.getType().name());
            statement.setInt(2,room.getCapacity());
            statement.setBigDecimal(3,room.getPrice());
            statement.setObject(4,room.getStatus().name());
            statement.setString(5,room.getRoomNumber());
            statement.executeUpdate();

        }catch (SQLException e){
         throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(Room room) {
       String sql = "UPDATE rooms SET is_deleted = TRUE WHERE room_number = ?";
       try{
           PreparedStatement statement = connection.prepareStatement(sql);
           statement.setString(1,room.getRoomNumber());
           statement.executeUpdate();
       }catch (SQLException e){
           throw new RuntimeException(e);
       }
    }

    public void updateStatus(Room room, RoomStatus roomStatus) {
        String sql = "UPDATE rooms SET type = ?, WHERE room_number = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1,room.getStatus());
            statement.setString(2,room.getRoomNumber());
            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
