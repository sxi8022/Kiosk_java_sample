package model;

// 메뉴
public class Menu {

    // 메뉴번호
    public int MenuNo = 0;
    // 메뉴명
    public String Name = "";
    // 설명
    public String Desc = "";

    // get, set 함수
    public int getMenuNo() {
        return MenuNo;
    }

    public void setMenuNo(int menuNo) {
        MenuNo = menuNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }
}
