/*
 * Created by JFormDesigner on Fri Dec 29 09:36:01 CST 2023
 */

package src.view;

import src.util.PublicUtil;
import src.view.sadmin.SuperAdminMainInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Mingxiang
 */
public class SuperAdminLoginInterface extends JDialog {
    private final LoginInterface father;
    public SuperAdminLoginInterface(LoginInterface father) {
        initComponents();
        this.father=father;
    }



    private void loginBtnClicked(ActionEvent e) {
        String password = String.valueOf(passwordTextField.getPassword());
        if (password.equals(PublicUtil.SUPERUSER)) {
            new SuperAdminMainInterface().setVisible(true);
            JOptionPane.showMessageDialog(this, "登录成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }else {
           JOptionPane.showMessageDialog(this,"登录失败","错误",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnBtnClicked(ActionEvent e) {
        father.setVisible(true);
        this.setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        label1 = new JLabel();
        passwordTextField = new JPasswordField();
        loginBtn = new JButton();
        button1 = new JButton();

        //======== this ========
        setTitle("\u8d85\u7ea7\u7ba1\u7406\u5458\u767b\u5f55\u754c\u9762");
        setModal(true);
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout());

            //---- label1 ----
            label1.setText("\u8f93\u5165\u7ba1\u7406\u5458\u5bc6\u7801");
            panel1.add(label1);

            //---- passwordTextField ----
            passwordTextField.setColumns(20);
            panel1.add(passwordTextField);

            //---- loginBtn ----
            loginBtn.setText("\u767b\u5f55");
            loginBtn.addActionListener(e -> loginBtnClicked(e));
            panel1.add(loginBtn);

            //---- button1 ----
            button1.setText("\u8fd4\u56de");
            button1.addActionListener(e -> returnBtnClicked(e));
            panel1.add(button1);
        }
        contentPane.add(panel1);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JLabel label1;
    private JPasswordField passwordTextField;
    private JButton loginBtn;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
