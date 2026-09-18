package main;

import exception.EmailAlreadyExistsException;
import exception.InvalidCredentialsException;
import service.AuthService;
import utils.InputUtils;


public class Main {
    private static AuthService authService;

    public static int menuAuth(){
        System.out.println("1-Register");
        System.out.println("2-Login");
        System.out.println("3-Exite");

        int choix = InputUtils.readInt("Entrer Votre choix");
       return choix;
    }

    public static void menuRegister() {

        while (true) {
            try {
                String fullName = InputUtils.readString("FullName : ");

                String phone = InputUtils.readString("Phone : ");

                String email = InputUtils.readString("Email : ");
                String password = InputUtils.readString("Mode passe : ");

                Main.authService.Register(fullName, email, phone, password , "user");

                System.out.println("Register réussi ");
                return;

            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
                System.out.println("Veuillez réessayer");
            } catch (EmailAlreadyExistsException e) {
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
                Main.authService.Login(email, password);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
                System.out.println("Veuillez réessayer");

            } catch (InvalidCredentialsException e) {

                System.out.println("Erreur : " + e.getMessage());
                System.out.println("Veuillez réessayer");

            }
        }
    }

    public static void main() {
        authService = new AuthService();

        while (true) {

            int choix = menuAuth();

            switch (choix) {

                case 1:
                    System.out.println("Register");
                    menuRegister();
                    break;

                case 2:
                    System.out.println("Login");
                    menuLogin();
                    System.out.println("Welcome ");
                    break;
                default:
                    System.out.println("Good Day");
                    System.exit(0);
                    break;
            }
        }
    }
}
