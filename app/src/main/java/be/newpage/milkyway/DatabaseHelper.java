package be.newpage.milkyway;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private static final String DATABASE_NAME = "milkyway.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Expression, Integer> expressionDao = null;
    private RuntimeExceptionDao<Expression, Integer> expressionRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Expression.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<Expression, Integer> getDao() throws SQLException {
        if (expressionDao == null) {
            expressionDao = getDao(Expression.class);
        }
        return expressionDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<Expression, Integer> getExpressionDao() {
        if (expressionRuntimeDao == null) {
            expressionRuntimeDao = getRuntimeExceptionDao(Expression.class);
        }
        return expressionRuntimeDao;
    }

    public List<Expression> queryForExpressions() {
        boolean ascending = false;

        QueryBuilder<Expression, Integer> statementBuilder = getExpressionDao().queryBuilder();
        try {
            statementBuilder.orderBy(Expression.COLUMN_NAME_DATE, ascending);
            return statementBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Integer> queryTotalPerDay() {
        HashMap<String, Integer> totals = new HashMap<String, Integer>();
        List<Expression> expressions = queryForExpressions();

        for (Expression expression : expressions) {
            String date = dateFormat.format(expression.getDate());
            int volume = expression.getVolume();

            if (totals.containsKey(date)) {
                volume += totals.get(date);
            }

            totals.put(date, volume);
        }

        return totals;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        expressionDao = null;
        expressionRuntimeDao = null;
    }
}