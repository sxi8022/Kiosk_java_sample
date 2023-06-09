package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
public class Hotel {
    private float asset;        // 보유 자산
    private LinkedHashMap<Room, Boolean> rooms; // 객실
    private HashMap<String, Reservation> reservations; // 호텔 예약 목록 key = uuid, value = Reservation

    public Hotel() {
        asset = 0f;
        rooms = initRooms();
        reservations = new HashMap<>();
    }

    // 호텔 보유 객실 초기화
    private LinkedHashMap<Room, Boolean> initRooms() {
        LinkedHashMap<Room, Boolean> rooms = new LinkedHashMap<>();
        rooms.put(new Room("1", "Single", 100f), false);
        rooms.put(new Room("2", "Single", 100f), false);
        rooms.put(new Room("3", "Single", 100f), false);
        rooms.put(new Room("4", "Double", 180f), false);
        rooms.put(new Room("5", "Double", 180f), false);
        rooms.put(new Room("6", "Double", 180f), false);
        rooms.put(new Room("7", "Double", 180f), false);
        rooms.put(new Room("8", "Family", 320f), false);
        rooms.put(new Room("9", "Family", 320f), false);
        rooms.put(new Room("10", "Family", 320f), false);
        rooms.put(new Room("11", "Family", 320f), false);
        rooms.put(new Room("12", "Premium", 750f), false);
        rooms.put(new Room("13", "Premium", 750f), false);
        rooms.put(new Room("14", "Premium", 750f), false);
        rooms.put(new Room("15", "Superior", 1250f), false);
        return rooms;
    }

    // 보유 자산 조회
    public float getAsset() {
        return asset;
    }

    // 보유 객실 조회
    public HashMap<Room, Boolean> getRooms() {
        return rooms;
    }

    // 보유 객실 추가
    public void addRoom(Room room) {
        rooms.put(room, false);
    }

    // 예약 가능한 객실 조회 (구현)
    public ArrayList<Room> displayAllRooms() {
        // 사용 중이지 않은 room들을 담을 ArrayList<Room>을 새로 정의한다.
        ArrayList<Room> roomList = new ArrayList<>();

        // rooms (Room 타입의 객실 Map)를 keySet()을 통한 for문을 통해 room을 하나씩 불러오고
        // room을 키로 rooms의 value에 접근해 해당 객실이 사용중('true')면 넘어가고, 그렇지 않으면('false') 출력한다.
        // 사용 중이지 않은 room은 ArrayList<Room>에 추가하고 for문이 끝나면 return 한다.
        for (Room room : rooms.keySet()) {
            if (!rooms.get(room)) {
                System.out.println(
                        String.format("Room번호: %s | Room크기: %s | 가격: %s "
                                , room.getRoomID()
                                , room.getSize()
                                , room.getPrice())
                );
                roomList.add(room);
            }
        }
        return roomList;
    }

    // 호텔 예약추가
    public void addReservation(String uuid, Reservation reservation){
        reservations.put(uuid, reservation);
    }

    // 1. 예약하기 -- 객실 예약 확정 (구현)
    public void bookRoom(Room room) {
        // 객실의 상태를 '사용중(true)'로 변경
        rooms.replace(room, true);
        // 호텔 보유 자산을 예약된 방 가격만큼 증가시킴
        asset += room.getPrice();
    }

    // 3. 예약 취소하기 -- 객실 예약 취소 (구현)
    public void cancelRoom(Room room) {
        // 객실의 상태를 '사용 안함(false)'로 변경
        rooms.replace(room, false);
        // 호텔 보유 자산을 예약된 방 가격만큼 감소시킴
        asset -= room.getPrice();
    }

    // 예약 목록 조회
    public void displayAllReservations() {
        for (String key : reservations.keySet()) {
            reservations.get(key).toString();
        }
    }
}
