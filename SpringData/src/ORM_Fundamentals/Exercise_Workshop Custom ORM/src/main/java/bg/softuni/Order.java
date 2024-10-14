package bg.softuni;

import orm.Annotations.Colum;
import orm.Annotations.Entity;
import orm.Annotations.Id;

import java.time.LocalDate;



    @Entity(name = "orders")
    public class Order {


        @Id
        @Colum(name = "id")
        private long id;

        @Colum(name = "number")
        private String number;

        @Colum(name = "date")
        private LocalDate date;

        // empty constructor
        public Order() {}

        //
        public Order(String number, LocalDate date) {
            this.number = number;
            this.date = date;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }
    }


