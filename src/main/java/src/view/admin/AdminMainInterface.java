/*
 * Created by JFormDesigner on Wed Dec 27 17:22:30 CST 2023
 */

package src.view.admin;

import src.interfaces.TableInitializeNeeded;
import src.util.PublicUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Mingxiang
 */
public class AdminMainInterface extends JFrame implements TableInitializeNeeded {
    private final AS_PersonalInfoInfoInterface asPersonalInfoInfoInterface;
    @Override
    public void initializeTable() {
        var modal = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return 0 != column;
            }
        };
        try (ResultSet set = PublicUtil.dao.viewValidTrainService()) {
            PublicUtil.setTable(table,set,modal);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
                return;
            }
        }
        table.setName("车次表");

    }

    private static int ACCOUNTSEQUENCE;

    public AdminMainInterface(int accountSequence) {
        initComponents();
        initializeTable();
        asPersonalInfoInfoInterface = new AS_PersonalInfoInfoInterface(this,accountSequence);
        initComboBox();
    }

    /**
     * 初始化本界面的候选框
     */
    private void initComboBox() {
        if(startStationComboBox.getItemCount()!=0)
            startStationComboBox.removeAllItems();
        if(endStationComboBox.getItemCount()!=0)
           endStationComboBox.removeAllItems();
        try (ResultSet resultSet = PublicUtil.dao.viewStation()) {
            while (resultSet.next()) {
                int stationIndex = resultSet.getInt(1);
                String stationName = resultSet.getString(2);
                stationName2Sequence.put(stationName, stationIndex);
                startStationComboBox.addItem(stationName);
                endStationComboBox.addItem(stationName);
            }
            startStationComboBox.addItem("空");
            endStationComboBox.addItem("空");
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private static HashMap<String, Integer> stationName2Sequence = new HashMap<>();


    /**
     * 输入用户的身份证号，然后点击订票按钮
     *
     * @param e e
     */
    private void orderBtnClicked(ActionEvent e) {

        if (orderBtn.getText().equals("开始订票")) {

            try {
                PublicUtil.setTable(table, PublicUtil.dao.viewValidTrainService(), new DefaultTableModel());
                table.setEnabled(false);
                return;
            } catch (SQLException ex) {
                int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    return;
                }
            }
            orderBtn.setText("输入会员账号的ID后点此按钮");
        } else if (orderBtn.getText().equals("输入会员账号的ID后点此按钮")) {
            String accountID = accountIDTextField.getText();
            if (accountID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入账号ID再点击", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            try {
                PublicUtil.orderTicket(table, accountID);
            } catch (SQLException ex) {
                int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    return;
                }
            }catch (RuntimeException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "订票成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            try {
                //再次刷新车次表
                PublicUtil.setTable(table, PublicUtil.dao.viewValidTrainService(), new DefaultTableModel());
            } catch (SQLException ex) {
                int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }
    }

    /**
     * 按照搜索条件搜索车次
     *
     * @param e e
     */
    private void searchTrainServiceBtnClicked(ActionEvent e) {
        if (Objects.equals(startStationComboBox.getSelectedItem(), endStationComboBox.getSelectedItem())) {
            JOptionPane.showMessageDialog(this, "起始站和终点站不得相同", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        var modal = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return 0 != column;
            }
        };
        Timestamp date;
        try {
            date = PublicUtil.dao.stringToDate(departureTimeTextField.getText());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "日期转换异常", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try (ResultSet set = PublicUtil.dao.searchTrainService(trainIDTextField.getText(), (String) startStationComboBox.getSelectedItem(), (String) endStationComboBox.getSelectedItem(), date)) {
            PublicUtil.setTable(table,set,modal);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private void personalInfoTextFieldClicked(ActionEvent e) {
        //显示窗体 AS_PersonalInfoInterface
        asPersonalInfoInfoInterface.setVisible(true);
    }

    private void ticketChangeBtnClicked(ActionEvent e) {
        // 将表格改为显示其他有效车次 VIEW_ValidTrainService
        if (ticketChangeBtn.getText().equals("开始改签")) {
            //点击开始改签，表格中先根据管理员填入的身份证号，显示相应账号购买的所有有效车票
            if (accountIDTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请先填写要改签的账号的ID再点击改签按钮", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            //确保目前表格显示指定会员的有效车票表
            int theAccountSequence;
            try {

                theAccountSequence = PublicUtil.dao.getAccountSequenceByAccountID(accountIDTextField.getText());
                if (PublicUtil.dao.isAdmin(theAccountSequence)) {
                    JOptionPane.showMessageDialog(this, "不能给管理员订票", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                ResultSet ticketSet = PublicUtil.dao.checkTicket(theAccountSequence,false);
                PublicUtil.setTable(table, ticketSet, new DefaultTableModel());
            } catch (SQLException ex) {
                int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    return;
                }
            }
            ticketChangeBtn.setText("查看有效车次");
        } else if (ticketChangeBtn.getText().equals("确认改签")) {
            if (table.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(this,"请选中一个车次信息","提示",JOptionPane.INFORMATION_MESSAGE );
                return;
            }
            int rowIndex = table.getSelectedRow();
            int validTrainSequence =(int) table.getValueAt(rowIndex, 0);
            try {
                PublicUtil.dao.modifyTicket(TICKETSEQUENCE,validTrainSequence);
            } catch (SQLException ex) {
                int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    return;
                }
            }
            JOptionPane.showMessageDialog(this,"改签成功","提示",JOptionPane.INFORMATION_MESSAGE);
            ticketChangeBtn.setText("开始改签");
        } else if (ticketChangeBtn.getText().equals("查看有效车次")) {
            if (table.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(this,"请选中一个要改签的车票再点击查看有效车次按钮","提示",JOptionPane.INFORMATION_MESSAGE );
                return;
            }
            int rowIndex = table.getSelectedRow();
            TICKETSEQUENCE =(int) table.getValueAt(rowIndex, 0);
            ResultSet set;
            try {
                set = PublicUtil.dao.viewValidTrainService();
                PublicUtil.setTable(table, set, new DefaultTableModel());
            } catch (SQLException ex) {
                int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    return;
                }
            }
            ticketChangeBtn.setText("确认改签");
        }

    }

    private int TICKETSEQUENCE;//代表在改签的过程中选中的要改签的车票序号



    private void ticketRefundBtnClicked(ActionEvent e) {
        //将表格改为显示该用户的当前的车票
        //将表组件的名字改为UserValidTicket
        // procedure ticketReturn(in theTicketSequence int)

        if (ticketRefundBtn.getText().equals("开始退票")) {
            //点击开始改签，表格中先根据管理员填入的身份证号，显示相应账号购买的所有有效车票
            if (accountIDTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请先填写要改签的账号的ID再点击退票按钮", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int theAccountSequence;
            try {
                theAccountSequence = PublicUtil.dao.getAccountSequenceByAccountID(accountIDTextField.getText());
                if (PublicUtil.dao.isAdmin(theAccountSequence)) {
                    JOptionPane.showMessageDialog(this, "不能给管理员订票", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                ResultSet ticketSet = PublicUtil.dao.checkTicket(theAccountSequence, false);
                PublicUtil.setTable(table, ticketSet, new DefaultTableModel());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            ticketRefundBtn.setText("确认退票");
        } else if (ticketRefundBtn.getText().equals("确认退票")) {
            if (table.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "请选中一个车票", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int rowIndex = table.getSelectedRow();
            int ticketSequence = (int) table.getValueAt(rowIndex, 0);
            try {
                PublicUtil.dao.ticketReturn(ticketSequence);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            JOptionPane.showMessageDialog(this, "退票成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            ticketRefundBtn.setText("开始退票");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        menuBar1 = new JMenuBar();
        personalInfoTextField = new JMenuItem();
        scrollPane1 = new JScrollPane();
        table = new JTable();
        panel2 = new JPanel();
        label8 = new JLabel();
        label2 = new JLabel();
        accountIDTextField = new JTextField();
        ticketChangeBtn = new JButton();
        ticketRefundBtn = new JButton();
        panel4 = new JPanel();
        label3 = new JLabel();
        startStationComboBox = new JComboBox<>();
        label4 = new JLabel();
        endStationComboBox = new JComboBox<>();
        label5 = new JLabel();
        departureTimeTextField = new JTextField();
        label7 = new JLabel();
        trainIDTextField = new JTextField();
        searchTrainServiceBtn = new JButton();
        panel3 = new JPanel();
        orderBtn = new JButton();

        //======== this ========
        setTitle("\u7ba1\u7406\u5458\u4e3b\u754c\u9762");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== menuBar1 ========
        {

            //---- personalInfoTextField ----
            personalInfoTextField.setText("\u6211\u7684\u4e2a\u4eba\u4fe1\u606f");
            personalInfoTextField.addActionListener(e -> personalInfoTextFieldClicked(e));
            menuBar1.add(personalInfoTextField);
        }
        setJMenuBar(menuBar1);

        //======== scrollPane1 ========
        {

            //---- table ----
            table.setModel(new DefaultTableModel(1, 0));
            scrollPane1.setViewportView(table);
        }
        contentPane.add(scrollPane1);

        //======== panel2 ========
        {
            panel2.setLayout(new FlowLayout());

            //---- label8 ----
            label8.setText("\u4e3a\u6307\u5b9a\u4e58\u5ba2\u6539\u7b7e\u9000\u7968");
            panel2.add(label8);

            //---- label2 ----
            label2.setText("\u8d26\u6237ID\u53f7");
            panel2.add(label2);

            //---- accountIDTextField ----
            accountIDTextField.setColumns(18);
            panel2.add(accountIDTextField);

            //---- ticketChangeBtn ----
            ticketChangeBtn.setText("\u5f00\u59cb\u6539\u7b7e");
            ticketChangeBtn.addActionListener(e -> ticketChangeBtnClicked(e));
            panel2.add(ticketChangeBtn);

            //---- ticketRefundBtn ----
            ticketRefundBtn.setText("\u5f00\u59cb\u9000\u7968");
            ticketRefundBtn.addActionListener(e -> ticketRefundBtnClicked(e));
            panel2.add(ticketRefundBtn);
        }
        contentPane.add(panel2);

        //======== panel4 ========
        {
            panel4.setLayout(new FlowLayout());

            //---- label3 ----
            label3.setText("\u8d77\u59cb\u7ad9\uff1a");
            panel4.add(label3);
            panel4.add(startStationComboBox);

            //---- label4 ----
            label4.setText("\u7ec8\u70b9\u7ad9\uff1a");
            panel4.add(label4);
            panel4.add(endStationComboBox);

            //---- label5 ----
            label5.setText("\u65f6\u95f4\uff1a");
            panel4.add(label5);

            //---- departureTimeTextField ----
            departureTimeTextField.setColumns(10);
            panel4.add(departureTimeTextField);

            //---- label7 ----
            label7.setText("\u8f66\u6b21\u53f7");
            panel4.add(label7);

            //---- trainIDTextField ----
            trainIDTextField.setColumns(10);
            panel4.add(trainIDTextField);

            //---- searchTrainServiceBtn ----
            searchTrainServiceBtn.setText("\u67e5\u8be2\u8f66\u6b21");
            searchTrainServiceBtn.addActionListener(e -> searchTrainServiceBtnClicked(e));
            panel4.add(searchTrainServiceBtn);
        }
        contentPane.add(panel4);

        //======== panel3 ========
        {
            panel3.setLayout(new FlowLayout());

            //---- orderBtn ----
            orderBtn.setText("\u5f00\u59cb\u8ba2\u7968");
            orderBtn.addActionListener(e -> orderBtnClicked(e));
            panel3.add(orderBtn);
        }
        contentPane.add(panel3);
        setSize(865, 415);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JMenuBar menuBar1;
    private JMenuItem personalInfoTextField;
    private JScrollPane scrollPane1;
    private JTable table;
    private JPanel panel2;
    private JLabel label8;
    private JLabel label2;
    private JTextField accountIDTextField;
    private JButton ticketChangeBtn;
    private JButton ticketRefundBtn;
    private JPanel panel4;
    private JLabel label3;
    private JComboBox<String> startStationComboBox;
    private JLabel label4;
    private JComboBox<String> endStationComboBox;
    private JLabel label5;
    private JTextField departureTimeTextField;
    private JLabel label7;
    private JTextField trainIDTextField;
    private JButton searchTrainServiceBtn;
    private JPanel panel3;
    private JButton orderBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
