package frc.robot.commands.drive;

import frc.robot.Constants.DriveConstants.DriveMode;
import frc.robot.commands.LoggingCommand;
import frc.robot.operator.OperatorInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class DefaultDriveCommand extends LoggingCommand {

    private final DriveSubsystem  driveSubsystem;
    private final VisionSubsystem visionSubsystem;
    private final OperatorInput   operatorInput;

    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DefaultDriveCommand(OperatorInput operatorInput,
        DriveSubsystem driveSubsystem, VisionSubsystem visionSubsystem) {

        this.operatorInput   = operatorInput;
        this.driveSubsystem  = driveSubsystem;
        this.visionSubsystem = visionSubsystem;

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

        DriveMode driveMode     = operatorInput.getSelectedDriveMode();

        // Default scaling (when neither boost nor slow are pressed
        double    scalingFactor = 0.6;

        if (operatorInput.getBoost()) {
            scalingFactor = 1.0;
        }

        if (operatorInput.getSlow()) {
            scalingFactor = .3;
        }

        switch (driveMode) {

        case DUAL_STICK_ARCADE:
        case SINGLE_STICK_ARCADE:
            setMotorSpeedsArcade(operatorInput.getSpeed(), operatorInput.getTurn(), scalingFactor);
            break;

        case TANK:
        default:

            double leftSpeed = operatorInput.getLeftSpeed() * scalingFactor;
            double rightSpeed = operatorInput.getRightSpeed() * scalingFactor;

            driveSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);
            break;
        }

        /*
         * FIXME: This should be a separate command.
         * DriveToAprilTagCommand(driveSubsystem);
         */
        double KpAim           = -0.1;                   // Propotional control constant
        double KpDistance      = -0.1;                   // Proportional control constant for
                                                         // distance

        double min_aim_command = 0.05;                   // Since it is impossible to perfectly
                                                         // align the robot with the target. This
                                                         // set minimum range in which the robot
                                                         // needs to be aiming.

        double tx              = visionSubsystem.getTX();
        double ty              = visionSubsystem.getTY();

        if (operatorInput.getDriveToVisionTarget() > 0) {

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

    private void setMotorSpeedsArcade(double speed, double turn, double scalingFactor) {

        double maxSpeed = 1.0;

        speed    *= scalingFactor;
        turn     *= scalingFactor;
        maxSpeed *= scalingFactor;

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