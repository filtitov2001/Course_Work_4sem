package ru.mirea.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cake_name")
    private String cakeName;

    @Column(name = "cream_name")
    private String creamName;

    @Column(name = "filler_name")
    private String fillerName;

    @Column(name = "date")
    private String date;

    @Column(name = "comment", length = 1023)
    private String comment;

    @ManyToOne
    private Customer customer;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cakeName='" + cakeName + '\'' +
                ", creamName='" + creamName + '\'' +
                ", fillerName='" + fillerName + '\'' +
                ", date='" + date + '\'' +
                ", customer=" + customer +
                '}';
    }
}
