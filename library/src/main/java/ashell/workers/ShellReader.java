package ashell.workers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ashell.listeners.OnShellErrorResultListener;
import ashell.listeners.OnShellResultListener;
import ashell.listeners.OnShellStateListener;

import static ashell.datatypes.ShellConstants.CMD_DONE;

/**
 * Created by Manne Öhlund on 27/03/17.
 * Copyright © 2017 All rights reserved.
 */

public class ShellReader extends ShellInputStreamThread<String> {

    public ShellReader(Process process, OnShellStateListener shellStateListener) {
        super(process, shellStateListener);
        this.setName("ShellReader");
    }

    @Override
    public void run() {
        OnShellResultListener onShellResultListener = getOnShellResultListener();
        boolean notErrorListener = !(onShellResultListener instanceof OnShellErrorResultListener);
        if (notErrorListener && onShellResultListener != null) {
            onShellResultListener.onShellResult("SHELL_START");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream()));
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                if (getOnShellResultListener() != null && line.length() != 0) {
                    //Log.e(this.getName(), "READER: " + line); // TODO: 03/04/17 Remove this?
                    if (!getOnShellResultListener().onShellResult(line)) {
                        //Log.e(this.getName(), "READER: " + line); // TODO: 03/04/17 Remove this?
                        if (line.equals(CMD_DONE)) {
                            setBusy(false);
                            onShellStateListener.onShellStateChanged(OnShellStateListener.State.IDLE);
                        } else {
                            setBusy(true);
                            onShellStateListener.onShellStateChanged(OnShellStateListener.State.BUSY);
                        }
                        //System.out.println(String.format("IS SHELL BUSY '%s': %b", this.getName(), isBusy())); // TODO: 03/04/17 Remove this?
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (notErrorListener && getOnShellResultListener() != null) {
            getOnShellResultListener().onShellResult("SHELL_EXIT");
            onShellStateListener.onShellStateChanged(OnShellStateListener.State.TERMINATED);
        }
    }

    public OnShellResultListener<String> getOnShellResultListener() {
        return onShellResultListener;
    }
}
