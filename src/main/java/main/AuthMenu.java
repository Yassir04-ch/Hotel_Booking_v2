package main;

import exception.EmailAlreadyExistsException;
import exception.InvalidBalanceException;
import exception.InvalidCredentialsException;
import model.User;
import model.enums.UserRole;
import utils.InputUtils;

import java.math.BigDecimal;
import java.util.List;

public class AuthMenu {
    public static int menuAuth(){
        System.out.println("1-Register");
        System.out.println("2-Login");
        System.out.println("3-Exite");

        int choix = InputUtils.readInt("Entrer Votre choix : ");
        return choix;
    }

    public static void menuRegister() {

        while (true) {
            try {
                String fullName = InputUtils.readString("FullName : ");

                String phone = InputUtils.readString("Phone : ");

                String email = InputUtils.readString("Email : ");
                String password = InputUtils.readString("Mode passe : ");
                BigDecimal balance = InputUtils.readBigDecimal("Entrer Votre balance : ");

                Main.authService.Register(fullName, email, phone, password , balance);

                System.out.println("Register réussi ");
                return;

            } catch (IllegalArgumentException | InvalidBalanceException | EmailAlreadyExistsException e) {
                System.out.println("Erreur : " + e.getMessage());
                System.out.println("Veuillez réessayer");
            }
        }
    }

    public static void menuLogin() {
        while(true) {
            try {
                String email = InputUtils.readString("Email : ");
                String password = InputUtils.readString("Mode passe : ");
                User user = Main.authService.Login(email, password);
                System.out.println("Welcome "+user.getFullName());
                if(user.getRole() == UserRole.ADMIN){
                    AdminMenu.menuAdmin();
                }else {
                    ClientMenu.menuCleint();
                }

            } catch (IllegalArgumentException | InvalidCredentialsException e) {
                System.out.println("Erreur : " + e.getMessage());
                System.out.println("Veuillez réessayer");
            }
        }
    }

    public static void menuUpdateProfile(){
        try {
            System.out.println("==== Modifier Profile ====");
            String name = InputUtils.readString("Entrer  nom : ");
            String email = InputUtils.readString("Entrer  email : ");
            String phone = InputUtils.readString("Entrer phone  : ");
            Main.authService.updateProfile(name, email, phone);
            System.out.println("Votre profile et modifier");
        }catch (IllegalArgumentException e){
            System.out.println("Erreur : " +e.getMessage());
        }catch (EmailAlreadyExistsException e){
            System.out.println("Erreur : "+ e.getMessage());
        }
    }

    public static void getAll(){
        List<User> users = Main.authService.getAll();
        if(users.isEmpty()){
            System.out.println("Aucune client");
             return;
        }
        for(User user : users){
            System.out.println("Nom : " + user.getFullName());
            System.out.println("Phone : " + user.getPhone());
            System.out.println("Email : " + user.getEmail());
            System.out.println("Role : " + user.getRole());
        }
    }


    public static void menuProfile(){
        User user = Main.authService.getUserLogin();
        boolean ret = true;
        while (ret) {

            System.out.println("Nom : " + user.getFullName());
            System.out.println("Phone : " + user.getPhone());
            System.out.println("Email : " + user.getEmail());
            System.out.println("Password : " + user.getPassword());
            System.out.println("1-Modifier Profile");
            System.out.println("2-Return");
            int choix = InputUtils.readInt("Entrer votre choix");
            switch (choix) {
                case 1:
                    menuUpdateProfile();
                    break;
                case 2:
                    ret = false;
                    break;
                default:
                    System.out.println("choix invalide");
                    break;
            }
        }
    }
}
