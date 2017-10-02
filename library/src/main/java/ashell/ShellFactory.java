package ashell;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import ashell.factory.CommandFactory;
import ashell.listeners.OnShellErrorResultListener;
import ashell.listeners.OnShellResultListener;
import ashell.listeners.OnShellStateListener;
import ashell.utils.ProcessUtils;
import ashell.workers.ErrorShellReader;
import ashell.workers.ShellInputStreamThread;
import ashell.workers.ShellReader;

/**
 * Created by Manne Öhlund on 23/03/17.
 * Copyright © 2017 All rights reserved.
 */

public class ShellFactory {

    private boolean isRoot = false;
    private AShell shell;

    public ShellFactory(AShell shell, boolean isRoot) {
        this.shell = shell;
        this.isRoot = isRoot;
    }

    public AShell getShell() {
        return shell;
    }

    public boolean isRoot() {
        return isRoot;
    }

    @SuppressWarnings("unchecked") // Single-interface proxy creation guarded by parameter safety.
    public <T> T create(final Class<T> shellModel) {
        //Utils.validateServiceInterface(service);

        return (T) Proxy.newProxyInstance(shellModel.getClassLoader(), new Class<?>[]{shellModel}, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // If the method is a method from Object then defer to normal invocation.

                // Build command
                List<String> commands = CommandFactory.build(method, args);

                // Execute
                System.out.println("\n------- OUTPUT -------");
                shell.exec(commands);

                return ShellFactory.this;
            }
        });
    }

    public static final class Builder {
        private ProcessBuilder processBuilder = null;
        private ShellInputStreamThread shellReader;
        private ShellInputStreamThread errorShellReader;
        private OnShellResultListener onShellResultListener;
        private OnShellErrorResultListener onShellErrorResultListener;
        private OnShellStateListener onShellStateListener;

        public Builder setProcessBuilder(ProcessBuilder processBuilder) {
            this.processBuilder = processBuilder;
            return this;
        }

        public Builder setShellReader(ShellInputStreamThread shellReader) {
            this.shellReader = shellReader;
            return this;
        }

        public Builder setErrorShellReader(ShellInputStreamThread errorShellReader) {
            this.errorShellReader = errorShellReader;
            return this;
        }

        public <T> Builder setOnShellListener(OnShellResultListener<T> onShellResultListener) {
            this.onShellResultListener = onShellResultListener;
            return this;
        }

        public Builder setOnShellErrorListener(OnShellErrorResultListener onShellErrorListener) {
            this.onShellErrorResultListener = onShellErrorListener;
            return this;
        }

        public Builder setOnShellStateListener(OnShellStateListener onShellStateListener) {
            this.onShellStateListener = onShellStateListener;
            return this;
        }

        public ShellFactory build() throws IOException {
            return build(false);
        }

        public ShellFactory build(boolean runAsSuperuser) throws IOException {
            boolean isRoot = false;
            if (processBuilder == null) {
                processBuilder = ProcessUtils.getDefaultProcessBuilder("sh");
                if (runAsSuperuser) {
                    try {
                        processBuilder = ProcessUtils.getDefaultProcessBuilder("su");
                        isRoot = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            Process process = processBuilder.start();
            AShell shell = new AShell(process);

            if (shellReader != null) {
                shell.setShellReader(shellReader);
            } else {
                shell.setShellReader(new ShellReader(process, shell));
            }
            if (errorShellReader != null) {
                shell.setErrorShellReader(errorShellReader);
            } else {
                shell.setErrorShellReader(new ErrorShellReader(process, shell));
            }
            if (onShellResultListener != null) {
                shell.setOnShellResultListener(onShellResultListener);
            }
            if (onShellErrorResultListener != null) {
                shell.setOnShellErrorResultListener(onShellErrorResultListener);
            }
            if (onShellStateListener != null) {
                shell.setOnShellStateListener(onShellStateListener);
            }

            shell.start();

            return new ShellFactory(shell, isRoot);
        }
    }
}
