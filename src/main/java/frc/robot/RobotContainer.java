// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.auto.AutonomousCommand;
import frc.robot.commands.climb.DefaultClimbCommand;
import frc.robot.commands.drive.DefaultDriveCommand;
import frc.robot.operator.OperatorInput;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LightsSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    // The operator input class
    private final OperatorInput    operatorInput    = new OperatorInput();

    // The lights (which may need to be passed to other subsystems
    private final LightsSubsystem  lightsSubsystem  = new LightsSubsystem();

    // The robot's subsystems and commands are defined here...
    private final DriveSubsystem   driveSubsystem   = new DriveSubsystem();
    private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem(lightsSubsystem);
    private final VisionSubsystem  visionSubsystem  = new VisionSubsystem();
    private final ClimbSubsystem   climbSubsystem   = new ClimbSubsystem();
    private final IntakeSubsystem  intakeSubsystem  = new IntakeSubsystem(lightsSubsystem, shooterSubsystem);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        // Initialize all Subsystem default commands.
        driveSubsystem.setDefaultCommand(
            new DefaultDriveCommand(
                operatorInput,
                driveSubsystem));

        climbSubsystem.setDefaultCommand(
            new DefaultClimbCommand(
                climbSubsystem,
                operatorInput, lightsSubsystem));


        // Configure the button bindings
        operatorInput.configureButtonBindings(driveSubsystem, shooterSubsystem, visionSubsystem, climbSubsystem, lightsSubsystem,
            intakeSubsystem);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {

        // Pass in all of the subsystems and all of the choosers to the auto command.
        return new AutonomousCommand(
            operatorInput.getSelectedAutoPattern(), operatorInput.getSelectedAutoPosition(),
            driveSubsystem, shooterSubsystem, visionSubsystem, lightsSubsystem, intakeSubsystem);
    }
}
