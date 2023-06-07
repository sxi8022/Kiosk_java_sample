package entity;

import java.util.Calendar;

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
                + today.get(Calendar.MINUTE)+":" + today.get(Calendar.SECOND);
    }

    public String getReservedInfo(){
        return String.format("룸id: %s | 방크기: %s | 가격: %s | 이름: %s | 전화번호: %s | 예약일시: %s", room.getRoom_id(), room.getSize(), room.getPrice(), name, phoneNum, date);
    }
}
