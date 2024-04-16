/*
 * Created by JFormDesigner on Thu Dec 28 10:36:58 CST 2023
 */

package src.view;

import src.util.PublicUtil;
import src.view.admin.AdminMainInterface;
import src.view.passenger.UserMainInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

/**
 * @author Mingxiang
 */
public class LoginInterface extends JFrame {
    public static void main(String[] args) {
        LoginInterface loginPage=new LoginInterface();
        loginPage.setVisible(true);
    }
    public LoginInterface() {
        initComponents();
    }

    private final SinginInterface singinInterface=new SinginInterface(LoginInterface.this);
    private final SuperAdminLoginInterface superAdminLoginInterface=new SuperAdminLoginInterface(LoginInterface.this);

    private void signInBtnClicked(ActionEvent e) {
        singinInterface.setVisible(true);
    }

    private void loginBtnClicked(ActionEvent e) {
        //procedure login(in theAccountId varchar(20), in thePassword varchar(20), out result boolean)
        String accountID = accountIDTextField.getText();
        String password = String.valueOf(passwordTextField.getPassword());
        if (accountID.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入内容后重试", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            int[] result = PublicUtil.dao.login(accountIDTextField.getText(), String.valueOf(passwordTextField.getPassword()));
            if(result[0]==1){
                //登录成功
                this.setVisible(false);
                if(result[1]==1){
                    //管理员账户
                    int sequence = PublicUtil.dao.getAccountSequenceByAccountID(accountIDTextField.getText());
                    (new AdminMainInterface(sequence)).setVisible(true);
                }else{
                    //会员账户
                    (new UserMainInterface(accountIDTextField.getText())).setVisible(true);
                }
            }else {
                //登录失败
                JOptionPane.showMessageDialog(this,"登录失败，请重新输入密码","提示",JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
                return;
            }
        }
        JOptionPane.showMessageDialog(this,"登录成功","提示",JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private void loginSuperAdminBtnClicked(ActionEvent e) {
        this.setVisible(false);
        superAdminLoginInterface.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        label1 = new JLabel();
        accountIDTextField = new JTextField();
        panel2 = new JPanel();
        label2 = new JLabel();
        passwordTextField = new JPasswordField();
        panel3 = new JPanel();
        signInBtn = new JButton();
        loginBtn = new JButton();
        loginSuperAdminBtn = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("\u767b\u5f55\u754c\u9762");
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout());

            //---- label1 ----
            label1.setText("\u8d26\u6237ID\uff1a");
            panel1.add(label1);

            //---- accountIDTextField ----
            accountIDTextField.setColumns(15);
            accountIDTextField.setPreferredSize(new Dimension(170, 30));
            panel1.add(accountIDTextField);
        }
        contentPane.add(panel1);

        //======== panel2 ========
        {
            panel2.setLayout(new FlowLayout());

            //---- label2 ----
            label2.setText("\u5bc6   \u7801\uff1a");
            panel2.add(label2);

            //---- passwordTextField ----
            passwordTextField.setColumns(20);
            passwordTextField.setPreferredSize(new Dimension(170, 30));
            panel2.add(passwordTextField);
        }
        contentPane.add(panel2);

        //======== panel3 ========
        {
            panel3.setLayout(new FlowLayout());

            //---- signInBtn ----
            signInBtn.setText("\u6ce8\u518c");
            signInBtn.addActionListener(e -> signInBtnClicked(e));
            panel3.add(signInBtn);

            //---- loginBtn ----
            loginBtn.setText("\u767b\u5f55");
            loginBtn.addActionListener(e -> loginBtnClicked(e));
            panel3.add(loginBtn);

            //---- loginSuperAdminBtn ----
            loginSuperAdminBtn.setText("\u767b\u5f55\u8d85\u7ea7\u7ba1\u7406\u5458");
            loginSuperAdminBtn.addActionListener(e -> loginSuperAdminBtnClicked(e));
            panel3.add(loginSuperAdminBtn);
        }
        contentPane.add(panel3);
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JLabel label1;
    private JTextField accountIDTextField;
    private JPanel panel2;
    private JLabel label2;
    private JPasswordField passwordTextField;
    private JPanel panel3;
    private JButton signInBtn;
    private JButton loginBtn;
    private JButton loginSuperAdminBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
