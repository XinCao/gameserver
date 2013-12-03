package action;

public interface IAction {
    void setArguments(Object... args) throws NumberFormatException;
    boolean canPerform(Object... args);
    void perform(Object... args);
}