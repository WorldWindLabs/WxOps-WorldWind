
<img src="https://wxops.joomla.com/images/assets/header_images/wxops-logo-sm.png" height="100"/>
# WorldWind Earth Application
Authors: Vanessa Haley & Peter Subacz
---------------------------------------------

##1. Introduction
This Worldwind Earth application is built into NASA WorldWind with support from the WxOps Inc. This WorldWind Earth application visualizes weather data and animation in NASA Worldwind. The main functions of this application allow users to manipulate the camera of WorldWind as well as plugin KML & KMZ data into the application. The following hyperlink is an example of animation and weather data.

Animation Example: https://www.youtube.com/watch?v=od7pc1fOsoo

---------------------------------------------
##2. Installation & Getting Started

These steps outline how to run the WxOpsWorldwindEarth.jar from the command line.

1. Download the project files on github.com
2. Open a windows command prompt.
3. Navigate to the WxopsWorldwindEarth directory and then go to the dist folder
4. Next type the following in the command prompt: java -jar "WxOpsWorldwindEarth.jar" 

The steps below outline how to import the project into NETBEANS 8.2 IDE and run the main class.

1. Download the project files on github.com
2. In the NETBEANS IDE add the project by selecting: File>Import Project>From ZIP...
3. Once the project is imported, you can compile the code by pressing the "Run Project" button
4. In the Projects Tab, you can navigate to the main classes by selecting: WxOpsWorldwindEarth>Source Packages> WxOps.Worldwind> WorldWindUI.java

---------------------------------------------

##3. How to Use

Start the program using on the methods provided in the section above. The active UI for Worldwind comes with four main onscreen elements as well a bottom panel that reports information to the user. The first elements are as follows:

* World Map - The World Map element is in the top left corner of the screen that reports the location of the center of the screen.
* Compass - The Compass element is in the top right and reports the location of the north as well as the tilt of the globe.
* View Controls - The Scale Bar element is in the lower right corner, and it dynamically reports the general scale of the World Wind globe. 
* Scale Bar - The View Controls element is in the bottom left corner, and this allows users to use on screen buttons to change the camera incrementally.
* Layer Menu - This menu located on the left allows users to toggle different layers within the Worldwind Application. This menu also dynamically updated when a new KML or KMZ file is added to the application

#####3.1 Mouse Control

NASA WorldWind natively supports mouse control to manipulate the globe. The following is a list of functions. Alternatively, these functions can also be accessed via the onscreen buttons located on the bottom left of the screen.
* Left Click on Globe.... Moves globe and the camera view coordinates so that the new location is centered on screen
* Left Click Drag....... Spins the Globe.
* Right Click Drag ..... Changes the Roll and Tilt of the camera view
* Mouse Wheel........... Causes the camera to zoom in or out to change the altitude of the globe.

#####3.2 Camera manipulation 

The WorldwindUI is equiped with a menu that allows users to input coordinates and camera settings to control the viewing angle and preferences. This menu can be accessed via View>Camera Control Menu or by pressing [alt+f4]. This menu reports the camera location and settings to the users by clicking the “Get Camera” button on the menu. To manipulate the camera through this menu, type the coordinates and settings into the text boxes and click the “Set Camera” button. The default camera settings can be found below.

**Default Camera Control Input**
* Long: -75.00000
* Lat: 38.00000
* Alt: 19070000.0
* Roll: 0.000
* Azi: 0.000
* F.O.V.: 45.000

#####3.3 Kml Support

The WorldWindUI has a menu allowing for importing KML and KMZ files. This can be accessed through File>Open KML Data. The user can choose either “Open File” or “Open URL.” Alternatively, “Open File” can be accessed by pressing [alt+2] and “Open URL” can be accessed by pressing [alt+3]. Choosing “Open File” will open a file chooser, defaulting to the user’s Documents folder. Selecting “Open URL” will open a popup allowing the user to type in a URL of a KML or KMZ file to open. 

#####3.4 Animation 

The WorldWindUI has a menu item to start and stop a time animation if a full KML or KMZ file contains a TimeSpan element. This control can be accessed through Animation>Play/Stop.
For the animation functionality to work correctly, the KML or KMZ file to animate must have its TimeSpan “Begin” and “End” values match the corresponding values assigned to the variables “start” and “end” on lines 53-54 in the WorldWindUI.java class. If the values do not match, the animation will not function properly. The “begin” and “end” variables in WorldWindUI.java must be changed accordingly.

---------------------------------------------

##4. Future Plans

---------------------------------------------

##5. Known Issues

* Kml files cannot be removed once added, however, the files can be disabled in the layer menu. 
* The icons for some KML data are unusually sized.