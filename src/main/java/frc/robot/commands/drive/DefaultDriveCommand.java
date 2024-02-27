package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.Constants.DriveConstants.DriveMode;
import frc.robot.commands.LoggingCommand;
import frc.robot.operator.GameController;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class DefaultDriveCommand extends LoggingCommand {

    private final DriveSubsystem             driveSubsystem;
    private final XboxController             driverController;
    private final SendableChooser<DriveMode> driveModeChooser;
    private final VisionSubsystem            visionSubsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DefaultDriveCommand(GameController driverController, SendableChooser<DriveMode> driveModeChooser,
        DriveSubsystem driveSubsystem, VisionSubsystem visionSubsystem) {

        this.driverController = driverController;
        this.driveModeChooser = driveModeChooser;
        this.driveSubsystem   = driveSubsystem;
        this.visionSubsystem  = visionSubsystem;

        // Use addRequirements() here to declare subsystem dependencies.
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

        DriveMode driveMode = driveModeChooser.getSelected();

        boolean   boost     = driverController.getRightBumper();

        double    b         = 1.0;

        if (boost)
            b = 10.0f;

        switch (driveMode) {

        case DUAL_STICK_ARCADE:
            setMotorSpeedsArcade(driverController.getLeftY() * 19, driverController.getRightX() * 19, boost);
            break;

        case SINGLE_STICK_ARCADE:
            setMotorSpeedsArcade(driverController.getLeftY() * b, driverController.getLeftX() * b, boost);
            break;

        case TANK:
        default:

            if (boost) {
                driveSubsystem.setMotorSpeeds(driverController.getLeftY() * b, driverController.getRightY() * b);
            }
            else {
                // If not in boost mode, then divide the motors speeds in half
                driveSubsystem.setMotorSpeeds(driverController.getLeftY() / 2.0, driverController.getRightY() / 2.0);
            }
            break;
        }

        double KpAim           = -0.1;                   // Propotional control constant
        double KpDistance      = -0.1;                   // Proportional control constant for
                                                         // distance

        double min_aim_command = 0.05;                   // Since it is impossible to perfectly
                                                         // align the robot with the target. This
                                                         // set minimum range in which the robot
                                                         // needs to be aiming.

        double tx              = visionSubsystem.getTX();
        double ty              = visionSubsystem.getTY();


        if (driverController.getLeftTriggerAxis() > 0) {
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
            double right_command   = (steering_adjust + distance_adjust) * -1;

            driveSubsystem.setMotorSpeeds(left_command, right_command);
        }



    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // The default drive command never ends, but can be interrupted by other commands.
        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }

    private void setMotorSpeedsArcade(double speed, double turn, boolean boost) {

        double maxSpeed = 1.0;

        if (!boost) {
            speed    /= 2.0;
            turn     /= 2.0;
            maxSpeed /= 2.0;
        }

        // The basic algorithm for arcade is to add the turn and the speed

        double leftSpeed  = speed + turn;
        double rightSpeed = speed - turn;

        // If the speed + turn exceeds the max speed, then keep the differential
        // and reduce the speed of the other motor appropriately

        if (Math.abs(leftSpeed) > maxSpeed || Math.abs(rightSpeed) > maxSpeed) {

            if (Math.abs(leftSpeed) > maxSpeed) {

                if (leftSpeed > 0) {
                    leftSpeed = maxSpeed;
                }
                else {
                    leftSpeed = -maxSpeed;
                }
                rightSpeed = leftSpeed - turn;

            }
            else {

                if (rightSpeed > 0) {
                    rightSpeed = maxSpeed;
                }
                else {
                    rightSpeed = -maxSpeed;
                }

                leftSpeed = rightSpeed + turn;
            }
        }

        driveSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);
    }


}