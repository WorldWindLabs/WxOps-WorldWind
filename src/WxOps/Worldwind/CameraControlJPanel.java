/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WxOps.Worldwind;

/**
 *
 * @author PC
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gov.nasa.worldwind.View;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;

public class CameraControlJPanel extends JFrame {

    private WorldWindow wwd;
    private JButton buttonSetPOV = new JButton("Set POV");
    private JButton buttonGetPOV = new JButton("Get POV");
    private JCheckBox jCheckBox1;
    private JLabel labelLong = new JLabel("Longitude");
    private JLabel labelLat = new JLabel("Latitude");
    private JLabel labelAlt = new JLabel("Altitude");
    private JLabel labelRoll = new JLabel("Roll");
    private JLabel labelTilt = new JLabel("Tilt");
    private JLabel labelAzi = new JLabel("Azimuth");
    private JLabel labelFOV = new JLabel("FOV");
    private JLabel label;
    // Use Formated text field instead?
    private JTextField textLong = new JTextField(20);
    private JTextField textLat = new JTextField(20);
    private JTextField textAlt = new JTextField(20);
    private JTextField textRoll = new JTextField(20);
    private JTextField textTilt = new JTextField(20);
    private JTextField textAzi = new JTextField(20);
    private JTextField textFOV = new JTextField(20);

    public CameraControlJPanel(WorldWindow wwd) {
        super("Camera Control Menu");
        this.wwd = wwd;

        JPanel CameraControlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // add components to the panel
        //Long
        constraints.gridx = 0;
        constraints.gridy = 0;
        CameraControlPanel.add(labelLong, constraints);

        constraints.gridx = 1;
        CameraControlPanel.add(textLong, constraints);

        //Lat
        constraints.gridx = 0;
        constraints.gridy = 1;
        CameraControlPanel.add(labelLat, constraints);

        constraints.gridx = 1;
        CameraControlPanel.add(textLat, constraints);

        //Alt
        constraints.gridx = 0;
        constraints.gridy = 2;
        CameraControlPanel.add(labelAlt, constraints);

        constraints.gridx = 1;
        CameraControlPanel.add(textAlt, constraints);

        //Roll
        constraints.gridx = 0;
        constraints.gridy = 3;
        CameraControlPanel.add(labelRoll, constraints);

        constraints.gridx = 1;
        CameraControlPanel.add(textRoll, constraints);

        //Tilt
        constraints.gridx = 0;
        constraints.gridy = 4;
        CameraControlPanel.add(labelTilt, constraints);

        constraints.gridx = 1;
        CameraControlPanel.add(textTilt, constraints);

        //Azi
        constraints.gridx = 0;
        constraints.gridy = 5;
        CameraControlPanel.add(labelAzi, constraints);

        constraints.gridx = 1;
        CameraControlPanel.add(textAzi, constraints);
        //FOV
        constraints.gridx = 0;
        constraints.gridy = 6;
        CameraControlPanel.add(labelFOV, constraints);

        constraints.gridx = 1;
        CameraControlPanel.add(textFOV, constraints);

        constraints.gridx = 0;
        constraints.gridy = 7;
        CameraControlPanel.add(labelLat, constraints);

        constraints.gridx = 1;
        CameraControlPanel.add(textLat, constraints);

        //Buttons are here
        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.WEST;
        CameraControlPanel.add(buttonGetPOV, constraints);
        buttonGetPOV.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                buttonGetPOVActionPerformed(ae);
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.WEST;
        CameraControlPanel.add(buttonSetPOV, constraints);
        buttonSetPOV.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                buttonSetPOVActionPerformed(ae);
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        // set border for the panel
        CameraControlPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Camera Controls"));

        // add the panel to this frame
        add(CameraControlPanel);

        pack();
        setLocationRelativeTo(null);

    }

    private void buttonGetPOVActionPerformed(java.awt.event.ActionEvent ae) {

        View view = this.wwd.getView();
        Position p = view.getEyePosition();

        //lat and lon
        textLong.setText(String.format("%.5f", p.longitude.degrees)); //lon
        textLat.setText(String.format("%.5f", p.latitude.degrees)); //lat

        double z = p.getElevation();
        textAlt.setText(String.format("%.1f", z));

        double roll = view.getRoll().degrees;
        textRoll.setText(String.format("%.3f", roll));

        //tilt which is pitch in WWJ
        double tilt = view.getPitch().degrees;
        textTilt.setText(String.format("%.3f", tilt));

        //azimuth which is heading in WWJ
        double azi = view.getHeading().degrees;
        textAzi.setText(String.format("%.3f", azi));

        //field of view
        double fov = view.getFieldOfView().degrees;
        textFOV.setText(String.format("%.3f", fov));
    }

    private void buttonSetPOVActionPerformed(java.awt.event.ActionEvent ae) {
        refresh();
    }

    private void refresh() {
        BasicOrbitView view = (BasicOrbitView) this.wwd.getView();
        view.setEyePosition(Position.fromDegrees(Double.parseDouble(textLat.getText()), Double.parseDouble(textLong.getText()), Double.parseDouble(textAlt.getText())));
        view.setRoll(Angle.fromDegrees(Double.parseDouble(textRoll.getText())));
        view.setPitch(Angle.fromDegrees(Double.parseDouble(textTilt.getText())));
        view.setHeading(Angle.fromDegrees(Double.parseDouble(textAzi.getText())));
        view.setFieldOfView(Angle.fromDegrees(Double.parseDouble(textFOV.getText())));
        this.wwd.redraw();
    }
    
//
//    public static void main(String[] args) {
//        // set look and feel to the system look and feel
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new CameraControlJPanel().setVisible(true);
//            }
//        });
//    }
}
