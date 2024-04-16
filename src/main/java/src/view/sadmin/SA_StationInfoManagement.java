/*
 * Created by JFormDesigner on Wed Dec 27 17:22:42 CST 2023
 */

package src.view.sadmin;

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
public class SA_StationInfoManagement extends JDialog implements TableInitializeNeeded {
    @Override
    public void initializeTable() {
        //输出view VIEW_Station
        var modal = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row,int cloumn){
                return 0 != cloumn;
            }
        };
        try {
            ResultSet set = PublicUtil.dao.viewStation();
            PublicUtil.setTable(this.stationTable,set,modal);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public SA_StationInfoManagement(Window owner) {
        super(owner);
        initComponents();
        initializeTable();
    }



    private void modifyStationBtnClicked(ActionEvent e) {
        //procedure modifyStation(in theStationSequence int, in theStationName varchar(20))
        int index1 = stationTable.getSelectedRow();//获取选中的行
        int key = (int) stationTable.getValueAt(index1, 0);
        System.out.println(key);
        String str = stationNameTextField.getText();
        try {
            PublicUtil.dao.modifyStation(key,str);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
                return;
            }
        }
        initializeTable();
    }

    private void deleteStationBtnClicked(ActionEvent e) {
        // procedure deleteStation(in theStationSequence int)
        int index1 = stationTable.getSelectedRow();//获取选中的行
        int key = (int) stationTable.getValueAt(index1, 0);
        System.out.println(key);
        try {
            PublicUtil.dao.deleteStation(key);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
                return;
            }
        }
        initializeTable();
    }

    private void searchStationBtnClicked(ActionEvent e) {
        // procedure searchStation(in theStationName varchar(20))
        String str = stationNameTextField.getText();    //获取站点名称
        var modal = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row,int cloumn){
                return 0 != cloumn;
            }
        };
        try {
            ResultSet set = PublicUtil.dao.searchStation(str);
            PublicUtil.setTable(this.stationTable,set,modal);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private void addStationBtnClicked(ActionEvent e) {
        //procedure addStation(in theStationName varchar(20))
        String str = stationNameTextField.getText();    //获取站点名称
        try {
            PublicUtil.dao.addStation(str);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
                return;
            }
        }
        initializeTable();
    }

    private void button1(ActionEvent e) {
        // TODO add your code here
        initializeTable();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        scrollPane1 = new JScrollPane();
        stationTable = new JTable();
        panel2 = new JPanel();
        label1 = new JLabel();
        stationNameTextField = new JTextField();
        panel1 = new JPanel();
        addStationBtn = new JButton();
        modifyStationBtn = new JButton();
        deleteStationBtn = new JButton();
        searchStationBtn = new JButton();
        button1 = new JButton();

        //======== this ========
        setTitle("\u8d85\u7ea7\u7ba1\u7406\u5458\u7ad9\u70b9\u4fe1\u606f\u7ba1\u7406");
        setModal(true);
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(stationTable);
        }
        contentPane.add(scrollPane1);

        //======== panel2 ========
        {
            panel2.setLayout(new FlowLayout());

            //---- label1 ----
            label1.setText("\u65b0\u52a0\u6216\u68c0\u7d22\u7ad9\u70b9\u540d\uff1a");
            panel2.add(label1);

            //---- stationNameTextField ----
            stationNameTextField.setColumns(7);
            panel2.add(stationNameTextField);
        }
        contentPane.add(panel2);

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout());

            //---- addStationBtn ----
            addStationBtn.setText("\u6dfb\u52a0");
            addStationBtn.addActionListener(e -> addStationBtnClicked(e));
            panel1.add(addStationBtn);

            //---- modifyStationBtn ----
            modifyStationBtn.setText("\u7f16\u8f91");
            modifyStationBtn.addActionListener(e -> modifyStationBtnClicked(e));
            panel1.add(modifyStationBtn);

            //---- deleteStationBtn ----
            deleteStationBtn.setText("\u5220\u9664");
            deleteStationBtn.addActionListener(e -> deleteStationBtnClicked(e));
            panel1.add(deleteStationBtn);

            //---- searchStationBtn ----
            searchStationBtn.setText("\u67e5\u8be2");
            searchStationBtn.addActionListener(e -> searchStationBtnClicked(e));
            panel1.add(searchStationBtn);

            //---- button1 ----
            button1.setText("\u5237\u65b0");
            button1.addActionListener(e -> button1(e));
            panel1.add(button1);
        }
        contentPane.add(panel1);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JScrollPane scrollPane1;
    private JTable stationTable;
    private JPanel panel2;
    private JLabel label1;
    private JTextField stationNameTextField;
    private JPanel panel1;
    private JButton addStationBtn;
    private JButton modifyStationBtn;
    private JButton deleteStationBtn;
    private JButton searchStationBtn;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
