package be.newpage.milkyway;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "expressions")
public class Expression {
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_VOLUME = "volume";
    public static final String TABLE_NAME = "expressions";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_NAME_DATE)
    private Date date;

    @DatabaseField(columnName = COLUMN_NAME_VOLUME)
    private int volume;

    public Expression() {
        // ORMLite needs a no-arg constructor
    }

    public Expression(Date date, int volume) {
        this.setDate(date);
        this.setVolume(volume);
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

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "id=" + id +
                ", date=" + date +
                ", volume=" + volume +
                '}';
    }
}