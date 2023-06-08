package ui;

import entity.Customer;
import entity.Hotel;
import entity.Room;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

// 관리자
public class Master {
    private Hotel hotel;

    private Scanner sc;
    private List<Customer> customers; // 고객 목록

    public Master(Hotel hotel) {
        this.hotel = hotel;
        sc = new Scanner(System.in);
    }
    public void init() {
        boolean admin = true;
        while (admin) {
            int option = displayAdminOptions();
            switch (option) {
                // 예산조회
                case 1:
                    System.out.println("호텔의 예산은" + hotel.getAsset() + "입니다.");
                    break;

                // 예약 목록 조회
                case 2:
                    hotel.displayAllReservations();
                    break;

                // 객실 추가
                case 3:
                    System.out.println("-----------------------");
                    boolean haveId = false;
                    System.out.println("추가할 룸 id를 입력");
                    String newRoomId = sc.nextLine();
                    LinkedHashMap<Room, Boolean> rooms = (LinkedHashMap<Room, Boolean>) hotel.getRooms();
                    for (Room room : rooms.keySet()) {
                        if (room.getRoomID().equals(newRoomId)) {
                            System.out.println("동일한 룸번호가 존재합니다. 다시입력하세요.");
                            haveId = true;
                        }
                    }
                    if (haveId) {
                        continue;
                    }


                    System.out.println("추가할 룸 크기를 입력(예시 : Single,Double,Family,Premium,Superior)");
                    String newSize = sc.nextLine();
                    System.out.println("추가할 룸 가격을 입력");
                    float newPrice = sc.nextFloat();

                    Room newRoom = new Room(newRoomId, newSize, newPrice);
                    hotel.addRoom(newRoom);
                    System.out.println("룸 추가 완료");
                    System.out.println("-----------------------");
                    break;

                // 나가기
                case 4:
                    admin = false;
                    break;
            }
        }
    }


    // 관리자 모드 메뉴
    public int displayAdminOptions(){
        System.out.println("관리자모드");
        System.out.println("1.  예산 조회 \t2. 예약 목록 조회\t3. 객실 추가\t4. 나가기");

        int choice;
        System.out.print("번호를 입력하세요 : ");
        while((choice = sc.nextInt()) > 4){
            System.out.println("유효하지 않은 번호입니다.");
            System.out.print("번호를 입력하세요 : ");
        }
        sc.nextLine();
        return choice;
    }
}
