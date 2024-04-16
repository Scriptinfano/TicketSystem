package src.util;

import src.dao.DataBaseDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 全局公共工具类
 * @author Mingxiang
 */
public class PublicUtil {
    public static DataBaseDao dao=new DataBaseDao();

    /**
     * 超级用户的密码
     */
    public static final String SUPERUSER="superuser";

    public static final float DEFAULT_PRIZE=200;

    static {
        try {
            dao.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用指定的查询结果集填充指定的表格组件
     * @param table       指定的表格组件
     * @param resultSet   指定的查询结果集
     * @param tableDataModel 表数据模型
     * @throws SQLException SQLException
     */
    public static void setTable(JTable table, ResultSet resultSet, DefaultTableModel tableDataModel) throws SQLException {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        var metaData = resultSet.getMetaData();
        var columnCount = metaData.getColumnCount();
        var columnNames = new String[columnCount];
        for (var i = 0; i < columnCount; i++) {
            columnNames[i] = metaData.getColumnName(i + 1);
        }

        for (var columnName : columnNames) {
            tableDataModel.addColumn(columnName);
        }
        while (resultSet.next()) {
            var rowData = new Object[columnCount];
            for (var i = 0; i < columnCount; i++) {
                rowData[i] = resultSet.getObject(i + 1);
            }
            tableDataModel.addRow(rowData);
        }
        table.setDefaultEditor(Object.class, null);
        table.setModel(tableDataModel);
    }

    /**
     * 设置表格可编辑权
     *
     * @param table    制定的表格组件
     * @param editable 是否可编辑
     */
    public static void setTableEditable(JTable table, boolean editable) {
        if (editable) table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));//临时启用单元格编辑
        else table.setDefaultEditor(Object.class, null);
    }

    /**
     * 表格是否可编辑
     *
     * @param table 待判断的表格
     * @return boolean true则代表可编辑
     */
    public static boolean isTableEditable(JTable table) {
        return null != table.getDefaultEditor(Object.class);
    }

    /**
     * 从车次表中得到选中的行，然后将其作为新车票的车次并卖给accountID
     *
     * @param trainServiceTable 输出列车车次信息的表格
     * @param accountID         帐户 ID
     * @throws RuntimeException 运行时异常
     * @throws SQLException     SQLException
     */
    public static void orderTicket(JTable trainServiceTable,String accountID) throws RuntimeException, SQLException {
        int index1 = trainServiceTable.getSelectedRow();//获取选中的行
        if (index1 == -1) {
            throw new RuntimeException("请选中相关车次之后执行操作");
        }
        int trainSequence =(int)trainServiceTable.getValueAt(index1, 0)    ;//得到列车序号
        dao.addTicket(dao.getAccountSequenceByAccountID(accountID),trainSequence,0);

    }
}
