package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem extends SubsystemBase {

    // The motors on the left side of the drive.
    private final CANSparkMax armMotor = new CANSparkMax(ArmConstants.ARM_MOTOR_PORT, MotorType.kBrushless);

    public double             armSpeed = 0;

    /** Creates a new ArmSubsystem. */
    public ArmSubsystem() {

        // We need to invert one side of the drivetrain so that positive voltages
        // result in both sides moving forward. Depending on how your robot's
        // gearbox is constructed, you might have to invert the left side instead.
    }

    /**
     * Set the arm speed of the arm motor
     *
     * @param armSpeed
     */
    public void setArmSpeed(double armSpeed) {

        this.armSpeed = armSpeed;

        armMotor.set(this.armSpeed);
    }

    /** Safely stop the subsystem from moving */
    public void stop() {
        setArmSpeed(0);
    }

    @Override
    public void periodic() {

        /*
         * Update all dashboard values in the periodic routine
         */
        SmartDashboard.putNumber("Arm Motor", this.armSpeed);
    }

    @Override
    public String toString() {

        // Create an appropriate text readable string describing the state of the subsystem
        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName())
            .append(" ").append(Math.round(armSpeed * 100.0d) / 100.0d);


        return sb.toString();
    }

}
