/*
 * Created by JFormDesigner on Wed Dec 27 17:22:55 CST 2023
 */

package src.view.passenger;

import src.interfaces.TableInitializeNeeded;
import src.util.PublicUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mingxiang
 */
public class UserMainInterface extends JFrame implements TableInitializeNeeded {
    @Override
    public void initializeTable() {
        //输出有效车次视图：view VIEW_ValidTrainService
        var modal = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row,int cloumn){
                return 0 != cloumn;
            }
        };
        try {
            ResultSet set = PublicUtil.dao.viewValidTrainService();
            PublicUtil.setTable(this.trainServiceTable,set,modal);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private String userId;

    /**
     * 0为全部车次，1为当前订票，2为历史购买
     */
    private  int tableType = 0;

    private  PersonalInfoInterface personalInfoInterface;
    private  TicketChangeInterface ticketChangeInterface;

    private PassengerAddNewAccountInterface passengerAddNewAccountInterface;

    public UserMainInterface(String theUserId) {
        userId = theUserId;
        initComponents();
        personalInfoInterface = new PersonalInfoInterface(this,theUserId);
        ticketChangeInterface = new TicketChangeInterface(this);
        passengerAddNewAccountInterface=new PassengerAddNewAccountInterface(this,theUserId);
        initializeTable();
    }


    private void myCurrentTicketMenuItemClicked(ActionEvent e) {
        // 直接在界面的下方表格中输出车票表格  procedure checkTicket(in theAccountSequence int, in outOfDate boolean) 第二个参数传入false
        //查看用户目前购买的尚未发车的票
        tableType = 1;
        var modal = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row,int cloumn){
                return 0 != cloumn;
            }
        };
        try {
            ResultSet set = PublicUtil.dao.checkTicket(PublicUtil.dao.getAccountSequenceByAccountID(userId),false);
            PublicUtil.setTable(this.trainServiceTable,set,modal);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private void changeTicketMenuItemClicked(ActionEvent e) {
        // 界面表格必须处于我的当前订票，否则提示切换表格,modifyTicket(in theTicketSequence int, in theNewTrainServiceSequence int)
        //用户从我的当前订票中选择一个需要改签的车票，然后点击改签，进入界面TicketChangeInterface，选择目前尚未发车的车次，然后创建新的车票给用户
        if (tableType != 1){
            tableType = 1;
            var modal = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row,int cloumn){
                    return 0 != cloumn;
                }
            };
            try {
                ResultSet set = PublicUtil.dao.checkTicket(PublicUtil.dao.getAccountSequenceByAccountID(userId),false);
                PublicUtil.setTable(this.trainServiceTable,set,modal);
            } catch (SQLException ex) {
                int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }
        else {
            //获取选中的行的起始站点值
            if (trainServiceTable.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(this,"请选中一张要改签的车票","提示",JOptionPane.INFORMATION_MESSAGE );
                return;
            }
            int rowIndex = trainServiceTable.getSelectedRow();//获取选中的行
            int ticketID = (int)(trainServiceTable.getValueAt(rowIndex, 0));
            ticketChangeInterface.initTable(ticketID);
            ticketChangeInterface.setVisible(true);
        }
    }

    private void refundMenuItmClicked(ActionEvent e) {
        //界面表格必须处于我的当前订票，否则提示切换表格，调用 procedure ticketReturn(in theTicketSequence int)
        if (tableType != 1){
            tableType = 1;
            var modal = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row,int cloumn){
                    return 0 != cloumn;
                }
            };
            try {
                ResultSet set = PublicUtil.dao.checkTicket(PublicUtil.dao.getAccountSequenceByAccountID(userId),false);
                PublicUtil.setTable(this.trainServiceTable,set,modal);
            } catch (SQLException ex) {
                int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    return;
                }
            }
        }
        else {
            //得到票的序号，并进行退票
            int index1 = trainServiceTable.getSelectedRow();//获取选中的行
            int key = (int) trainServiceTable.getValueAt(index1, 0);
            System.out.println(key);
            try {
                PublicUtil.dao.ticketReturn(key);
            } catch (SQLException ex) {
                int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    return;
                }
            }
            JOptionPane.showMessageDialog(this,"退票成功！","提示",JOptionPane.WARNING_MESSAGE);
        }
    }

    private void orderTicketBtnClicked(ActionEvent e) {
        //先检测表格是否显示的车次信息（因为上方的我的当前订票和历史订票会显示车票信息），若显示车次信息则可以开始执行订票流程，如果不是则提示用户点击刷新车次列表按钮
        //addTicket(in theAccountSequence int, in theTrainServiceSequence int, in thePrize float)
        if (tableType == 0){
            try {
                PublicUtil.orderTicket(trainServiceTable,userId);
            } catch (SQLException ex) {
                int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(this,"请刷新表格！","提示",JOptionPane.WARNING_MESSAGE);
        }
    }


    private void myTicketHistoryMenuItemClicked(ActionEvent e) {
        //直接在下方表格输出 procedure checkTicket(in theAccountSequence int, in outOfDate boolean) 第二个参数传入true
        //查看用户购买的已经发车的车票，也就是已经作废的车票
        tableType = 2;
        var modal = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row,int cloumn){
                return 0 != cloumn;
            }
        };
        try {
            ResultSet set = PublicUtil.dao.checkTicket(PublicUtil.dao.getAccountSequenceByAccountID(userId),true);
            PublicUtil.setTable(this.trainServiceTable,set,modal);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private void flushTrainServiceBtnClicked(ActionEvent e) {
        // 刷新当前车次列表，使用view VIEW_ValidTrainService
        tableType = 0;
        var modal = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row,int cloumn){
                return 0 != cloumn;
            }
        };
        try {
            ResultSet set = PublicUtil.dao.viewValidTrainService();
            PublicUtil.setTable(this.trainServiceTable,set,modal);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private void addNewAccountClicked(ActionEvent e) {
        passengerAddNewAccountInterface.setVisible(true);
    }

    private void personalInfoMenuItemClicked(ActionEvent e) {
        personalInfoInterface.setVisible(true);
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        menuBar1 = new JMenuBar();
        myCurrentTicketMenuItem = new JMenuItem();
        myTicketHistoryMenuItem = new JMenuItem();
        modifyTicketMenuItem = new JMenuItem();
        refundMenuItem = new JMenuItem();
        addNewAccountMenuItem = new JMenuItem();
        personalInfoMenuItem = new JMenuItem();
        scrollPane1 = new JScrollPane();
        trainServiceTable = new JTable();
        panel1 = new JPanel();
        orderTicketBtn = new JButton();
        flushTrainServiceBtn = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("\u7528\u6237\u4e3b\u754c\u9762");
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== menuBar1 ========
        {

            //---- myCurrentTicketMenuItem ----
            myCurrentTicketMenuItem.setText("\u6211\u7684\u5f53\u524d\u8ba2\u7968");
            myCurrentTicketMenuItem.setPreferredSize(new Dimension(20, 21));
            myCurrentTicketMenuItem.addActionListener(e -> myCurrentTicketMenuItemClicked(e));
            menuBar1.add(myCurrentTicketMenuItem);

            //---- myTicketHistoryMenuItem ----
            myTicketHistoryMenuItem.setText("\u6211\u7684\u5386\u53f2\u8ba2\u7968");
            myTicketHistoryMenuItem.addActionListener(e -> myTicketHistoryMenuItemClicked(e));
            menuBar1.add(myTicketHistoryMenuItem);

            //---- modifyTicketMenuItem ----
            modifyTicketMenuItem.setText("\u6539\u7b7e");
            modifyTicketMenuItem.setPreferredSize(new Dimension(20, 21));
            modifyTicketMenuItem.addActionListener(e -> changeTicketMenuItemClicked(e));
            menuBar1.add(modifyTicketMenuItem);

            //---- refundMenuItem ----
            refundMenuItem.setText("\u9000\u7968");
            refundMenuItem.setPreferredSize(new Dimension(20, 21));
            refundMenuItem.addActionListener(e -> refundMenuItmClicked(e));
            menuBar1.add(refundMenuItem);

            //---- addNewAccountMenuItem ----
            addNewAccountMenuItem.setText("\u6dfb\u52a0\u65b0\u8d26\u53f7");
            addNewAccountMenuItem.addActionListener(e -> addNewAccountClicked(e));
            menuBar1.add(addNewAccountMenuItem);

            //---- personalInfoMenuItem ----
            personalInfoMenuItem.setText("\u6211\u7684\u4e2a\u4eba\u4fe1\u606f");
            personalInfoMenuItem.addActionListener(e -> personalInfoMenuItemClicked(e));
            menuBar1.add(personalInfoMenuItem);
        }
        setJMenuBar(menuBar1);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(trainServiceTable);
        }
        contentPane.add(scrollPane1);

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout());

            //---- orderTicketBtn ----
            orderTicketBtn.setText("\u8ba2\u7968");
            orderTicketBtn.addActionListener(e -> orderTicketBtnClicked(e));
            panel1.add(orderTicketBtn);

            //---- flushTrainServiceBtn ----
            flushTrainServiceBtn.setText("\u5237\u65b0\u8f66\u6b21\u5217\u8868");
            flushTrainServiceBtn.addActionListener(e -> flushTrainServiceBtnClicked(e));
            panel1.add(flushTrainServiceBtn);
        }
        contentPane.add(panel1);
        setSize(815, 415);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JMenuBar menuBar1;
    private JMenuItem myCurrentTicketMenuItem;
    private JMenuItem myTicketHistoryMenuItem;
    private JMenuItem modifyTicketMenuItem;
    private JMenuItem refundMenuItem;
    private JMenuItem addNewAccountMenuItem;
    private JMenuItem personalInfoMenuItem;
    private JScrollPane scrollPane1;
    private JTable trainServiceTable;
    private JPanel panel1;
    private JButton orderTicketBtn;
    private JButton flushTrainServiceBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
