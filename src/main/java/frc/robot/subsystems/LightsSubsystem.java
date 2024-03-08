package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LightsConstants;

public class LightsSubsystem extends SubsystemBase {

    // The motors on the left side of the drive.
    private final AddressableLED       led       = new AddressableLED(LightsConstants.LED_PORT);
    private final AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(LightsConstants.NUM_LEDS);

    /** Creates a new LedSubsystem. */
    public LightsSubsystem() {

        led.setData(ledBuffer);
        led.start();
    }

    // Set the LEDs on or off when you have a note
    public void setNote(boolean hasNote) {

        if (hasNote) {
            setLEDColor(255, 15, 0);
        }
        else {
            setLEDColor(0, 0, 0);
        }
    }

    /**
     * Set the led color
     *
     * @param red Red component (0 to 1)
     * @param green Green Component (0 to 1)
     * @param blue Blue Component (0 to 1)
     */
    private void setLEDColor(double red, double green, double blue) {

        int r = (int) (red * 255);
        int g = (int) (green * 255);
        int b = (int) (blue * 255);

        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            this.ledBuffer.setRGB(i, r, g, b);
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
