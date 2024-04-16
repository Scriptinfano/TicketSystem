/*
 * Created by JFormDesigner on Tue Jan 02 16:01:35 CST 2024
 */

package src.view.sadmin;

import src.util.PublicUtil;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;

/**
 * @author HuiYan
 */
public class changeUser extends JDialog {
    String accountID;
    public changeUser(Window owner,String accountID) {
        super(owner);
        this.accountID = accountID;
        initComponents();
    }

    private void button1(ActionEvent e) {
        // TODO add your code here
        try {
            PublicUtil.dao.modifyManagerAccountInfo(PublicUtil.dao.getAccountSequenceByAccountID(accountID),this.textField2.getText(),this.textField1.getText());
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
        textField1 = new JTextField();
        panel2 = new JPanel();
        label2 = new JLabel();
        textField2 = new JTextField();
        panel3 = new JPanel();
        button1 = new JButton();

        //======== this ========
        setTitle("\u4fee\u6539\u8d26\u6237");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout());

            //---- label1 ----
            label1.setText("\u7ba1\u7406\u5458\u7528\u6237\u540d\uff1a");
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
            label2.setText("\u65b0\u5bc6\u7801\uff1a");
            panel2.add(label2);

            //---- textField2 ----
            textField2.setColumns(13);
            panel2.add(textField2);
        }
        contentPane.add(panel2);

        //======== panel3 ========
        {
            panel3.setLayout(new FlowLayout());

            //---- button1 ----
            button1.setText("\u786e\u5b9a");
            button1.addActionListener(e -> button1(e));
            panel3.add(button1);
        }
        contentPane.add(panel3);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JLabel label1;
    private JTextField textField1;
    private JPanel panel2;
    private JLabel label2;
    private JTextField textField2;
    private JPanel panel3;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
