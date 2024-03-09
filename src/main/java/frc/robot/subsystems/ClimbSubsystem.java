package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
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
        rightMotor.setIdleMode(IdleMode.kBrake);
        leftMotor.setIdleMode(IdleMode.kBrake);

        resetClimbEncoders();

        // Burn the flash at the end to permanently write the settings
        // so if the battery voltage causes a reset, the motors will
        // come back in the right state.
        rightMotor.burnFlash();
        leftMotor.burnFlash();
    }

    public void resetClimbEncoders() {
        rightMotor.getEncoder().setPosition(0);
        leftMotor.getEncoder().setPosition(0);
    }

    /**
     * Set the left and right speed of the primary and follower motors
     *
     * @param leftSpeed
     * @param rightSpeed
     */

    public void setMotorSpeeds(double leftSpeed, double rightSpeed) {

        this.leftSpeed  = leftSpeed;
        this.rightSpeed = rightSpeed;

        leftMotor.set(leftSpeed);
        rightMotor.set(rightSpeed);
    }

    public double getLeftEncoder() {
        return leftMotor.getEncoder().getPosition();
    }

    public double getRightEncoder() {
        return leftMotor.getEncoder().getPosition();
    }

    /** Safely stop the subsystem from moving */
    public void stop() {
        setMotorSpeeds(0, 0);
    }

    @Override
    public void periodic() {

        /*
         * Update all dashboard values in the periodic routine
         */
        SmartDashboard.putNumber("Right Motor", rightSpeed);
        SmartDashboard.putNumber("Left  Motor", leftSpeed);

        SmartDashboard.putNumber("Right Climb Encoder", getRightEncoder());
        SmartDashboard.putNumber("Left Climb Encoder", getLeftEncoder());

        // L and R encoders should run in the range 0-60
        SmartDashboard.putBoolean("Climb L Bottom", getLeftEncoder() > -2);
        SmartDashboard.putBoolean("Climb R Bottom", getRightEncoder() > -2);

        SmartDashboard.putBoolean("Climb L Top", getLeftEncoder() < -58);
        SmartDashboard.putBoolean("Climb R Top", getRightEncoder() < -58);
    }

    @Override
    public String toString() {

        // Create an appropriate text readable string describing the state of the subsystem
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }


}
