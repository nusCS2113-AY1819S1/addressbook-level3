package seedu.addressbook.commands;

public abstract class UndoAbleCommand extends Command{
    @Override
    public CommandResult execute(){
        commandStack.checkForAction(this);
        return executeLogic();
    }

    public abstract CommandResult executeLogic();

    public abstract void executeUndo();

    public abstract void executeRedo();
}
