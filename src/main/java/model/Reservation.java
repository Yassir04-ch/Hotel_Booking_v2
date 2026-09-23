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
    private int numberOfNights;
    private BigDecimal totalPrice;
    private ReservationStatus status;
    private LocalDate createdAt;

    public Reservation(){

    }

    public Reservation(String code, UUID userId, Room room, LocalDate checkIn, LocalDate checkOut, int guests, int numberOfNights, BigDecimal totalPrice, ReservationStatus status) {
        this.code = code;
        this.userId = userId;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guests = guests;
        this.numberOfNights = numberOfNights;
        this.totalPrice = totalPrice;
        this.status = status;
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

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
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

    public int getNumberOfNights() {
        return numberOfNights;
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

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

}