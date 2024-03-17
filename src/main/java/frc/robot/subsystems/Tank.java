package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Tank extends SubsystemBase{
    

    public CANSparkMax leftOne;
    public CANSparkMax leftTwo;

    public CANSparkMax rightOne;
    public CANSparkMax rightTwo;



    public Tank(){
        leftOne = new CANSparkMax(0, MotorType.kBrushless);
        //leftTwo = new CANSparkMax(1, MotorType.kBrushless);
        rightOne = new CANSparkMax(2, MotorType.kBrushless);
       // rightTwo = new CANSparkMax(3, MotorType.kBrushless);

    }



    public void setPowerStates(double leftPow, double rightPow){
       leftOne.set(leftPow);
       //leftTwo.set(leftPow);
       rightOne.set(rightPow);
       //rightTwo.set(rightPow);
    }
    public void rest(){
        leftOne.set(0);
       //leftTwo.set(0);
       rightOne.set(0);
      // rightTwo.set(0);
    }

    public Command getDriveCommand(DoubleSupplier leftPow, DoubleSupplier rightPow){
        return this.startEnd(()->setPowerStates(leftPow.getAsDouble(), rightPow.getAsDouble()), ()->rest());
    }
}
