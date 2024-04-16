/*
 * Created by JFormDesigner on Wed Dec 27 17:22:47 CST 2023
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
public class SA_UserManagement extends JDialog implements TableInitializeNeeded {
    changeUser changeuser;

    /**
     * 加载账户列表
     */
    @Override
    public void initializeTable() {
        //输出select 1 VIEW_AccountList where 账户类型=管理员账号;
        var modal = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row,int cloumn){
                return 0 != cloumn;
            }
        };
        try {
            ResultSet set = PublicUtil.dao.viewAccountList();
            PublicUtil.setTable(this.accountTable,set,modal);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public SA_UserManagement(Window owner) {
        super(owner);
        initComponents();
        initializeTable();
    }

    private void addAccountBtnClicked(ActionEvent e) {
        String password = String.valueOf(passwordTextField.getPassword());

        if (isNewAdminComboBox.isSelected()) {
            //添加新管理员
            String identityId = accountIDOrIdentityIDTextField.getText();
            try {
                PublicUtil.dao.addAccount(1, null, password, identityId);
            } catch (SQLException ex) {
                int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    return;
                }
            }
        } else if (!isNewAdminComboBox.isSelected()) {
            //为老管理员添加新账号
            String accountID = accountIDOrIdentityIDTextField.getText();
            try {
                PublicUtil.dao.addAccount(2, accountID,password,null);
            } catch (SQLException ex) {
                int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    return;
                }
            }
        }
        initializeTable();
    }

    private void deleteAccountBtnClicked(ActionEvent e) {
        int index1 = accountTable.getSelectedRow();//获取选中的行
        int key = (int) accountTable.getValueAt(index1, 0);
        System.out.println(key);
        try {
            PublicUtil.dao.deleteAccount(key);
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

    private void modifyManagerNameOrPasswordBtnClicked(ActionEvent e) {

        int index1 = accountTable.getSelectedRow();//获取选中的行
        String key = (String) accountTable.getValueAt(index1, 1);

        changeuser = new changeUser(this,key);
        changeuser.setVisible(true);
        initializeTable();
    }

    private void searchAccountBtnClicked(ActionEvent e) {
        //收集信息
        String accounId = (accountIDTextFiled.getText());
        String accountType = (String) accountTypeComboBox.getSelectedItem();
        String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
        String accountName = userNameSearchTextField.getText();
        String identityId = identityIDTextField.getText();
        String theName = nameTextField.getText();
        String theGender = (String) genderComboBox.getSelectedItem();
        Integer theAge;
        try{
            theAge = Integer.parseInt(ageTextField.getText());
        }catch (NumberFormatException ex){
            theAge=null;
        }
        var modal = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row,int cloumn){
                return 0 != cloumn;
            }
        };
        try {
            ResultSet set = PublicUtil.dao.searchAccount(accounId,accountType,paymentMethod,accountName,identityId,theName,theGender,theAge);
            PublicUtil.setTable(this.accountTable,set,modal);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private void button1(ActionEvent e) {
        // TODO add your code here
        initializeTable();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel4 = new JPanel();
        scrollPane1 = new JScrollPane();
        accountTable = new JTable();
        panel1 = new JPanel();
        label7 = new JLabel();
        label1 = new JLabel();
        accountIDTextFiled = new JTextField();
        label2 = new JLabel();
        accountTypeComboBox = new JComboBox<>();
        label3 = new JLabel();
        paymentMethodComboBox = new JComboBox<>();
        label9 = new JLabel();
        userNameSearchTextField = new JTextField();
        panel7 = new JPanel();
        label10 = new JLabel();
        identityIDTextField = new JTextField();
        label11 = new JLabel();
        genderComboBox = new JComboBox<>();
        label12 = new JLabel();
        ageTextField = new JTextField();
        label13 = new JLabel();
        nameTextField = new JTextField();
        searchAccountBtn = new JButton();
        panel3 = new JPanel();
        deleteAccountBtn = new JButton();
        modifyManagerUserNameOrPasswordBtn = new JButton();
        button1 = new JButton();
        panel5 = new JPanel();
        panel6 = new JPanel();
        label4 = new JLabel();
        panel8 = new JPanel();
        label5 = new JLabel();
        passwordTextField = new JPasswordField();
        panel11 = new JPanel();
        isNewAdminComboBox = new JCheckBox();
        panel12 = new JPanel();
        label8 = new JLabel();
        accountIDOrIdentityIDTextField = new JTextField();
        panel2 = new JPanel();
        addAccountBtn = new JButton();

        //======== this ========
        setResizable(false);
        setTitle("\u8d85\u7ea7\u7ba1\u7406\u5458\u8d26\u6237\u7ba1\u7406");
        setModal(true);
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

        //======== panel4 ========
        {
            panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(accountTable);
            }
            panel4.add(scrollPane1);

            //======== panel1 ========
            {
                panel1.setLayout(new FlowLayout());

                //---- label7 ----
                label7.setText("\u68c0\u7d22\u6761\u4ef6");
                panel1.add(label7);

                //---- label1 ----
                label1.setText("\u8d26\u6237ID");
                panel1.add(label1);

                //---- accountIDTextFiled ----
                accountIDTextFiled.setColumns(10);
                panel1.add(accountIDTextFiled);

                //---- label2 ----
                label2.setText("\u8d26\u6237\u7c7b\u578b");
                panel1.add(label2);

                //---- accountTypeComboBox ----
                accountTypeComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                    "\u7ba1\u7406\u5458\u8d26\u53f7",
                    "\u4f1a\u5458\u8d26\u53f7"
                }));
                accountTypeComboBox.setSelectedIndex(-1);
                panel1.add(accountTypeComboBox);

                //---- label3 ----
                label3.setText("\u652f\u4ed8\u65b9\u5f0f");
                panel1.add(label3);

                //---- paymentMethodComboBox ----
                paymentMethodComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                    "\u5fae\u4fe1\u652f\u4ed8",
                    "\u652f\u4ed8\u5b9d"
                }));
                paymentMethodComboBox.setSelectedIndex(-1);
                panel1.add(paymentMethodComboBox);

                //---- label9 ----
                label9.setText("\u7528\u6237\u540d");
                panel1.add(label9);

                //---- userNameSearchTextField ----
                userNameSearchTextField.setColumns(5);
                panel1.add(userNameSearchTextField);
            }
            panel4.add(panel1);

            //======== panel7 ========
            {
                panel7.setLayout(new FlowLayout());

                //---- label10 ----
                label10.setText("\u8eab\u4efd\u8bc1\u53f7");
                panel7.add(label10);

                //---- identityIDTextField ----
                identityIDTextField.setColumns(10);
                panel7.add(identityIDTextField);

                //---- label11 ----
                label11.setText("\u6027\u522b");
                panel7.add(label11);

                //---- genderComboBox ----
                genderComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                    "\u7537",
                    "\u5973"
                }));
                genderComboBox.setSelectedIndex(-1);
                panel7.add(genderComboBox);

                //---- label12 ----
                label12.setText("\u5e74\u9f84");
                panel7.add(label12);

                //---- ageTextField ----
                ageTextField.setColumns(5);
                panel7.add(ageTextField);

                //---- label13 ----
                label13.setText("\u59d3\u540d");
                panel7.add(label13);

                //---- nameTextField ----
                nameTextField.setColumns(5);
                panel7.add(nameTextField);

                //---- searchAccountBtn ----
                searchAccountBtn.setText("\u67e5\u8be2");
                searchAccountBtn.addActionListener(e -> searchAccountBtnClicked(e));
                panel7.add(searchAccountBtn);
            }
            panel4.add(panel7);

            //======== panel3 ========
            {
                panel3.setLayout(new FlowLayout());

                //---- deleteAccountBtn ----
                deleteAccountBtn.setText("\u5220\u9664\u9009\u4e2d\u8d26\u6237");
                deleteAccountBtn.addActionListener(e -> deleteAccountBtnClicked(e));
                panel3.add(deleteAccountBtn);

                //---- modifyManagerUserNameOrPasswordBtn ----
                modifyManagerUserNameOrPasswordBtn.setText("\u4fee\u6539\u7ba1\u7406\u5458\u7528\u6237\u540d\u6216\u5bc6\u7801");
                modifyManagerUserNameOrPasswordBtn.addActionListener(e -> modifyManagerNameOrPasswordBtnClicked(e));
                panel3.add(modifyManagerUserNameOrPasswordBtn);

                //---- button1 ----
                button1.setText("\u5237\u65b0");
                button1.addActionListener(e -> button1(e));
                panel3.add(button1);
            }
            panel4.add(panel3);
        }
        contentPane.add(panel4);

        //======== panel5 ========
        {
            panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));

            //======== panel6 ========
            {
                panel6.setLayout(new FlowLayout());

                //---- label4 ----
                label4.setText("\u6dfb\u52a0\u65b0\u7ba1\u7406\u5458\u5b57\u6bb5");
                panel6.add(label4);
            }
            panel5.add(panel6);

            //======== panel8 ========
            {
                panel8.setLayout(new FlowLayout());

                //---- label5 ----
                label5.setText("\u5bc6\u7801");
                panel8.add(label5);

                //---- passwordTextField ----
                passwordTextField.setColumns(20);
                panel8.add(passwordTextField);
            }
            panel5.add(panel8);

            //======== panel11 ========
            {
                panel11.setLayout(new FlowLayout());

                //---- isNewAdminComboBox ----
                isNewAdminComboBox.setText("\u662f\u5426\u4e3a\u65b0\u7ba1\u7406\u5458");
                panel11.add(isNewAdminComboBox);
            }
            panel5.add(panel11);

            //======== panel12 ========
            {
                panel12.setLayout(new FlowLayout());

                //---- label8 ----
                label8.setText("\u8f93\u5165\u65b0\u7ba1\u7406\u5458\u7684\u8eab\u4efd\u8bc1\u53f7\u6216\u8001\u7ba1\u7406\u5458\u7684\u8d26\u6237ID");
                panel12.add(label8);

                //---- accountIDOrIdentityIDTextField ----
                accountIDOrIdentityIDTextField.setColumns(10);
                panel12.add(accountIDOrIdentityIDTextField);
            }
            panel5.add(panel12);

            //======== panel2 ========
            {
                panel2.setLayout(new FlowLayout());

                //---- addAccountBtn ----
                addAccountBtn.setText("\u786e\u8ba4\u6dfb\u52a0");
                addAccountBtn.addActionListener(e -> addAccountBtnClicked(e));
                panel2.add(addAccountBtn);
            }
            panel5.add(panel2);
        }
        contentPane.add(panel5);
        setSize(1015, 535);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel4;
    private JScrollPane scrollPane1;
    private JTable accountTable;
    private JPanel panel1;
    private JLabel label7;
    private JLabel label1;
    private JTextField accountIDTextFiled;
    private JLabel label2;
    private JComboBox<String> accountTypeComboBox;
    private JLabel label3;
    private JComboBox<String> paymentMethodComboBox;
    private JLabel label9;
    private JTextField userNameSearchTextField;
    private JPanel panel7;
    private JLabel label10;
    private JTextField identityIDTextField;
    private JLabel label11;
    private JComboBox<String> genderComboBox;
    private JLabel label12;
    private JTextField ageTextField;
    private JLabel label13;
    private JTextField nameTextField;
    private JButton searchAccountBtn;
    private JPanel panel3;
    private JButton deleteAccountBtn;
    private JButton modifyManagerUserNameOrPasswordBtn;
    private JButton button1;
    private JPanel panel5;
    private JPanel panel6;
    private JLabel label4;
    private JPanel panel8;
    private JLabel label5;
    private JPasswordField passwordTextField;
    private JPanel panel11;
    private JCheckBox isNewAdminComboBox;
    private JPanel panel12;
    private JLabel label8;
    private JTextField accountIDOrIdentityIDTextField;
    private JPanel panel2;
    private JButton addAccountBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
