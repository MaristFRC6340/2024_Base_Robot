package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

public class ClimberSubsystem extends SubsystemBase {
  private CANSparkMax leftClimber;
  private CANSparkMax rightClimber;

  private RelativeEncoder leftEncoder;
  private RelativeEncoder rightEncoder;

  private SparkMaxPIDController leftClimberPID;
  private SparkMaxPIDController rightClimberPID;

  private boolean climberUp = false;

  /** Creates a new ClimberSubsystem. */
  public ClimberSubsystem() {
    leftClimber = new CANSparkMax(ClimberConstants.kLeftClimberID, MotorType.kBrushless);
    rightClimber = new CANSparkMax(ClimberConstants.kRightClimberID, MotorType.kBrushless);

    leftClimber.setIdleMode(IdleMode.kBrake);
    rightClimber.setIdleMode(IdleMode.kBrake);

    leftClimber.setSmartCurrentLimit(40);
    rightClimber.setSmartCurrentLimit(40);

    leftEncoder = leftClimber.getEncoder();
    rightEncoder = rightClimber.getEncoder();

    leftClimberPID = leftClimber.getPIDController();
    rightClimberPID = rightClimber.getPIDController();




  }

  @Override
  public void periodic() {
    

  }




  /**
   * Sets the PID to a given encoder position
   * @param encoderCounts
   */
  public void goToPosition(double encoderCounts) {
    if(encoderCounts > 0)encoderCounts = 0;

    leftClimberPID.setReference(encoderCounts, CANSparkMax.ControlType.kPosition);
    rightClimberPID.setReference(encoderCounts, CANSparkMax.ControlType.kPosition);
  }

  /**
   * Returns the encoder counts of the left encoder
   * @return
   */
  public double getLeftEncoderCounts() {
    return leftEncoder.getPosition();
  }

  /**
   * Returns the encoder counts of the right encoder
   * @return
   */
  public double getRightEncoderCounts() {
    return rightEncoder.getPosition();
  }

  /**
   * Raises the climber to the climbing height
   */
  public void goToClimbHeight() {
    goToPosition(ClimberConstants.kClimbHeight);
  }

  public void toggleClimber(){
    if(climberUp){
      //goToPosition(0);
      System.out.println("GO DOWN");
    }
    else{
      //goToClimbHeight();
      System.out.println("GO UP");
    }
  }
  public void setClimberPower(double power){
    rightClimber.set(power);
    leftClimber.set(power);
  }

  /**
   * Stops the climber
   */
  public void stop() {
    setClimberPower(0);
  }

  /**
   * Command to set the climber to a given power
   * @param power
   * @return stopped climber
   */
  public Command getSetClimberPowerCommand(double power) {
    return this.startEnd(() -> {
      setClimberPower(power);
    }, () -> {
      stop();
    });
  }

  /**
   * Command to set the climber to a changing given power
   * @param power
   * @return stopped climber
   */
  public Command getSetClimberPowerCommand(DoubleSupplier powerSupplier) {
    return this.startEnd(() -> {
      setClimberPower(powerSupplier.getAsDouble());
    }, () -> {
      stop();
    });
  }

  public Command setRightClimberPower(DoubleSupplier powerSupplier){
    return this.startEnd(()->{rightClimber.set(powerSupplier.getAsDouble());}, ()->{rightClimber.set(0);});
  }
  public Command setLeftClimberPower(DoubleSupplier powerSupplier){
    return this.startEnd(()->{leftClimber.set(powerSupplier.getAsDouble());}, ()->{leftClimber.set(0);});
  }

  /**
   * Raises the hooks
   * @return stopped climber
   */
  public Command getRaiseHooksCommand() {
    return this.startEnd(() -> {
      goToClimbHeight();
    }, () -> {
      stop();
    });
  }

  /**
   * Starts lowering the hooks
   * @return lowering climber
   */
  public Command getLowerHooksCommand() {
    return this.startEnd(() -> {
      setClimberPower(ClimberConstants.kLowerSpeed);
    }, () -> {
      //none
    });
  }

  /**
   * Holds the position in place
   * Default Command
   * @return
   */
  public Command getHoldPositionCommand() {
    return this.run(() -> {
      goToPosition(getLeftEncoderCounts());
    });
  }




}