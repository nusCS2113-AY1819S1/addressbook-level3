package seedu.addressbook.data;

import seedu.addressbook.commands.UndoAbleCommand;

import java.util.Stack;

public class CommandStack {
    private Stack<UndoAbleCommand> undoStack;
    private Stack<UndoAbleCommand> redoStack;

    CommandStack(){
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public static class HistoryOutOfBoundException extends Exception {}

    public void addCommandToStack(UndoAbleCommand toStack) {
        undoStack.push(toStack);
    }

    public void truncateOldPath() {
        redoStack.clear();
    }

    /**
     * check whether to execute truncateOldPath
     */
    public void checkForAction(UndoAbleCommand toStack) {
        if(!nextBoxIsEmpty()) truncateOldPath();
        addCommandToStack(toStack);
    }

    public boolean nextBoxIsEmpty() {
        return redoStack.empty();
    }

    public UndoAbleCommand undoLast() throws HistoryOutOfBoundException {
        if(undoStack.isEmpty()){
            throw new HistoryOutOfBoundException();
        }
        UndoAbleCommand toUndo = undoStack.peek();
        redoStack.push(toUndo);
        undoStack.pop();
        return toUndo;
    }

    public UndoAbleCommand redoLast() throws HistoryOutOfBoundException {
        if(redoStack.isEmpty()){
            throw new HistoryOutOfBoundException();
        }
        UndoAbleCommand toRedo = redoStack.peek();
        undoStack.push(toRedo);
        redoStack.pop();
        return toRedo;
    }

}
