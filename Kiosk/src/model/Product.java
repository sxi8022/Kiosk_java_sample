package model;

import java.util.ArrayList;

public class Product extends Menu implements Order{

    public double Price;


    @Override
    public void printMenu(ArrayList<? extends Menu> array) {

        for(int i = 0 ; i < array.size(); i ++) {
            Product pd = (Product) array.get(i);
            System.out.println(
                    (i+1)
                            + ". "
                            +  String.format("%-10s", pd.Name)
                            + " | "
                            +  String.format("%-4s", pd.Price)
                            + " | "
                            + pd.Desc);
        }
        System.out.println("주문하려면 각 번호를, 이전메뉴는 " + (array.size()+1) + " 종료는 -1을 입력하세요.");
    }
}
