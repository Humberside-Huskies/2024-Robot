package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {

    // The motors on the left side of the drive.
    private final CANSparkMax leftPrimaryMotor   = new CANSparkMax(DriveConstants.LEFT_MOTOR_PORT, MotorType.kBrushed);
    private final CANSparkMax leftFollowerMotor  = new CANSparkMax(DriveConstants.LEFT_MOTOR_PORT + 1, MotorType.kBrushed);

    // The motors on the right side of the drive.
    private final CANSparkMax rightPrimaryMotor  = new CANSparkMax(DriveConstants.RIGHT_MOTOR_PORT, MotorType.kBrushed);
    private final CANSparkMax rightFollowerMotor = new CANSparkMax(DriveConstants.RIGHT_MOTOR_PORT + 1, MotorType.kBrushed);

    // Motor speeds
    private double            leftSpeed          = 0;
    private double            rightSpeed         = 0;

    /** Creates a new DriveSubsystem. */
    public DriveSubsystem() {

        // We need to invert one side of the drivetrain so that positive voltages
        // result in both sides moving forward. Depending on how your robot's
        // gearbox is constructed, you might have to invert the left side instead.
        leftPrimaryMotor.setInverted(DriveConstants.LEFT_MOTOR_REVERSED);
        leftFollowerMotor.setInverted(DriveConstants.LEFT_MOTOR_REVERSED);

        // leftPrimaryMotor.setNeutralMode(NeutralMode.Brake);
        // leftFollowerMotor.setNeutralMode(NeutralMode.Brake);

        // leftFollowerMotor.follow(leftPrimaryMotor);


        rightPrimaryMotor.setInverted(DriveConstants.RIGHT_MOTOR_REVERSED);
        rightFollowerMotor.setInverted(DriveConstants.RIGHT_MOTOR_REVERSED);

        // rightPrimaryMotor.setNeutralMode(NeutralMode.Brake);
        // rightFollowerMotor.setNeutralMode(NeutralMode.Brake);

        // rightFollowerMotor.follow(rightPrimaryMotor);

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

        leftPrimaryMotor.set(leftSpeed);
        rightPrimaryMotor.set(rightSpeed);

        // NOTE: The follower motors are set to follow the primary motors
        leftFollowerMotor.set(leftSpeed);
        rightFollowerMotor.set(rightSpeed);
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
    }

    @Override
    public String toString() {

        // Create an appropriate text readable string describing the state of the subsystem
        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName())
            .append(" [").append(Math.round(leftSpeed * 100.0d) / 100.0d)
            .append(',').append(Math.round(rightSpeed * 100.0d) / 100.0d).append(']');

        return sb.toString();
    }

}
