/*
 * Created by JFormDesigner on Wed Dec 27 17:22:45 CST 2023
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Mingxiang
 */
public class SA_trainDispatchingManagement extends JDialog implements TableInitializeNeeded {
    @Override
    public void initializeTable() {

        try {
            PublicUtil.setTable(trainServiceTable, PublicUtil.dao.viewValidTrainService(), new DefaultTableModel());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private final static HashMap<String, Integer> stationName2Sequence = new HashMap<>();

    /**
     * 将站点的名字填满ComboBox
     */
    public void initComboBox() {
        if (startStationComboBox.getItemCount() != 0)
            startStationComboBox.removeAllItems();
        if (endStationComboBox.getItemCount() != 0)
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

    public SA_trainDispatchingManagement(Window owner) {
        super(owner);
        initComponents();
        initializeTable();
        initComboBox();
    }

    private void addTrain(ActionEvent e) {
        //procedure addTrainService(in theTrainServiceId varchar(20),in theStartStation int, in theEndStation int, in theDepartureTime datetime)
        new SA_AddTrainDispatching(this).setVisible(true);
    }

    private void findTrain(ActionEvent e) {
        //procedure searchTrainService(in theTrainService varchar(20), in theStartStation int, in theEndStation int, in theDepartureTime datetime)

        String startStation = (String) startStationComboBox.getSelectedItem();
        String endStation = (String) endStationComboBox.getSelectedItem();
        String trainID = trainIdTextField.getText();
        java.sql.Timestamp sqlDate;
        if (departureTimeTextField.getText().isEmpty()) sqlDate = null;
        else {
            try {
                sqlDate = str2date(departureTimeTextField.getText());
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "日期转换错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }


        try (ResultSet resultSet = PublicUtil.dao.searchTrainService(trainID, startStation, endStation, sqlDate)) {
            PublicUtil.setTable(trainServiceTable, resultSet, new DefaultTableModel());
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private java.sql.Timestamp str2date(String theDateStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(theDateStr);
        return new java.sql.Timestamp(date.getTime());
    }

    private void flushBtnClicked(ActionEvent e) {
        initializeTable();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        scrollPane1 = new JScrollPane();
        trainServiceTable = new JTable();
        panel2 = new JPanel();
        label5 = new JLabel();
        label1 = new JLabel();
        startStationComboBox = new JComboBox<>();
        label2 = new JLabel();
        endStationComboBox = new JComboBox<>();
        label3 = new JLabel();
        trainIdTextField = new JTextField();
        label4 = new JLabel();
        departureTimeTextField = new JTextField();
        searchTrainBtn = new JButton();
        panel1 = new JPanel();
        addTrainBtn = new JButton();
        flushBtn = new JButton();

        //======== this ========
        setTitle("\u8d85\u7ea7\u7ba1\u7406\u5458\u8f66\u6b21\u8c03\u5ea6\u7ba1\u7406");
        setResizable(false);
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setModal(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(trainServiceTable);
        }
        contentPane.add(scrollPane1);

        //======== panel2 ========
        {
            panel2.setLayout(new FlowLayout());

            //---- label5 ----
            label5.setText("\u68c0\u7d22\u6761\u4ef6");
            panel2.add(label5);

            //---- label1 ----
            label1.setText("\u8d77\u59cb\u7ad9\uff1a");
            panel2.add(label1);

            //---- startStationComboBox ----
            startStationComboBox.setMinimumSize(new Dimension(85, 30));
            startStationComboBox.setPreferredSize(new Dimension(100, 30));
            panel2.add(startStationComboBox);

            //---- label2 ----
            label2.setText("\u7ec8\u70b9\u7ad9\uff1a");
            panel2.add(label2);

            //---- endStationComboBox ----
            endStationComboBox.setMinimumSize(new Dimension(85, 30));
            endStationComboBox.setPreferredSize(new Dimension(100, 30));
            panel2.add(endStationComboBox);

            //---- label3 ----
            label3.setText("\u5217\u8f66\u53f7\uff1a");
            panel2.add(label3);

            //---- trainIdTextField ----
            trainIdTextField.setColumns(8);
            panel2.add(trainIdTextField);

            //---- label4 ----
            label4.setText("\u53d1\u8f66\u65f6\u95f4\uff1a");
            panel2.add(label4);

            //---- departureTimeTextField ----
            departureTimeTextField.setColumns(10);
            panel2.add(departureTimeTextField);

            //---- searchTrainBtn ----
            searchTrainBtn.setText("\u67e5\u8be2\u8f66\u6b21");
            searchTrainBtn.addActionListener(e -> findTrain(e));
            panel2.add(searchTrainBtn);
        }
        contentPane.add(panel2);

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout());

            //---- addTrainBtn ----
            addTrainBtn.setText("\u6dfb\u52a0\u8f66\u6b21");
            addTrainBtn.addActionListener(e -> addTrain(e));
            panel1.add(addTrainBtn);

            //---- flushBtn ----
            flushBtn.setText("\u5237\u65b0");
            flushBtn.addActionListener(e -> flushBtnClicked(e));
            panel1.add(flushBtn);
        }
        contentPane.add(panel1);
        setSize(820, 415);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JScrollPane scrollPane1;
    private JTable trainServiceTable;
    private JPanel panel2;
    private JLabel label5;
    private JLabel label1;
    private JComboBox<String> startStationComboBox;
    private JLabel label2;
    private JComboBox<String> endStationComboBox;
    private JLabel label3;
    private JTextField trainIdTextField;
    private JLabel label4;
    private JTextField departureTimeTextField;
    private JButton searchTrainBtn;
    private JPanel panel1;
    private JButton addTrainBtn;
    private JButton flushBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
