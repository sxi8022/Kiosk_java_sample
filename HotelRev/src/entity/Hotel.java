package entity;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Hotel {
    private float asset;        // 보유 자산
    private ArrayList<Room> rooms;   // 객실
    // HashMap<Room, boolean> rooms;
    private HashMap<String, Reservation> reservations; // 호텔 예약 목록 key = uuid, value = Reservation

    public Hotel() {
        asset = 0f;
        rooms = initRooms();
        reservations = new HashMap<>();
    }

    // 호텔 보유 객실 초기화
    private ArrayList<Room> initRooms() {
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room("1", "Single", 100f));
        //테스트코드
        // rooms.get(0).setStatus(true);
        rooms.add(new Room("2", "Single", 100f));
        rooms.add(new Room("3", "Single", 100f));
        rooms.add(new Room("4", "Double", 180f));
        rooms.add(new Room("5", "Double", 180f));
        rooms.add(new Room("6", "Double", 180f));
        rooms.add(new Room("7", "Family", 320f));
        rooms.add(new Room("8", "Family", 320f));
        rooms.add(new Room("9", "Family", 320f));
        rooms.add(new Room("10", "Premium", 750f));
        rooms.add(new Room("11", "Premium", 750f));
        rooms.add(new Room("12", "Superior", 1250f));
        return rooms;
    }

    // 보유 자산 조회
    public float getAsset() {
        return asset;
    }

    // 보유 자산 추가
    public void addAsset(float asset) {
        this.asset += asset;
    }

    // 보유 자산 감소
    public void subAsset(float asset) {
        this.asset -= asset;
    }

    // 보유 객실 조회
    public List<Room> getRooms() {
        return rooms;
    }


    // 보유 객실 수정
    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    // 보유 객실 추가
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // 예약 가능한 객실 조회
    public void viewAllRooms() {
        // for문 , iterator으로 ArrayList<Room> 하나씩 불러올 때
        // Room의 status 필드가 true이면 넘어가고, false이면 출력한다.
        for (int i = 0; i < rooms.size(); i++) {
            if (!rooms.get(i).isStatus())
            System.out.println(
                    String.format("Room번호: %s | Room크기: %s | 가격: %s ", rooms.get(i).getRoom_id(), rooms.get(i).getSize(), rooms.get(i).getPrice())
            );
        }
    }

    // 예약 목록 조회
    public void viewMyReservations(String name, String phoneNum) {
        for (String key : reservations.keySet()) {
            Reservation nowRev = reservations.get(key);
            if (nowRev.getName().equals(name) && nowRev.getPhoneNum().equals(phoneNum)) {
                System.out.println(
                        String.format(" 예약번호 : %s | Room번호: %s | 예약자: %s | 연락처: %s | 예약일시: %s "
                                , key
                                , nowRev.getRoom().getRoom_id()
                                , nowRev.getName()
                                , nowRev.getPhoneNum()
                                , nowRev.getDate())
                );
            }
            // reservations.get(key).getReservedInfo();
        }
    }

    // 예약 목록 조회
    public void viewAllReservations() {
        for (String key : reservations.keySet()) {
            reservations.get(key).getReservedInfo();
        }
    }

    public HashMap<String, Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(HashMap<String, Reservation> reservations) {
        this.reservations = reservations;
    }
}
