package Repository.jdbc;

import Repository.PaymentRespository;
import db.DatabaseConnection;
import model.Payment;
import model.Reservation;
import model.enums.PaymentStatus;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class JdbcPaymentRepository implements PaymentRespository {
    private final Connection connection;

    public JdbcPaymentRepository(){
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Payment payment){
        String sql = "INSERT INTO payments(id,reservation_id,amount,status,paid_at) VALUES(?,?,?,?::payment_status,?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1,payment.getId());
            statement.setObject(2,payment.getReservation().getId());
            statement.setBigDecimal(3,payment.getAmount());
            statement.setString(4,payment.getStatus().name());
            statement.setDate(5, Date.valueOf(payment.getPaidAt()));
            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Payment payment){
        String sql = "UPDATE  payments SET amount = ?,paid_at = ? WHERE id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1,payment.getAmount());
            statement.setDate(2, Date.valueOf(payment.getPaidAt()));
            statement.setObject(3,payment.getId());
            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Payment> getPaymentByResevationID(Reservation reservation) {

        String sql = "SELECT * FROM payments WHERE reservation_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setObject(1, reservation.getId());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Payment payment = new Payment();
                payment.setId(resultSet.getObject("id", UUID.class));
                payment.setReservation(reservation);
                payment.setAmount(resultSet.getBigDecimal("amount"));
                payment.setStatus(PaymentStatus.valueOf(resultSet.getString("status")));
                payment.setPaidAt(resultSet.getDate("paid_at").toLocalDate());

                return Optional.of(payment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

}
