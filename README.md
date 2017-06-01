# AShell
Perfect for Android and Mac os!

Utilize the power of lowlevel unix based os commands.<br/>
Just define unix commands with a shell executor model with simple java annotations and let AShell do the heavy work.
Add result listeners to handle command outputs and shell state.<br/>
Customize the shell build process and run as root, if root access is available on system.

# Gradle
#### Step 1. Add the JitPack repository to your build file
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
#### Step 2. Add the dependency
```groovy
dependencies {
    compile 'com.github.manneohlund:ashell:1.0.0' // Coming soon!
}
```

# Usage
### Shell model setup

```java
public interface ShellModel {

    @LS("{options} {paths}")
    void ls(@Param("options") String options, @Param("paths") String... paths);
    
    @CP("{in} {out}")
    void copy(@Paths @Param("in") String in, @Paths @Param("out") String out);
    
    @MV("{in} {out}")
    void rename(@Paths @Param("in") String in, @Paths @Param("out") String out);

    @CAT("{path}")
    void cat(@Param("path") String path);
}
```

### Basic usage

```java
    ShellFactory shellFactory = new ShellFactory.Builder()
            // Supply builder with listeners and customizations
            .build();

    ShellModel shellModel = shellFactory.create(ShellModel.class);
    
    // List files in a folder
    shellModel.ls("-l -a", "/system");
    
    // Copy an image
    shellModel.cp("/system/image.jpg", "/system/image2.jpg");
    
    // Copy an image
    shellModel.mv("/system/image.jpg", "/system/subfolder/image.jpg");
    
    // Read content in a text file
    shellModel.cat("/system/some_file.txt");
```

### Add shell result listeners
```java
    OnShellResultListener listener = new OnShellResultListener<String>() {
        @Override
        public boolean onShellResult(String result) {
            System.out.println(result);
            return false; // Must always return false if you didn't handle the command
        }
    };
    
    OnShellErrorResultListener errorListener = new OnShellErrorResultListener() {
        @Override
        public boolean onShellErrorResult(String result) {
            System.err.println(result);
            return false; // Must always return false if you didn't handle the command
        }
    };
    
    ShellFactory shellFactory = new ShellFactory.Builder()
            .setOnShellListener(listener)
            .setOnShellErrorListener(errorListener)
            .build();
```

### Advanced usage
```java
Coming soon!
```

License
-------

    Copyright 2017 Manne Öhlund

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
