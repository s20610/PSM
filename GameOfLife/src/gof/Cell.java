package gof;

public class Cell {
    private boolean state = false;
    private boolean newState;

    public Cell() {

    }

    public void setNewState(boolean state) {
        newState = state;
    }

    public void updateState() {
        state = newState;
    }

    public boolean getState() {
        return state;
    }
}
