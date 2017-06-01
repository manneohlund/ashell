package ashell;

import java.util.List;

import ashell.listeners.OnShellErrorResultListener;
import ashell.listeners.OnShellResultListener;
import ashell.listeners.OnShellStateListener;
import ashell.utils.TimerUtils;
import ashell.workers.ShellInputStreamThread;
import ashell.workers.ShellOutputStreamThread;

/**
 * Created by Manne Öhlund on 26/03/17.
 * Copyright © 2017 All rights reserved.
 */

public class AShell implements OnShellStateListener {

    private State shellState = State.IDLE;
    TimerUtils timer = new TimerUtils();
    private long executionTime = -1;

    private Process process;
    private ShellInputStreamThread shellReader;
    private ShellInputStreamThread errorShellReader;
    private OnShellStateListener shellStateListener;
    private ShellOutputStreamThread shellWriter;
    private OnShellStateListener onShellStateListener;

    public AShell(Process process) {
        this.process = process;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public void setShellReader(ShellInputStreamThread shellReader) {
        this.shellReader = shellReader;
    }

    public void setErrorShellReader(ShellInputStreamThread errorShellReader) {
        this.errorShellReader = errorShellReader;
    }

    public <T> void setOnShellResultListener(OnShellResultListener<T> onShellResultListener) {
        this.shellReader.setOnShellResultListener(onShellResultListener);
    }

    public void setOnShellErrorResultListener(OnShellErrorResultListener onShellErrorResultListener) {
        this.errorShellReader.setOnShellErrorResultListener(onShellErrorResultListener);
    }

    public void setOnShellStateListener(OnShellStateListener onShellStateListener) {
        this.onShellStateListener = onShellStateListener;
    }

    public void setShellReaderThreadPriority(int priority) {
        shellReader.setPriority(priority);
    }

    public void setErrorShellReaderThreadPriority(int priority) {
        errorShellReader.setPriority(priority);
    }

    public void exec(List<String> commands) {
        timer.start();
        setExecutionTime(-1);
        onShellStateChanged(State.BUSY);
        shellReader.setBusy(true);
        errorShellReader.setBusy(true);
        shellWriter.exec(commands);
    }

    public void start() {
        // Init writer
        shellWriter = new ShellOutputStreamThread(process);
        // Start readers and writers
        shellWriter.start();
        shellReader.start();
        errorShellReader.start();
    }

    public void terminate() {
        try {
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isBusy() {
        return shellReader.isBusy() || errorShellReader.isBusy();
    }

    public State getShellState() {
        return shellState;
    }

    @Override
    public void onShellStateChanged(State newShellState) {
        if (this.shellState == newShellState) {
            return;
        }
        if (shellReader.isBusy() || errorShellReader.isBusy()) {
            this.shellState = State.BUSY;
        } else if (!shellReader.isBusy() && !errorShellReader.isBusy()) {
            this.shellState = State.IDLE;
        } else {
            this.shellState = newShellState;
        }

        //Log.w(AShell.class.getName(), "SHELL STATE: " + this.shellState); // TODO: 03/04/17 Remove this?

        if (this.shellState == State.IDLE) {
            timer.stop();
            setExecutionTime(timer.getTime());
            //Log.e(AShell.class.getName(), "SHELL EXEC TIME: " + getExecutionTime());// TODO: 03/04/17 Remove this?
        }

        // Callback
        if (onShellStateListener != null) {
            onShellStateListener.onShellStateChanged(shellState);
        }
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
}
