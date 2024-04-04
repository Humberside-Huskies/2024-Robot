package frc.robot.operator;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.AutoConstants.AutoPattern;
import frc.robot.Constants.AutoConstants.AutoPosition;
import frc.robot.Constants.DriveConstants.DriveMode;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.ShooterConstants.shooterType;
import frc.robot.commands.CancelCommand;
import frc.robot.commands.intake.DefaultGroundIntakeCommand;
import frc.robot.commands.shooter.DefaultShooterCommand;
import frc.robot.commands.shooter.ShooterIntakeCommand;
import frc.robot.commands.vision.DefaultVisionCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LightsSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;

/**
 * The Operator input class is used to map buttons to functions and functions to commands
 * <p>
 * This class extends SubsystemBase so that the periodic() routine is called each loop. The periodic
 * routine can be used to send debug information to the dashboard
 */
public class OperatorInput extends SubsystemBase {

    public final GameController                 driverController    = new GameController(
        OperatorConstants.DRIVER_CONTROLLER_PORT,
        OperatorConstants.GAME_CONTROLLER_STICK_DEADBAND);

    // All dashboard choosers are defined here...
    private final SendableChooser<DriveMode>    driveModeChooser    = new SendableChooser<>();
    private final SendableChooser<AutoPattern>  autoPatternChooser  = new SendableChooser<>();
    private final SendableChooser<AutoPosition> autoPositionChooser = new SendableChooser<>();

    public OperatorInput() {

        driveModeChooser.setDefaultOption("Dual Stick Arcade", DriveMode.DUAL_STICK_ARCADE);
        driveModeChooser.addOption("Single Stick Arcade", DriveMode.SINGLE_STICK_ARCADE);
        driveModeChooser.addOption("Tank", DriveMode.TANK);

        // Put the drive mode option to the SmartDashboard
        SmartDashboard.putData("Drive Mode", driveModeChooser);

        // Change Auto pattern Type
        autoPatternChooser.setDefaultOption("ShootSpeaker & DriveOut", AutoPattern.SHOOT_SPEAKER_AND_DRIVE);
        // Other option for Auto pattern Type
        autoPatternChooser.addOption("Nothing", AutoPattern.DO_NOTHING);
        autoPatternChooser.addOption("ShootSpeaker", AutoPattern.SHOOT_SPEAKER);
        autoPatternChooser.addOption("ShootAmp", AutoPattern.SHOOT_AMP);
        autoPatternChooser.addOption("DriveOut", AutoPattern.DRIVE_OUT);

        // Put the auto pattern option to the SmartDashboard
        SmartDashboard.putData("Auto Pattern", autoPatternChooser);

        autoPositionChooser.setDefaultOption("CENTER", AutoPosition.CENTER);
        autoPositionChooser.addOption("RIGHT", AutoPosition.RIGHT);
        autoPositionChooser.addOption("LEFT", AutoPosition.LEFT);

        SmartDashboard.putData("Auto Position", autoPositionChooser);
    }

    /*
     * Map all functions to buttons.
     *
     * A function should be a description of the robot behavior it is triggering.
     *
     * This separation of concerns allows for remapping of the robot functions to different
     * controller buttons without the need to change the command or the trigger. The mapping
     * from controller button to function is done in the following methods.
     */

    // Cancel all commands when the driver presses the XBox controller three lines (aka. start)
    // button
    public boolean isCancel() {
        return driverController.getStartButton();
    }

    public boolean isResetEncoders() {
        return driverController.getBackButton();
    }

    /*
     * Selected Auto Pattern
     */
    public AutoPattern getSelectedAutoPattern() {
        return autoPatternChooser.getSelected();
    }

    public AutoPosition getSelectedAutoPosition() {
        return autoPositionChooser.getSelected();
    }

    /*
     * Drive Mode and Speed Boost/Slow
     */
    public DriveMode getSelectedDriveMode() {
        return driveModeChooser.getSelected();
    }

    public boolean isBoost() {
        return driverController.getRightBumper();
    }

    public boolean isSlow() {
        return driverController.getLeftBumper();
    }

    /*
     * Arcade Drive Methods
     */
    public double getSpeed() {
        return driverController.getLeftY();
    }

    public double getTurn() {
        if (getSelectedDriveMode() == DriveMode.SINGLE_STICK_ARCADE) {
            return driverController.getLeftX();
        }
        return driverController.getRightX();
    }

    /*
     * Tank Drive Methods
     */
    public double getLeftSpeed() {
        return driverController.getLeftY();
    }

    public double getRightSpeed() {
        return driverController.getRightY();
    }

    /*
     * Vision Drive Methods
     */
    public boolean isDriveToVisionTarget() {
        return driverController.getAButton();
    }

    /*
     * Shooter Trigger Boolean
     */
    public boolean isShootSpeaker() {
        return driverController.getBButton();
    }

    public boolean isShootAmp() {
        return driverController.getPOV() == 0;
    }

    public boolean isPassShot() {
        return driverController.getYButton();
    }

    /*
     * is Intake Trigger Boolean
     */
    public boolean isIntake() {
        return driverController.getAButton();
    }

    public boolean isGroundIntake() {
        return driverController.getXButton();
    }


    /*
     * Is Climber Trigger Button
     */
    public double isClimb() {
        return driverController.getLeftTriggerAxis();
    }

    public double isRetract() {
        return driverController.getRightTriggerAxis();
    }

    /*
     * Is emmergency Intake
     */

    /**
     * Configure Button Bindings
     *
     * Use this method to define your robotFunction -> command mappings.
     *
     * NOTE: all subsystems should be passed into this method.
     */
    public void configureButtonBindings(DriveSubsystem driveSubsystem, ShooterSubsystem shooterSubsystem,
        VisionSubsystem visionSubsystem, ClimbSubsystem climbSubsystem, LightsSubsystem lightsSubsystem,
        IntakeSubsystem intakeSubsystem) {

        new Trigger(() -> isCancel())
            .onTrue(new CancelCommand(this, driveSubsystem, shooterSubsystem, intakeSubsystem, visionSubsystem));

        // Intake button
        new Trigger(() -> isIntake())
            .onTrue(new ShooterIntakeCommand(shooterSubsystem, this, intakeSubsystem));

        // Shooter button
        new Trigger(() -> isShootSpeaker())
            .onTrue(new DefaultShooterCommand(shooterSubsystem, lightsSubsystem, intakeSubsystem,
                ShooterConstants.shooterType.SpeakerShooter));

        new Trigger(() -> isShootAmp())
            .onTrue(new DefaultShooterCommand(shooterSubsystem, lightsSubsystem, intakeSubsystem,
                ShooterConstants.shooterType.AMPShooter));

        new Trigger(() -> isGroundIntake())
            .onTrue(new DefaultGroundIntakeCommand(intakeSubsystem, lightsSubsystem, shooterSubsystem));

        new Trigger(() -> isDriveToVisionTarget())
            .onTrue(new DefaultVisionCommand(2, driveSubsystem, visionSubsystem));

        new Trigger(() -> isPassShot())
            .onTrue(new DefaultShooterCommand(shooterSubsystem, lightsSubsystem, intakeSubsystem, shooterType.PassShooter));

    }

    @Override
    public void periodic() {

        // Display any operator input values on the smart dashboard.

        SmartDashboard.putString("Driver Controller", driverController.toString());
    }

}
