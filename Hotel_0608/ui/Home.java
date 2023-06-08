package ui;

import entity.*;

import java.util.*;
import java.util.regex.Pattern;

public class Home {
    private Hotel hotel;
    private List<Customer> customers; // 고객 목록
    // <-- HashMap<Customer, price>  customers  => key = Customer, value = price
    // 이름, 전화번호 입력 -> customers 안에 있는지 확인해서 있으면 price랑 reservation까지 오는것.
    private Scanner sc;

    public Home() {
        hotel = new Hotel();
        customers = new ArrayList<>();
        sc = new Scanner(System.in);
    }

    public void start() {
        // Master , Customer 분기문
        System.out.println("-----------------------");
        while (true) {
            int loginOption = displayLoginOptions();
            switch(loginOption) {
                // 관리자
                case 1:
                    boolean admin = true;
                    while(admin) {
                        int choice = displayAdminOptions();
                        switch(choice){
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
                    break;

                // 로그인
                case 2:
                    Customer customer = registerCustomer();
                    boolean login = true;

                    System.out.println("-----------------------");

                    while(login) {
                        int choice = displayOptions();
                        switch(choice){
                            // 예약하기
                            case 1:
                                makeReservation(customer);
                                break;

                            // 예약 확인
                            case 2:
                                checkReservation(customer);
                                break;

                            // 예약 취소
                            case 3:
                                cancelReservation(customer);
                                break;

                            // 나가기
                            case 4:
                                login = false;
                                break;
                        }
                    }
                    System.out.println("-----------------------");
                    break;
                // 나가기
                case 3:
                    admin = false;
                    break;
            }
        }


    }

    public Customer registerCustomer(){
        System.out.println("[Hotel 로그인 화면]");
        String name = askName();            // 이름 묻기
        String phoneNum = askPhoneNum();    // 전화번호 묻기
        float wallet = askWallet();         // 소지금 묻기
        sc.nextLine();

        Customer customer = new Customer(name, phoneNum, wallet);
        customers.add(customer);
        return customer;
    }

    public String askName(){
        System.out.print("이름: ");
        return sc.nextLine();
    }

    public String askPhoneNum() {
        System.out.print("전화번호 (ex. 012-3456-7890): ");
        String phoneNum = sc.nextLine();

        String regex_phoneNum = "^01([0|1|6|7|8|9])-?([0-9]{4})-?([0-9]{4})$";
        while(!Pattern.matches(regex_phoneNum, phoneNum)){
            System.out.println("전화번호의 형식이 일치하지 않습니다. 다시 입력해주세요.");
            System.out.print("전화번호 (ex. 012-3456-7890): ");
            phoneNum = sc.nextLine();
        }
        return phoneNum;
    }

    public float askWallet() {
        System.out.print("얼마를 소지하고 계신가요?: ");
        return sc.nextFloat();
    }

    // 로그인을 관리자 , 고객으로 할 것인지 결정
    public int displayLoginOptions(){
        System.out.println("Hotel에 오신걸 환영합니다.");
        System.out.println("1. 관리자 로그인 \t2. 고객 로그인 \t3. 나가기");

        int choice;
        System.out.print("번호를 입력하세요 : ");
        while((choice = sc.nextInt()) > 3){
            System.out.println("유효하지 않은 번호입니다.");
            System.out.print("번호를 입력하세요 : ");
        }
        sc.nextLine();
        return choice;
    }

    // 로그인 사용자 메뉴 
    public int displayOptions(){
        System.out.println("Hotel에 오신걸 환영합니다.");
        System.out.println("1. 예약하기 \t2. 예약 확인\t3. 예약 취소\t4. 나가기");

        int choice;
        System.out.print("번호를 입력하세요 : ");
        while((choice = sc.nextInt()) > 4){
            System.out.println("유효하지 않은 번호입니다.");
            System.out.print("번호를 입력하세요 : ");
        }
        sc.nextLine();
        return choice;
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

    // 1. 예약하기 (구현)
    public void makeReservation(Customer customer) {
        // Hotel이 보유한 객실 출력 -- 이미 예약된 객실은 제외 (구현)
        ArrayList<Room> rooms = hotel.displayAllRooms();

        // 객실 선택 -- 객실 번호를 제대로 입력할 때까지 반복
        int choice = 0;
        System.out.print("객실 번호를 선택해주세요 (취소: 0): ");
        while((choice = sc.nextInt()) > rooms.size()){
            System.out.println("해당 객실은 존재하지 않습니다. 다시 입력해주세요");
            System.out.print("객실 번호를 선택해주세요 (취소: 0): ");
        }

        if(choice == 0) return;

        // 선택한 객실 불러오기
        Room room = rooms.get(choice-1);

        // 객실 가격과 고객 소지금을 비교하여 고객 소지금이 더 적다면 실패 메시지 띄우고 return
        if(customer.getWallet() < room.getPrice()){
            System.out.println("소지 금액이 객실 대여료보다 낮아 예약이 불가능합니다.");
        } else {
            // 고객 소지금이 더 많다면 예약 진행
            bookRoom(customer, room);
        }
    }

    // 1-1. 예약 완료하기 (구현)
    public void bookRoom(Customer customer, Room room) {
        // 호텔: 해당 객실을 예약 상태로 바꾸고 객실 가격만큼을 호텔의 보유자산에 추가 (구현)
        hotel.bookRoom(room);


        // 고객: 고객 소지금에서 객실 가격을 뺄셈 (구현)
        // customer의 wallet을 get으로 불러와 room의 가격으로 빼고 set으로 wallet을 수정

        // 예약 번호(id <- uuid) 생성
        String uuid = UUID.randomUUID().toString();

        // Reservation 인스턴스 생성 (구현) -- new Reservation()
        // Hotel 예약 목록에 추가 (구현)    -- hotel.addReservation(...)
        // Customer 예약 목록에 추가 (구현) -- customer.addReservation(...)
        Reservation reservation = new Reservation(room, customer.getName(), customer.getPhoneNum());
        customer.addReservation(uuid,reservation);
        // v)예약 완료 메시지 출력
        System.out.println("-----------------------");
        System.out.println("예약이 완료되었습니다!");
        System.out.println("-----------------------");
    }

    // 2. 예약 확인하기 (구현)
    public void checkReservation(Customer customer) {
        // 고객의 예약 목록 출력하기 (구현)
        customer.displayReservations();

        // 고객의 예약 목록(HashMap<String, Reservation>) 가져오기
        HashMap<String, Reservation> reservations = customer.getReservations();

        // 예약 번호(id) 입력 받기
        String uuid = getUUID(reservations);
        if(uuid.equals("0")) return;

        // 고객의 예약 목록(reservations)에서 해당 예약 정보(Reservation)를 불러오기
        Reservation reservation = reservations.get(uuid);

        // 불러온 예약 정보 출력하기
        printReservation(reservation);
    }

    // 3. 예약 취소하기 (구현)
    public void cancelReservation(Customer customer) {
        // 고객의 예약 목록 출력하기 (구현)
        customer.displayReservations();

        // 고객의 예약 목록(HashMap<String, Reservation>) 불러오기
        HashMap<String, Reservation> reservations = customer.getReservations();

        // 예약 번호(id) 입력 받기
        String uuid = getUUID(reservations);
        if(uuid.equals("0")) return;

        // 고객의 예약 목록(reservations)에서 해당 예약 정보(Reservation)를 불러오기
        Reservation reservation = reservations.get(uuid);

        // 불러온 예약 정보 출력하기
        printReservation(reservation);

        // 예약 정보(reservation)에서 Room 가져오기(getRoom)
        Room room = reservation.getRoom();

        // 예약 목록 취소 및 가격 반환 (구현)
        cancelRoom(customer, room, uuid);

        // 고객: 해당 예약 정보를 예약 목록에서 제거하기
        reservations.remove(uuid);
    }

    public String getUUID(HashMap<String, Reservation> reservations) {
        String uuid = "";

        System.out.print("확인할 예약번호를 입력하세요 (취소: 0): ");
        while(!reservations.containsKey(uuid = sc.nextLine())){
            if(uuid.equals("0")) break;
            System.out.println("일치하는 예약번호가 존재하지 않습니다. 확인 후 다시 입력해주세요.");
            System.out.print("확인할 예약번호를 입력하세요 (취소: 0): ");
        }

        return uuid;
    }

    public void printReservation(Reservation reservation){
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------");
        System.out.println(reservation.toString());
        System.out.println("------------------------------------------------------------------------------------------------------------------------\n");
    }

    public void cancelRoom(Customer customer, Room room, String uuid) {
        // 호텔: 해당 객실을 예약 가능으로 바꾸고, 호텔의 보유자산을 객실 가격 만큼 빼기 (구현)
        hotel.cancelRoom(room);
        // 호텔: 해당 예약 정보를 예약 목록에서 제거하기 (구현)
        hotel.removeReservation(uuid);

        // 고객: 고객의 소지금에 객실 가격만큼 다시 추가한다 (구현)
        customer.setWallet(customer.getWallet() + room.getPrice());
    }
}