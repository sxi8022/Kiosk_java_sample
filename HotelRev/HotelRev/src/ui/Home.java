package ui;

import entity.*;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.System.*;

public class Home {
    private Hotel hotel;
    private List<Customer> customers; // 고객 목록
    private Scanner sc;

    public Home() {
        hotel = new Hotel();
        customers = new ArrayList<>();
        sc = new Scanner(in);
    }

    public void start() {
        while(true){
            Customer customer = registerCustomer();

            out.println("-----------------------");
            boolean login = true;
            while(login) {
                int choice = displayOptions();
                switch(choice){
                    // 예약하기
                    case 1:
                        makeReservation();
                        break;

                    // 예약 확인
                    case 2:
                        checkReservation();
                        break;

                    // 예약 취소
                    case 3:
                        cancelReservation();
                        break;

                    // 나가기
                    case 4:
                        login = false;
                        break;
                }
            }
            out.println("-----------------------");
        }

    }

    public Customer registerCustomer(){
        out.println("[Hotel 로그인 화면]");
        String name = askName();
        String phoneNum = askPhoneNum();
        float wallet = askWallet();
        sc.nextLine();

        Customer customer = new Customer(name, phoneNum, wallet);
        customers.add(customer);
        return customer;
    }

    public String askName(){
        out.print("이름 : ");
        return sc.nextLine();
    }

    public String askPhoneNum() {
        out.print("전화번호 (ex. 012-3456-7890): ");
        String phoneNum = sc.nextLine();

        String regex_phoneNum = "^01([0|1|6|7|8|9])-?([0-9]{4})-?([0-9]{4})$";
        while(!Pattern.matches(regex_phoneNum, phoneNum)){
            out.println("전화번호의 형식이 일치하지 않습니다. 다시 입력해주세요.");
            out.print("전화번호 (ex. 012-3456-7890): ");
            phoneNum = sc.nextLine();
        }
        return phoneNum;
    }

    public float askWallet() {
        out.print("얼마를 소지하고 계신가요? :");
        return sc.nextFloat();
    }

    public int displayOptions(){
        out.println("Hotel에 오신걸 환영합니다.");
        out.println("1. 예약하기 \t2. 예약 확인\t3. 예약 취소\t4. 나가기");

        int choice;
        out.print("번호를 입력하세요 : ");
        while((choice = sc.nextInt()) > 4){
            out.println("유효하지 않은 번호입니다.");
            out.print("번호를 입력하세요 : ");
        }
        sc.nextLine();
        return choice;
    }

    public void makeReservation() {
        // Hotel이 보유한 객실 출력 -- 이미 예약된 객실은 제외
        hotel.viewAllRooms();

        // 객실 선택 -- 객실 번호를 제대로 입력할 때까지 반복
        String selectRoomId = selectRoom();

        // 객실 가격과 고객 소지금을 비교하여 고객 소지금이 더 적다면 실패 메시지 띄우고 return
        ArrayList<Room> rooms = (ArrayList) hotel.getRooms();
        Stream<Room> pstream = rooms.stream();
        Room selectRoom = pstream.filter(m -> m.getRoom_id().equals(selectRoomId) ).findAny().orElse(null);
        if (customers.get(customers.size()-1).getWallet() < selectRoom.getPrice()) {
            out.println("고객 소지금이 객실가격보다 적습니다. 시스템을 종료합니다.");
            System.exit(-1);
        }

        // 고객 소지금이 더 많다면 해당 객실을 예약 상태로 바꾸고, 고객 소지금에서 객실 가격을 빼고, 객실 가격만큼을 호텔의 보유자산에 추가
        for (int i =0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoom_id().equals(selectRoomId)) {
                Room changeRoom = rooms.get(i);
                //  해당 객실을 예약 상태로 바꾸고
                changeRoom.setStatus(true);
                rooms.set(i, changeRoom);
                hotel.setRooms(rooms);
                // 고객 소지금에서 객실 가격을 뺀다
                Customer nowCustomer = customers.get(customers.size()-1);
                nowCustomer.setWallet(nowCustomer.getWallet() - changeRoom.getPrice());
                customers.set(customers.size()-1,nowCustomer);
                // 객실 가격만큼을 호텔의 보유자산에 추가
                hotel.addAsset(changeRoom.getPrice());


                // 예약 번호(id <- uuid) 생성
                String uuid = "";
                uuid = UUID.randomUUID().toString();

                // 새로 추가된 예약
                Reservation nowRev = new Reservation(changeRoom, nowCustomer.getName(), nowCustomer.getPhoneNum());
                HashMap<String, Reservation> reservations = hotel.getReservations();
                reservations.put(uuid, nowRev);
                // Reservation을 생성 후 Hotel과 Customer의 예약 목록에 각각 추가
                hotel.setReservations(reservations);
                nowCustomer.setReservations(reservations);
                customers.set(customers.size()-1, nowCustomer);

            }
        }

        // 예약 완료 메시지 출력
        out.println("호텔 예약이 완료되었습니다.");
    }

    public void checkReservation() {
        if (customers.size() < 1) {
            out.println("예약된 건이 없습니다.");
        } else {
            // 고객의 예약 목록(List<Reservation>) 불러오기
            Customer nowCustomer = customers.get(customers.size() -1);
            hotel.viewMyReservations(nowCustomer.getName(), nowCustomer.getPhoneNum());
            // 예약 번호(id) 입력 후 예약 목록에서 해당 예약 정보를 받아오기
            out.println("예약번호를 입력해주세요.");
            String revId = sc.nextLine();
            for (String key : nowCustomer.getReservations().keySet()) {
                if (revId.equals(key)) {
                    String revInfo = nowCustomer.getReservations().get(key).getReservedInfo();
                    out.println(revInfo);
                    break;
                }
            }

        }


    }

    public void cancelReservation() {
        // 고객의 예약 목록(List<Reservation>) 불러오기

        // 예약 번호(id) 입력 후 예약 목록에서 해당 예약 정보 삭제하기

        // 객실의 상태를 예약 가능으로 바꾸고, 호텔의 보유자산을 객실 가격 만큼 빼고, 고객의 소지금에 다시 추가한다
    }

    // 객실선택
    public String selectRoom() {
        ArrayList<Room> rooms = (ArrayList<Room>) hotel.getRooms();
        String selectId = "";
        while(true) {
            boolean nextOk = false;
            out.println("객실번호를 입력해주세요.");
            selectId = sc.nextLine();
            for (int i= 0; i < rooms.size(); i++) {
                if (rooms.get(i).getRoom_id().equals(selectId)) {
                    nextOk = true;
                    break;
                }
            }
            if (nextOk) break;

        }
        return selectId;
    }



}