package entity;

import java.util.HashMap;

// 고객
public class Customer {
    private String name;        // 고객 명
    private String phoneNum;    // 고객 전화번호
    private float wallet;       // 고객 보유 소지금
    private HashMap<String, Reservation> reservations; // 고객 예약 목록 key = uuid, value = Reservation

    public Customer(String name, String phoneNum){
        this.name = name;
        this.phoneNum = phoneNum;
        this.wallet = 0f;
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

    public HashMap<String, Reservation> getReservations() { return reservations; }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

    public void setReservations(HashMap<String, Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(String uuid, Reservation reservation){
        reservations.put(uuid, reservation);
    }

    public void removeReservation(String uuid){
        reservations.remove(uuid);
    }

    public void displayReservations() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--------------------------------------------------------------------------[예약 목록]------------------------------------------------------------------------\n");
        reservations.entrySet().stream().forEach(entry -> {
            sb.append(String.format("예약 번호: %s | %s\n", entry.getKey(), entry.getValue().toString()));
        });
        sb.append("------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n");
        System.out.print(sb);
    }

}
