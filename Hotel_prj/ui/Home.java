package ui;

import entity.*;

import java.util.*;
import java.util.regex.Pattern;

public class Home {
    private Hotel hotel;
    private Scanner sc;


    public Home() {
        hotel = new Hotel();
        sc = new Scanner(System.in);
    }

    public void start() {
        // Master , Customer 분기문
        Master master = new Master(hotel);
        Client client = new Client(hotel);
        while(true) {
            int loginOption = displayLoginOptions();
            switch (loginOption) {
                case 1 :
                    master.init();
                    break;
                case 2 :
                    client.init();
                    break;
                case 3 :
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("올바른 번호를입력해주세요.");
                    System.exit(-1);
                    break;
            }
        }
    }


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



}