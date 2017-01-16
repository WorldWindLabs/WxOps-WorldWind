
<img src="https://wxops.joomla.com/images/assets/header_images/wxops-logo-sm.png" height="100"/>
# WorldWind Earth Application
Authors: Vanessa Haley & Peter Subacz 
---------------------------------------------

##1. Introduction
This Worldwind Earth application is built using NASA WorldWind with support from WxOps Inc. This WorldWind Earth application visualizes weather data and peforms animation by extending the NASA WorldWind JAVA Client. The main functions of this application allow users 1) load KML & KMZ, 2) manipulate the WorldWind Camera or Point of View (POV), and 3) animate KML with standard TimeSpans. The following hyperlink is an example of animation and weather data.

Animation Example: https://www.youtube.com/watch?v=od7pc1fOsoo

---------------------------------------------
##2. Installation & Getting Started

How to import the project into NETBEANS 8.2 IDE and run the main class.

1. Download the project files on github.com
2. Add the project to NETBEANS IDE by selecting: File>Import Project>From ZIP...
3. Once the project is imported, compile the code by pressing the "Run Project" button
4. In the Projects Tab, navigate to the main classes by selecting: WxOpsWorldwindEarth>Source Packages> WxOps.Worldwind> WorldWindUI.java

How to Run WxOpsWorldwindEarth.jar from the command line.

1. Download the project files on github.com and compile in NETBEANS IDE as described above
2. Open a windows command prompt.
3. Navigate to the WxopsWorldwindEarth directory and then go to the dist subfolder
4. Type the following in the command prompt: java -jar "WxOpsWorldwindEarth.jar" 

Alternate approach using JRE 8.

1. Download and install JAVA Runtime (JRE) 8 from  http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
2. Download dist.zip from https://wxops.com/demo/dist.zip
3. Extract dist.zip into any local folder
4. Open RUNME.BAT, or Run WxOpsWorldwindEarth.jar from the command line as described above

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

The WorldWindUI has a menu allowing for importing KML and KMZ files. This can be accessed through File>Open KML Data. The user can choose either “Open File” or “Open URL.” Alternatively, “Open File” can be accessed by pressing [alt+2] and “Open URL” can be accessed by pressing [alt+3]. Choosing “Open File” will open a file chooser, defaulting to the user’s Documents folder. Selecting “Open URL” will open a popup allowing the user to type in a URL of a KML or KMZ file to open. Example data can be located at: https://wxops.com/demo/

#####3.4 Animation 


The WorldWindUI has elements to start and stop a time animation if a full KML or KMZ file contains a TimeSpan element. For the animation functionality to work correctly, the KML or KMZ file to animate must have its TimeSpan “Begin” and “End” values match the corresponding values assigned to the variables “start” and “end”. For the animation functionality to work correctly, the KML or KMZ file to animate must have its TimeSpan “Begin” and “End” values match the corresponding values assigned to the variables “start” and “end”. These time variables can be input into the corresponding text fields, if the values do not match, the animation will not function properly. Worldwind Earth has its default animation timestamps and delta set to allow for the demo animation "Hurricane Dolly" to function properly. 

Input must match the following format Example: 2008-07-23T18:02:00Z where (Year)-(month)-(date)T(Hour):Minute(Second)Z

Animation Data can be found at: https://wxops.com/demo under Hurrican Dolly.

Input the following data 
* Date Begin: 	2008-07-23T18:02:00Z
* Date End:	2008-07-23T18:56:00Z
* Time Delta:	9

---------------------------------------------

##4. Future Plans

* Dynamic Animation support 
* Increase KML data functionality 

---------------------------------------------

##5. Known Issues

* Kml files cannot be removed once added, however, the files can be disabled in the layer menu. 
* The icons for some KML data are unusually sized.
* Camera Control menu cannot be closed without closing the application
* Current Time UI elements do not display on iOS. 
 
---------------------------------------------

##6. Change Log

* Change Log 1/11/2017
* Reworked Animation Controls 
* Added new UI elements to allow for manual input of animation Data.
* Added Animation Help Menu, This can be accessed clicking on Help > Animation 
* Section 3.4 updated to reflect changes
