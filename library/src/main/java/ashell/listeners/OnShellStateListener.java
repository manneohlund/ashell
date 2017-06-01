package ashell.listeners;

/**
 * Created by Manne Öhlund on 30/03/17.
 * Copyright © 2017 All rights reserved.
 */

public interface OnShellStateListener {
    enum State {
        BUSY, IDLE, TERMINATED
    }

    void onShellStateChanged(State shellState);
}
