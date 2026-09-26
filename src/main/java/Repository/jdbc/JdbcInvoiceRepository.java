package Repository.jdbc;

import Repository.InvoiceRepository;
import db.DatabaseConnection;
import model.Invoice;
import model.Payment;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class JdbcInvoiceRepository implements InvoiceRepository {
    private final Connection connection;

    public JdbcInvoiceRepository(){
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Invoice invoice) {
      String sql = "INSERT INTO invoices(payment_id,subtotal_ht,vat,total_ttc,issued_at)VALUES(?,?,?,?,?)";
      try{
          PreparedStatement statement = connection.prepareStatement(sql);
          statement.setObject(1,invoice.getPayment().getId());
          statement.setBigDecimal(2,invoice.getSubtotalHT());
          statement.setBigDecimal(3,invoice.getVat());
          statement.setBigDecimal(4,invoice.getSubtotalHT());
          statement.setDate(5, Date.valueOf(invoice.getIssuedAt()));
          statement.executeUpdate();

  }catch (SQLException e){
          throw  new RuntimeException(e);
      }
    }

    @Override
    public void update(Invoice invoice) {

        String sql = " UPDATE invoices SET subtotal_ht = ?, vat = ?, total_ttc = ?, issued_at = ? WHERE id = ? ";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setBigDecimal(1, invoice.getSubtotalHT());
            statement.setBigDecimal(2, invoice.getVat());
            statement.setBigDecimal(3, invoice.getTotalTTC());
            statement.setDate(4, Date.valueOf(invoice.getIssuedAt()));
            statement.setObject(5, invoice.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Invoice> getInvoiceByPayment(Payment payment) {

        String sql = "SELECT * FROM invoices WHERE payment_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, payment.getId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Invoice invoice = new Invoice();

                invoice.setId(resultSet.getObject("id", UUID.class));
                invoice.setPayment(payment);
                invoice.setSubtotalHT(resultSet.getBigDecimal("subtotal_ht"));
                invoice.setVat(resultSet.getBigDecimal("vat"));
                invoice.setTotalTTC(resultSet.getBigDecimal("total_ttc"));
                invoice.setIssuedAt(resultSet.getDate("issued_at").toLocalDate());

                return Optional.of(invoice);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }


}
