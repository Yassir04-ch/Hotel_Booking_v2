package Repository.jdbc;

import Repository.UserRepository;
import db.DatabaseConnection;
import model.User;
import model.enums.UserRole;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcUserRepository implements UserRepository {
    private final Connection connection;


    public JdbcUserRepository() {
     connection = DatabaseConnection.getInstance().getConnection();
    }
    @Override
    public void save(User user) {
        String sql = "INSERT INTO users(full_name,email,phone,password,role,balance) VALUES (?,?,?,?,?::user_role,?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,user.getFullName());
            statement.setString(2, user.getEmail());
            statement.setString(3,user.getPhone());
            statement.setString(4,user.getPassword());
            statement.setString(5,user.getRole().name());
            statement.setBigDecimal(6,user.getBalance());
            statement.executeUpdate();
        }catch (SQLException e){
          throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET email = ?, phone = ?, password = ? WHERE id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,user.getEmail());
            statement.setString(2,user.getPhone());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
        }catch (SQLException e){
         throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,email);
            ResultSet result = statement.executeQuery();
             if(result.next()){
                 User user = new User();
                 user.setId(result.getObject("id",UUID.class));
                 user.setFullName(result.getString("full_name"));
                 user.setEmail(result.getString("email"));
                 user.setPhone(result.getString("phone"));
                 user.setPassword(result.getString("password"));
                 user.setRole(UserRole.valueOf(result.getString("role")));
                 user.setBalance(result.getBigDecimal("balance"));
                 return Optional.of(user);
             }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1 , id);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                User user =new User();
                user.setId(result.getObject("id",UUID.class));
                user.setFullName(result.getString("full_name"));
                user.setEmail(result.getString("email"));
                user.setPhone(result.getString("phone"));
                user.setRole(UserRole.valueOf(result.getString("role")));
                return Optional.of(user);
            }
            return Optional.empty();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean existsByEmail(String email){
        String sql = "SELECT email FROM users WHERE email = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,email);
            ResultSet result = statement.executeQuery();
            return result.next();
        }catch (SQLException e){
             throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                UUID id = UUID.fromString(result.getString("id"));
                String fullName = result.getString("full_name");
                String email = result.getString("email");
                String phone = result.getString("phone");
                UserRole role = UserRole.valueOf(result.getString("role"));

                User user = new User();
                user.setId(id);
                user.setFullName(result.getString("full_name"));
                user.setEmail(result.getString("email"));
                user.setPhone(result.getString("phone"));
                user.setRole(role);
                users.add(user);
            }
        }catch (SQLException e) {
          throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void updateBalance(User user, BigDecimal balance) {
        String sql = "UPDATE users SET balance = ? WHERE id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1,balance);
            statement.setObject(2,user.getId());
            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
