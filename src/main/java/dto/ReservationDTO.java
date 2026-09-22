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
        private BigDecimal totalPrice;
        private ReservationStatus status;

        public ReservationDTO(String code , String roomNumber ,LocalDate checkIn , LocalDate checkout
                , int guests , BigDecimal totalPrice , ReservationStatus status)
        {
            this.code = code;
            this.roomNumber = roomNumber;
            this.checkIn = checkIn;
            this.checkOut = checkout;
            this.guests = guests;
            this.totalPrice = totalPrice;
            this.status = status;
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

}
