package frc.robot.commands.drive;

import frc.robot.Constants.DriveConstants.DriveMode;
import frc.robot.commands.LoggingCommand;
import frc.robot.operator.OperatorInput;
import frc.robot.subsystems.DriveSubsystem;

public class DefaultDriveCommand extends LoggingCommand {

    private final DriveSubsystem driveSubsystem;
    private final OperatorInput  operatorInput;

    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DefaultDriveCommand(OperatorInput operatorInput,
        DriveSubsystem driveSubsystem) {

        this.operatorInput  = operatorInput;
        this.driveSubsystem = driveSubsystem;

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

        DriveMode driveMode         = operatorInput.getSelectedDriveMode();

        // Default scaling (when neither boost nor slow are pressed
        double    scalingFactor     = 0.4;
        double    scalingFactorTurn = 0.7;

        if (operatorInput.isBoost()) {
            scalingFactor     = 1.0;
            scalingFactorTurn = 1.0;
        }

        if (operatorInput.isSlow()) {
            scalingFactor     = 0.15;
            scalingFactorTurn = 0.5;
        }

        switch (driveMode) {

        case DUAL_STICK_ARCADE:
        case SINGLE_STICK_ARCADE:
            setMotorSpeedsArcade(operatorInput.getSpeed(), operatorInput.getTurn() * scalingFactorTurn, scalingFactor);
            break;

        case TANK:
        default:

            double leftSpeed = operatorInput.getLeftSpeed() * scalingFactor;
            double rightSpeed = operatorInput.getRightSpeed() * scalingFactor;

            driveSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);
            break;
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