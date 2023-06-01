import model.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// 키오스크에서 메뉴 표시 저장 등 기본적인 기능을 수행
public class Kiosk {

    public static ArrayList<Category> cg; //햄버거, 드링크 등 대형 카테고리
    public static ArrayList<OrderType> og; // 주문, 취소
    public static ArrayList<Product> pg; // 상품 목록

    public static ArrayList<OrderProduct> bucket; // 장바구니

    public static ArrayList<Purchase> purchase; // 구매상품

    public static Scanner sc = new Scanner(System.in); // 표준입력

    public static int waitNo = 0; // 대기번호

    public Kiosk() {
        cg = new ArrayList<>();
        bucket = new ArrayList<>();
        purchase = new ArrayList<>();
    }

    // 대메뉴표시
    public void displayMenu() {
        System.out.println("SHAKESHACK BURGER 에 오신걸 환영합니다.");
        System.out.println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.");

        for(int i = 0 ; i < cg.size(); i ++) {
            System.out.println(
                    cg.get(i).MenuNo
                            + ". "
                            +  String.format("%-16s", cg.get(i).Name)
                            + " | "
                            + cg.get(i).Desc);
        }

        for(int i = 0 ; i < og.size(); i ++) {
            System.out.println(
                    og.get(i).MenuNo
                            + ". "
                            +  String.format("%-25s", og.get(i).Name)
                            + " | "
                            + og.get(i).Desc);
        }

        System.out.println("종료하려면 -1을 입력하세요.(구매 완료한 상품조회 : 0)");
    }

    // 대메뉴(카테고리) 초기화
    public void initMenu() {
        Category category = new Category();

        category.MenuNo = 1;
        category.Name = "Burgers";
        category.Desc = "앵거스 비프 통살을 다져만든 버거";
        cg.add(category);

        category = new Category();
        category.MenuNo = 2;
        category.Name = "Forzen Custard";
        category.Desc = "매장에서 신선하게 만드는 아이스크림";
        cg.add(category);

        category = new Category();
        category.MenuNo = 3;
        category.Name = "Drinks";
        category.Desc = "매장에서 직접 만드는 음료";
        cg.add(category);

        category = new Category();
        category.MenuNo = 4;
        category.Name = "Beer";
        category.Desc = "뉴욕 브루클린 브루어리에서 양조한 맥주";
        cg.add(category);

        og = new ArrayList<OrderType>();
        OrderType om = new OrderType();
        om.MenuNo = 5;
        om.Name = "Order";
        om.Desc = "장바구니를 확인 후 주문합니다.";
        og.add(om);

        om = new OrderType();
        om.MenuNo = 6;
        om.Name = "Cancel";
        om.Desc = "진행중인 주문을 취소합니다.";
        og.add(om);
    }

    // 카테고리(대메뉴) 선택
    public void selectMenu() {
        int selectNo = sc.nextInt();

        // 0보다 작거나 같거나 카테고리, 주문 개수를 합친거보다 클때 시스템종료
        if (selectNo == -1) {
            System.out.println("종료됩니다.");
            System.exit(1);
        } else if (selectNo < 0 || selectNo > (og.size() + cg.size())) {
            System.out.println("올바른 메뉴 숫자를 입력해주세요.");
            System.exit(-1);
        } else if (selectNo == 5) {
            showBucket();
            purchaseProduct();
        } else if (selectNo == 6) {
            cancelBucket();
        } else if (selectNo == 0) {
            displayPurchase();
        }  else {
            initProduct(selectNo);
            selectProduct(selectNo);
        }
    }

