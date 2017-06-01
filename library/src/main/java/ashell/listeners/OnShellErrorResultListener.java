package ashell.listeners;

/**
 * Created by Manne Öhlund on 27/03/17.
 * Copyright © 2017 All rights reserved.
 */

public abstract class OnShellErrorResultListener<T> implements OnShellResultListener<T> {
    public abstract boolean onShellErrorResult(String result);

    @Override
    public boolean onShellResult(T result) {
        if (result instanceof String) {
            return onShellErrorResult((String) result);
        }
        return false;
    }
}
