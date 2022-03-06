package ro.fasttrackit.entity;
import ro.fasttrackit.model.Type;
import javax.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private int id;
    private String product;
    @Enumerated(EnumType.STRING)
    private Type type;
    private double amount;


    public Transaction() {
    }

    public Transaction(String product, Type type, double amount) {
        this.product = product;
        this.type = type;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
