# Example: Setting the LED Strip to a Solid Color

```java
public class LEDSubsystem extends Subsystem {
	private static final int LED_COUNT = 100;

	/**
	 * Serial communication port for sending data to the arduino over USB
	 */
	private final ArduinoConnection arduino;

	public LEDSubsystem() {
		// Initialize new connection on USB port 1, and with 100 pixels
		this.arduino = new ArduinoConnection(Port.kUSB1, LED_COUNT);
	}

	/**
	 * Sets the LED strip to a solid color
	 */
	public setSolidColor(Color color) {
		arduino.setColidColor(color);
	}
}
```

## Ok what is going on here?

We are creating a new Subsystem to manage our LED strip, and instantiating a new ArduinoConnection.

The `LEDSubsystem#setSolidColor` method takes in a color, and calls `ArduinoConnection#setColidColor` which will do all the work to serialize the data and send it to the arduino automatically.
