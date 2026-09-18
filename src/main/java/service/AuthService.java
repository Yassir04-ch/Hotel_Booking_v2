package service;

import exception.EmailAlreadyExistsException;
import exception.InvalidCredentialsException;
import model.User;
import Repository.jdbc.JdbcUserRepository;
import model.enums.UserRole;
import utils.PasswordUtils;
import utils.ValidationUtils;

import java.util.List;
import java.util.UUID;

public class AuthService {
    private final JdbcUserRepository userRepo;
    private static User userLogin = null;

    public AuthService(){
        this.userRepo = new JdbcUserRepository();
    }

    public JdbcUserRepository getRepo() {
        return userRepo;
    }

    public void Register(String fullName , String email, String phone , String password , String role)  {
        if(!ValidationUtils.isValidName(fullName)){
            throw new IllegalArgumentException("Name invalide");
        }
        if(!ValidationUtils.isValidEmail(email)){
            throw new IllegalArgumentException("Email invalide");
        }
        if (!ValidationUtils.isValidPhone(phone)){
            throw new IllegalArgumentException("Phone invalide");
        }
        if(!ValidationUtils.isValidPassword(password)){
            throw new IllegalArgumentException("Password invalide");
        }
        if(userRepo.existsByEmail(email)){
            throw new EmailAlreadyExistsException("Email déja exist");
        }
            String passwordHash = PasswordUtils.hashPassword(password);

            User user = new User(fullName ,email ,phone , passwordHash , UserRole.CLIENT);
            this.userRepo.save(user);
    }

    public void Login(String email , String password){
        if(!ValidationUtils.isValidEmail(email)){
            throw new IllegalArgumentException("Email invalide");
        }
        if(!userRepo.existsByEmail(email)){
            throw new InvalidCredentialsException("Email n'existe pas");
        }
        User user = userRepo.getUserByEmail(email).orElseThrow(()->
                new InvalidCredentialsException("user not found"));

        if(!PasswordUtils.checkPassword(password,user.getPassword())){
            throw new InvalidCredentialsException("Password incorrect");
        }

        userLogin = user;
    }

    public static User getUserLogin(){
        return userLogin;
    }
    public void setUserLogin(User user){
        userLogin = user;
    }

    public  boolean isLogin(){
        return userLogin != null;
    }

    public void logOut(){
        userLogin = null;
    }

    public void updateProfile(String fullName , String email , String phone){
        User user = this.getUserLogin();

        if(!ValidationUtils.isValidName(fullName)){
            throw new IllegalArgumentException("Name invalide");
        }
        if(!ValidationUtils.isValidEmail(email)){
            throw new IllegalArgumentException("Email invalide");
        }
        if (!ValidationUtils.isValidPhone(phone)){
            throw new IllegalArgumentException("Phone invalide");
        }
        if(userRepo.existsByEmail(email) && !user.getEmail().equals(email)){
            throw new EmailAlreadyExistsException("Email déja exist");
        }
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        userRepo.update(user);
    }

    public void UpdatePassword(String password,String oldPassword){
        User user = this.getUserLogin();
        if(!ValidationUtils.isValidPassword(password)){
            throw new IllegalArgumentException("Password invalide");
        }
        if(!user.getPassword().equals(oldPassword)){
            throw new InvalidCredentialsException("Password incorect");
        }
        user.setPassword(password);
        userRepo.update(user);
    }

    public List<User> getAll(){
        List<User> users = this.userRepo.findAll().stream().
                filter(e->e.getRole().equals("user")).toList();
        if(users.isEmpty()){
            System.out.println("Aucune client");
        }
        return users;
    }


}
