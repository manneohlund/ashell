package ashell.workers;

import java.io.InputStream;

import ashell.listeners.OnShellResultListener;
import ashell.listeners.OnShellStateListener;

/**
 * Created by Manne Öhlund on 27/03/17.
 * Copyright © 2017 All rights reserved.
 */

public class ErrorShellReader extends ShellReader {
    public ErrorShellReader(Process process, OnShellStateListener shellStateListener) {
        super(process, shellStateListener);
        this.setName("ErrorShellReader");
    }

    public OnShellResultListener<String> getOnShellResultListener() {
        return onShellErrorResultListener;
    }

    public InputStream getInputStream() {
        return process.getErrorStream();
    }
}
