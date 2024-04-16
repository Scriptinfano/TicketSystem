/*
 * Created by JFormDesigner on Thu Dec 28 10:39:38 CST 2023
 */

package src.view;

import src.util.PublicUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

/**
 * @author Mingxiang
 */
public class SinginInterface extends JDialog {
    public SinginInterface(Window owner) {
        super(owner);
        initComponents();
    }

    private void okBtnClicked(ActionEvent e) {
        // 注册界面：SignInInterface
        String thePassword=passswordTextField.getText();
        String theIdentity= identityTextField.getText();
        if (thePassword.isEmpty() || theIdentity.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入内容后重试", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            PublicUtil.dao.addAccount(3,null,thePassword,theIdentity);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "登录成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel2 = new JPanel();
        label2 = new JLabel();
        passswordTextField = new JTextField();
        panel4 = new JPanel();
        label6 = new JLabel();
        identityTextField = new JTextField();
        panel6 = new JPanel();
        okButton = new JButton();

        //======== this ========
        setTitle("\u6ce8\u518c");
        setModal(true);
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== panel2 ========
        {
            panel2.setLayout(new FlowLayout());

            //---- label2 ----
            label2.setText("\u5bc6    \u7801\uff1a");
            panel2.add(label2);

            //---- passswordTextField ----
            passswordTextField.setColumns(10);
            panel2.add(passswordTextField);
        }
        contentPane.add(panel2);

        //======== panel4 ========
        {
            panel4.setLayout(new FlowLayout());

            //---- label6 ----
            label6.setText("\u8eab\u4efd\u8bc1\u53f7\u7801;");
            panel4.add(label6);

            //---- identityTextField ----
            identityTextField.setColumns(18);
            panel4.add(identityTextField);
        }
        contentPane.add(panel4);

        //======== panel6 ========
        {
            panel6.setLayout(new FlowLayout());

            //---- okButton ----
            okButton.setText("\u786e\u8ba4");
            okButton.addActionListener(e -> okBtnClicked(e));
            panel6.add(okButton);
        }
        contentPane.add(panel6);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel2;
    private JLabel label2;
    private JTextField passswordTextField;
    private JPanel panel4;
    private JLabel label6;
    private JTextField identityTextField;
    private JPanel panel6;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
