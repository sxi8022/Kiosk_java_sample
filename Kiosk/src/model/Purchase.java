package model;

import util.DuplicationUtil;

import java.util.ArrayList;

//구매(주문)한 상품
public class Purchase extends Menu implements Order{
    // 가격
    public double Price;

    // 구매 완료한 상품 표시
    @Override
    public void printMenu(ArrayList<? extends Menu> array) {
        ArrayList<Purchase> bucketList = new ArrayList<>();
        System.out.println("총 판매상품 목록 현황");
        System.out.println("현재까지 총 판매된 상품 목록은 아래와 같습니다.");
        System.out.println("");
        double totalPrice= 0;

        // 구매 목록 출력
        for(int i = 0 ; i < array.size(); i ++) {
            Purchase pc = (Purchase) array.get(i);
            totalPrice += pc.Price;
            System.out.println(
                            "- "
                            +  String.format("%-25s", pc.Name)
                            + " | "
                            +  "W " + String.format("%-4s", pc.Price)
            );
        }

        System.out.println("[ 총 판매금액 현황 ]");
        System.out.println("현재까지 총 판매된 상품 목록은 아래와 같습니다. [W " + String.format("%.1f", totalPrice) + " ] 입니다.");

        System.out.println("1. 돌아가기");
    }
}
