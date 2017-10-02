package ashell.workers;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import ashell.utils.StringUtils;

/**
 * Created by Manne Öhlund on 27/03/17.
 * Copyright © 2017 All rights reserved.
 */

/** todo old code, can be removed
    DataOutputStream writer;
    public void exec(List<String> commands) {
        onShellStateChanged(State.BUSY);
        if (writer == null) {
            writer = new DataOutputStream(process.getOutputStream());
        }
        try {
            writer.writeBytes(new String(StringUtils.listToString(commands).getBytes(Charset.forName("UTF-8"))));
            writer.writeBytes("\n");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

public class ShellOutputStreamThread<T> extends Thread {
    private Process process;
    private OutputStream outputStream;
    private DataOutputStream writer;

    public ShellOutputStreamThread(Process process) {
        this.process = process;
        this.outputStream = process.getOutputStream();
        writer = new DataOutputStream(outputStream);
    }

    @Override
    public void run() {
        synchronized (this) {
            while (true) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void exec(List<String> commands) {
        try {
            writer.writeBytes(new String(StringUtils.listToString(commands).getBytes(Charset.forName("UTF-8"))));
            writer.writeBytes("\n");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
