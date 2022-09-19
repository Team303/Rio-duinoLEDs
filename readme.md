# Rioduino LEDs

This is a library for FRC that allows you to easily interface with an arduino over USB serial in order to facilitate complex LED animations. It supports a fully custom and easily extendable packet protocol that can be used to define any custom animation you can imageine with custom data from the robot code fully accessible.

The RoboRIO part of the library is written in Java (sorry C++ and LabView users), and the arduino code is written in C++.

This library is still a work in progress but I have some really big hopes.

## Feature Goals

* Set the strip contents from a `AddressableLEDBuffer` object
* Easily set a solid color for the strip
* Easily clear the strip
* Make integration with existing command based subsystems seamless
* Make it easy to define custom high speed animations and packet extensions
* Automatically handle error cases such as connection loss without crashing the main robot code