package frc.robot.commands;

import frc.robot.operator.OperatorInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;

/**
 * This command is used to safely stop the robot in its current position, and to cancel any running
 * commands
 */
public class CancelCommand extends LoggingCommand {

    private final OperatorInput    operatorInput;
    private final ShooterSubsystem shooterSubsystem;
    private final DriveSubsystem   driveSubsystem;
    private final IntakeSubsystem  intakeSubsystem;

    /**
     * Cancel the commands running on all subsystems.
     *
     * All subsystems must be passed to this command, and each subsystem should have a stop command
     * that safely stops the robot from moving.
     */
    public CancelCommand(OperatorInput operatorInput, DriveSubsystem driveSubsystem, ShooterSubsystem shooterSubsystem,
        IntakeSubsystem intakeSubsystem, VisionSubsystem visionSubsystem) {

        this.operatorInput    = operatorInput;
        this.driveSubsystem   = driveSubsystem;
        this.shooterSubsystem = shooterSubsystem;
        this.intakeSubsystem  = intakeSubsystem;

        addRequirements(driveSubsystem, shooterSubsystem, intakeSubsystem);
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        /*
         * The Cancel command is not interruptable and only ends when the cancel button is released.
         */
        return InterruptionBehavior.kCancelIncoming;
    }

    @Override
    public void initialize() {

        logCommandStart();

        stopAll();
    }

    @Override
    public void execute() {
        stopAll();
    }

    @Override
    public boolean isFinished() {

        // The cancel command has a minimum timeout of .5 seconds
        if (!isTimeoutExceeded(.5)) {
            return false;
        }

        // Only end once the cancel button is released after .5 seconds has elapsed
        if (!operatorInput.isCancel()) {
            setFinishReason("Cancel button released");
            return true;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }

    private void stopAll() {

        // Stop all of the robot movement
        driveSubsystem.stop();
        shooterSubsystem.stop();
        intakeSubsystem.stop();
    }
}
