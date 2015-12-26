package app.com.ringmenot.util;

/**
 * Created by sonam on 25-Dec-15.
 */
public class Missed {
    private String number;
    private String circle;
    private String operator;
   public Missed(String number, String circle, String operator){
        this.circle = circle;
        this.operator = operator;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
