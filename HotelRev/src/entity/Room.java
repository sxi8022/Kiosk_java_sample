package entity;

public class Room {
    private String room_id;
    private String size;
    private float price;
    private boolean status;

    Room(String room_id, String size, float price) {
        this.room_id = room_id;
        this.size = size;
        this.price = price;
        status = false;
    }

    public String getRoom_id() {
        return room_id;
    }

    public String getSize() {
        return size;
    }

    public float getPrice() {
        return price;
    }

    public void turnStatus() {
        status = !status;
    }

    // 추가 kth
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
