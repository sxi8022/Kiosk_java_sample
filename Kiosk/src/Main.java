
// 실행 클래스 Main
public class Main {
/*
* 프로젝트 설명
* model 폴더
* 메인메뉴(Menu), 주문종류(OrderType),
* 상품(Product), 주문(OrderProduct), 구매(Purchase) 5가지 객체 존재
* util 폴더
* DuplicationUtil : arrayList<T>  배열의 중복 체크 하는 소스코드 가 담긴 클래스
* Kiosk  실행에 필요한 메소드를 담은 클래스
* Main 프로그램 시작을 위한 클래스
*
* */


    public static Kiosk T; // 메인메소드 함수

    public static void main(String[] args) {
        Runnable run  = () -> {
            T = Kiosk.getInstance();
            T.initMenu();  // 메뉴 데이터 세팅
            while(true) {
                T.displayMenu(); //초기 메인 메뉴 표시
                T.selectMenu(); // 메뉴 선택
            }
        };
        Thread thread = new Thread(run);
        thread.start();

    }
}
