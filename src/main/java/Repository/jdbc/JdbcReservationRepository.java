package Repository.jdbc;

import Repository.ReservationRepository;
import db.DatabaseConnection;
import exception.RoomNotFoundException;
import model.Reservation;
import model.Room;
import model.enums.ReservationStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcReservationRepository implements ReservationRepository {

    private final Connection connection;
    private JdbcRoomRepository jbdcRoom;

    public JdbcReservationRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.jbdcRoom = new JdbcRoomRepository();
    }


    @Override
    public void save(Reservation reservation) {
        String sql= "INSERT INTO reservations (reservation_code,user_id,room_number,check_in,check_out,number_of_guests,number_of_nights,total_price,status)"
              + "VALUES (?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,reservation.getCode());
            statement.setObject(2,reservation.getUserId());
            statement.setString(3,reservation.getRoom().getRoomNumber());
            statement.setDate(4, Date.valueOf(reservation.getCheckIn()));
            statement.setDate(5, Date.valueOf(reservation.getCheckOut()));
            statement.setInt(6,reservation.getGuests());
            statement.setInt(7,reservation.getNumberOfNights());
            statement.setBigDecimal(8,reservation.getTotalPrice());
            statement.setString(9,reservation.getStatus().name());
            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Reservation reservation) {

    String sql= "UPDATE  reservations SET room_number = ? , check_in = ? , check_out = ? , number_of_guests = ? , number_of_nights = ? " +
            ",total_price = ? ,status = ?  WHERE id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,reservation.getRoom().getRoomNumber());
            statement.setDate(2, Date.valueOf(reservation.getCheckIn()));
            statement.setDate(3, Date.valueOf(reservation.getCheckOut()));
            statement.setInt(4,reservation.getGuests());
            statement.setInt(5,reservation.getNumberOfNights());
            statement.setBigDecimal(6,reservation.getTotalPrice());
            statement.setString(7,reservation.getStatus().name());
            statement.setObject(8 , reservation.getId());
            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException();
        }

    }

    @Override
    public Optional<Reservation> findByCode(String code) {
        String sql = "SELECT * FROM reservations WHERE reservation_code = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,code);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                Reservation reservation = new Reservation();
                reservation.setId(resultSet.getObject("id", UUID.class));
                reservation.setCode(resultSet.getString("reservation_code"));
                reservation.setUserId(resultSet.getObject("user_id", UUID.class));
                Room room = this.jbdcRoom.findByRoomNumber(resultSet.getString("room_number")).orElseThrow(
                        ()-> new RoomNotFoundException("Room not found")
                );
                reservation.setRoom(room);
                reservation.setCheckIn(resultSet.getDate("check_in").toLocalDate());
                reservation.setCheckOut(resultSet.getDate("check_out").toLocalDate());
                reservation.setGuests(resultSet.getInt("number_of_guests"));
                reservation.setNumberOfNights(resultSet.getInt("number_of_nights"));
                reservation.setStatus(ReservationStatus.valueOf(resultSet.getString("status")));
                reservation.setCreatedAt(resultSet.getDate("created_at").toLocalDate());

              return Optional.of(reservation);
            }
            return Optional.empty();
        }catch (SQLException e){
            throw new RuntimeException(e);
        } catch (RoomNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reservation> findByUserId(UUID userId) {
        String sql = "SELECT * FROM reservations WHERE user_id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1,userId);
            ResultSet resultSet = statement.executeQuery();
            List<Reservation> reservationslist = new ArrayList<>();
            while (resultSet.next()){
                Reservation reservation = new Reservation();
                reservation.setId(resultSet.getObject("id", UUID.class));
                reservation.setCode(resultSet.getString("reservation_code"));
                reservation.setUserId(resultSet.getObject("user_id", UUID.class));
                Room room = this.jbdcRoom.findByRoomNumber(resultSet.getString("room_number")).orElseThrow(
                        ()-> new RoomNotFoundException("Room not found")
                );
                reservation.setRoom(room);
                reservation.setCheckIn(resultSet.getDate("check_in").toLocalDate());
                reservation.setCheckOut(resultSet.getDate("check_out").toLocalDate());
                reservation.setGuests(resultSet.getInt("number_of_guests"));
                reservation.setNumberOfNights(resultSet.getInt("number_of_nights"));
                reservation.setStatus(ReservationStatus.valueOf(resultSet.getString("status")));
                reservation.setCreatedAt(resultSet.getDate("created_at").toLocalDate());

                reservationslist.add(reservation);
            }
            return reservationslist;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (RoomNotFoundException e){
         throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservations";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<Reservation> reservationslist = new ArrayList<>();
            while (resultSet.next()){
                Reservation reservation = new Reservation();
                reservation.setId(resultSet.getObject("id", UUID.class));
                reservation.setCode(resultSet.getString("reservation_code"));
                reservation.setUserId(resultSet.getObject("user_id", UUID.class));
                Room room = this.jbdcRoom.findByRoomNumber(resultSet.getString("room_number")).orElseThrow(
                        ()-> new RoomNotFoundException("Room not found")
                );
                reservation.setRoom(room);
                reservation.setCheckIn(resultSet.getDate("check_in").toLocalDate());
                reservation.setCheckOut(resultSet.getDate("check_out").toLocalDate());
                reservation.setGuests(resultSet.getInt("number_of_guests"));
                reservation.setNumberOfNights(resultSet.getInt("number_of_nights"));
                reservation.setStatus(ReservationStatus.valueOf(resultSet.getString("status")));
                reservation.setCreatedAt(resultSet.getDate("created_at").toLocalDate());

                reservationslist.add(reservation);
            }
            return reservationslist;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (RoomNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
