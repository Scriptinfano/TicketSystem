/*
 * Created by JFormDesigner on Tue Jan 02 21:32:19 CST 2024
 */

package src.view.passenger;

import src.util.PublicUtil;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;

/**
 * @author Mingxiang
 */
public class PassengerAddNewAccountInterface extends JDialog {
    private final String  userID;
    public PassengerAddNewAccountInterface(Window owner,String  userID) {
        super(owner);
        initComponents();
        this.userID=userID;
    }

    private void okBtnClicked(ActionEvent e) {
        try {
            PublicUtil.dao.addAccount(4, userID,passwordtextField.getText(),null);
            JOptionPane.showMessageDialog(this,"添加成功","成功",JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        label1 = new JLabel();
        passwordtextField = new JTextField();
        panel2 = new JPanel();
        okBtn = new JButton();

        //======== this ========
        setTitle("\u4f1a\u5458\u6dfb\u52a0\u65b0\u8d26\u53f7");
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout());

            //---- label1 ----
            label1.setText("\u8f93\u5165\u65b0\u8d26\u53f7\u7684\u5bc6\u7801\uff1a");
            panel1.add(label1);

            //---- passwordtextField ----
            passwordtextField.setColumns(10);
            panel1.add(passwordtextField);
        }
        contentPane.add(panel1);

        //======== panel2 ========
        {
            panel2.setLayout(new FlowLayout());

            //---- okBtn ----
            okBtn.setText("\u786e\u8ba4\u6dfb\u52a0");
            okBtn.addActionListener(e -> okBtnClicked(e));
            panel2.add(okBtn);
        }
        contentPane.add(panel2);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JLabel label1;
    private JTextField passwordtextField;
    private JPanel panel2;
    private JButton okBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
