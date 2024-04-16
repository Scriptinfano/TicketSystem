package src.entity;

public class Discount {
    private int discountSequence;
    private int value;
    private String type;

    public Discount() {
    }

    public Discount(int value, String type) {
        this.value = value;
        this.type = type;
    }

    public int getDiscountSequence() {
        return discountSequence;
    }

    public void setDiscountSequence(int discountSequence) {
        this.discountSequence = discountSequence;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
