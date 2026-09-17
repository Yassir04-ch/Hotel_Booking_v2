package model;

import model.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Reservation {

    private UUID id;
    private String code;
    private UUID userId;
    private Room room;

    private LocalDate checkIn;
    private LocalDate checkOut;

    private int guests;
    private BigDecimal totalPrice;

    private ReservationStatus status;
    private LocalDate createdAt;

    public Reservation(String code, UUID userId, Room room, LocalDate checkIn, LocalDate checkOut, int guests, BigDecimal totalPrice, ReservationStatus status, LocalDate createdAt) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.userId = userId;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guests = guests;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public UUID getUserId() {
        return userId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room){
        this.room = room;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public int getGuests() {
        return guests;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}