package service;

import dto.AvailableRoomDTO;
import model.*;
import exception.RoomNotFoundException;
import exception.RoomUnavailableException;
import Repository.jdbc.JdbcRoomRepository;
import model.Room;
import model.enums.RoomStatus;
import model.enums.RoomType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RoomService {

    private JdbcRoomRepository roomRepo;

    public RoomService(){
        this.roomRepo = new JdbcRoomRepository();
    }

    public JdbcRoomRepository getRepo(){
        return this.roomRepo;
    }

    public Room findRoom(String roomNumber) throws RoomNotFoundException {
        Room room = this.roomRepo.findByRoomNumber(roomNumber).orElseThrow(()->
                new RoomNotFoundException("Room not found"));
        return room;
    }

    public void creetRoom(String roomNumber , int capacity , BigDecimal price, RoomType type ){
        Room room = new Room(roomNumber, type, capacity, price, RoomStatus.AVAILABLE);
        this.roomRepo.save(room);
        System.out.println("Room crée");
    }

    public void updateRoom( String roomNumber , int capacity , BigDecimal price,RoomType type )throws RoomNotFoundException{
        Room room = this.findRoom(roomNumber);
//        System.out.println(room.getRoomNumber());
//        System.exit(0);
        room.setCapacity(capacity);
        room.setPrice(price);
        room.setType(type);
        this.roomRepo.update(room);
        System.out.println("room update");
    }


    public List<Room> getAllRooms(){
        return this.roomRepo.findAll();
    }

    public void getAvailableRoom(){
       List<Room> rooms = this.roomRepo.findAvailable();
        List<AvailableRoomDTO> availebRoom = rooms.stream().map(room -> new AvailableRoomDTO(room.getRoomNumber(),room.getType(),room.getCapacity(),room.getPrice()))
               .toList();
       this.afficherRoomsAvailable(availebRoom);
    }

    public static void afficherRoomsAvailable(List<AvailableRoomDTO> rooms ){

        if (rooms.isEmpty()) {
            System.out.println("Aucune room disponible.");
            return;
        }
        for (AvailableRoomDTO room : rooms) {
            System.out.println("=======================");
            System.out.println("roomNumber : " + room.getRoomNumber());
            System.out.println("capacity : " + room.getCapacity());
            System.out.println("price : " + room.getPrice());
            System.out.println("type : " + room.getType());
            System.out.println("=======================");
        }
    }

    public void updateStatusAvailable(String roomNumber) throws RoomNotFoundException,RoomUnavailableException{
        Room room = this.findRoom(roomNumber);
        if(room.getStatus() == RoomStatus.AVAILABLE){
            throw new RoomUnavailableException("Chombre déja en AVAILABLE");
        }
        this.roomRepo.updateStatus(room , RoomStatus.AVAILABLE );
    }

    public void updateStatusMAINTENANCE(String roomNumber) throws  RoomNotFoundException,RoomUnavailableException{
        Room room = this.findRoom(roomNumber);
        if(room.getStatus() == RoomStatus.MAINTENANCE){
            throw new RoomUnavailableException("Chombre déja en MAINTENANCE");
        }
        this.roomRepo.updateStatus(room , RoomStatus.MAINTENANCE );
    }

    public void afficherRooms(List<Room> rooms){
        if(rooms.isEmpty()){
            System.out.println("Aucune room ");
            return;
        }
        for (Room room : rooms){
            System.out.println("=======================");
            System.out.println("roomNumber : " + room.getRoomNumber());
            System.out.println("capacity : " + room.getCapacity());
            System.out.println("price : " + room.getPrice());
            System.out.println("type : " + room.getType());
            System.out.println("status : " + room.getStatus());
            System.out.println("=======================");
        }
    }

}
