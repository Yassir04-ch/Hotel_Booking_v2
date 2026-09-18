package Repository.jdbc;

import Repository.UserRepository;
import db.DatabaseConnection;
import model.User;
import model.enums.UserRole;

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
        String sql = "INSERT INTO users(full_name,email,phone,password,role) VALUES (?,?,?,?,?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,user.getFullName());
            statement.setString(2, user.getEmail());
            statement.setString(3,user.getPhone());
            statement.setString(4,user.getPassword());
            statement.setString(5,user.getRole().name());
            statement.executeQuery();

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
            statement.executeQuery();
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
            ResultSet resultSet = statement.executeQuery();
             if(resultSet.next()){
                 User user = new User();
                 user.setId(resultSet.getObject("id",UUID.class));
                 user.setFullName(resultSet.getString("full_name"));
                 user.setEmail(resultSet.getString("email"));
                 user.setPhone(resultSet.getString("phone"));
                 user.setRole(UserRole.valueOf(resultSet.getString("role")));
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
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                User user =new User();
                user.setId(resultSet.getObject("id",UUID.class));
                user.setFullName(resultSet.getString("full_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(UserRole.valueOf(resultSet.getString("role")));
                return Optional.of(user);
            }
            return Optional.empty();
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
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                UUID id = UUID.fromString(resultSet.getString("id"));
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                UserRole role = UserRole.valueOf(resultSet.getString("role"));

                User user = new User();
                user.setId(id);
                user.setFullName(resultSet.getString("full_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(role);
                users.add(user);
            }
        }catch (SQLException e) {
          throw new RuntimeException(e);
        }
        return users;
    }
}
