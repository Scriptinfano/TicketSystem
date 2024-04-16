/*
 * Created by JFormDesigner on Thu Dec 28 19:58:10 CST 2023
 */

package src.view.sadmin;

import src.util.PublicUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mingxiang
 */
public class SA_TicketPrizeManagement extends JDialog {
    public SA_TicketPrizeManagement(Window owner) {
        super(owner);
        initComponents();
        initTextField();
    }

    /**
     * 初始化文本字段
     */
    private void initTextField() {
        ResultSet discount = null;
        try {
            discount = PublicUtil.dao.getDiscount();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        try {
            discount.next();
            float childDiscount = discount.getFloat(2);
            discount.next();
            float studentDiscount = discount.getFloat(2);
            studentDiscountTextField.setText(String.valueOf(studentDiscount));
            childDiscountTextField.setText(String.valueOf(childDiscount));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }


    private void confirmDiscountBtnClicked(ActionEvent e) {
        // procedure modifyDiscount(in theType int, in theValue float)
        if (confirmDiscountBtn.getText().equals("开始编辑")) {

            childDiscountTextField.setEditable(true);
            studentDiscountTextField.setEditable(true);
            confirmDiscountBtn.setText("结束编辑");
            return;
        }
        if (confirmDiscountBtn.getText().equals("结束编辑")) {
            String stuDiscount = studentDiscountTextField.getText();
            String childDiscount = childDiscountTextField.getText();

            try {
                PublicUtil.dao.modifyDiscount(1, Float.parseFloat(childDiscount));
                PublicUtil.dao.modifyDiscount(2, Float.parseFloat(stuDiscount));
            } catch (SQLException ex) {
                int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "转换错误警告", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this,"修改成功","提示",JOptionPane.INFORMATION_MESSAGE  );

            childDiscountTextField.setEditable(false);
            studentDiscountTextField.setEditable(false);
            confirmDiscountBtn.setText("开始编辑");

        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel2 = new JPanel();
        label2 = new JLabel();
        childDiscountTextField = new JTextField();
        label3 = new JLabel();
        panel3 = new JPanel();
        label5 = new JLabel();
        studentDiscountTextField = new JTextField();
        label6 = new JLabel();
        panel4 = new JPanel();
        confirmDiscountBtn = new JButton();

        //======== this ========
        setModal(true);
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setResizable(false);
        setTitle("\u7968\u4ef7\u7ba1\u7406");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== panel2 ========
        {
            panel2.setLayout(new FlowLayout());

            //---- label2 ----
            label2.setText("\u513f\u7ae5\u6298\u6263\uff1a");
            panel2.add(label2);

            //---- childDiscountTextField ----
            childDiscountTextField.setColumns(7);
            childDiscountTextField.setEditable(false);
            panel2.add(childDiscountTextField);

            //---- label3 ----
            label3.setText("%");
            panel2.add(label3);
        }
        contentPane.add(panel2);

        //======== panel3 ========
        {
            panel3.setLayout(new FlowLayout());

            //---- label5 ----
            label5.setText("\u5b66\u751f\u6298\u6263\uff1a");
            panel3.add(label5);

            //---- studentDiscountTextField ----
            studentDiscountTextField.setColumns(7);
            studentDiscountTextField.setEditable(false);
            panel3.add(studentDiscountTextField);

            //---- label6 ----
            label6.setText("%");
            panel3.add(label6);
        }
        contentPane.add(panel3);

        //======== panel4 ========
        {
            panel4.setLayout(new FlowLayout());

            //---- confirmDiscountBtn ----
            confirmDiscountBtn.setText("\u5f00\u59cb\u7f16\u8f91");
            confirmDiscountBtn.addActionListener(e -> confirmDiscountBtnClicked(e));
            panel4.add(confirmDiscountBtn);
        }
        contentPane.add(panel4);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel2;
    private JLabel label2;
    private JTextField childDiscountTextField;
    private JLabel label3;
    private JPanel panel3;
    private JLabel label5;
    private JTextField studentDiscountTextField;
    private JLabel label6;
    private JPanel panel4;
    private JButton confirmDiscountBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