    // 버거 상품 초기화
    public void initProduct(int selectNo) {
        pg = new ArrayList<Product>();

        Product pd = new Product();

        switch(selectNo) {
            case 1:
                pd.MenuNo = 1;
                pd.Name = "ShackBurger";
                pd.Price = 6.9;
                pd.Desc = "토마토, 양상추, 쉑소스가 토핑된 치즈버거";
                pg.add(pd);

                pd = new Product();
                pd.MenuNo = 2;
                pd.Name = "SmokeShack";
                pd.Price = 8.9;
                pd.Desc = "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거";
                pg.add(pd);

                pd = new Product();
                pd.MenuNo = 3;
                pd.Name = "Mushroom Burger";
                pd.Price = 9.4;
                pd.Desc = "몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거";
                pg.add(pd);

                pd = new Product();
                pd.MenuNo = 4;
                pd.Name = "Cheeseburger";
                pd.Price = 6.9;
                pd.Desc = "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거";
                pg.add(pd);

                // 옵션선택
                pd = new Product();
                pd.MenuNo = 5;
                pd.Name = "Bulgogi Burger";
                pd.Price = 5.4;
                pd.Desc = "토마토, 양상추, 불고기소스가 토핑된 불고기 패티버거";
                pg.add(pd);

                pd = new Product();
                pd.printMenu(pg); //상품목록출력
                break;
            case 2 :
                pd = new Product();
                pd.MenuNo = 16;
                pd.Name = "Frozen Custard";
                pd.Price = 5.0;
                pd.Desc = "냉동 커스터드는 아이스크림과 비슷한 냉동 디저트이지만 크림과 설탕 외에 계란으로 만듭니다. 일반적으로 아이스크림에 비해 더 따뜻한 온도로 유지";
                pg.add(pd);

                pd = new Product();
                pd.printMenu(pg); //상품목록출력
                break;
            case 3 :
                pd.MenuNo = 6;
                pd.Name = "Signature Americano";
                pd.Price = 2.7;
                pd.Desc = "은은한 과일향의 부담없는 산미와 카라멜의 단맛, 견과의 풍미가 어우러진 감칠맛이 가득한 커피";
                pg.add(pd);

                pd = new Product();
                pd.MenuNo = 7;
                pd.Name = "Milk Caramel Macchiato";
                pd.Price = 4.3;
                pd.Desc = "은은하게 버터풍미가 도는 밀크카라멜 + 진한에스프레소 + 고소한 우유의 앙상블이 매력적인, 달콤한 카라멜 커피";
                pg.add(pd);

                pd = new Product();
                pd.MenuNo = 8;
                pd.Name = "Royal Milk Tea";
                pd.Price = 4.0;
                pd.Desc = "우아한 향과 은은한 단맛으로 유명한 실론티 베이스에 부드러운 우유를 가미한 일본 스타일의 밀크티";
                pg.add(pd);

                pd = new Product();
                pd.MenuNo = 9;
                pd.Name = "Real Choco";
                pd.Price = 3.5;
                pd.Desc = "진한 초콜릿의 풍부한 맛과 향이 그대로 살아있는 제대로된 초코라떼";
                pg.add(pd);

                pd = new Product();
                pd.MenuNo = 10;
                pd.Name = "Strawberry Juice";
                pd.Price = 4.0;
                pd.Desc = "언제먹어도 맛있는, 새콤달콤 딸기쥬스";
                pg.add(pd);

                pd = new Product();
                pd.printMenu(pg); //상품목록출력
                break;
            case 4 :
                pd.MenuNo = 11;
                pd.Name = "PURE ALE";
                pd.Price = 4.9;
                pd.Desc = "톡쏘는 탄산과 향긋한 홉이 입안을 깔끔하게 정리해 줄 새로운 페일 에일";
                pg.add(pd);

                pd = new Product();
                pd.MenuNo = 12;
                pd.Name = "1st Wort Lager";
                pd.Price = 6.9;
                pd.Desc = "첫번째 맥즙의 깊고 풍부한 맛과 향 극강의 부드러움을 가진 라거!";
                pg.add(pd);

                pd = new Product();
                pd.MenuNo = 13;
                pd.Name = "Dark Lager";
                pd.Price = 3.9;
                pd.Desc = "구운보리의 고소함을 입안가득 느낄 수 있는 어두운 색의 라거";
                pg.add(pd);

                pd = new Product();
                pd.MenuNo = 15;
                pd.Name = "Guinness Draught";
                pd.Price = 8.0;
                pd.Desc = "검은색에 가까운 진한 루비 색깔 맥주. 차게 마시는 것이 좋다.";
                pg.add(pd);


                pd = new Product();
                pd.printMenu(pg); //상품목록출력
                break;
            default :
        }


    }

    // 제품 선택 후 장바구니 추가
    // selectMenu : 햄버거, 드링크, 맥주와 같은 메인메뉴 번호
    public void selectProduct(int selectMenu) {
        int selectNo = sc.nextInt();
        if (selectNo > 0 && selectNo < pg.size() + 1) {
            Product pd = new Product();
            // 현재는 1, 즉 햄버거일때만 옵션 추가 가능
            if (selectMenu == 1) {
                pd = chooseOption(selectMenu, selectNo);
            }

            if (pd.Name != null && !pd.Name.equals("")) {
            } else {
                pd = pg.get(selectNo-1);
            }

            System.out.println(
                    String.format("%-25s", pd.Name)
                            + " | "
                            + String.format("%-4s", pd.Price)
                            + " | "
                            + pd.Desc
            );
            System.out.println("위메뉴를 장바구니에 추가하시겠습니까?");
            System.out.println("1. 확인             2. 취소");
            //확인, 취소
            int decideNo = sc.nextInt();
            if (decideNo == 1) {
                OrderProduct op = new OrderProduct();
                op.MenuNo = pd.MenuNo;
                op.Desc = pd.Desc;
                op.Name = pd.Name;
                op.Amount = 0;
                op.Price = pd.Price;
                bucket.add(op);
                System.out.println(pg.get(selectNo-1).Name + "가 장바구니에 추가되었습니다.");
                System.out.println("메뉴판으로 돌아갑니다.");
                displayMenu();
                selectMenu();
            }  else if (decideNo == 2) {
                System.out.println("취소하고 이전메뉴로 이동합니다.");
                displayMenu();
                selectMenu();
            }
        } else if (selectNo == (pg.size()+1)) {
            System.out.println("취소하고 이전메뉴로 이동합니다.");
            displayMenu();
            selectMenu();
        } else {
            System.exit(0);
        }
    }

