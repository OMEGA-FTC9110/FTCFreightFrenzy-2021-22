package org.firstinspires.ftc.teamcode.production.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotorEx;

@FunctionalInterface
public interface MotorAction {

    void perform(DcMotorEx motor);
}
