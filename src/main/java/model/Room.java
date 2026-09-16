package model;

import model.enums.RoomStatus;
import model.enums.RoomType;

import java.math.BigDecimal;

public class Room {

    private String roomNumber;
    private RoomType type;
    private int capacity;
    private BigDecimal price;
    private RoomStatus status;

    public Room(String roomNumber, RoomType type, int capacity, BigDecimal price, RoomStatus status) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.capacity = capacity;
        this.price = price;
        this.status = status;
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

    public RoomStatus getStatus() {
        return status;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }
}