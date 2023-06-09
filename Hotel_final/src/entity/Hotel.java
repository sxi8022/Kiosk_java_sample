package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

// 호텔
public class Hotel {
    private float asset; // 보유자산

    private LinkedHashMap<Room, Boolean> rooms; //객실

    private HashMap<String, Reservation> reservations; // 호텔 예약  목록

    private HashMap<String, Float> walletMap; // 기존고객의 소지금 목록

    private HashMap<String, HashMap<String, Reservation>> reservationMap; // 기존 고객의 예약 목록

    //초기화
    public Hotel() {
        asset = 0f;
        rooms = initRooms();
        reservations = new HashMap<>();
        walletMap = new HashMap<>();
        reservationMap = new HashMap<>();
    }

    // 보유객실 초기설정
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

    // 예약 가능한 객실 조회
    public ArrayList<Room> displayAllRooms() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Room> roomList = new ArrayList<>();

        sb.append("\n--------------------------[예약하기]-------------------------\n");
        rooms.keySet().stream().filter(room -> !rooms.get(room)).forEach(
                room -> {
                    roomList.add(room);
                    // sb.append(roomList.size() +". " + room.toString() + "\n");
                    sb.append(String.format("%,2d. %s\n", roomList.size(), room.toString()));
                }
        );
        sb.append("-----------------------------------------------------------\n");

        System.out.print(sb);
        return roomList;
    }

    // 예약 목록 조회
    public void displayAllReservations() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--------------------------------------------------------[예약 목록]------------------------------------------------------\n");
        for (String key : reservations.keySet()) {
            sb.append(reservations.get(key).toString()+"\n");
        }
        sb.append("------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println(sb);
    }

    // 객실 예약하기
    public void bookRoom(String uuid, Room room, Reservation reservation) {
        rooms.put(room, true);      // 객실의 상태를 '사용중(true)'로 변경
        asset += room.getPrice();   // 호텔 보유 자산을 예약된 방 가격만큼 증가시킴
        reservations.put(uuid, reservation);    // 예약 목록에 예약 정보 추가하기
    }

    // 객실 예약 취소
    public void cancelRoom(String uuid, Room room) {
        rooms.put(room, false);     // 객실의 상태를 '사용 안함(false)'로 변경
        asset -= room.getPrice();   // 호텔 보유 자산을 예약된 방 가격만큼 감소시킴
        reservations.remove(uuid);  // 예약 목록에서 예약 정보 제거하기
    }

    // 고객이 기존에 가입한 적 있는지 조회
    public boolean hasHistory(String key) {
        return reservationMap.containsKey(key);
    }

    // 기존 고객의 소지금 불러오기
    public float getCustomerWallet(String key){
        return walletMap.get(key);
    }

    // 기존 고객의 남은 소지금 저장하기
    public void putCustomerWallet(String key, float value){
        walletMap.put(key, value);
    }

    // 기존 고객의 예약 내역 불러오기
    public HashMap<String, Reservation> getCustomerReservations(String key) {
        return reservationMap.get(key);
    }

    // 기존 고객의 예약 내역 저장하기
    public void setCustomerReservations(String key, HashMap<String, Reservation> value) {
        reservationMap.put(key, value);
    }

}
