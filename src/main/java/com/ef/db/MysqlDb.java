package com.ef.db;

import com.ef.entity.Duration;
import com.ef.entity.Logger;
import dnl.utils.text.table.TextTable;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import static com.ef.util.Config.*;

/**
 * Created by Swifta System on 10/1/2017.
 * Email: ${USER_EMAIL}
 * Phone: ${USER_PHONE}
 * Website: ${USER_WEBSITE}
 */

public class MysqlDb implements IMysqlDb{

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss", Locale.US);
    private Connection conn = null;
    private Statement stmt = null;

    private String dbUser;
    private String dbPass;

    public MysqlDb() {}

    public MysqlDb(String dbUser, String dbPass) {
        this.dbUser = (dbUser == null)? MYSQL_DB_USER : dbUser;
        this.dbPass = (dbPass == null)? MYSQL_DB_PASSWORD: dbPass;
    }

    //CONNECT TO DATABASE
    public void init() {
        Properties connectionProps = new Properties();
        connectionProps.put("user", dbUser);
        connectionProps.put("password", dbPass);

        try {
            conn = DriverManager.getConnection(MYSQL_DB_URL+""+ MYSQL_TABLE_NAME, connectionProps);
            stmt = conn.createStatement();
            createTable();
        } catch (SQLException e) {
            System.out.println(""+e.getMessage());
            System.exit(1);
        }

    }

    private void getConnection(){
        try {
            if(conn.isClosed()){ init(); }
        } catch (SQLException e) {
            System.out.println(""+ e.getMessage());
        }
    }

    private void createTable() throws SQLException {
        //1. CHECK IF TABLE ALREADY EXIST, IF IT EXIST, CLEAR TABLE AND RETURN NULL
        DatabaseMetaData dbm = conn.getMetaData();
        ResultSet set = dbm.getTables(null, null, "%", null);

        if(set.next()){
            //Clear database
//            System.exit(1);
        }else{

            String createHourlyString =
                    "CREATE TABLE IF NOT EXISTS " + HOURLY + MYSQL_DB_NAME +
                            "("+ _ID + " integer NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                            IP_ADDRESS+" VARCHAR(50) NOT NULL UNIQUE, " +
                            DURATION+" VARCHAR(10) NOT NULL,"+
                            THRESHOLD+" INT(10) NOT NULL, "+
                            REQUESTS +" INT(10) NOT NULL, "+
                            COMMENT+" TEXT NOT NULL, "+
                            LOG_TIME+" VARCHAR(50) NOT NULL)";

            String createDailyString =
                    "CREATE TABLE IF NOT EXISTS " + DAILY + MYSQL_DB_NAME +
                            "("+ _ID + " integer NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                            IP_ADDRESS+" VARCHAR(50) NOT NULL UNIQUE, " +
                            DURATION+" VARCHAR(10) NOT NULL,"+
                            THRESHOLD+" INT(10) NOT NULL, "+
                            REQUESTS +" INT(10) NOT NULL, "+
                            COMMENT+" TEXT NOT NULL, "+
                            LOG_TIME+" VARCHAR(50) NOT NULL)";

            stmt.execute(createHourlyString);
            stmt.execute(createDailyString);

            if(stmt != null){
                System.out.println("Table created successfully!");
//                stmt.close();
            }

        }
    }


    public boolean insertQuery(Logger record) {

        //check if record exist
        if(this.checkIfRecordExist(record)){ return true; }
        String prefix_ = record.getDuration().name().toLowerCase();

        String insertQuery = "INSERT INTO "+ prefix_+MYSQL_DB_NAME +" ("+ IP_ADDRESS +", "+ DURATION +", "+
                THRESHOLD +", "+ COMMENT +", "+ REQUESTS +", "+ LOG_TIME +") VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = null;

        try {

            statement = conn.prepareStatement(insertQuery);
            statement.setString(1, record.getIpAddress());
            statement.setString(2, record.getDuration().name());
            statement.setInt(3, record.getThreshold());
            statement.setString(4, record.getComment());
            statement.setInt(5, record.getRequest() );
            statement.setString(6, record.getLogTime() );

            int row = statement.executeUpdate();
            /*
            if(row > 0){
                System.out.println("A record was created!");
            }
            */
            conn.close();

            return true;

        } catch (SQLException e) {
            System.out.println(" "+e.getMessage() );
        }

        return false;
    }

    private boolean checkIfRecordExist(Logger record) {
        getConnection();
        String prefix_ = record.getDuration().name().toLowerCase();

        String sql_select_query = "SELECT * FROM "+ prefix_+MYSQL_DB_NAME+" WHERE "+ IP_ADDRESS +" = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(sql_select_query);
            statement.setString(1, record.getIpAddress());

            List<Logger> mLogger = getLogRecords(statement);
            if(mLogger != null && mLogger.size() > 0){
                //Update file
                String updateQuery = "UPDATE "+ prefix_+MYSQL_DB_NAME + " SET "+
                        DURATION +" = ?,"+
                        THRESHOLD+" = ?,"+
                        COMMENT+" = ?,"+
                        REQUESTS+" = ?,"+
                        LOG_TIME+" = ?"+" " +
                        "WHERE "+ IP_ADDRESS + " = ?";

                PreparedStatement stnt = conn.prepareStatement(updateQuery);
                stnt.setString(1, record.getDuration().name());
                stnt.setInt(2, record.getThreshold() );
                stnt.setString(3, record.getComment() );
                stnt.setInt(4, record.getRequest() );
                stnt.setString(5, record.getLogTime() );
                stnt.setString(6, record.getIpAddress() );

                int row = stnt.executeUpdate();
                conn.close();
                return row > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private List<Logger> getLogRecords(PreparedStatement sql) {

        List<Logger> records = new ArrayList<Logger>();

        try {
            ResultSet set = sql.executeQuery();
            while (set.next()){
                Logger record = new Logger();
                record.set_id( set.getLong( _ID ) );
                record.setIpAddress( set.getString(IP_ADDRESS) );
                record.setThreshold( set.getInt( THRESHOLD ) );
                record.setDuration( Duration.findByLabel( set.getString( DURATION )) );
                record.setComment( set.getString( COMMENT ) );
                record.setLogTime( set.getString( LOG_TIME ) );
                record.setRequest( set.getInt( REQUESTS ) );
                records.add(record);
            }
        } catch (SQLException e) {
            System.out.println(" "+e.getMessage() );
            System.exit(1);
        }

        return records;
    }

    /**
     * This method print out the list of information
     * @param records
     */
    public void print(List<Logger> records) {

        String[] columnNames = {"No.", "IP ADDRESS", "THRESHOLD", "DURATION", "No. OF REQUESTS", "REASON", "LOG TIME"};
        Object[][] data = new Object[records.size()][7];

        for (int i = 0; i < records.size(); i++) {
            data [i][0] = records.get(i).get_id();
            data [i][1] = records.get(i).getIpAddress();
            data [i][2] = records.get(i).getThreshold();
            data [i][3] = records.get(i).getDuration();
            data [i][4] = records.get(i).getRequest();
            data [i][5] = records.get(i).getComment();
            data [i][6] = records.get(i).getLogTime();
        }

        TextTable tt = new TextTable(columnNames, data);
        tt.setSort(0);
        tt.printTable();
    }
}
