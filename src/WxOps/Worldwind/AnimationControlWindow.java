package WxOps.Worldwind;

//import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import com.wxops.WxOpsKMLTimeSpan;
import gov.nasa.worldwind.*;

import javax.swing.JFrame;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


import javax.swing.*;

/**
 *
 * @author wxazygy 15 January 2017
 */
public class AnimationControlWindow extends javax.swing.JFrame{
    private int c;
    private WorldWindUI.AppFrame appFrame;
    private String ani;  //equivalent to POV
    private String responseFile = "";
    private String response = "";
    
    private Date dateBegin = WxOpsKMLTimeSpan.parseTimeString("2008-07-23T18:02:00Z");
    private Date dateEnd = WxOpsKMLTimeSpan.parseTimeString("2008-07-23T18:56:00Z");
    private Date date = new Date(dateBegin.getTime());
    private int dateStep = 6;  //minutes
    private long dateDelta = dateStep * 60000; //(dateEnd.getTime() - dateBegin.getTime()) / dateSteps; 
     
    public AnimationControlWindow(WorldWindUI.AppFrame appFrame) {
        this.appFrame = appFrame;
        this.appFrame.setCurrentDate(date);
        initComponents();
        if (c<1) {
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
        this.setTitle("ANI");
        //initialize ANI params 
        
         System.out.println("date = " + date);
         System.out.println("dateBegin = " + dateBegin);
         System.out.println("dateEnd = " + dateEnd);
         System.out.println("dateSteps = " + dateStep);
         System.out.println("dateDelta = " + dateDelta);
         
    }
    
    @SuppressWarnings("unchecked")
     // <editor-fold defaultstate="collapsed" desc="Generated Code">      
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();  //Valid DateTime
        jLabel2 = new javax.swing.JLabel();  //Start DateTime
        jLabel3 = new javax.swing.JLabel();  //End DateTime
        jLabel4 = new javax.swing.JLabel();  //Time Step (Delta, hours)
        jLabel5 = new javax.swing.JLabel();  //Frame Rate (Int per second)
        //jLabel6 = new javax.swing.JLabel();  //Azi
        //jLabel7 = new javax.swing.JLabel();  //FOV
        jButton1 = new javax.swing.JButton();  //Rewind
        jButton2 = new javax.swing.JButton();  //Step
        jButton3 = new javax.swing.JButton();  //Forward to End
        
        jCheckBox1 = new javax.swing.JCheckBox(); //Animate
        jLabel6 = new javax.swing.JLabel();
        
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        //jTextField6 = new javax.swing.JTextField();
        //jTextField7 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Valid DateTime");
        jLabel2.setText("Begin DateTime");
        jLabel3.setText("End DateTime");
        jLabel4.setText("Time Step [min]");
        jLabel5.setText("Frame Dwell [msec]");
        //jLabel6.setText("Azimuth");
        //jLabel7.setText("F.O.V.");

        jButton1.setText("Rewind to Begin");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Step Forward");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        
        jButton3.setText("Forward to End");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        
        jCheckBox1.setText("ANI Timer");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel6.setText(" Animation Control Panel");

        jTextField1.setText("2008-07-23T18:02:00Z");
        jTextField2.setText("2008-07-23T18:02:00Z");
        jTextField3.setText("2008-07-23T18:56:00Z");
        jTextField4.setText("9");
        jTextField5.setText("1000");
        //jTextField6.setText("(Null)");
        //jTextField7.setText("(Null)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jCheckBox1)
                    //.addComponent(jLabel7)
                    //.addComponent(jLabel6)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField1)
                    .addComponent(jTextField2)
                    //.addComponent(jTextField6)
                    //.addComponent(jTextField7)
                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                    .addComponent(jTextField4)
                    .addComponent(jTextField5))
                .addGap(28, 28, 28))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                //.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                //.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                //    .addComponent(jLabel6)
                //    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                //.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                //.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                //    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                //    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox1)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold> 
    
    //REWIND TO BEGIN
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        jCheckBox1.setSelected(false); //stop ANI    
        String begin = jTextField2.getText();
        jTextField1.setText(begin);
        dateBegin = WxOpsKMLTimeSpan.parseTimeString(begin);
        date.setTime(dateBegin.getTime());
        refreshANI();
    }  
    
    //STEP FORWARD
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {   
        jCheckBox1.setSelected(false); //stop ANI
        //recalculate dateDelta
        dateStep = Integer.parseInt(jTextField4.getText());
        dateDelta = dateStep * 60000;
        
        date.setTime(date.getTime() + dateDelta);  //add millisec
        //jTextField1.setText("2008-07-23T18:30:00Z");
        refreshANI();   
    }  
    
    //FORWARD TO END
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {   
        jCheckBox1.setSelected(false); //stop ANI
        String end = jTextField3.getText();
        jTextField1.setText(end);
        dateEnd = WxOpsKMLTimeSpan.parseTimeString(end);
        date.setTime(dateEnd.getTime());
        refreshANI();
    }  
    
    private Timer twx2;
    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
    if (jCheckBox1.isSelected()) {
            //recalculate dateDelta
            dateStep = Integer.parseInt(jTextField4.getText());
            dateDelta = dateStep * 60000;
        
            String timerDelta = jTextField5.getText();  //step in millisec
            int foo = Integer.parseInt(timerDelta);
            twx2 = new Timer(foo, new AnimationListener());
            twx2.start();
        } else {
            twx2.stop();
        }
    } 
   
    public void refreshANI() {
        df.setTimeZone(TimeZone.getTimeZone("UTC"));        
        jCheckBox1.setText(df.format(date));
        //    jTextArea1.setText(date.toString()); // print the sting a text file on screen. to know when dates/time
        appFrame.setCurrentDate(date);
        appFrame.getWwd().redraw();
    }
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        
    class AnimationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            //String hh = String.format("%02d", now.get(Calendar.HOUR_OF_DAY));
            //String mm = String.format("%02d", now.get(Calendar.MINUTE));
            //String ss = String.format("%02d", now.get(Calendar.SECOND));
            //String ms = String.format("%03d", now.get(Calendar.MILLISECOND));
            //jCheckBox1.setText("" + hh + ":" + mm + ":" + ss + "." + ms + "Z");
            
            //update animation 
            date.setTime(date.getTime() + dateDelta);  //add millisec
            if (date.compareTo(dateEnd) > 0) {
                date.setTime(dateBegin.getTime());
            }
            refreshANI();
        }
        
 
        
    } //ClockListener

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    //private javax.swing.JLabel jLabel6;
    //private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration 
}
