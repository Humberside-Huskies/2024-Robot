package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimbConstants;

public class ClimbSubsystem extends SubsystemBase {
    private final CANSparkMax rightMotor = new CANSparkMax(ClimbConstants.RIGHT_MOTOR_PORT, MotorType.kBrushless);
    private final CANSparkMax leftMotor  = new CANSparkMax(ClimbConstants.LEFT_MOTOR_PORT, MotorType.kBrushless);


    // Motor speeds
    private double            leftSpeed  = 0;
    private double            rightSpeed = 0;

    /** Creates a new ClimbSubsystem. */
    public ClimbSubsystem() {
    }

    /**
     * Set the left and right speed of the primary and follower motors
     *
     * @param leftSpeed
     * @param rightSpeed
     */

    public void setMotorSpeeds(double climbSpeed) {

        // FIXME: the javadoc^^^ says there are two parameters, left and right.
        // This makes a lot of sense as you may want to control the lift separately if you are on one
        // side of the chain. Pass in two separate speeds.
        this.leftSpeed  = climbSpeed;
        this.rightSpeed = climbSpeed;

        leftMotor.set(leftSpeed);
        rightMotor.set(rightSpeed);
    }

    /** Safely stop the subsystem from moving */
    public void stop() {
        setMotorSpeeds(0);
    }

    @Override
    public void periodic() {

        /*
         * Update all dashboard values in the periodic routine
         */
        SmartDashboard.putNumber("Right Motor", rightSpeed);
        SmartDashboard.putNumber("Left  Motor", leftSpeed);
    }

    @Override
    public String toString() {

        // Create an appropriate text readable string describing the state of the subsystem
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

}
