package me.skymc.theborder.game;

public enum BorderState {

    WAIT(true), PREGAME(false), GAME(false), GAME_PVP(false), FINISH(false);

    private boolean canJoin;
    private static BorderState currentState;

    BorderState(boolean paramBoolean) {
        this.canJoin = paramBoolean;
    }

    public boolean canJoin() {
        return this.canJoin;
    }

    public static void setState(BorderState paramBorderState) {
        currentState = paramBorderState;
    }

    public static boolean isState(BorderState paramBorderState) {
        return currentState == paramBorderState;
    }

    public static BorderState getState() {
        return currentState;
    }
}


/* Location:              C:\Users\sky\Desktop\TheBorders .jar!\net\gravenilvec\listener\BorderState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */