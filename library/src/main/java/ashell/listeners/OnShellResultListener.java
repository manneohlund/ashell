package ashell.listeners;

/**
 * Created by Manne Öhlund on 27/03/17.
 * Copyright © 2017 All rights reserved.
 */

public interface OnShellResultListener<T> {
    /**
     * @param result returns false if not handled
     * @return
     */
    boolean onShellResult(T result);
}
