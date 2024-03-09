package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LightsConstants;

public class LightsSubsystem extends SubsystemBase {

    // The motors on the left side of the drive.
    private final AddressableLED       led                  = new AddressableLED(LightsConstants.LED_PORT);
    private final AddressableLEDBuffer ledBuffer            = new AddressableLEDBuffer(LightsConstants.NUM_LEDS);

    private int                        rainbowFirstPixelHue = 0;

    /** Creates a new LedSubsystem. */
    public LightsSubsystem() {

        led.setLength(ledBuffer.getLength());

        led.setData(ledBuffer);
        led.start();
    }

    // Set the LEDs on or off when you have a note
    public void setNote(boolean hasNote) {

        if (hasNote) {
            setLEDColor(0, 255, 0);
        }
        else {
            setLEDColor(255, 0, 0);
        }
    }

    /**
     * Set the led color
     */
    private void setLEDColor(int red, int green, int blue) {

        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            this.ledBuffer.setRGB(i, red, green, blue);
        }

        this.led.setData(ledBuffer);
    }

    public void setLEDRainbow() {
        rainbowFirstPixelHue -= 5;

        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            int hue = (rainbowFirstPixelHue + (i * 180 / this.ledBuffer.getLength())) % 180;
            this.ledBuffer.setHSV(i, hue, 255, 120);

            rainbowFirstPixelHue += 3;
            // Check bounds
            rainbowFirstPixelHue %= 180;
        }

        this.led.setData(ledBuffer);
    }

    @Override
    public void periodic() {
        /*
         * Update all dashboard values in the periodic routine
         */
    }

    @Override
    public String toString() {
        // Create an appropriate text readable string describing the state of the subsystem

        return "Tony was here... Shhhhhhh";
    }

}
