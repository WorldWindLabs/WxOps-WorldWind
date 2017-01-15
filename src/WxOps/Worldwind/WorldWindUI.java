/*
 * Copyright (C) 2012 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package WxOps.Worldwind;

import gov.nasa.worldwindx.examples.util.*;
import gov.nasa.worldwind.util.layertree.*;
import gov.nasa.worldwind.ogc.kml.*;
import gov.nasa.worldwind.util.*;
import static gov.nasa.worldwindx.examples.ApplicationTemplate.start;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.retrieve.RetrievalService;
import gov.nasa.worldwindx.examples.ApplicationTemplate;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.ogc.kml.impl.KMLController;
import gov.nasa.worldwindx.examples.kml.KMLApplicationController;
import gov.nasa.worldwind.render.Offset;
import gov.nasa.worldwindx.examples.util.BalloonController;
import javax.xml.stream.XMLStreamException;
import gov.nasa.worldwind.layers.ViewControlsLayer;
import java.io.IOException;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import com.wxops.*;
import gov.nasa.worldwind.util.xml.XMLEventParserContextFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.SimpleDateFormat;

// @author pSubacz & vHaley, updated wxazygy 11 Jan 2017
public class WorldWindUI extends ApplicationTemplate {

    public static class AppFrame extends ApplicationTemplate.AppFrame {

        static {
            // Register the WxOps KML parser context, giving it priority over the default KML parser context
            String[] mimeTypes = new String[]{KMLConstants.KML_MIME_TYPE, KMLConstants.KMZ_MIME_TYPE};
            XMLEventParserContextFactory.prependParserContext(mimeTypes, new WxOpsKMLParserContext(KMLConstants.KML_NAMESPACE));
        }

        protected LayerTree layerTree;
        protected RenderableLayer hiddenLayer;
        protected HotSpotController hotSpotController;
        protected KMLApplicationController kmlAppController;
        protected BalloonController balloonController;
        private WorldWindow wwd;
//        Disabled to defer varibles into the animation control popup menu        
//        protected Date begin = WxOpsKMLTimeSpan.parseTimeString("2008-07-23T18:02:00Z");
//        protected Date end = WxOpsKMLTimeSpan.parseTimeString("2008-07-23T18:56:00Z");
//        protected long delta = (end.getTime() - begin.getTime()) / 9; // divided by  due to hard coding timesteps.
//        The variables below should 
        String dateBegin = "2008-07-23T18:02:00Z";
        String dateEnd = "2008-07-23T18:56:00Z";
        protected Date begin = WxOpsKMLTimeSpan.parseTimeString(dateBegin);
        protected Date end = WxOpsKMLTimeSpan.parseTimeString(dateEnd);
        protected int subDelta = 9;
        protected long delta = (end.getTime() - begin.getTime()) / subDelta; // divided by  due to hard coding timesteps.       
        protected Date date = new Date(begin.getTime());
        protected JFormattedTextField dateTextField;
        protected Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onTimerFired();
            }
        });

        public AppFrame() {
            super(true, false, false); // Leave as is unless a the side panel for Windlayers is needed.
            this.layerTree = new LayerTree(new Offset(20d, 160d, AVKey.PIXELS, AVKey.INSET_PIXELS));
            this.layerTree.getModel().refresh(this.getWwd().getModel().getLayers());
            this.hiddenLayer = new RenderableLayer();
            this.hiddenLayer.addRenderable(this.layerTree);
            this.getWwd().getModel().getLayers().add(this.hiddenLayer);
            this.hotSpotController = new HotSpotController(this.getWwd());
            this.kmlAppController = new KMLApplicationController(this.getWwd());
            this.balloonController = new BalloonController(this.getWwd()) {
                @Override
                protected void addDocumentLayer(KMLRoot document) {
                    addKMLLayer(document);
                }
            };
            this.kmlAppController.setBalloonController(balloonController);
            //Sets the dimensions to the program
            Dimension size = new Dimension(1400, 800);
            this.setPreferredSize(size);
            this.pack();
            WWUtil.alignComponent(null, this, AVKey.CENTER);
            this.makeMenu(); //Calls the makeMenu function for the UI components 
            WorldWind.getRetrievalService().setSSLExceptionListener(new RetrievalService.SSLExceptionListener() {
                public void onException(Throwable e, String path) {
                    System.out.println(path);
                    System.out.println(e);
                }
            });
        }

        protected void makeMenu() // Main UI and Fuctions are located here.    
        {
            // The following are UI components to be used later. Note: Need to fix naming stucture
            JMenuBar menuBar;
        //sts JMenu fileSubmenu, viewSubmenu;
            JMenuItem viewMenu, editMenu, helpMenu ,openHelpItem, animationMenu, openFileMenuItem, openURLMenuItem, openCameraControlItem, playMenuItem, animationMenuItem;
            JLabel label1, label2, label3, label4, label5, label6, label7, label8, label9;
            JFormattedTextField jTextField1, jTextField2;
            JTextField jTextField4;
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("KML/KMZ File", "kml", "kmz"));

//--------------FILE-Menu------------------------------------------
            menuBar = new JMenuBar(); // Creates the menubar to work off of.
            this.setJMenuBar(menuBar);
            JMenu fileMenu = new JMenu("File"); // Adds "File" to the menu bar.
            menuBar.add(fileMenu); // Adds the "File" section to the menu bar.

//-------------File-Submenu----------------------------------------
            // The Following Code creates a menu item that has seperate items.
            fileMenu.addSeparator();
        //sts fileSubmenu = new JMenu("Open KML Data");
        //sts  fileSubmenu.setMnemonic(KeyEvent.VK_S); // Mnemonic allows for hotkeys
            // Open KML File
            // The Following section allows for the importing of KML files from any directory to the local WorldWind directory. NOTICE: IMPORTED FILES ARE NOT SAVED!
            openFileMenuItem = new JMenuItem("Open KML File..."); // This button opens a file selection tool to import a KML files
            openFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
            openFileMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        int status = fileChooser.showOpenDialog(AppFrame.this);
                        if (status == JFileChooser.APPROVE_OPTION) {
                            for (File file : fileChooser.getSelectedFiles()) {
                                new WorldWindUI.WorkerThread(file, AppFrame.this).start();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            fileMenu.add(openFileMenuItem);
            
        //sts fileSubmenu.add(openFileMenuItem);
            // Open KML URL
            // The Following section allows for the importing of KML files from a online URL address to the local WorldWind directory. NOTICE: IMPORTED FILES ARE NOT SAVED!
            openURLMenuItem = new JMenuItem("Open KML Url...");
            openURLMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
            openURLMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        String status = JOptionPane.showInputDialog(AppFrame.this, "URL","http://wxops.com/demo/WWE-11.kmz");
                        if (!WWUtil.isEmpty(status)) {
                            new WorldWindUI.WorkerThread(status.trim(), AppFrame.this).start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            fileMenu.add(openURLMenuItem);
        //sts fileSubmenu.add(openURLMenuItem);
        //sts fileMenu.add(fileSubmenu);

//--------------EDIT-Menu------------------------------------
            editMenu = new JMenu("Edit");
            editMenu.setMnemonic(KeyEvent.VK_E);
            editMenu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
            menuBar.add(editMenu);

//--------------VIEW-Menu------------------------------------     
            viewMenu = new JMenu("View");
            viewMenu.setMnemonic(KeyEvent.VK_V);
            menuBar.add(viewMenu);
            // The following code creates a pop-up window that allows users to control the camera via typing input commands. The menu can be found in the file called CameraControlWindow.java
            openCameraControlItem = new JMenuItem("Camera Controls ");
            viewMenu.add(openCameraControlItem); // Adds the Set Camera Menu, 
            openCameraControlItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.ALT_MASK)); // Sets a Hotkey to 
            openCameraControlItem.addActionListener(new ActionListener() {   //The Following code is excuted when the button is pressed
                public void actionPerformed(ActionEvent actionEvent) {
                    boolean ccwRunning = false;
                    // show/hide the Camera Control Form
                    Frame[] frames = Frame.getFrames();
                    for (int i = 0; i < frames.length; i++) {
                        String title = frames[i].getTitle();
                        if (title == "POV") {
                            ccwRunning = true;
                            boolean vis = frames[i].isVisible();
                            vis = !vis;
                            frames[i].setVisible(vis);
                        }
                        //System.out.println(title + " = " + vis);
                    }

                    if (!ccwRunning) {
                        try {
                            CameraControlWindow ccw1 = new CameraControlWindow(getWwd());
                            ccw1.setVisible(true);
//                            MARKED FOR DELETION
//                            NewCameraControlMenu QQ = new NewCameraControlMenu(getWwd());
//                            QQ.setVisible(true);
                     //sts       CameraControlJPanel CCJP = new CameraControlJPanel(getWwd());
                     //sts       CCJP.setVisible(true);
                            
//spawn a separate thread
                            Path dir = Paths.get("c:/test1");
                            // works as static process, but cannot talk to CamUI runtime
                            // *************
                            WatchDir2 watcher = new WatchDir2(dir, true);
                            watcher.setCamUI(ccw1);
                            watcher.processEvents();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
            
//--------------Help-Menu------------------------------------     
            helpMenu = new JMenu("Help");
            viewMenu.setMnemonic(KeyEvent.VK_H);
            menuBar.add(helpMenu);
            // The following code creates a pop-up window that allows users to control the camera via typing input commands. The menu can be found in the file called CameraControlWindow.java
            openHelpItem = new JMenuItem("Help");
            helpMenu.add(openHelpItem); // Adds the Set Camera Menu, 
            openCameraControlItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_7, ActionEvent.ALT_MASK));

//--------------Animation-Menu-and-Buttons------------------------------------
            //The animation Menu allows a user to play animations found within the hardcoded timestamps.  
            //Disabled, Code Below for menu buttons 
//            animationMenu = new JMenu("Animation");
//            animationMenu.setMnemonic(KeyEvent.VK_N);
//            menuBar.add(animationMenu);
////            This function menu item allows the users to toggle animations loaded in KML Files. Disables for testing
//            playmenuItem = new JMenuItem("Play/Stop ");
//            playmenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.ALT_MASK));
//            animationMenu.add(playmenuItem); // Adds play/stop button, 
//            playmenuItem.addActionListener(new ActionListener() {   //The Following code is excuted when the button is pressed
//                public void actionPerformed(ActionEvent actionEvent) {
//                    try {
//                        onPlayPressed();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            animationmenuItem = new JMenuItem("Open Animation Menu");
//            animationmenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6, ActionEvent.ALT_MASK));
//            animationMenu.add(animationmenuItem);
//            animationmenuItem.addActionListener(new ActionListener() {   //The Following code is excuted when the button is pressed
//                public void actionPerformed(ActionEvent actionEvent) {
//                    try {
////                        AnimationControlWindow test = new AnimationControlWindow(dateBegin, dateEnd, subDelta); // Bring in arguments
////                        test.setVisible(true);
////                    } catch (Exception e) {
////                        e.printStackTrace();
//                    }
//                }
//            });
// The following code creates a the animation controls in the menu bar
            label1 = new JLabel("   Animation:  ");
            menuBar.add(label1);
// Creates the labels and text fields to support typing in the animation code. 
            //Adds The Begin Date Code
            label2 = new JLabel(" Begin Date: ");
            menuBar.add(label2);//Date Begin should be formated like 2008-07-23T18:02:00Z
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            jTextField1 = new JFormattedTextField(dateFormat);
            jTextField1.setValue(begin);
            menuBar.add(jTextField1);
            // Adds the End date code
            label3 = new JLabel(" End Date: ");
            menuBar.add(label3);
            jTextField2 = new JFormattedTextField(dateFormat);//Date Begin should be formated like 2008-07-23T18:56:00Z
            jTextField2.setValue(end);
            menuBar.add(jTextField2);
            // Adds the Current date code. 
            label4 = new JLabel(" Current Date :");
            menuBar.add(label4);
            dateTextField = new JFormattedTextField(dateFormat); //Current Date should be formated like 2008-07-23T18:56:00Z
            menuBar.add(dateTextField);
            dateTextField.setValue(date);
            label5 = new JLabel(" Animation Delta: ");
            menuBar.add(label5);
            jTextField4 = new JTextField(); //Current Date should be formated like 2008-07-23T18:56:00Z
            menuBar.add(jTextField4);
            jTextField4.setText(Integer.toString(subDelta));
//          Button 2 should grab the text input from the text fields Assumeing that ther are  
            JButton button2 = new JButton(" Set Animation Arguements ");
            menuBar.add(button2, BorderLayout.PAGE_END);
            button2.addActionListener(new ActionListener() {   //The Following code is excuted when the button is pressed
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        dateBegin = jTextField1.getText();
                        dateEnd = jTextField2.getText();
                        subDelta = Integer.parseInt(jTextField4.getText());
                        delta = (end.getTime() - begin.getTime()) / subDelta;
                        begin = WxOpsKMLTimeSpan.parseTimeString(dateBegin);
                        end = WxOpsKMLTimeSpan.parseTimeString(dateEnd);
                        // system test readout
//                        System.out.println(dateBegin);
//                        System.out.println(dateEnd);
//                        System.out.println(subDelta);

                    } catch (Exception e) {
                        e.printStackTrace();
                        AnimationHelpJPanel test = new AnimationHelpJPanel(); // Bring in arguments
                        new AnimationHelpJPanel().setVisible(true);
                    }
                }
            });

            //Creates a button that will play and pause animtion
            JButton button1 = new JButton(" Animation Play/Pause ");
            menuBar.add(button1, BorderLayout.PAGE_END);
            button1.addActionListener(new ActionListener() {   //The Following code is excuted when the button is pressed
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        onPlayPressed();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
//            JButton button3 = new JButton("Rewind");
//            menuBar.add(button3, BorderLayout.PAGE_END);
//            JButton button4 = new JButton("Long-Named Button 4 (PAGE_END)");
//            menuBar.add(button1, BorderLayout.PAGE_END);


        }

        protected void onPlayPressed() {    // When the item is selected the timer is stoped and started.
            if (timer.isRunning()) {
                timer.stop();
            } else {
                timer.start();
            }
        }

        protected void onTimerFired() {
            date.setTime(date.getTime() + delta);
            if (date.compareTo(end) > 0) {
                date.setTime(begin.getTime());
            }
            //    jTextArea1.setText(date.toString()); // print the sting a text file on screen. to know when dates/time
            getWwd().redraw();
            // Consoul readout of dates
            System.out.println(date);
             System.out.println(dateBegin);
                        System.out.println(dateEnd);
                        System.out.println(subDelta);
            dateTextField.setValue(date);
        }

        protected void addKMLLayer(KMLRoot kmlRoot) {
            //KML Layers are individual files that are brought together via XML file. 
            KMLController kmlController = new KMLController(kmlRoot);
            RenderableLayer layer = new RenderableLayer();
            layer.setName((String) kmlRoot.getField(AVKey.DISPLAY_NAME));
            layer.addRenderable(kmlController);
            this.getWwd().getModel().getLayers().add(layer);
            KMLLayerTreeNode layerNode = new KMLLayerTreeNode(layer, kmlRoot);
            this.layerTree.getModel().addLayer(layerNode);
            this.layerTree.makeVisible(layerNode.getPath());
            layerNode.expandOpenContainers(this.layerTree);
            layerNode.addPropertyChangeListener(AVKey.RETRIEVAL_STATE_SUCCESSFUL, new PropertyChangeListener() {
                public void propertyChange(final PropertyChangeEvent event) {
                    if (event.getSource() instanceof KMLNetworkLinkTreeNode) {
                        // Manipulate the tree on the EDT.
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                ((KMLNetworkLinkTreeNode) event.getSource()).expandOpenContainers(layerTree);
                                getWwd().redraw();
                            }
                        });
                    }
                }
            });

            layer.setValue(WxOpsConstants.DATE, date);
        }
    }

    public static class WorkerThread extends Thread {

        protected Object kmlSource;
        protected AppFrame appFrame;

        public WorkerThread(Object kmlSource, AppFrame appFrame) {
            this.kmlSource = kmlSource;
            this.appFrame = appFrame;
        }

        public void run() {
            try {
                KMLRoot kmlRoot = this.parse();
                kmlRoot.setField(AVKey.DISPLAY_NAME, formName(this.kmlSource, kmlRoot));
                final KMLRoot finalKMLRoot = kmlRoot;
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        appFrame.addKMLLayer(finalKMLRoot);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected KMLRoot parse() throws IOException, XMLStreamException {
            return KMLRoot.createAndParse(this.kmlSource);
        }
    }

    protected static String formName(Object kmlSource, KMLRoot kmlRoot) {
        KMLAbstractFeature rootFeature = kmlRoot.getFeature();
        if (rootFeature != null && !WWUtil.isEmpty(rootFeature.getName())) {
            return rootFeature.getName();
        }

        if (kmlSource instanceof File) {
            return ((File) kmlSource).getName();
        }

        if (kmlSource instanceof String && WWIO.makeURL((String) kmlSource) != null) {
            return WWIO.makeURL((String) kmlSource).getPath();
        }

        return "KML Layer";
    }
//    These are test buttons that are not implemented.  
//    private void animationPlayButton() {
//
//        aniplayButton = new JButton("Start/Stop Animation");
//        aniplayButton.setVerticalTextPosition(AbstractButton.CENTER);
//        aniplayButton.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
//        aniplayButton.setMnemonic(KeyEvent.VK_D);
//        aniplayButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//            }
//        });
//    }
//
//    private void animationStopButton() {
//
//        anistopButton = new JButton("Start/Stop Animation");
//        anistopButton.setVerticalTextPosition(AbstractButton.CENTER);
//        anistopButton.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
//        anistopButton.setMnemonic(KeyEvent.VK_D);
//        anistopButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//
//            }
//        });
//    }

    public static void main(String[] args) {
        final AppFrame af = (AppFrame) start("WxOps Inc. WorldWind Earth 0.6.0", AppFrame.class);

        // create the CameraControlWindow and stay resident
        //    try {     
        //        final CameraControlWindow ccw = new CameraControlWindow(wwd);
        //        ccw.setVisible(true);
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
        /* Create and display the form */
    }

    // Variables declaration - do not modify   
    protected static ViewControlsLayer viewControlsLayer;
    private static WorldWindow wwd;
    protected JButton aniplayButton, anistopButton;
    Boolean animationFlag = false;
}
