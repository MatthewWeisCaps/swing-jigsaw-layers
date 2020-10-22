package org.sireum.jigsaw;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

public class Launcher {

    public static final String JAR_NAME = "scala-swing-form-1.0-SNAPSHOT.jar";
    public static final String MODULE_NAME = "scala.swing.form";
    public static final String PACKAGE_NAME = "org.sireum.form";
    public static final String MAIN_CLASS_NAME = "JLauncher"; // must have main(String[] args) { ... } method

    public static void main(String[] args) {
        new Thread(() -> runModuleLayer(createModuleLayer(urlToPath(JAR_NAME), MODULE_NAME), MODULE_NAME, "Module 1")).start();
        new Thread(() -> runModuleLayer(createModuleLayer(urlToPath(JAR_NAME), MODULE_NAME), MODULE_NAME, "Module 2")).start();
        new Thread(() -> runModuleLayer(createModuleLayer(urlToPath(JAR_NAME), MODULE_NAME), MODULE_NAME, "Module 3")).start();
    }

    private static Path urlToPath(String url) {
        try {
            return Path.of(Launcher.class.getResource(url).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static void runModuleLayer(ModuleLayer moduleLayer, String moduleName, String name) {
        try {
            final Object args = new String[] { name };
            final Module simpleMod = moduleLayer.findModule(moduleName).orElseThrow();
            final Class<?> mainClass = simpleMod.getClassLoader().loadClass(PACKAGE_NAME + "." + MAIN_CLASS_NAME);
            final Method main = mainClass.getMethod("main", String[].class);
            final Object instance = mainClass.getConstructor().newInstance();
            main.invoke(instance, args);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static ModuleLayer createModuleLayer(Path jar, String moduleName) {
        final ModuleLayer bootLayer = ModuleLayer.boot();
        final Configuration config = bootLayer.configuration().
                resolve(ModuleFinder.of(jar), ModuleFinder.of(jar), List.of("java.desktop", moduleName));
        return bootLayer.defineModulesWithManyLoaders(config, Thread.currentThread().getContextClassLoader());
    }

}
