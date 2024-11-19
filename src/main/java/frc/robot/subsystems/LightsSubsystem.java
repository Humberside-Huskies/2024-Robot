package frc.robot.subsystems;

import java.util.Random;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LightsConstants;

public class LightsSubsystem extends SubsystemBase {

    // The motors on the left side of the drive.
    private final AddressableLED       led                  = new AddressableLED(LightsConstants.LED_PORT);
    private final AddressableLEDBuffer ledBuffer            = new AddressableLEDBuffer(LightsConstants.NUM_LEDS);

    private int                        rainbowFirstPixelHue = 0;
    private boolean                    firstTime            = true;

    private boolean                    HasNote;
    private boolean                    isClimb;


    /** Creates a new LedSubsystem. */
    public LightsSubsystem() {

        led.setLength(ledBuffer.getLength());

        led.setData(ledBuffer);
        led.start();

        this.setLEDColor(0, 0, 0);
    }

    // Set the LEDs on or off when you have a note
    public void setNote(boolean hasNote) {

        this.HasNote = hasNote;
        // setLEDRainbow();
        // setLEDTony2();
        // setLEDRainbow();
        // return;
        setLEDTony999();


        if (DriverStation.isTeleop() && DriverStation.getMatchTime() < 30) {
            // setLEDTony4();
            if (this.HasNote = hasNote) {
                setLEDTony3();
            }
            else {
                setLEDTony999();
            }
            return;
        }

        if (HasNote) {
            // setLEDPhilip();
            setLEDColor(0, 255, 0);
            // setLEDTony2();
        }
        else {
            // setLEDColor(255, 0, 0);
            setLEDRainbow();

        }
    }

    public void setClimb(boolean isClimb) {
        this.isClimb = isClimb;
    }


    // Set the led color


    private void setLEDColor(int red, int green, int blue) {

        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            this.ledBuffer.setRGB(i, red, green, blue);
        }

        this.led.setData(ledBuffer);
    }

    // RGB transitioning
    public void setLEDRainbow() {
        rainbowFirstPixelHue -= 1;

        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            int hue = (rainbowFirstPixelHue + (i * 180 / this.ledBuffer.getLength())) % 180;
            this.ledBuffer.setHSV(i, hue, 255, 120);
            // Check bounds

        }
        rainbowFirstPixelHue += 3;
        rainbowFirstPixelHue %= 180;

        this.led.setData(ledBuffer);
    }

    public void setLEDTony() {
        Random rand = new Random();

        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            this.ledBuffer.setHSV(i, rand.nextInt(255), 255, 120);
        }

        this.led.setData(this.ledBuffer);

    }

    // random red lights
    public void setLEDTony2() {
        Random rand = new Random();

        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            Color color = this.ledBuffer.getLED(i);
            if (color.red >= 1) {
                if (rand.nextDouble() < 0.05)
                    this.ledBuffer.setRGB(i, 0, 0, 0);
            }
            else
                this.ledBuffer.setRGB(i, (int) (color.red * 255) + 5, 0, 0);
        }

        this.led.setData(this.ledBuffer);
    }

    public void setLEDTony999() {
        Random rand = new Random();

        if (rand.nextDouble() < 0.01) {
            int bufferIndex = rand.nextInt(this.ledBuffer.getLength());
            int hue         = rand.nextInt(179);

            for (int i = bufferIndex - 5; i < bufferIndex + 5; i++) {
                this.ledBuffer.setHSV(Math.floorMod(i, this.ledBuffer.getLength()), hue, 255,
                    Math.max(255 - 65 * Math.abs(bufferIndex - i), 0));
            }
        }

        this.led.setData(this.ledBuffer);
    }

    // tony2 but green
    public void setLEDTony3() {
        Random rand = new Random();

        for (int i = 0; i < this.ledBuffer.getLength(); i++) {
            if (rand.nextDouble() < 0.1) {
                Color color = this.ledBuffer.getLED(i);

                if (color.green != 1)
                    this.ledBuffer.setRGB(i, 0, 255, 0);
                else
                    this.ledBuffer.setRGB(i, 0, 0, 0);

            }
        }

        this.led.setData(this.ledBuffer);

    }

    public double[] rgb_to_hsv(double r, double g, double b) {

        // R, G, B values are divided by 255
        // to change the range from 0..255 to 0..1
        r = r / 255.0;
        g = g / 255.0;
        b = b / 255.0;

        // h, s, v = hue, saturation, value
        double cmax = Math.max(r, Math.max(g, b)); // maximum of r, g, b
        double cmin = Math.min(r, Math.min(g, b)); // minimum of r, g, b
        double diff = cmax - cmin;                 // diff of cmax and cmin.
        double h    = -1, s = -1;

        // if cmax and cmax are equal then h = 0
        if (cmax == cmin)
            h = 0;

        // if cmax equal r then compute h
        else if (cmax == r)
            h = (60 * ((g - b) / diff) + 360) % 360;

        // if cmax equal g then compute h
        else if (cmax == g)
            h = (60 * ((b - r) / diff) + 120) % 360;

        // if cmax equal b then compute h
        else if (cmax == b)
            h = (60 * ((r - g) / diff) + 240) % 360;

        // if cmax equal zero
        if (cmax == 0)
            s = 0;
        else
            s = (diff / cmax) * 100;

        // compute v
        double v = cmax * 100;

        return new double[] { h, s, v };
    }

    public void setLEDFlashGreen() {


        for (int i = 0; i < this.ledBuffer.getLength(); i++) {


            Color color = this.ledBuffer.getLED(i);

            if (color.green != 1)
                this.ledBuffer.setRGB(i, 0, 255, 0);
            else
                this.ledBuffer.setRGB(i, 0, 0, 0);


        }

        this.led.setData(this.ledBuffer);

    }

    public void setLEDPhilip() {
        if (firstTime) {
            for (int index = 0; index < this.ledBuffer.getLength(); index++) {
                this.ledBuffer.setLED(index, new Color(Math.random(), Math.random(), Math.random()));
            }
            firstTime = false;
        }
        for (int index = 0; index < this.ledBuffer.getLength(); index++) {
            this.ledBuffer.setLED(index, randomColorShift(this.ledBuffer.getLED(index)));
        }
        this.led.setData(this.ledBuffer);

    }

    private Color randomColorShift(Color aColor) {
        return new Color(randomShift(aColor.red), randomShift(aColor.green), randomShift(aColor.blue));
    }

    private double randomShift(double value) {
        double sign   = Math.random() >= 0.5 ? 1.0 : -1.0;
        double amount = Math.random() / 10;
        return MathUtil.clamp(value + sign * amount, 0, 1);
    }

    public boolean isAnimated() {
        return true;
    }


    @Override
    public void periodic() {
        /*
         * Update all dashboard values in the periodic routine
         */
        // if (isClimb)
        // setLEDPhilip();
        // else {
        // setLEDColor(255, 0, 0);
        // }
    }

    @Override
    public String toString() {
        // Create an appropriate text readable string describing the state of the subsystem

        return "Ryder changed the thing Vlad changed which was belonged Ryder's... Shhhhhhh";
    }

}
