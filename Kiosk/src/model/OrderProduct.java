package model;

import util.DuplicationUtil;

import java.util.ArrayList;

// 주문 상품(장바구니)
public class OrderProduct extends Menu implements Order{
    // 가격
    public double Price;
    // 개수
    public int Amount;




    // 주문 개수 기능 포함
    @Override
    public void printMenu(ArrayList<? extends Menu> array) {
        ArrayList<OrderProduct> bucketList = new ArrayList<>();
        double totalPrice= 0;
        for(int i = 0 ; i < array.size(); i ++) {
            int amount = 0;
            OrderProduct op = (OrderProduct) array.get(i);
            for (int j = 0; j < array.size(); j++) {
                // 값이 같으면 개수 +1
                if(array.get(i).Name.equals(array.get(j).Name)) {
                    amount++;
                }
            }
            totalPrice += op.Price;
            op.Amount = amount;
            bucketList.add(op);
        }
        DuplicationUtil duplicationCheck = new DuplicationUtil();
        // 주문 목록 중복  제거하여 출력
        try {
            ArrayList<OrderProduct> finalList = (ArrayList<OrderProduct>) duplicationCheck.deduplication(bucketList, OrderProduct::getName);
            System.out.println("아래와 같이 주문하시겠습니까?");
            System.out.println("[Orders]");
            // 갯수 세기 완료한 주문목록 출력
            for(int i = 0 ; i < finalList.size(); i ++) {
                System.out.println(
                        (i+1)
                                + ". "
                                +  String.format("%-25s", finalList.get(i).Name)
                                + " | "
                                +  String.format("%-4s", finalList.get(i).Price)
                                + " | "
                                +  String.format("%-4s", finalList.get(i).Amount) + "개"
                                + " | "
                                + finalList.get(i).Desc);
            }
            System.out.println("[Total]");
            System.out.println("W " + String.format("%.1f", totalPrice));
            System.out.println("1. 주문         2.메뉴판");
        } catch(ArrayIndexOutOfBoundsException ex) {
            System.out.println("에러가발생하였습니다. 관리자에게 문의하여주세요.");
            System.exit(-1);
        }
    }
}
