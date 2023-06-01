
// 실행 클래스 Main
public class Main {

    public static Kiosk T; // 메인메소드 함수

    public static void main(String[] args) {
        T = new Kiosk();
        T.initMenu();  // 메뉴 데이터 세팅
        T.displayMenu(); //초기 메인 메뉴 표시
        T.selectMenu(); // 메뉴 선택
    }
}
