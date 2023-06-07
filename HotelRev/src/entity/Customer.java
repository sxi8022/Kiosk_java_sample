package entity;

import java.util.HashMap;

public class Customer {
    private String name;        // 고객 명
    private String phoneNum;    // 고객 전화번호
    private float wallet;       // 고객 보유 소지금
    private HashMap<String, Reservation> reservations; // 고객 예약 목록 key = uuid, value = Reservation

    public Customer(String name, String phoneNum, float wallet) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.wallet = wallet;
        this.reservations = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

    public HashMap<String, Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(HashMap<String, Reservation> reservations) {
        this.reservations = reservations;
    }
}
