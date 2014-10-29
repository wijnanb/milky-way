package be.newpage.milkyway;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "weights")
public class Weight {
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_WEIGHT = "weight";
    public static final String TABLE_NAME = "weights";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_NAME_DATE)
    private Date date;

    @DatabaseField(columnName = COLUMN_NAME_WEIGHT)
    private int weight;

    public Weight() {
        // ORMLite needs a no-arg constructor
    }

    public Weight(Date date, int weight) {
        this.setDate(date);
        this.setWeight(weight);
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "id=" + id +
                ", date=" + date +
                ", weight=" + weight +
                '}';
    }
}