    // 상품옵션 기능
    /*
     * @Param selectMenu 메인메뉴번호
     * @Param selectNo 메인메뉴에서 선택한 메뉴 번호
     * */
    public Product chooseOption(int selectMenu, int selectNo) {
        Product pd = new Product();
        // 햄버거일 경우 더블 옵션이 가능
        if (selectMenu == 1) {
                Product origin = pg.get(selectNo -1);
                System.out.println(
                        String.format("%-25s", origin.Name)
                                + " | "
                                +  String.format("%-4s", origin.Price)
                                + " | "
                                + origin.Desc);

                System.out.println("위 메뉴의 어떤 옵션으로 추가하시겠습니까?");
                // double 메뉴 가격 원래가격 * 1.5
                System.out.println("1. Single(W " + origin.Price + ")        2. Double(W "+ String.format("%.1f", origin.Price * 1.5) +")?");
                int chooseNo = sc.nextInt();

                if (chooseNo == 2) {
                    // 불고기버거일경우 5
                    if (selectNo == 5) {
                        pd.MenuNo = 17;
                        pd.Name = "Bulgogi Burger(Double)";
                        pd.Price = Double.parseDouble(String.format("%.1f", origin.Price * 1.5));
                        pd.Desc = "비프패티를 기반으로 패티가 두장 들어간 기본버거";
                    } else {
                        pd.MenuNo = origin.MenuNo * 20; // 임의로 키값 설정
                        pd.Name =  origin.Name + "(Double)";
                        pd.Price = Double.parseDouble(String.format("%.1f", origin.Price * 1.5));
                        pd.Desc = origin.Desc + " (패티2개)";
                    }
                } else {
                    pd.MenuNo = origin.MenuNo;
                    pd.Name = origin.Name;
                    pd.Price = origin.Price;
                    pd.Desc = origin.Desc;
                }
        }

        return  pd;
    }

    // 장바구니 보기
    public void showBucket() {
        OrderProduct op = new OrderProduct();
        op.printMenu(bucket); // 장바구니 목록 출력
    }

    // 주문한 장바구니 취소
    public void cancelBucket() {
        System.out.println("진행중인 주문을 취소하시겠습니까?");
        System.out.println("1.주문 취소   2. 돌아가기");
        int selectNo = sc.nextInt();
        if (selectNo == 1) {
            bucket.clear();
            System.out.println("진행하던 주문이 취소되었습니다.");
        }
        displayMenu();
        selectMenu();
    }

    // 제품 주문
    public void purchaseProduct() {
        int selectNo = sc.nextInt();
        if (selectNo == 1) {
            double totalPrice = 0.0;
            for (int i=0; i < bucket.size(); i++) {
                Purchase pc = new Purchase();
                OrderProduct op = bucket.get(i);
                pc.MenuNo = op.MenuNo;
                pc.Name = op.Name;
                pc.Desc = op.Desc;
                pc.Price = op.Price;
                totalPrice += op.Price;
                purchase.add(pc);
            }

            if (totalPrice <= 0) {
                System.out.println("주문한상품이 없습니다. 이전 메뉴로 이동합니다.");
                displayMenu();
                selectMenu();
                return;
            }

            waitNo++;
            System.out.println("대기번호는 [ " + waitNo  + "] 번 입니다.");
            System.out.println("3초후 메뉴판으로 돌아갑니다.");
            try {
                bucket.clear();
                TimeUnit.SECONDS.sleep(3);
                displayMenu();
                selectMenu();
            } catch(Exception e) {
                System.out.println(e);
                System.exit(-1);
            }
        } else if (selectNo == 2) {
            System.out.println("취소하고 이전메뉴로 이동합니다.");
            displayMenu();
            selectMenu();
        } else {
            System.exit(0);
        }
    }

    // 총 판매금액, 총 판매목록 조회
    public void  displayPurchase() {
        Purchase pc = new Purchase();
        pc.printMenu(purchase);
        int selectNo = sc.nextInt();
        if (selectNo == 1) {
            System.out.println("이전메뉴로 이동합니다.");
            displayMenu();
            selectMenu();
        }
    }


}
