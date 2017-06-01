package ashell.workers;

import java.io.InputStream;

import ashell.listeners.OnShellErrorResultListener;
import ashell.listeners.OnShellResultListener;
import ashell.listeners.OnShellStateListener;

/**
 * Created by Manne Öhlund on 27/03/17.
 * Copyright © 2017 All rights reserved.
 */

public class ShellInputStreamThread<T> extends Thread {
    private boolean isBusy = true;
    protected Process process;
    protected OnShellResultListener<T> onShellResultListener;
    protected OnShellErrorResultListener<String> onShellErrorResultListener;
    protected OnShellStateListener onShellStateListener;

    public ShellInputStreamThread(Process process, OnShellStateListener onShellStateListener) {
        this.process = process;
        this.onShellStateListener = onShellStateListener;
    }

    public void setOnShellResultListener(OnShellResultListener<T> onShellResultListener) {
        this.onShellResultListener = onShellResultListener;
    }

    public void setOnShellErrorResultListener(OnShellErrorResultListener<String> onShellErrorResultListener) {
        this.onShellErrorResultListener = onShellErrorResultListener;
    }

    public InputStream getInputStream() {
        return process.getInputStream();
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public boolean isBusy() {
        return isBusy;
    }
}
