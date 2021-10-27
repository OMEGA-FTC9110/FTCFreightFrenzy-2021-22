package org.firstinspires.ftc.teamcode.experimental;

import com.qualcomm.robotcore.hardware.DcMotor;

public interface DriveTrain {

    void initialize();

    void setRunMode(DcMotor.RunMode mode);

    void setPower(double motorPower);

    void setPower(double flPower, double frPower, double blPower, double brPower);
}
