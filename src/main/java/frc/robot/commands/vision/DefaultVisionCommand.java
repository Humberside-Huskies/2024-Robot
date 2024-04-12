// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.vision;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;


public class DefaultVisionCommand extends LoggingCommand {

    private final double          speed;
    private final DriveSubsystem  driveSubsystem;
    private double                desiredHeadingDelta;
    private final VisionSubsystem visionSubsystem;

    private double                targetOffsetAngle_Vertical;

    /**
     * DriveForTime command drives at the specified heading at the specified speed for the specified
     * time.
     *
     * @param speed in the range -1.0 to +1.0
     * @param driveSubsystem
     */

    public DefaultVisionCommand(double speed, DriveSubsystem driveSubsystem,
        VisionSubsystem visionSubsystem) {

        this.visionSubsystem = visionSubsystem;
        this.speed           = speed;
        this.driveSubsystem  = driveSubsystem;


        // Add required subsystems
        addRequirements(driveSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

        logCommandStart();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        desiredHeadingDelta        = visionSubsystem.getTX();
        targetOffsetAngle_Vertical = visionSubsystem.getTY();
        double errorF = desiredHeadingDelta * 0.02;
        driveSubsystem.setMotorSpeeds(speed + errorF, -speed + errorF);
        System.out.println();
        SmartDashboard.putNumber("Delta", desiredHeadingDelta);
        // Nothing to do here except wait for the end
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {

        // When the command finishes, do nothing
        // NOTE: control will return to the driver
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // no targets found
        if (visionSubsystem.getTV() == 0)
            return true;
        if (visionSubsystem.getTA() >= 5)
            return true;
        if (isTimeoutExceeded(2.5))
            return true;
        return false;
    }
}
