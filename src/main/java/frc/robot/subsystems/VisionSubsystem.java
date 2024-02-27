package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {

    private final NetworkTable m_limelightTable;
    // Recieve the cordination of the target
    private double             tv, ty, tx, ta;

    // Store avaiable targets for shooting
    private ArrayList<Double>  m_targetList;

    public VisionSubsystem() {
        m_limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

    }

    public double getTX() {
        return tx;
    }

    public double getTY() {
        return ty;
    }

    /** Safely stop the subsystem from moving */
    public void stop() {
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        tv = m_limelightTable.getEntry("tv").getDouble(0);
        tx = m_limelightTable.getEntry("tx").getDouble(0);
        ty = m_limelightTable.getEntry("ty").getDouble(0);
        ta = m_limelightTable.getEntry("ta").getDouble(0);



    }

    @Override
    public String toString() {
        // Create an appropriate text readable string describing the state of the
        // subsystem

        return "Tony was here... Shhhhhh, and Philip was too";
    }
}
