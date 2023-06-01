package model;

import java.util.ArrayList;

// 상품
public class Product extends Menu implements Order{

    // 가격
    public double Price;
    
    // 상품 메뉴 표시
    @Override
    public void printMenu(ArrayList<? extends Menu> array) {

        for(int i = 0 ; i < array.size(); i ++) {
            Product pd = (Product) array.get(i);
            System.out.println(
                    (i+1)
                            + ". "
                            +  String.format("%-25s", pd.Name)
                            + " | "
                            +  String.format("%-4s", pd.Price)
                            + " | "
                            + pd.Desc);
        }
        System.out.println("주문하려면 각 번호를, 이전메뉴는 " + (array.size()+1) + " 종료는 -1을 입력하세요.");
    }
}
