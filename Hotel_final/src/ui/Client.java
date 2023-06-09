package ui;

import entity.Customer;
import entity.Hotel;
import entity.Reservation;
import entity.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

// 사용자
public class Client {

    private Hotel hotel;

    private Scanner sc;

    public Client(Hotel hotel) {
        this.hotel = hotel;
        sc = new Scanner(System.in);
    }

    public Customer init(Customer customer) {
        System.out.println("-----------------------");
        boolean login = true;
        while(login) {
            displayOptions(customer);

            int choice = sc.nextInt();
            sc.nextLine(); // 정수입력후에 개행해줘야함
            switch(choice){
                // 예약하기
                case 1:
                    makeReservation(customer);
                    break;

                // 예약 확인
                case 2:
                    customer.displayReservations();
                    break;

                // 예약 취소
                case 3:
                    cancelReservation(customer);
                    break;

                // 나가기
                case 4:
                    login = false;
                    break;

                default:
                    System.out.println("---------------------");
                    System.out.println("유효하지 않은 번호입니다.");
                    System.out.println("---------------------");
                    break;
            }
        }
        System.out.println("-----------------------");
        return customer;

    }

    // 예약 취소
    private void cancelReservation(Customer customer) {
        // 고객의 예약 목록(HashMap<String, Reservation>) 불러오기
        HashMap<String, Reservation> reservations = customer.getReservations();

        // 예약 번호(id) 입력 후 고객의 예약 목록에서 해당 예약 정보를 불러오기
        String uuid = getUUID(reservations);
        if(uuid.equals("0")) return;
        Reservation reservation = reservations.get(uuid);

        // 불러온 예약 정보 출력하기
        printReservation(reservation);

        // 예약 취소 확인 메시지 출력
        System.out.println("위 예약을 취소하시겠습니까?");
        System.out.println("1. 취소\t 2. 돌아가기");
        System.out.print("번호를 입력하세요 : ");
        if(sc.nextInt() == 1){
            // 예약 정보(reservation)에서 Room 가져오기(getRoom)
            Room room = reservation.getRoom();

            // 호텔: 해당 객실을 예약 가능으로 바꾸고, 호텔의 보유자산을 객실 가격 만큼 빼기, 해당 예약 정보를 예약 목록에서 제거하기
            hotel.cancelRoom(uuid, room);

            // 고객: 고객의 소지금에 객실 가격만큼 다시 추가한다
            customer.setWallet(customer.getWallet() + room.getPrice());
            // 고객: 해당 예약 정보를 예약 목록에서 제거하기
            reservations.remove(uuid);

            // 예약 취소 메시지 출력
            System.out.println("-----------------------");
            System.out.println("예약이 취소되었습니다!");
            System.out.println("-----------------------");
        } else {
            System.out.println("메인 메뉴로 돌아갑니다.");
        }
        sc.nextLine();
    }

    // 예약 출력
    public void printReservation(Reservation reservation){
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------");
        System.out.println(reservation.toString());
        System.out.println("------------------------------------------------------------------------------------------------------------------------\n");
    }

    //uuid 얻기
    public String getUUID(HashMap<String, Reservation> reservations) {
        String uuid = "";

        System.out.print("취소할 예약번호를 입력하세요 (취소: 0): ");
        while(!reservations.containsKey(uuid = sc.nextLine())){
            if(uuid.equals("0")) break;
            System.out.println("일치하는 예약번호가 존재하지 않습니다. 확인 후 다시 입력해주세요.");
            System.out.print("예약번호를 입력하세요 (취소: 0): ");
        }

        return uuid;
    }

    // 사용자 메뉴 선택
    private void displayOptions(Customer customer) {
        System.out.printf("Hotel에 오신걸 환영합니다. \t [이름: %s, 소지금: %.0f]\n", customer.getName(), customer.getWallet());
        System.out.println("1. 예약하기 \t2. 예약 확인\t3. 예약 취소\t4. 나가기");

        System.out.print("번호를 입력하세요 : ");
    }

    public void makeReservation(Customer customer) {
        // Hotel이 보유한 객실 출력 -- 이미 예약된 객실은 제외
        ArrayList<Room> rooms = hotel.displayAllRooms();

        // 객실 선택 -- 객실 번호를 제대로 입력할 때까지 반복
        int choice = 0;
        System.out.print("객실 번호를 선택해주세요 (취소: 0): ");
        while((choice = sc.nextInt()) > rooms.size()){
            System.out.println("해당 객실은 존재하지 않습니다. 다시 입력해주세요");
            System.out.print("객실 번호를 선택해주세요 (취소: 0): ");
        }

        if(choice == 0) return;

        // 객실 가격과 고객 소지금을 비교하여 고객 소지금이 더 적다면 실패 메시지 띄우고 return
        Room room = rooms.get(choice-1);
        if(customer.getWallet() < room.getPrice()){
            System.out.println("--------------------------------------------");
            System.out.println("소지 금액이 객실 대여료보다 낮아 예약이 불가능합니다.");
            System.out.println("--------------------------------------------");
        } else {
            // 고객 소지금이 더 많다면 예약 진행
            bookRoom(customer, room);
        }
    }

    //객실 예약
    public void bookRoom(Customer customer, Room room) {
        // 예약 번호(id <- uuid) 생성
        String uuid = UUID.randomUUID().toString();

        // Reservation을 생성 후 Hotel과 Customer의 예약 목록에 각각 추가
        Reservation reservation = new Reservation(room, customer.getName(), customer.getPhoneNum());

        // 호텔: 해당 객실을 예약 상태로 바꾸고 객실 가격만큼을 호텔의 보유자산에 추가, 예약 목록에 예약 정보 추가하기
        hotel.bookRoom(uuid, room, reservation);

        // 고객: 고객 소지금에서 객실 가격을 뺄셈
        customer.setWallet(customer.getWallet() - room.getPrice());

        // 고객: 예약 목록에 예약 정보 추가하기
        customer.addReservation(uuid, reservation);

        // 예약 완료 메시지 출력
        System.out.println("-----------------------");
        System.out.println("예약이 완료되었습니다!");
        System.out.println("-----------------------");
    }

}
