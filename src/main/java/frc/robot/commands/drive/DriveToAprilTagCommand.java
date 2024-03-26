package frc.robot.commands.drive;

import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class DriveToAprilTagCommand extends LoggingCommand {

    private final DriveSubsystem  driveSubsystem;
    private final VisionSubsystem visionSubsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DriveToAprilTagCommand(DriveSubsystem driveSubsystem, VisionSubsystem visionSubsystem) {

        this.driveSubsystem  = driveSubsystem;
        this.visionSubsystem = visionSubsystem;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem);
        addRequirements(visionSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        logCommandStart();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double KpAim           = -0.1;                   // Propotional control constant
        double KpDistance      = -0.1;                   // Proportional control constant for
                                                         // distance

        double min_aim_command = 0.05;                   // Since it is impossible to perfectly
                                                         // align the robot with the target. This
                                                         // set minimum range in which the robot
                                                         // needs to be aiming.

        double tx              = visionSubsystem.getTX();
        double ty              = visionSubsystem.getTY();



        double heading_error   = -tx;
        double distance_error  = -ty;
        double steering_adjust = 0.0f;

        if (tx > 1.0) {
            steering_adjust = KpAim * heading_error - min_aim_command;
        }
        else if (tx < -1.0) {
            steering_adjust = KpAim * heading_error + min_aim_command;
        }

        double distance_adjust = KpDistance * distance_error;

        double left_command    = steering_adjust + distance_adjust;
        double right_command   = -(steering_adjust + distance_adjust);

        driveSubsystem.setMotorSpeeds(left_command, right_command);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (isTimeoutExceeded(2.0)) {
            setFinishReason("tony was here");
            return true;
        }

        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }


}