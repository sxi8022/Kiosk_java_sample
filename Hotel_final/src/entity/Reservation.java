package entity;

import java.util.Calendar;

// 예약
public class Reservation {
    private Room room;
    private String name;
    private String phoneNum;
    private String date;

    public Reservation(Room room, String name, String phoneNum) {
        this.room = room;
        this.name = name;
        this.phoneNum = phoneNum;
        date = dateFormat(Calendar.getInstance());
    }

    public Room getRoom() {
        return room;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getDate() {
        return date;
    }

    // 작성일 형식 반환 함수
    public String dateFormat(Calendar today) {
        return today.get(Calendar.YEAR)+"-" + (today.get(Calendar.MONTH)+1)+"-"
                + today.get(Calendar.DATE)+"T" + today.get(Calendar.HOUR)+":"
                + today.get(Calendar.MINUTE)+":" + today.get(Calendar.SECOND)+"Z";
    }

    // 예약 정보 반환하는 함수
    public String toString() {
        return String.format("Room_id: %-2s | Room_size: %-9s | Room_price: %7s | name: %s | phoneNum: %s | date: %s",
                room.getRoomID(), room.getSize(), Float.toString(room.getPrice()), name, phoneNum, date);
    }
}
