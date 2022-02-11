package org.firstinspires.ftc.teamcode.production.intake;

import org.firstinspires.ftc.teamcode.production.RobotModule;

public interface Intake extends RobotModule {

    void setSlidePower(double power);

    void setSlidePosition(double inches);

    void activateIntake();

    void stopIntake();

    void reverseIntake();

    void toggleCarousel();

    void toggleCarouselReverse();

    void stopCarousel();

}
