package mindescape.app;

import mindescape.controller.maincontroller.impl.MainControllerImpl;

/**
 * The main class of the application.
 */
class MindEscape {

    /**
     * The main method of the application.
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        new MainControllerImpl().start();
    }
}
