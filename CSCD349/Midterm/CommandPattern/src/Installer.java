import Commands.*;

class Installer {
    private Command[] commands;

    Installer(Commands.Command[] commands) {
        this.commands = commands;
    }

    void install() {
        System.out.println("Installing OS");
        double totalTimeEstimate = 0;
        double timeCompleted = 0;
        double percentCompleted;
        for (Commands.Command c : commands) {
            totalTimeEstimate += c.estimatedDuration();
        }
        for (Commands.Command c : commands) {
            c.execute();
            timeCompleted += c.estimatedDuration();
            percentCompleted = (timeCompleted / totalTimeEstimate) * 100;
            System.out.print((int)percentCompleted + "% : [");
            for (int i = 0; i < 100; i++) {
                if (i < percentCompleted)
                    System.out.print("=");
                else
                    System.out.print(" ");
            }
            System.out.println("]");
        }
    }
}