package main;

import model.*;
import exception.RoomUnavailableException;
import exception.RoomNotFoundException;
import model.enums.RoomType;
import utils.InputUtils;

import java.math.BigDecimal;
import java.util.List;

public class RoomMenu {

    public static void afficherRooms(){
        Main.roomService.getAllRooms();
    }

    public static void afficherRoomsAvailable(){
        Main.roomService.getAvailableRoom();
    }


    public static RoomType roomType(){
        System.out.println("type de room");
        System.out.println("1-SINGLE");
        System.out.println("2-DOUBLE");
        System.out.println("3-SUITE");
        int choix = InputUtils.readInt("Choose type : ");
        RoomType type;
        switch (choix) {
            case 1:
                type = RoomType.SINGLE;
                break;

            case 2:
                type = RoomType.DOUBLE;
                break;

            case 3:
                type = RoomType.SUITE;
                break;

            default:
                throw new IllegalArgumentException("Type de chambre invalide");
        }
        return type;
    }

    public static void creetRoom(){

        System.out.println("===crée Room ===");
        String roomNumber = InputUtils.readString("Entrer room number : ");
        int capacity = InputUtils.readInt("Entrer capacity : ");
        BigDecimal price = InputUtils.readBigDecimal("Entrer Prix : ");

        RoomType type = roomType();

        Main.roomService.creetRoom(roomNumber,capacity,price,type);

    }

    public static void updateRoom(){
        afficherRooms();
        String roomNumber = InputUtils.readString("Entrer room number : ");
        int capacity = InputUtils.readInt("Entrer capacity : ");
        BigDecimal price = InputUtils.readBigDecimal("Entrer Prix : ");
        RoomType type = roomType();
        try {
            Main.roomService.updateRoom(roomNumber,capacity,price,type);
        }catch (RoomNotFoundException e){
            System.out.println("Erreur : "+e.getMessage());
        }
    }

    public static void updateStatusAvailable(){
        String roomNumber = InputUtils.readString("Entrer room number : ");
        try {
            Main.roomService.updateStatusAvailable(roomNumber);
            System.out.println("status Update ");
        }catch (RoomNotFoundException | RoomUnavailableException e){
            System.out.println("Erreur : "+e.getMessage());
        }
    }

    public static void updateStatusMAINTENANCE(){
        String roomNumber = InputUtils.readString("Entrer room number : ");
        try {
            Main.roomService.updateStatusMAINTENANCE(roomNumber);
            System.out.println("status Update ");
        }catch (RoomNotFoundException | RoomUnavailableException e){
            System.out.println("Erreur : "+e.getMessage());
        }
    }

    public static void deleteRoom(){
        String roomNumber = InputUtils.readString("Entrer room number");
        try{
            Main.roomService.deleteRoom(roomNumber);
        }catch (RoomNotFoundException e){
            System.out.println("Room Not found");
        }
    }

    public static void RoomParType(){
        System.out.println("1-SINGLE");
        System.out.println("2-DOUBLE");
        System.out.println("3-SUITE");
        int choix = InputUtils.readInt("Enter votre choix : ");
        switch (choix){
            case 1 :
                Main.roomService.filterParType(RoomType.SINGLE);
                break;
            case 2:
                Main.roomService.filterParType(RoomType.DOUBLE);
                break;
            case 3:
                Main.roomService.filterParType(RoomType.SUITE);
                break;
            default:
                System.out.println("choix Invalide");
                break;
        }
    }

    public static void  RoomParPrix(){
        BigDecimal prix = InputUtils.readBigDecimal("Entrer maximum prix : ");
        Main.roomService.filterParPrix(prix);
    }

    public static void RoomParCapacity(){
        int capacity = InputUtils.readInt("Entrer capacity : ");
        Main.roomService.filterCapacity(capacity);
    }

    public static void filterRooms() {
        while (true) {
            System.out.println("1-filtrer par Type");
            System.out.println("2-filtrer par capacité");
            System.out.println("3-filtrer par prix");
            System.out.println("0-return");
            int choix = InputUtils.readInt("Entrer votre choix");
            switch (choix) {
                case 1:
                    RoomParType();
                    break;
                case 2:
                    RoomParCapacity();
                    break;
                case 3:
                    RoomParPrix();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("invalide choix");
                    break;
            }
        }
    }

}
