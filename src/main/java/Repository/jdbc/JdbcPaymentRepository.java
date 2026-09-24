package Repository.jdbc;

import Repository.PaymentRespository;
import db.DatabaseConnection;
import model.Payment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class JdbcPaymentRepository implements PaymentRespository {
    private final Connection connection;

    public JdbcPaymentRepository(){
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Payment payment){
        String sql = "INSERT INTO payments(reservation_id,amount,status,paid_at) VALUES(?,?,?::payment_status,?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1,payment.getReservation().getId());
            statement.setBigDecimal(2,payment.getAmount());
            statement.setString(3,payment.getStatus().name());
            statement.setDate(4, Date.valueOf(payment.getPaidAt()));
            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Payment> getPaymentById(UUID id) {
        return Optional.empty();
    }
}
