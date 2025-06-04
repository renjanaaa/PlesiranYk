package menu;

public class MenuAction {
    private boolean cancel = false;

    public void cancel() {
        this.cancel = true;
    }

    public boolean isCancel() {
        return cancel;
    }
}