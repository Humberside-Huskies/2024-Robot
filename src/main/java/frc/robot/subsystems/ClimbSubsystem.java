package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimbConstants;

public class ClimbSubsystem extends SubsystemBase {
    private final SparkMax rightMotor = new SparkMax(ClimbConstants.RIGHT_MOTOR_PORT, MotorType.kBrushless);
    private final SparkMax leftMotor  = new SparkMax(ClimbConstants.LEFT_MOTOR_PORT, MotorType.kBrushless);

    // Motor speeds
    private double         leftSpeed  = 0;
    private double         rightSpeed = 0;

    /** Creates a new ClimbSubsystem. */
    public ClimbSubsystem() {

        // Configure the SparkMax
        // Left and right climb are configured the same
        SparkMaxConfig config = new SparkMaxConfig();

        config.idleMode(IdleMode.kBrake)
            .disableFollowerMode();

        leftMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        rightMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        resetClimbEncoders();
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
        return rightMotor.getEncoder().getPosition();
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
