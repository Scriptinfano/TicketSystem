/*
 * Created by JFormDesigner on Wed Dec 27 17:22:53 CST 2023
 */

package src.view.sadmin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Mingxiang
 */
public class SuperAdminMainInterface extends JFrame {
    public SuperAdminMainInterface() {
        initComponents();
    }

    private void trainDispatchingManageBtnClicked(ActionEvent e) {
        new SA_trainDispatchingManagement(this).setVisible(true);
    }

    private void stationInfoManageBtnClicked(ActionEvent e) {
        new SA_StationInfoManagement(this).setVisible(true);
    }

    private void usersManageBtnClicked(ActionEvent e) {
        new SA_UserManagement(this).setVisible(true);
    }

    private void ticketPriceManageBtnClicked(ActionEvent e) {
        new SA_TicketPrizeManagement(this).setVisible(true);
    }

    private void financialStatisticsBtnClicked(ActionEvent e) {
        new SA_FinancialStatistics(this).setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - abcde
        panel6 = new JPanel();
        trainDispatchingManageBtn = new JButton();
        panel5 = new JPanel();
        stationInfoManageBtn = new JButton();
        panel4 = new JPanel();
        usersManageBtn = new JButton();
        panel3 = new JPanel();
        ticketPriceManageBtn = new JButton();
        panel1 = new JPanel();
        financialStatisticsBtn = new JButton();

        //======== this ========
        setResizable(false);
        setTitle("\u8d85\u7ea7\u7ba1\u7406\u5458\u4e3b\u754c\u9762");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== panel6 ========
        {
            panel6.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder
            ( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion", javax. swing. border. TitledBorder. CENTER, javax. swing. border
            . TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ), java. awt
            . Color. red) ,panel6. getBorder( )) ); panel6. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void
            propertyChange (java .beans .PropertyChangeEvent e) {if ("bord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException( )
            ; }} );
            panel6.setLayout(new FlowLayout());

            //---- trainDispatchingManageBtn ----
            trainDispatchingManageBtn.setText("\u8f66\u6b21\u8c03\u5ea6\u7ba1\u7406");
            trainDispatchingManageBtn.addActionListener(e -> trainDispatchingManageBtnClicked(e));
            panel6.add(trainDispatchingManageBtn);
        }
        contentPane.add(panel6);

        //======== panel5 ========
        {
            panel5.setLayout(new FlowLayout());

            //---- stationInfoManageBtn ----
            stationInfoManageBtn.setText("\u7ad9\u70b9\u4fe1\u606f\u7ba1\u7406");
            stationInfoManageBtn.addActionListener(e -> stationInfoManageBtnClicked(e));
            panel5.add(stationInfoManageBtn);
        }
        contentPane.add(panel5);

        //======== panel4 ========
        {
            panel4.setLayout(new FlowLayout());

            //---- usersManageBtn ----
            usersManageBtn.setText("\u7528\u6237\u7ba1\u7406");
            usersManageBtn.addActionListener(e -> usersManageBtnClicked(e));
            panel4.add(usersManageBtn);
        }
        contentPane.add(panel4);

        //======== panel3 ========
        {
            panel3.setLayout(new FlowLayout());

            //---- ticketPriceManageBtn ----
            ticketPriceManageBtn.setText("\u7968\u4ef7\u7ba1\u7406");
            ticketPriceManageBtn.addActionListener(e -> ticketPriceManageBtnClicked(e));
            panel3.add(ticketPriceManageBtn);
        }
        contentPane.add(panel3);

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout());

            //---- financialStatisticsBtn ----
            financialStatisticsBtn.setText("\u8d22\u52a1\u7edf\u8ba1");
            financialStatisticsBtn.addActionListener(e -> financialStatisticsBtnClicked(e));
            panel1.add(financialStatisticsBtn);
        }
        contentPane.add(panel1);
        setSize(350, 300);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - abcde
    private JPanel panel6;
    private JButton trainDispatchingManageBtn;
    private JPanel panel5;
    private JButton stationInfoManageBtn;
    private JPanel panel4;
    private JButton usersManageBtn;
    private JPanel panel3;
    private JButton ticketPriceManageBtn;
    private JPanel panel1;
    private JButton financialStatisticsBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
