package main;

import exception.EmailAlreadyExistsException;
import exception.InvalidBalanceException;
import exception.InvalidCredentialsException;
import model.User;
import model.enums.UserRole;
import service.AuthService;
import service.ReservationService;
import service.RoomService;
import utils.InputUtils;
import java.math.BigDecimal;


public class Main {
    public static AuthService authService;
    public static RoomService roomService;
    public static ReservationService reservationService;

    public static void main() {
        authService = new AuthService();
        roomService = new RoomService();
        reservationService = new ReservationService(roomService);

        while (true) {

            int choix = AuthMenu.menuAuth();

            switch (choix) {

                case 1:
                    System.out.println("Register");
                    AuthMenu.menuRegister();
                    break;

                case 2:
                    System.out.println("Login");
                    AuthMenu.menuLogin();
                    break;
                default:
                    System.out.println("Good Day");
                    System.exit(0);
                    break;
            }
        }
    }
}
