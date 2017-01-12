/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WxOps.Worldwind;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author pSubacz
 */
public class AnimationHelpJPanel extends JFrame{

    private JTextArea textArea = new JTextArea("Invalid Input.\n"
            + "\n"
            + "Input must match the following format Example: 2008-07-23T18:02:00Z\n"
            + "(Year)-(month)-(date)T(Hour):Minute(Second)Z\n"
            + "\n"
            + "Animation Data can be found at: https://wxops.com/demo under Hurrican Dolly.\n"
            + "Input the following data \n"
            + "Date Begin: 	2008-07-23T18:02:00Z\n"
            + "Date End:	2008-07-23T18:56:00Z\n"
            + "Time Delta:	9");

    private JButton buttonOk = new JButton("Ok");

    public AnimationHelpJPanel() {
        super("Animation Help");

        // create a new panel with GridBagLayout manager
        JPanel aHelpPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        aHelpPanel.add(textArea, constraints);
        textArea.setEditable(false);

        constraints.gridx = 1;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        aHelpPanel.add(buttonOk, constraints);
         buttonOk.addActionListener(new ActionListener() {   //The Following code is excuted when the button is pressed
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        //AnimationHelpJPanel.EXIT_ON_CLOSE;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        // set border for the panel
        aHelpPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), "Animation Input Help"));

        // add the panel to this frame
        add(aHelpPanel);

        pack();
        setLocationRelativeTo(null);
    }
}
