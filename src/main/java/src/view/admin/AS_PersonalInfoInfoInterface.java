/*
 * Created by JFormDesigner on Thu Dec 28 20:01:21 CST 2023
 */

package src.view.admin;

import src.util.PublicUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author Mingxiang
 */
public class AS_PersonalInfoInfoInterface extends JDialog {

    private int accountSequence;
    public AS_PersonalInfoInfoInterface(Window owner,int accountSequence) {
        super(owner);
        this.accountSequence = accountSequence;
        initComponents();
        initAccountInfo();

    }


    private void initAccountInfo() {
        //procedure checkAccountInfo(in theAccountType bool, in theAccountSequence int) 第一个参数写false,第二个参数写用户的序号
        //账户ID, 用户名, 姓名, 性别, 年龄, 身份证号, 联系方式, 注册时间
        ResultSet set;
        try {
            set = PublicUtil.dao.checkAccountInfo(1, accountSequence);
            set.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String theName = set.getString(3);
            int theGender = set.getInt(4);
            int theAge = set.getInt(5);
            String identityID = set.getString(6);
            String theContactInfo = set.getString(7);
            String theRegisterTime = set.getString(8);

            nameTextField.setText(theName);
            genderComboBox.setSelectedIndex(theGender - 1);
            ageTextField.setText(String.valueOf(theAge));
            identificationTextField.setText(identityID);
            contactInfoTextField.setText(theContactInfo);
            registerTimeTextField.setText(theRegisterTime);

            nameTextField.setEditable(false);
            genderComboBox.setEditable(false);
            ageTextField.setEditable(false);
            identificationTextField.setEditable(false);
            contactInfoTextField.setEditable(false);
            registerTimeTextField.setEditable(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage() + "\n编程错误，重新编程", "错误", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }


    }

    private void modifyBtnClicked(ActionEvent e) {
        // 点击修改按钮之后，开放各textField编辑权限并将此按钮的文本替换为完成修改，然后用户执行修改，修改之后再次点击完成修改，将新修改的内容传给数据库，并将编辑权限取消，然后将按钮的文本修改回‘修改’
        if(modifyBtn.getText().equals("开始修改")){
            ageTextField.setEditable(true);
            contactInfoTextField.setEditable(true);
            identificationTextField.setEditable(true);
            nameTextField.setEditable(true);
            registerTimeTextField.setEditable(true);
            genderComboBox.setEditable(true);
            modifyBtn.setText("结束修改");
            return;
        }
        if(modifyBtn.getText().equals("结束修改")){

            int theAge = Integer.parseInt(ageTextField.getText());
            int theGender= Objects.equals(genderComboBox.getSelectedItem(), "男") ?1:2;
            try {
                PublicUtil.dao.modifyPersonalInfo(1, accountSequence, nameTextField.getText(), theGender, theAge, identificationTextField.getText(), contactInfoTextField.getText(), null);
            } catch (SQLException ex) {
                int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    return;
                }
            }
            nameTextField.setEditable(false);
            genderComboBox.setEditable(false);
            ageTextField.setEditable(false);
            identificationTextField.setEditable(false);
            contactInfoTextField.setEditable(false);
            registerTimeTextField.setEditable(false);
        }

    }

    private void returnBtnClicked(ActionEvent e) {
        // 返回上一个界面
        setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        label1 = new JLabel();
        nameTextField = new JTextField();
        label2 = new JLabel();
        genderComboBox = new JComboBox<>();
        label5 = new JLabel();
        ageTextField = new JTextField();
        panel2 = new JPanel();
        label3 = new JLabel();
        identificationTextField = new JTextField();
        panel3 = new JPanel();
        label4 = new JLabel();
        contactInfoTextField = new JTextField();
        panel5 = new JPanel();
        label6 = new JLabel();
        registerTimeTextField = new JTextField();
        panel4 = new JPanel();
        modifyBtn = new JButton();
        rreturnBtn = new JButton();

        //======== this ========
        setTitle("\u7ba1\u7406\u5458\u4e2a\u4eba\u4fe1\u606f\u7ba1\u7406");
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setModal(true);
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout(FlowLayout.LEFT));

            //---- label1 ----
            label1.setText("\u59d3\u540d\uff1a");
            panel1.add(label1);

            //---- nameTextField ----
            nameTextField.setColumns(10);
            nameTextField.setEditable(false);
            panel1.add(nameTextField);

            //---- label2 ----
            label2.setText("\u6027\u522b\uff1a");
            panel1.add(label2);

            //---- genderComboBox ----
            genderComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                "\u7537",
                "\u5973"
            }));
            panel1.add(genderComboBox);

            //---- label5 ----
            label5.setText("\u5e74\u9f84\uff1a");
            panel1.add(label5);

            //---- ageTextField ----
            ageTextField.setColumns(6);
            ageTextField.setEditable(false);
            panel1.add(ageTextField);
        }
        contentPane.add(panel1);

        //======== panel2 ========
        {
            panel2.setLayout(new FlowLayout(FlowLayout.LEFT));

            //---- label3 ----
            label3.setText("\u8eab\u4efd\u8bc1\u53f7\u7801\uff1a");
            panel2.add(label3);

            //---- identificationTextField ----
            identificationTextField.setColumns(18);
            identificationTextField.setEditable(false);
            panel2.add(identificationTextField);
        }
        contentPane.add(panel2);

        //======== panel3 ========
        {
            panel3.setLayout(new FlowLayout(FlowLayout.LEFT));

            //---- label4 ----
            label4.setText("\u8054\u7cfb\u65b9\u5f0f\uff1a");
            panel3.add(label4);

            //---- contactInfoTextField ----
            contactInfoTextField.setColumns(12);
            contactInfoTextField.setEditable(false);
            panel3.add(contactInfoTextField);
        }
        contentPane.add(panel3);

        //======== panel5 ========
        {
            panel5.setLayout(new FlowLayout(FlowLayout.LEFT));

            //---- label6 ----
            label6.setText("\u6ce8\u518c\u65f6\u95f4");
            panel5.add(label6);

            //---- registerTimeTextField ----
            registerTimeTextField.setColumns(10);
            registerTimeTextField.setEditable(false);
            panel5.add(registerTimeTextField);
        }
        contentPane.add(panel5);

        //======== panel4 ========
        {
            panel4.setLayout(new FlowLayout());

            //---- modifyBtn ----
            modifyBtn.setText("\u5f00\u59cb\u4fee\u6539");
            modifyBtn.addActionListener(e -> modifyBtnClicked(e));
            panel4.add(modifyBtn);

            //---- rreturnBtn ----
            rreturnBtn.setText("\u8fd4\u56de");
            rreturnBtn.addActionListener(e -> returnBtnClicked(e));
            panel4.add(rreturnBtn);
        }
        contentPane.add(panel4);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JLabel label1;
    private JTextField nameTextField;
    private JLabel label2;
    private JComboBox<String> genderComboBox;
    private JLabel label5;
    private JTextField ageTextField;
    private JPanel panel2;
    private JLabel label3;
    private JTextField identificationTextField;
    private JPanel panel3;
    private JLabel label4;
    private JTextField contactInfoTextField;
    private JPanel panel5;
    private JLabel label6;
    private JTextField registerTimeTextField;
    private JPanel panel4;
    private JButton modifyBtn;
    private JButton rreturnBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
