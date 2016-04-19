package org.haycco.nativeexplorer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * @author haycco
 * @version 1.1
 */
public class NativeExplorerPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.haycco.nativeexplorer"; //$NON-NLS-1$

	// The shared instance
	private static NativeExplorerPlugin plugin;
	
	private ResourceBundle resourceBundle;
	
    public static final String WINDOWS = "win32";
    public static final String LINUX = "linux";
    public static final String MACOSX = "macosx";
    
	/**
	 * The constructor
	 */
	public NativeExplorerPlugin() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static NativeExplorerPlugin getDefault() {
		return plugin;
	}
	
	public static String getResourceString(String key) {
        ResourceBundle bundle = getDefault().getResourceBundle();
        String res = null;
        try {
            res = bundle.getString(key);
        } catch(MissingResourceException _ex) {
            res = key;
        }
        return res;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public static void log(Object msg) {
        ILog log = getDefault().getLog();
        Status status = new Status(1, PLUGIN_ID, 4, msg + "\n", null);
        log.log(status);
    }

    public static void log(Throwable ex) {
        ILog log = getDefault().getLog();
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        String msg = stringWriter.getBuffer().toString();
        Status status = new Status(4, PLUGIN_ID, 4, msg, null);
        log.log(status);
    }
	
	protected void initializeDefaultPreferences(IPreferenceStore store) {
        String defaultTarget = "shell_open_command {0}";
        String osName = System.getProperty("osgi.os");
        if(WINDOWS.equalsIgnoreCase(osName)) {
        	defaultTarget = "explorer.exe {0}";
        } else if(LINUX.equalsIgnoreCase(osName)) {
        	defaultTarget = "nautilus {0}";
        } else if(MACOSX.equalsIgnoreCase(osName)) {
        	defaultTarget = "open {0}";
        }
        store.setDefault("org.haycco.nativeexplorer.targetPreference", defaultTarget);
    }

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public String getTarget() {
        return getPreferenceStore().getString("org.haycco.nativeexplorer.targetPreference");
    }
	
	public boolean isSupported() {
        String osName = System.getProperty("osgi.os");
        return osName.indexOf(WINDOWS) != -1 || osName.indexOf(MACOSX) != -1 || osName.indexOf(LINUX) != -1;
    }
}
