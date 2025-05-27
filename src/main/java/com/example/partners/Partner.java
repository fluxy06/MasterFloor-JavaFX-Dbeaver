package com.example.partners;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Partner {
    private String name_company;
    private String type;
    private String name_director;
    private String email;
    private String phone_number;
    private long rate;
    private String adres;
    private long inn;
    private double discount;
    DbHandler dbHandler = new DbHandler();

    public Partner(String name_company, String type, String name_director, String email,
                   String phone_number, long rate, String adres, long inn, double discount) throws SQLException {
        try {
            this.name_company = name_company;
            this.type = type;
            this.name_director = name_director;
            this.email = email;
            this.phone_number = phone_number;
            this.rate = rate;
            this.adres = adres;
            this.inn = inn;
            this.discount = discount;
        }catch (Exception e) {
            System.out.println(e.getMessage() + "\nОшибка при попытке создания класса на основании данных из БД");
        }
    }

    public String getName_company() {
        return name_company;
    }

    public String getType() {
        return type;
    }

    public String getName_director() {
        return name_director;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public long getRate() {
        return rate;
    }
    public long getInn() {
        return inn;
    }
    public String getAdres() {
        return adres;
    }

    public void setName_company(String name_company) {
        this.name_company = name_company;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName_director(String name_director) {
        this.name_director = name_director;
    }
    public void setInn(long inn) {
        this.inn = inn;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    @Override
    public String toString() {
        return String.format(
                "%s\n%s\n%s\n%s\nИНН: %d\nРейтинг: %d\n%s \"%s\"\nСкидка: %.1f%%",
                name_director, email, phone_number, adres,
                inn, rate, type, name_company, discount // Используйте поле discount
        );
    }
}
