package dto;

import model.enums.RoomType;

import java.math.BigDecimal;

public class AvailableRoomDTO {
    private String roomNumber;
    private RoomType type;
    private int capacity ;
    private BigDecimal price;

    public AvailableRoomDTO(String roomNumber , RoomType type , int capacity , BigDecimal price){
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.type = type;
        this.price = price;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
