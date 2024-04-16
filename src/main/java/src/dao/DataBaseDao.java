package src.dao;

import src.util.PublicUtil;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 数据库交互类，负责上层应用和数据库交换数据
 * 要切换登录用户以及登录密码，还有需要连接的数据库，请在此修改
 * 默认连接本地mysql8.0版本的root用户的TicketDatabase数据库
 *
 * @author Mingxiang
 */
public class DataBaseDao {

    /**
     * 数据库用户名
     */
    private static final String userName = "root";
    /**
     * 数据库密码
     */
    private static final String password = "200329";
    /**
     * 数据库url
     */
    private static final String url = "jdbc:mysql://127.0.0.1:3306/TicketDatabase";
    /**
     * 驱动程序路径
     */
    private static final String driverPath = "com.mysql.cj.jdbc.Driver";
    private Connection connection;

    static {
        //注册驱动

        try {
            Class.forName(driverPath);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public final Connection getConnection() {
        return connection;
    }

    private final HashMap<String, CallableStatement> callableStatements = new HashMap<>();
    private final HashMap<String, PreparedStatement> preparedStatements = new HashMap<>();
    private Statement sta;
    public final void connect() throws SQLException {
        if (null != this.connection) return;

        //获取连接
        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLTimeoutException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage() ,"连接超时错误",JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage() ,"数据库错误",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }


        //初始化存储过程执行对象池
        callableStatements.put("addAccount", connection.prepareCall("call ticketdatabase.addAccount(?,?,?,?)"));
        callableStatements.put("addStation", connection.prepareCall("call ticketdatabase.addStation(?)"));
        callableStatements.put("addTrainService", connection.prepareCall("call ticketdatabase.addTrainService(?,?,?,?)"));
        callableStatements.put("checkAccountInfo", connection.prepareCall("call ticketdatabase.checkAccountInfo(?,?)"));
        callableStatements.put("checkFinance", connection.prepareCall("call ticketdatabase.checkFinance(?)"));
        callableStatements.put("deleteAccount", connection.prepareCall("call ticketdatabase.deleteAccount(?)"));
        callableStatements.put("deleteStation", connection.prepareCall("call ticketdatabase.deleteStation(?)"));
        callableStatements.put("login", connection.prepareCall("call ticketdatabase.login(?,?,?,?)"));
        callableStatements.put("modifyDiscount", connection.prepareCall("call ticketdatabase.modifyDiscount(?,?)"));
        callableStatements.put("modifyManagerAccountInfo", connection.prepareCall("call ticketdatabase.modifyManagerAccountInfo(?,?,?)"));
        callableStatements.put("modifyPersonalInfo", connection.prepareCall("call ticketdatabase.modifyPersonalInfo(?,?,?,?,?,?,?,?)"));
        callableStatements.put("searchTrainService", connection.prepareCall("call ticketdatabase.searchTrainService(?,?,?,?)"));
        callableStatements.put("ticketReturn", connection.prepareCall("call ticketdatabase.ticketReturn(?)"));
        callableStatements.put("checkTicket", connection.prepareCall("call ticketdatabase.checkTicket(?,?)"));
        callableStatements.put("modifyStation",connection.prepareCall("call modifyStation(?,?);"));
        callableStatements.put("searchAccount", connection.prepareCall("call searchAccount(?,?,?,?,?,?,?,?)"));

        preparedStatements.put("view_accountList", connection.prepareStatement("select * FROM view_accountlist;"));
        preparedStatements.put("view_allTrainService", connection.prepareStatement("select * FROM view_allTrainService;"));
        preparedStatements.put("view_station", connection.prepareStatement("select * FROM view_station;"));
        preparedStatements.put("view_ticket", connection.prepareStatement("select * FROM view_ticket;"));
        preparedStatements.put("view_validTrainService", connection.prepareStatement("select * FROM view_validTrainService;"));

        sta = connection.createStatement();
        connection.createStatement().execute("use ticketdatabase;");
    }

    /**
     * 添加帐户<p>
     -- 添加新管理员：addAccount(1,'password',null,'身份证号')<p>
     -- 添加老管理员，老管理员创建新账号：addAccount(1,'password','accountID',null)<p>
     -- 添加新会员：addAccount(2,'password',null,'身份证号')<p>
     -- 老会员添加新账号：addAccount(2,'password','accountID',null)<p>
     * 所调用的存储过程：procedure addAccount(IN theType tinyint, IN thePassword varchar(20), IN theAccountID varchar(20), IN theIdentificationId varchar(18))
     *
     * @param divide     1为添加新管理员,2为老管理员创建新账号，3为添加新用户，4为老用户添加新账号
     * @param accountId  账号ID
     * @param password   密码
     * @param identityID 身份证号码
     * @throws SQLException SQLException
     */ //添加账户
    public void addAccount(int divide, String accountId, String password, String identityID) throws SQLException {
        CallableStatement sta = callableStatements.get("addAccount");
        if (divide == 1) {
            sta.setInt(1, 1);
            sta.setString(2, password);
            sta.setNull(3, Types.VARCHAR);
            sta.setString(4, identityID);
        }
        if(divide == 2){
            sta.setInt(1, 1);
            sta.setString(2, password);
            sta.setString(3, accountId);
            sta.setNull(4, Types.VARCHAR);
        }
        if(divide == 3){
            sta.setInt(1, 2);
            sta.setString(2, password);
            sta.setNull(3, Types.VARCHAR);
            sta.setString(4,identityID);
        }
        if(divide == 4){
            sta.setInt(1, 2);
            sta.setString(2, password);
            sta.setString(3, accountId);
            sta.setNull(4, Types.VARCHAR);
        }
        sta.executeUpdate();
    }

    public void addStation(String stationName) throws SQLException {
        callableStatements.get("addStation").setString(1,stationName);
        callableStatements.get("addStation").execute();
    }

    public void addTrainService(String trainServiceId,int StartStation,int endStation,java.sql.Timestamp departureTime) throws SQLException {
        callableStatements.get("addTrainService").setString(1,trainServiceId);
        callableStatements.get("addTrainService").setInt(2,StartStation);
        callableStatements.get("addTrainService").setInt(3,endStation);
        callableStatements.get("addTrainService").setTimestamp(4,departureTime);
        callableStatements.get("addTrainService").execute();
    }

    public ResultSet checkAccountInfo(int accountType,int accountSequence) throws SQLException {
        callableStatements.get("checkAccountInfo").setInt(1,accountType);
        callableStatements.get("checkAccountInfo").setInt(2,accountSequence);
        return callableStatements.get("checkAccountInfo").executeQuery();
    }

    /**
     * 返回近一个月的财务收入
     *
     * @return float
     * @throws SQLException SQLException
     */
    public float checkFinance() throws SQLException {
        callableStatements.get("checkFinance").registerOutParameter(1, Types.FLOAT);
        callableStatements.get("checkFinance").execute();
        return callableStatements.get("checkFinance").getFloat(1);
    }

    /**
     * 返回近一个月之内的所有票
     *
     * @return {@link ResultSet}
     */
    public ResultSet checkFinanceTicket() throws SQLException {
        return sta.executeQuery("select * from view_ticket where 下单时间>DATE_SUB(NOW(), INTERVAL 1 MONTH) and 下单时间<now()");
    }



    public void deleteAccount(int accountSequence) throws SQLException {
        CallableStatement sta = callableStatements.get("deleteAccount");
        callableStatements.get("deleteAccount").setInt(1,accountSequence);
        callableStatements.get("deleteAccount").execute();
    }

    public void deleteStation(int stationSequence) throws SQLException {
        CallableStatement sta = callableStatements.get("deleteStation");
        callableStatements.get("deleteStation").setInt(1,stationSequence);
        callableStatements.get("deleteStation").execute();
    }

    /**
     * 登录
     *
     * @param accountId 帐户 ID
     * @param password  密码
     * @return {@link int[]} arr[0]代表是否登录成功（1成功，0失败），arr[1]代表登录的是管理员账号还是会员账号
     * @throws SQLException SQLException
     */
    public int[] login(String accountId, String password) throws SQLException {
        CallableStatement sta = callableStatements.get("login");
        sta.setString(1, accountId);
        sta.setString(2, password);
        sta.registerOutParameter(3, Types.TINYINT);
        sta.registerOutParameter(4, Types.TINYINT);

        sta.execute();
        int[] arr = new int[2];
        arr[0] = sta.getInt(3);
        arr[1] = sta.getInt(4);
        return arr;
    }

    /**
     * 修改折扣
     *
     * @param type  type为1时代表儿童，为2时代表学生
     * @param value 具体的折扣值
     * @throws SQLException SQLException
     */
    public void modifyDiscount(int type,float value) throws SQLException {
        if (type != 1 && type != 2) throw new RuntimeException("type设置不正确");
        CallableStatement sta = callableStatements.get("modifyDiscount");
        callableStatements.get("modifyDiscount").setInt(1,type);
        callableStatements.get("modifyDiscount").setFloat(2,value);
        callableStatements.get("modifyDiscount").execute();
        sta.execute();
    }

    public void modifyManagerAccountInfo(int accountSequence,String password,String accountName) throws SQLException {
        CallableStatement sta = callableStatements.get("modifyManagerAccountInfo");
        callableStatements.get("modifyManagerAccountInfo").setInt(1,accountSequence);
        callableStatements.get("modifyManagerAccountInfo").setString(2,password);
        callableStatements.get("modifyManagerAccountInfo").setString(3,accountName);
        callableStatements.get("modifyManagerAccountInfo").execute();
        sta.execute();
    }

    public void modifyStation(int theStationSequence,String theStationName) throws SQLException {
        CallableStatement sta=callableStatements.get("modifyStation");
        sta.setInt(1,theStationSequence);
        sta.setString(2,theStationName);
        sta.execute();
    }

    public void modifyPersonalInfo(int accountType,int accountSequence,String name,int gender,int age,String identityID,String ContactInfo,String paymentMethod) throws SQLException {
        CallableStatement sta = callableStatements.get("modifyPersonalInfo");
        callableStatements.get("modifyPersonalInfo").setInt(1,accountType);
        callableStatements.get("modifyPersonalInfo").setInt(2,accountSequence);
        callableStatements.get("modifyPersonalInfo").setString(3,name);
        callableStatements.get("modifyPersonalInfo").setInt(4,gender);
        callableStatements.get("modifyPersonalInfo").setInt(5,age);
        callableStatements.get("modifyPersonalInfo").setString(6,identityID);
        callableStatements.get("modifyPersonalInfo").setString(7,ContactInfo);
        callableStatements.get("modifyPersonalInfo").setString(8,paymentMethod);
        callableStatements.get("modifyPersonalInfo").execute();

    }

    /**
     * 搜索列车车次，仅显示有效车次
     *
     * @param startStation  起始站，若为字符串“空”则忽略此条件
     * @param endStation    终端站，若为字符串“空”则忽略此条件
     * @param departureTime 出发时间，若为null则忽略此条件
     * @param trainID       列车ID号，若为null则忽略此条件
     * @return {@link ResultSet}
     * @throws SQLException SQLException
     */
    public ResultSet searchTrainService(String trainID, String startStation, String endStation, Timestamp departureTime) throws SQLException {
        if (trainID.isEmpty()) callableStatements.get("searchTrainService").setNull(1, Types.VARCHAR);
        else callableStatements.get("searchTrainService").setString(1, trainID);
        if (startStation.equals("空")) callableStatements.get("searchTrainService").setNull(2, Types.VARCHAR);
        else callableStatements.get("searchTrainService").setString(2, startStation);
        if (endStation.equals("空")) callableStatements.get("searchTrainService").setNull(3, Types.VARCHAR);
        else callableStatements.get("searchTrainService").setString(3, endStation);
        if (departureTime == null) callableStatements.get("searchTrainService").setNull(4, Types.DATE);
        return  callableStatements.get("searchTrainService").executeQuery();
    }

    public void ticketReturn(int ticketSequence) throws SQLException {
        callableStatements.get("ticketReturn").setInt(1,ticketSequence);
        callableStatements.get("ticketReturn").execute();
    }

    //SQL返回视图中的数据

    public final ResultSet viewAccountList() throws SQLException {
        return preparedStatements.get("view_accountList").executeQuery();

    }

    public ResultSet viewAllTrainService() throws SQLException {
        return preparedStatements.get("view_allTrainService").executeQuery();
    }

    public ResultSet viewStation() throws SQLException {
        return preparedStatements.get("view_station").executeQuery();
    }

    public ResultSet viewTicket() throws SQLException {
        return preparedStatements.get("view_ticket").executeQuery();
    }

    public ResultSet viewValidTrainService() throws SQLException {
        return preparedStatements.get("view_validTrainService").executeQuery();
    }

    public int getAccountSequenceByAccountID(String theAccountID ) throws SQLException {
        PreparedStatement sta = connection.prepareStatement("select 账户序号 from view_accountlist where 账户ID=?;");
        sta.setString(1, theAccountID);
        ResultSet set = sta.executeQuery();
        set.next();
        return set.getInt(1);
    }

    /**
     * 返回车票结果集
     *
     * @param theAccountSequence 帐户序号
     * @param outOfDate          车票是否已过期，true则代表已过期
     * @return {@link ResultSet}
     * @throws SQLException SQLException
     */
    public ResultSet checkTicket(int theAccountSequence,boolean outOfDate) throws SQLException {
        CallableStatement sta = callableStatements.get("checkTicket");
        sta.setInt(1, theAccountSequence);
        sta.setBoolean(2, outOfDate);
        return sta.executeQuery();
    }

    /**
     * 将给定的具有固定格式的字符串转换为java.sql.Date，若给定的字符串为空则返回null，若转换失败则抛出异常
     *
     * @param str 给定的日期时间字符串
     * @return {@link Date}
     * @throws ParseException 转换异常
     *///String转Date
    public Timestamp stringToDate(String str) throws ParseException,IllegalArgumentException {
        if (str.isEmpty())
            throw new IllegalArgumentException("未输入发车日期");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//SimpleDateFormat格式和解析日期的类
        java.util.Date parse;//得到java.util.Date对象
        parse = sdf.parse(str);
        long time = parse.getTime();//返回当前日期对应的long类型的毫秒数
        return new Timestamp(time);
    }

    /**
     * 获取指定车次的有效座位号码和车厢号码
     *
     * @param theTrainServiceSequence 列车车次序号
     * @return {@link int[]} 返回的数组是arr,arr[0]代表车厢号，arr[1]代表座位号
     * @throws SQLException SQLException
     */
    private int[] getValidSeatIndex(int theTrainServiceSequence) throws SQLException {
        PreparedStatement sta2 = connection.prepareStatement("select trainServiceId from trainservice where trainServiceSequence=?");
        sta2.setInt(1, theTrainServiceSequence);
        ResultSet resultSet = sta2.executeQuery();
        resultSet.next();
        String theTrainID = resultSet.getString(1);

        PreparedStatement sta = connection.prepareStatement("select 车厢号, 座位号 from VIEW_Ticket where 是否有效 = '有效' and 车次号 = ?;");
        sta.setString(1, theTrainID);
        ArrayList<Integer> indexes = new ArrayList<>();
        ResultSet set = sta.executeQuery();
        int wagonNum, seatNum, seatIndex;
        while (set.next()) {
            wagonNum = set.getInt(1);
            seatNum = set.getInt(2);
            seatIndex = (wagonNum - 1) * 10 + seatNum;
            indexes.add(seatIndex);
        }
        int theIndex = 1;
        while (indexes.contains(theIndex) && theIndex <= 200)
            theIndex++;
        int[] arr = new int[2];
        arr[0] = (theIndex / 10) + 1;

        seatIndex=theIndex%20;
        if(seatIndex==0){
            seatIndex=20;
        }
        arr[1]=seatIndex;

        return arr;
    }

    /**
     * 添加车票，适用于管理员端给会员订票和会员自己订票
     *
     * @param theAccountSequence 帐户序号，给哪个账号订票
     * @param theTrainSequence   车次序号，要订购哪个车次
     * @param thePrize           管理员端请输入设定的基础价格，会员端输入0即可
     */
    public void addTicket(int theAccountSequence, int theTrainSequence, float thePrize) throws SQLException {
        PreparedStatement sta = connection.prepareStatement("select personNum from trainservice where trainServiceSequence = ?;");
        sta.setInt(1, theTrainSequence);
        ResultSet resultSet = sta.executeQuery();
        resultSet.next();
        PreparedStatement sta2 = connection.prepareStatement("select 年龄 from VIEW_AccountList where 账户序号 = ?;");
        sta2.setInt(1, theAccountSequence);
        ResultSet resultSet3 = sta2.executeQuery();
        resultSet3.next();
        int age = resultSet3.getInt(1);
        ResultSet resultSet1 = connection.createStatement().executeQuery("select value from discount where discountSequence=1;");
        resultSet1.next();
        float childDiscount = resultSet1.getFloat(1);
        ResultSet resultSet2 = connection.createStatement().executeQuery("select value from discount where discountSequence=2");
        resultSet2.next();
        float studentDiscount = resultSet2.getFloat(1);
        PreparedStatement sta3 = connection.prepareStatement("insert into ticket (ticketId, wagonNum, seatNum, orderTime, prize, accountSequence_FK, trainServiceSequence_FK)values (substring(md5(rand()), 1, 20),?,?, curdate(), ?, ?, ?)");

        int theRestOfPerson = resultSet.getInt(1);
        if (thePrize == 0)
            thePrize = PublicUtil.DEFAULT_PRIZE;
        if (theRestOfPerson > 0) {
            int[] arr = getValidSeatIndex(theTrainSequence);
            sta3.setInt(1, arr[0]);
            sta3.setInt(2, arr[1]);
            if (age < 10) thePrize = thePrize * childDiscount;
            else if (age <= 20) thePrize = thePrize * studentDiscount;
            sta3.setFloat(3, thePrize);
            sta3.setInt(4, theAccountSequence);
            sta3.setInt(5, theTrainSequence);
            sta3.executeUpdate();
            PreparedStatement sta4 = connection.prepareStatement("update trainservice set personNum=personNum - 1 where trainServiceSequence = ?;");
            sta4.setInt(1, theTrainSequence);
            sta4.executeUpdate();
        } else {
            throw new RuntimeException("票已卖光");
        }
    }

    /**
     * 对会员的某张有效车票实现改签，更换列车车次
     *
     * @param theTicketSequence   对哪一张车票进行改签
     * @param theNewTrainSequence 要改签到哪一个车次
     * @throws SQLException SQLException
     */
    public void modifyTicket(int theTicketSequence, int theNewTrainSequence) throws SQLException {
        PreparedStatement preparedStatement6 = connection.prepareStatement("select 起点站,终点站 from view_ticket where 车票序号=?;");
        preparedStatement6.setInt(1, theTicketSequence);
        ResultSet resultSet2 = preparedStatement6.executeQuery();
        resultSet2.next();
        String startStation = resultSet2.getString(1);
        String endStation = resultSet2.getString(2);

        PreparedStatement preparedStatement1 = connection.prepareStatement("select trainServiceSequence_FK from ticket where ticketSequence = ?;");
        preparedStatement1.setInt(1, theTicketSequence);
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        resultSet1.next();
        int oldTrainSequence = resultSet1.getInt(1);

        PreparedStatement preparedStatement = connection.prepareStatement("select 车厢号, 座位号 from VIEW_Ticket where 是否有效 = '有效' and 车次号 = ? and 车次号!=? and 起点站=? and 终点站=?;");
        preparedStatement.setInt(1, theNewTrainSequence);
        preparedStatement.setInt(2,oldTrainSequence);
        preparedStatement.setString(3,startStation);
        preparedStatement.setString(4,endStation);
        ResultSet resultSet = preparedStatement.executeQuery();

        PreparedStatement preparedStatement2 = connection.prepareStatement("update ticket set trainServiceSequence_FK=? where ticketSequence = ?;");
        preparedStatement2.setInt(1, theNewTrainSequence);
        preparedStatement2.setInt(2, theTicketSequence);
        preparedStatement2.executeUpdate();

        PreparedStatement preparedStatement3 = connection.prepareStatement("update trainservice set personNum=personNum - 1 where trainServiceSequence = ?;");
        preparedStatement3.setInt(1, theNewTrainSequence);
        preparedStatement3.executeUpdate();

        PreparedStatement preparedStatement4 = connection.prepareStatement("update trainservice set personNum=personNum + 1 where trainServiceSequence = ?;");
        preparedStatement4.setInt(1, oldTrainSequence);
        preparedStatement4.executeUpdate();

        int[] validSeatIndex = getValidSeatIndex(theNewTrainSequence);
        PreparedStatement preparedStatement5 = connection.prepareStatement("update ticket set wagonNum=? ,seatNum=? where ticketSequence = ?");
        preparedStatement5.setInt(1, validSeatIndex[0]);
        preparedStatement5.setInt(2, validSeatIndex[1]);
        preparedStatement5.setInt(3, theTicketSequence);
        preparedStatement5.executeUpdate();

    }

    /*public int getAccountSequence(int accountID) throws SQLException {
        PreparedStatement sta = connection.prepareStatement("select accountSequence from myaccount where accountId=?;");
        sta.setInt(1, accountID);
        ResultSet set= sta.executeQuery();
        set.next();
       return set.getInt(1);
    }*/

    /**
     * 判断给定的账号是否是管理员账号，是则返回true
     *
     * @param accountSequence 账户序号
     * @return boolean 是则返回true
     */
    public boolean isAdmin(int accountSequence) throws SQLException {
        PreparedStatement sta = connection.prepareStatement("select accountType from myaccount where accountSequence=?;");
        sta.setInt(1, accountSequence);
        ResultSet set = sta.executeQuery();
        set.next();
        return set.getInt(1) == 1;
    }

    public ResultSet searchStation(String theName) throws SQLException {
        PreparedStatement sta = connection.prepareCall("call searchStation(?);");
        sta.setString(1, theName);
        ResultSet set = sta.executeQuery();
        set.next();
        int theStationSequence = set.getInt(1);
        PreparedStatement sta2 = connection.prepareStatement("select * from view_station where 序号=?");
        sta2.setInt(1, theStationSequence);
        return sta2.executeQuery();
    }

    public ResultSet searchAccount(String theAccountID, String theAccountType, String thePaymentMethod, String theAccountName, String identityID, String theName, String theGender, Integer theAge) throws SQLException {
        CallableStatement callableStatement = callableStatements.get("searchAccount");
        //in theAccountID varchar(20), in theAccountType varchar(20), in thePaymentMethod varchar(20), in theAccountName varchar(20), in identityID varchar(18), in theName varchar(20), in theGender tinyint, in theAge tinyint
        callableStatement.setString(1, theAccountID);
        callableStatement.setString(2, theAccountType);
        callableStatement.setString(3, thePaymentMethod);
        callableStatement.setString(4, theAccountName);
        callableStatement.setString(5, identityID);
        callableStatement.setString(6, theName);
        int theGenderIndex;
        if (theGender == null) callableStatement.setNull(7, Types.TINYINT);
        else {
            if (theGender.equals("男")) theGenderIndex = 1;
            else theGenderIndex = 2;
            callableStatement.setInt(7, theGenderIndex);
        }
        if (theAge == null)callableStatement.setNull(8, Types.TINYINT);
        else callableStatement.setInt(8, theAge);
        return callableStatement.executeQuery();

    }

   /* public String getUserIdByIdentityId(String identityId) throws SQLException {
        PreparedStatement sta = connection.prepareStatement("select 账户ID from view_accountlist where 身份证号=?");
        sta.setString(1, identityId);
        ResultSet set = sta.executeQuery();
        set.next();
        return set.getString(1);
    }*/
    public ResultSet getDiscount() throws SQLException {
        return sta.executeQuery("select discountSequence,value from discount;");
    }

}
