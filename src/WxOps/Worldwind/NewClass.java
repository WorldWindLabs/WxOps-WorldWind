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
import java.awt.Dialog.ModalityType;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NewClass {
   public static final int PREF_W = 600;
   public static final int PREF_H = 400;

   private static void createAndShowGui() {
      final JFrame frame = new JFrame("Main Application");
      JPanel buttonPanel = new JPanel(new GridLayout(1, 0, 5, 0));

      buttonPanel.add(new JButton(new AbstractAction("New JFrame") {
        {
           putValue(MNEMONIC_KEY, KeyEvent.VK_F);
        }

         @Override
         public void actionPerformed(ActionEvent e) {
            JFrame frame2 = new JFrame("Frame 2");
            frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setupAndDisplay(frame2);
         }
      }));
      buttonPanel.add(new JButton(new AbstractAction("New Modeless JDialog") {
        {
           putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
           JDialog dialog = new JDialog(frame, "Modeless Dialog", ModalityType.MODELESS);
           setupAndDisplay(dialog);
        }
     }));

      buttonPanel.add(new JButton(new AbstractAction("New Modal JDialog") {
        {
           putValue(MNEMONIC_KEY, KeyEvent.VK_M);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
           JDialog dialog = new JDialog(frame, "Modal Dialog", ModalityType.APPLICATION_MODAL);
           setupAndDisplay(dialog);
        }
     }));

      JPanel mainPanel = new JPanel() {
         @Override
         public Dimension getPreferredSize() {
            if (isPreferredSizeSet()) {
               return super.getPreferredSize();
            }
            return new Dimension(PREF_W, PREF_H);
         }
      };

      mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      mainPanel.add(buttonPanel);

      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.getContentPane().add(mainPanel);
      frame.pack();
      frame.setLocationByPlatform(true);
      frame.setVisible(true);
   }

   private static void setupAndDisplay(Window window) {
      window.add(Box.createRigidArea(new Dimension(200, 100)));
      window.pack();
      window.setLocationByPlatform(true);
      window.setVisible(true);
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui();
         }
      });
   }
}