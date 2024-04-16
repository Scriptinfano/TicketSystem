/*
 * Created by JFormDesigner on Wed Dec 27 17:22:38 CST 2023
 */

package src.view.sadmin;

import src.util.PublicUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;

/**
 * @author Mingxiang
 */
public class SA_AddTrainDispatching extends JDialog {
    public SA_AddTrainDispatching(Window owner) {
        super(owner);
        initComponents();
        initComboBox();
    }

    private static HashMap<String, Integer> stationName2Sequence = new HashMap<>();

    private void initComboBox() {
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
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private void okBtnClicked(ActionEvent e) {
        String startStation = (String) startStationComboBox.getSelectedItem();
        String endStation = (String) endStationComboBox.getSelectedItem();
        String trainID = trainIDTextField.getText();
        if(trainID.isEmpty()){
            JOptionPane.showMessageDialog(this,"车次号不能为空","警告",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (startStation != null  && startStation.equals(endStation)){
            JOptionPane.showMessageDialog(this,"起始站不能和终点站相同","逻辑错误",JOptionPane.WARNING_MESSAGE);
            return;
        }
        Timestamp date;
        try {
            date = PublicUtil.dao.stringToDate(departureTimeTextField.getText());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "日期转换异常", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            PublicUtil.dao.addTrainService(trainID, stationName2Sequence.get(startStation), stationName2Sequence.get(endStation), date);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        label1 = new JLabel();
        startStationComboBox = new JComboBox<>();
        label2 = new JLabel();
        endStationComboBox = new JComboBox<>();
        label9 = new JLabel();
        trainIDTextField = new JTextField();
        panel2 = new JPanel();
        label3 = new JLabel();
        departureTimeTextField = new JTextField();
        panel3 = new JPanel();
        okBtn = new JButton();

        //======== this ========
        setTitle("\u8d85\u7ea7\u7ba1\u7406\u5458\u6dfb\u52a0\u8f66\u6b21");
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout());

            //---- label1 ----
            label1.setText("\u8d77\u59cb\u7ad9\uff1a");
            panel1.add(label1);

            //---- startStationComboBox ----
            startStationComboBox.setPreferredSize(new Dimension(100, 30));
            panel1.add(startStationComboBox);

            //---- label2 ----
            label2.setText("\u7ec8\u70b9\u7ad9\uff1a");
            panel1.add(label2);

            //---- endStationComboBox ----
            endStationComboBox.setPreferredSize(new Dimension(100, 30));
            panel1.add(endStationComboBox);

            //---- label9 ----
            label9.setText("\u5217\u8f66\u53f7\uff1a");
            panel1.add(label9);

            //---- trainIDTextField ----
            trainIDTextField.setPreferredSize(new Dimension(100, 30));
            panel1.add(trainIDTextField);
        }
        contentPane.add(panel1);

        //======== panel2 ========
        {
            panel2.setLayout(new FlowLayout());

            //---- label3 ----
            label3.setText("\u8f93\u5165\u53d1\u8f66\u65f6\u95f4\uff1a");
            panel2.add(label3);

            //---- departureTimeTextField ----
            departureTimeTextField.setColumns(20);
            panel2.add(departureTimeTextField);
        }
        contentPane.add(panel2);

        //======== panel3 ========
        {
            panel3.setLayout(new FlowLayout());

            //---- okBtn ----
            okBtn.setText("\u786e\u5b9a");
            okBtn.addActionListener(e -> okBtnClicked(e));
            panel3.add(okBtn);
        }
        contentPane.add(panel3);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JLabel label1;
    private JComboBox<String> startStationComboBox;
    private JLabel label2;
    private JComboBox<String> endStationComboBox;
    private JLabel label9;
    private JTextField trainIDTextField;
    private JPanel panel2;
    private JLabel label3;
    private JTextField departureTimeTextField;
    private JPanel panel3;
    private JButton okBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
