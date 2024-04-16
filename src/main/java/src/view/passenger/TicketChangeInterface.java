/*
 * Created by JFormDesigner on Thu Dec 28 20:08:33 CST 2023
 */

package src.view.passenger;

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
public class TicketChangeInterface extends JDialog {
    private int ticketId;

    public TicketChangeInterface(Window owner) {
        super(owner);
        initComponents();
    }

    private void okBtnClicked(ActionEvent e) {
        //modifyTicket(in theTicketSequence int, in theNewTrainServiceSequence int)
        //获取修改后的车次
        //获取选中的行的起始站点值
        if (trainServiceTable.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(this,"请选中一个车次信息","提示",JOptionPane.INFORMATION_MESSAGE );
            return;
        }
        int index1 = trainServiceTable.getSelectedRow();//获取选中的行
        int key = (int) trainServiceTable.getValueAt(index1, 0);
        System.out.println(key);
        try {
            PublicUtil.dao.modifyTicket(ticketId,key);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
                return;
            }
        }
        JOptionPane.showMessageDialog(this,"改签成功","提示",JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * 重新加载列车车次表，更新要改签的车票ID
     *
     * @param theTicketID 票证 ID
     */
    public void initTable(int theTicketID) {
        ticketId = theTicketID;
        var modal = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row,int cloumn){
                return 0 != cloumn;
            }
        };
        try {
            //通过起始站点选着车次
            ResultSet set = PublicUtil.dao.viewValidTrainService();
            PublicUtil.setTable(this.trainServiceTable,set,modal);
        } catch (SQLException ex) {
            int selected = JOptionPane.showConfirmDialog(this, ex.getMessage() + "\n是否直接退出程序", "错误", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        scrollPane1 = new JScrollPane();
        trainServiceTable = new JTable();
        panel1 = new JPanel();
        okBtn = new JButton();

        //======== this ========
        setTitle("\u6539\u7b7e");
        setModal(true);
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(trainServiceTable);
        }
        contentPane.add(scrollPane1);

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout());

            //---- okBtn ----
            okBtn.setText("\u786e\u8ba4\u6539\u7b7e");
            okBtn.addActionListener(e -> okBtnClicked(e));
            panel1.add(okBtn);
        }
        contentPane.add(panel1);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JScrollPane scrollPane1;
    private JTable trainServiceTable;
    private JPanel panel1;
    private JButton okBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
