package org.firstinspires.ftc.teamcode.experimental.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotorEx;

@FunctionalInterface
public interface MotorAction {

    void perform(DcMotorEx motor);
}
