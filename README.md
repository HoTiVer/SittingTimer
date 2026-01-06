# SittingTimer

A lightweight and minimalistic JavaFX timer designed for monitoring time on your computer.

[![Latest Release](https://img.shields.io/github/v/release/hotiver/SittingTimer?label=Download%20EXE)](https://github.com/hotiver/SittingTimer/releases/latest)
##  Project Goal
This project was created **primarily for personal use**. 
I needed a simple tool to remind me to take a break, 
without being overloaded with unnecessary features and ads.

If this proves useful to anyone else, I'd be delighted!
---

##  How to Build the Project into an .EXE

To get a full-fledged installer for Windows, follow these two simple steps.

### 1. Build the JAR file
First, you need to compile the project and combine all 
dependencies into a single executable JAR. Use Maven:

```
mvn clean install
```
Command to build in exe file by using jpackage:
```
jpackage --input target --name SittingTimer --main-jar SittingTimer.jar --main-class org.hotiver.sittingtimer.Launcher --type app-image --dest out --icon src/main/resources/app-icon.ico
```

After this, I advise you to move it anywhere except the desktop,
and then you can move it anywhere

###  Features
Tray-based operation: The application doesn't interfere with your 
taskbar. After launching, it minimizes to the system tray (next to the clock),
where you can manage it.

Duplicate protection: To prevent you from accidentally running 10 timers simultaneously,
the application uses a network port check.

Note: When you first launch the application,
Windows Firewall may ask for permission. This is normal;
the application doesn't require internet access;
it simply uses a local port to ensure that one instance is already running.

Current status: This is an early version.
There are some minor issues that will be fixed over time,
but for now, they don't interfere with basic use.