/*
 * Created by JFormDesigner on Wed Dec 27 17:22:40 CST 2023
 */

package src.view.sadmin;

import src.interfaces.TableInitializeNeeded;
import src.util.PublicUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

/**
 * @author Mingxiang
 */
public class SA_FinancialStatistics extends JDialog implements TableInitializeNeeded {
    @Override
    public void initializeTable() {
        try {
            PublicUtil.setTable(financialTable, PublicUtil.dao.checkFinanceTicket(), new DefaultTableModel());
            totalIncomeTextField.setText(String.valueOf(PublicUtil.dao.checkFinance()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }

    public SA_FinancialStatistics(Window owner) {
        super(owner);
        initComponents();
        initializeTable();

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        scrollPane1 = new JScrollPane();
        financialTable = new JTable();
        panel1 = new JPanel();
        label1 = new JLabel();
        totalIncomeTextField = new JTextField();
        label2 = new JLabel();

        //======== this ========
        setTitle("\u8d85\u7ea7\u7ba1\u7406\u5458\u8d22\u52a1\u7edf\u8ba1");
        setModal(true);
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(financialTable);
        }
        contentPane.add(scrollPane1);

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout(FlowLayout.RIGHT));

            //---- label1 ----
            label1.setText("\u603b\u6536\u5165\uff1a");
            panel1.add(label1);

            //---- totalIncomeTextField ----
            totalIncomeTextField.setColumns(10);
            totalIncomeTextField.setEditable(false);
            panel1.add(totalIncomeTextField);

            //---- label2 ----
            label2.setText("\u5143");
            panel1.add(label2);
        }
        contentPane.add(panel1);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JScrollPane scrollPane1;
    private JTable financialTable;
    private JPanel panel1;
    private JLabel label1;
    private JTextField totalIncomeTextField;
    private JLabel label2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
