package seedu.addressbook.data;

import seedu.addressbook.commands.UndoAbleCommand;

import java.util.Stack;

/**
 * Represents the command stack where UndoAbleCommands are stored after execution
 */
public class CommandStack {
    private Stack<UndoAbleCommand> undoStack;
    private Stack<UndoAbleCommand> redoStack;

    /**
     * Creates a new empty CommandStack
     */
    CommandStack(){
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public static class HistoryOutOfBoundException extends Exception {}

    /**
     * Push a UndoAbleCommand into the undo stack
     * @param toStack is the command to be pushed
     */
    public void addCommandToStack(UndoAbleCommand toStack) {
        undoStack.push(toStack);
    }

    /**
     * Empties the redo stack
     */
    public void truncateOldPath() {
        redoStack.clear();
    }

    /**
     * Check whether to execute truncateOldPath and then add toStack to the undo stack
     * @param toStack is the object to be added to the undo stack
     */
    public void checkForAction(UndoAbleCommand toStack) {
        if(!isRedoStackEmpty()) truncateOldPath();
        addCommandToStack(toStack);
    }

    /**
     * Checks if redo stack is empty
     * @return true if redo stack is empty
     */
    public boolean isRedoStackEmpty() {
        return redoStack.empty();
    }

    /**
     * Undo the last executed command
     * @return the command to be undone
     * @throws HistoryOutOfBoundException if there is no command to be undone
     */
    public UndoAbleCommand undoLast() throws HistoryOutOfBoundException {
        if(undoStack.isEmpty()){
            throw new HistoryOutOfBoundException();
        }
        return undoLogic();
    }

    /**
     * Get the command to be undone and push it into the redo stack
     * @return the command at the top of the undo stack
     */
    public UndoAbleCommand undoLogic() {
        UndoAbleCommand toUndo = undoStack.peek();
        redoStack.push(toUndo);
        undoStack.pop();
        return toUndo;
    }

    /**
     * Redo the last command that was undone
     * @return the command to be redone
     * @throws HistoryOutOfBoundException if there is no command to be redone
     */
    public UndoAbleCommand redoLast() throws HistoryOutOfBoundException {
        if(redoStack.isEmpty()){
            throw new HistoryOutOfBoundException();
        }
        return redoLogic();
    }

    /**
     * Get the command to be redone and push it into the undo stack
     * @return the command at the top of the redo stack
     */
    public UndoAbleCommand redoLogic() {
        UndoAbleCommand toRedo = redoStack.peek();
        undoStack.push(toRedo);
        redoStack.pop();
        return toRedo;
    }

}
