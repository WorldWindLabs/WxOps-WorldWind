
<img src="https://wxops.joomla.com/images/assets/header_images/wxops-logo-sm.png" height="100"/>
# WorldWind Earth Application
Authors: Vanessa Haley & Peter Subacz 
---------------------------------------------

##1. Introduction
This Worldwind Earth application is built using NASA WorldWind with support from WxOps Inc. This WorldWind Earth application visualizes weather data and peforms animation by extending the NASA WorldWind JAVA Client. The main functions of this application allow users 1) load KML & KMZ, 2) manipulate the WorldWind Camera or Point of View (POV), and 3) animate KML with standard TimeSpans. The following hyperlink is an example of animation and weather data.

Animation Example: https://www.youtube.com/watch?v=od7pc1fOsoo

---------------------------------------------
##2. Installation & Getting Started

How to import the project into NETBEANS 8.2 IDE and run the main class:

1. Download the project files on github.com
2. Add the project to NETBEANS IDE by selecting: File>Import Project>From ZIP
3. Once the project is imported, compile the code by pressing the "Run Project" button
4. In the Projects Tab, navigate to the main classes by selecting: WxOpsWorldwindEarth>Source Packages> WxOps.Worldwind> WorldWindUI.java

How to Run WxOpsWorldwindEarth.jar from the command line:

1. Download the project files on github.com and compile in NETBEANS IDE as described above
2. Open a windows command prompt.
3. Navigate to the WxopsWorldwindEarth directory and then go to the dist subfolder
4. Type the following in the command prompt: java -jar "WxOpsWorldwindEarth.jar" 

Alternate approach using JRE 8:

1. Download and install JAVA Runtime (JRE) 8 from  http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
2. Download dist.zip from https://wxops.com/demo/dist.zip
3. Extract dist.zip into any local folder
4. Open RUNME.BAT, or Run WxOpsWorldwindEarth.jar from the command line as described above

---------------------------------------------

##3. How to Use

Start the program using one of the methods provided in the section above. The active UI for WorldWind comes with four main onscreen elements as well a bottom panel that reports information to the user. The first elements are as follows:

* World Map - The World Map element is in the top left corner of the screen that reports the location of the center of the screen.
* Compass - The Compass element is in the top right and reports the direction towards North as well as the tilt of the globe.
* View Controls - The Scale Bar element is in the lower right corner, and it dynamically reports the general scale of the WorldWind globe. 
* Scale Bar - The View Controls element is in the bottom left corner, and this allows users to use on screen buttons to change the camera incrementally.
* Layer Menu - This menu located on the left allows users to toggle different layers within the WorldWind Application. This menu is dynamically updated when a new KML or KMZ file is added to the application

####3.1 Mouse Control

NASA WorldWind natively supports mouse control to manipulate the globe. The following is a list of functions. These functions can also be accessed via the onscreen buttons located on the bottom left of the screen. WorldWind also natively supports touch controls for touch-enabled devices (not described here).
* Left Click on Globe.... Moves globe and the camera view coordinates so that the new location is centered on screen
* Left Click Drag....... Spins the Globe.
* Right Click Drag ..... Changes the Roll and Tilt of the camera view
* Mouse Wheel........... Causes the camera to zoom in or out to change the altitude of the globe.

####3.2 Camera Manipulation 

The WorldwindUI is provisioned with a JPanel that allows users to input coordinates and camera settings to control the viewing angle and preferences, aka Point Of View (POV). This interface can be accessed from the Menu using "View > Camera Controls" or by pressing [alt+f4]. Camera Controls reports the camera settings when users click the “Get Camera” button. To manipulate the camera through this interface, enter the coordinates/settings desired into the text boxes and click the “Set Camera” button. The default camera settings are:

**Default Camera Control Input**
* Long: -75.00000
* Lat: 38.00000
* Alt: 19070000.0
* Roll: 0.000
* Tilt: 0.000
* Azi: 0.000
* F.O.V.: 45.000

####3.3 KML Support

The WorldWindUI has a menu allowing for importing KML and KMZ files. This can be accessed through Menu item File. The user can choose either “Open KML File” or “Open KML Url.” Alternatively, “Open KML File” can be accessed by pressing [alt+2] and “Open KML Url” can be accessed by pressing [alt+3]. “Open KML File” will open a file chooser, defaulting to the user’s Documents folder. “Open KML Url” will open a popup allowing the user enter the Url of a KML or KMZ file. 
Example data are provided for testing at http://wxops.com/demo/
Defaults are preloaded for convenience:
* https://wxops.com/demo/Dolly.kmz = test data with TimeSpans (for Animation)
* https://wxops.com/demo/WWE-11.kmz = test data without TimeSpans (Placemark, GroundOverlay, COLLADA Model)

####3.4 Animation 

The WorldWindUI has elements to start and stop a time animation when any KML or KMZ file contains a TimeSpan element. Unlike other geobrowsers such as Google Earth, the current displayed Valid Time can be set to any value, regardless of the TimeSpans for any layers that are loaded and/or visible. For animation to show data on the Canvas, the KML or KMZ file to animate must have its TimeSpan “Begin” and “End” values match the corresponding values assigned to the variables “start” and “end”. These time variables can be input into the corresponding text fields. Worldwind Earth has its default animation timestamps and delta set to allow for the demo animation of "Hurricane Dolly". 

Time information must match the following format where (Year)-(month)-(day)T(Hour):(Minute):(Second)Z
Example: 2008-07-23T18:02:00Z 

For Hurricane Dolly test KMZ, enter the following: 
* Date Begin: 	2008-07-23T18:02:00Z
* Date End:	2008-07-23T18:56:00Z
* Time Delta:	9

####3.5 COM API Emulation 

The COM API emulation is currently initiated when the user invokes Camera Controls.  This is an advanced topic beyond the scope of this introduction.  Please refer to the user manual embedded in the github repository. Current IApplicationGE functions emulated are GetCamera() and SetCamera().

---------------------------------------------

##4. Future Plans

* Dynamic Animation support
* COM API Emulation for EarthLib IAnimationControllerGE (all functions)
* COM API Emulation for IApplicationGE functions GetPointOnTerrainFromScreenCoordinates(), GetMainHWND(), GetRenderHWND(), and OpenKmlFile()

---------------------------------------------

##5. Known Issues

* Kml files cannot be removed once added, but are deleted upon WWE termination. 
* The icons for some KML data are unusually sized.
* Camera Controls menu cannot be closed without closing the application, but can be hidden.
* Current Time UI elements do not display on iOS. 
 
---------------------------------------------
##6. Contacts

* Dr. Scott T. Shipley, WxOps CIO, sshipley@wxops.com
* Peter Subacz, psubacz@wxops.com
