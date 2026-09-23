package dto;

import model.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservationDTO {
        private String code;
        private String roomNumber;
        private LocalDate checkIn;
        private LocalDate checkOut;
        private int guests;
        private int numberNight;
        private BigDecimal totalPrice;
        private ReservationStatus status;
        private LocalDate createdAt;

    public ReservationDTO(String code , String roomNumber ,LocalDate checkIn , LocalDate checkout
                , int guests ,int numberNight, BigDecimal totalPrice , ReservationStatus status , LocalDate createdAt)
        {
            this.code = code;
            this.roomNumber = roomNumber;
            this.checkIn = checkIn;
            this.checkOut = checkout;
            this.guests = guests;
            this.numberNight = numberNight;
            this.totalPrice = totalPrice;
            this.status = status;
            this.createdAt = createdAt;
        }

        public String getCode(){
            return this.code;
        }

         public void setCode(String code){
            this.code = code;
        }

        public String getRoomNumber(){
            return  this.roomNumber;
        }

        public void setRoomNumber(String roomNumber){
            this.roomNumber = roomNumber;
        }

        public LocalDate getCheckIn(){
            return this.checkIn;
        }

        public  void setCheckIn(LocalDate checkIn){
            this.checkIn = checkIn;
        }

        public LocalDate getCheckOut(){
            return this.checkOut;
        }

        public  void setCheckOut(LocalDate checkOut){
            this.checkOut = checkOut;
        }

        public int getGuests(){
            return this.guests;
        }

        public void setGuests(){
            this.guests = guests;
        }

        public int getNumberNight() {
            return numberNight;
        }

        public void setNumberNight(int numberNight) {
            this.numberNight = numberNight;
        }

    public BigDecimal getTotalPrice(){
            return this.totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice){
            this.totalPrice = totalPrice;
        }

        public ReservationStatus getStatus(){
            return this.status;
        }

        public void setStatus(ReservationStatus status){
             this.status = status;
        }

        public LocalDate getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDate createdAt) {
            this.createdAt = createdAt;
        }

}
