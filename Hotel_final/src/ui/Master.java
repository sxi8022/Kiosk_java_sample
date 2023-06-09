package ui;

import entity.Hotel;
import entity.Room;

import java.util.Scanner;

// 관리자
public class Master {
    private Hotel hotel; 
    private Scanner sc;

    // 생성자
    public Master(Hotel hotel) {
        this.hotel = hotel;
        sc = new Scanner(System.in);
    }

    public void init() {
        System.out.println("------------------------------");
        boolean login = true;
        while (login) {
            displayOptions();
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case  1:
                    displayAsset();
                    break;
                case 2:
                    hotel.displayAllReservations();
                    break;
                case 3:
                    addRoom();
                    break;
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
    }

    private void displayOptions() {
        System.out.println("Hotel에 오신걸 환영합니다. [관리자 모드]");
        System.out.println("1. 예산 조회 \t 2. 예약 조회\t 3. 객실 추가\t 4. 나가기");

        System.out.print("번호를 입력하세요 : ");
    }

    public void displayAsset(){
        System.out.println("\n-------------------------------");
        System.out.printf("Hotel의 총 예산은 %.0f입니다.\n", hotel.getAsset());
        System.out.println("뒤로 가기 위해 아무 키나 눌러주세요.");
        System.out.println("-------------------------------");
        sc.nextLine();
    }

    // 객실 추가
    public void addRoom() {
        System.out.println("-------------------------[신규 객실 등록]--------------------------");
        System.out.println("객실 크기 : Single | Double | Triple | Family | Premium | Superior");
        System.out.print("객실의 크기를 지정해주세요: ");
        String size = sc.nextLine();

        System.out.println("객실 가격 : 최소 100 - 최대 10,000");
        System.out.print("객실의 가격을 지정해주세요: ");
        float price;
        while((price = sc.nextFloat()) < 100 || price > 10000) {
            System.out.println("객실 가격 규정에 어긋납니다!");
            System.out.print("객실 가격을 지정해주세요: ");
        }
        sc.nextLine();

        System.out.print("객실 이름을 지정해주세요: ");
        String name = sc.nextLine();
        System.out.println("-----------------------------------------------------------------");

        Room room = new Room(name, size, price);
        System.out.printf("%s \n", room.toString());
        System.out.println("해당 규격으로 방을 추가하시겠습니까?");
        System.out.println("1. 확인\t 2. 취소");
        if(sc.nextInt() == 1) {
            hotel.addRoom(room);
            System.out.println("-------------------");
            System.out.println("객실이 추가되었습니다!");
            System.out.println("-------------------");
        }

    }

}
