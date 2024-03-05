package frc.robot.commands.shooter;

import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.ShooterSubsystem;

public class IntakeCommand extends LoggingCommand {

    private final ShooterSubsystem shooterSubsystem;
    private final boolean          emmergencyOverride;

    /**
     * Creates a new ExampleCommand.
     *
     * @param shooterSubsystem The subsystem used by this command.
     */
    public IntakeCommand(ShooterSubsystem shooterSubsystem, boolean emmergencyOverride) {

        this.shooterSubsystem   = shooterSubsystem;
        this.emmergencyOverride = emmergencyOverride;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(shooterSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        System.out.println("Intake begin");
        logCommandStart();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // Run the shooter wheel
        shooterSubsystem.setFeederSpeed(-ShooterConstants.SHOOTER_SHOOT_SPEAKER_SPEED);
        shooterSubsystem.setShooterSpeed(-ShooterConstants.FEEDER_SHOOT_SPEAKER_SPEED);


    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {


        // Checks if the note has triggered the laser. If it hasn't runs Intake motors for 4 seconds
        // and then stops
        if (shooterSubsystem.isNoteLoaded() || isTimeoutExceeded(4)) {
            System.out.println("Intake stop");
            setFinishReason("Intake no more");
            return true;

        }
        return false;

    }


    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.stop();
        logCommandEnd(interrupted);
    }
}