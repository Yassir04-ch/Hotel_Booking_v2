package Repository.jdbc;

import Repository.InvoiceRepository;
import db.DatabaseConnection;
import model.Invoice;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcInvoiceRepository implements InvoiceRepository {
    private final Connection connection;

    public JdbcInvoiceRepository(){
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Invoice invoice) {
      String sql = "INSERT INTO invoices(reservation_id,subtotal_ht,vat,total_ttc,issued_at)VALUES(?,?,?,?,?)";
      try{
          PreparedStatement statement = connection.prepareStatement(sql);
          statement.setObject(1,invoice.getReservation().getId());
          statement.setBigDecimal(2,invoice.getSubtotalHT());
          statement.setBigDecimal(3,invoice.getVat());
          statement.setBigDecimal(4,invoice.getSubtotalHT());
          statement.setDate(5, Date.valueOf(invoice.getIssuedAt()));
          statement.executeUpdate();

  }catch (SQLException e){
          throw  new RuntimeException(e);
      }
    }
}
