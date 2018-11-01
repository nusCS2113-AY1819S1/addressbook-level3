package seedu.addressbook.commands;

public abstract class UndoAbleCommand extends Command{

    UndoAbleCommand(int targetVisibleIndex, int targetVisibleIndex2){
        super(targetVisibleIndex, targetVisibleIndex2);
    }

    UndoAbleCommand(int targetVisibleIndex){
        super(targetVisibleIndex);
    }

    protected UndoAbleCommand(){}

    public abstract void executeUndo() throws Exception;

    public abstract void executeRedo() throws Exception;
}
