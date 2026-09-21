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
        List<Room> rooms = Main.roomService.getAllRooms();

        if (rooms.isEmpty()){
            System.out.println("Aucune chambre disponible.");
            return;
        }

        for(Room room : rooms){
            System.out.println("=======================");
            System.out.println("roomNumber : " + room.getRoomNumber());
            System.out.println("capacity : " + room.getCapacity());
            System.out.println("price : " + room.getPrice());
            System.out.println("type : " + room.getType());
            System.out.println("status : " + room.getStatus());
            System.out.println("=======================");
        }
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

}
