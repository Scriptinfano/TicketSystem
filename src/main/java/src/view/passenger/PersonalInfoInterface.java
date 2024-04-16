/*
 * Created by JFormDesigner on Thu Dec 28 16:03:06 CST 2023
 */

package src.view.passenger;

import src.util.PublicUtil;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;

/**
 * @author Mingxiang
 */
public class PersonalInfoInterface extends JDialog {
    private String account;
    public PersonalInfoInterface(Window owner,String account) {
        super(owner);
        initComponents();
        this.account = account;
        showInfo();
    }

    private void modifyBtnClicked(ActionEvent e) {
        // 用户点击后开放各编辑框的编辑权限，并将此按钮的文本改为结束编辑，点击结束编辑之后将数据传给数据库并锁住编辑权限，并将文本改回开始编辑
        // procedure modifyPersonalInfo(in theAccountType tinyint, in theAccountSequence int, in theName varchar(20), in theGender tinyint, in theAge tinyint, in theIdentificationId varchar(18), in theContactInfo varchar(18), in thePaymentMethod varchar(20))
        if (e.getActionCommand() == "开始编辑") {
            textField1.setEnabled(true);
            textField3.setEnabled(true);
            textField4.setEnabled(true);
            textField5.setEnabled(true);
            comboBox1.setEnabled(true);
            paymentMethodComboBox.setEnabled(true);
            modifyBtn.setText("保存");
        }
        if (e.getActionCommand() == "保存") {
            textField1.setEnabled(false);
            textField3.setEnabled(false);
            textField4.setEnabled(false);
            textField5.setEnabled(false);
            comboBox1.setEnabled(false);
            paymentMethodComboBox.setEnabled(false);
            modifyBtn.setText("开始编辑");
            try {
                int i = 0;
                if (comboBox1.getSelectedItem() == "男") i = 1;
                else i = 2;
                PublicUtil.dao.modifyPersonalInfo(2, PublicUtil.dao.getAccountSequenceByAccountID(account),textField1.getText(),i,Integer.parseInt(textField3.getText()),textField5.getText(),textField4.getText(),(String) paymentMethodComboBox.getSelectedItem());
            } catch (SQLException ex) {
                int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }
    }

    private void showInfo(){
        try {
            ResultSet set =  PublicUtil.dao.checkAccountInfo(2,PublicUtil.dao.getAccountSequenceByAccountID(account));
            set.next();
            textField1.setText(set.getString("姓名"));
            if (set.getInt("性别") == 1) {
                comboBox1.setSelectedItem(1);
            }
            else {
                comboBox1.setSelectedItem(2);
            }
            textField3.setText(String.valueOf(set.getInt("年龄")));
            textField4.setText(set.getString("联系方式"));
            textField5.setText(set.getString("身份证号"));
            if (set.getString("支付方式").equals("微信")) {
                paymentMethodComboBox.setSelectedItem(1);
            }
            else {
                paymentMethodComboBox.setSelectedItem(2);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
        textField1.setEnabled(false);
        textField3.setEnabled(false);
        textField4.setEnabled(false);
        textField5.setEnabled(false);
        comboBox1.setEnabled(false);
        paymentMethodComboBox.setEnabled(false);
    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - abcde
        panel1 = new JPanel();
        label1 = new JLabel();
        textField1 = new JTextField();
        panel2 = new JPanel();
        label2 = new JLabel();
        comboBox1 = new JComboBox<>();
        panel3 = new JPanel();
        label3 = new JLabel();
        textField3 = new JTextField();
        panel4 = new JPanel();
        label4 = new JLabel();
        textField4 = new JTextField();
        panel5 = new JPanel();
        label5 = new JLabel();
        textField5 = new JTextField();
        panel7 = new JPanel();
        label6 = new JLabel();
        paymentMethodComboBox = new JComboBox<>();
        panel6 = new JPanel();
        modifyBtn = new JButton();

        //======== this ========
        setTitle("\u4f1a\u5458\u4e2a\u4eba\u4fe1\u606f\u7ba1\u7406");
        setModal(true);
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== panel1 ========
        {
            panel1.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder
            ( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion", javax. swing. border. TitledBorder. CENTER, javax. swing. border
            . TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ), java. awt
            . Color. red) ,panel1. getBorder( )) ); panel1. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void
            propertyChange (java .beans .PropertyChangeEvent e) {if ("bord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException( )
            ; }} );
            panel1.setLayout(new FlowLayout());

            //---- label1 ----
            label1.setText("\u59d3\u540d");
            panel1.add(label1);

            //---- textField1 ----
            textField1.setColumns(10);
            panel1.add(textField1);
        }
        contentPane.add(panel1);

        //======== panel2 ========
        {
            panel2.setLayout(new FlowLayout());

            //---- label2 ----
            label2.setText("\u6027\u522b");
            panel2.add(label2);

            //---- comboBox1 ----
            comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
                "\u7537",
                "\u5973"
            }));
            panel2.add(comboBox1);
        }
        contentPane.add(panel2);

        //======== panel3 ========
        {
            panel3.setLayout(new FlowLayout());

            //---- label3 ----
            label3.setText("\u5e74\u9f84");
            panel3.add(label3);

            //---- textField3 ----
            textField3.setColumns(5);
            panel3.add(textField3);
        }
        contentPane.add(panel3);

        //======== panel4 ========
        {
            panel4.setLayout(new FlowLayout());

            //---- label4 ----
            label4.setText("\u8054\u7cfb\u65b9\u5f0f");
            panel4.add(label4);

            //---- textField4 ----
            textField4.setColumns(20);
            panel4.add(textField4);
        }
        contentPane.add(panel4);

        //======== panel5 ========
        {
            panel5.setLayout(new FlowLayout());

            //---- label5 ----
            label5.setText("\u8eab\u4efd\u8bc1\u53f7");
            panel5.add(label5);

            //---- textField5 ----
            textField5.setColumns(20);
            panel5.add(textField5);
        }
        contentPane.add(panel5);

        //======== panel7 ========
        {
            panel7.setLayout(new FlowLayout());

            //---- label6 ----
            label6.setText("\u652f\u4ed8\u65b9\u5f0f");
            panel7.add(label6);

            //---- paymentMethodComboBox ----
            paymentMethodComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                "\u5fae\u4fe1",
                "\u652f\u4ed8\u5b9d"
            }));
            panel7.add(paymentMethodComboBox);
        }
        contentPane.add(panel7);

        //======== panel6 ========
        {
            panel6.setLayout(new FlowLayout());

            //---- modifyBtn ----
            modifyBtn.setText("\u5f00\u59cb\u7f16\u8f91");
            modifyBtn.addActionListener(e -> modifyBtnClicked(e));
            panel6.add(modifyBtn);
        }
        contentPane.add(panel6);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - abcde
    private JPanel panel1;
    private JLabel label1;
    private JTextField textField1;
    private JPanel panel2;
    private JLabel label2;
    private JComboBox<String> comboBox1;
    private JPanel panel3;
    private JLabel label3;
    private JTextField textField3;
    private JPanel panel4;
    private JLabel label4;
    private JTextField textField4;
    private JPanel panel5;
    private JLabel label5;
    private JTextField textField5;
    private JPanel panel7;
    private JLabel label6;
    private JComboBox<String> paymentMethodComboBox;
    private JPanel panel6;
    private JButton modifyBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
