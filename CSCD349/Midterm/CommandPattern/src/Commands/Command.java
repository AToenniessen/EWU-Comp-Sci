package Commands;

public interface Command {
    void execute();
    int estimatedDuration();
}
