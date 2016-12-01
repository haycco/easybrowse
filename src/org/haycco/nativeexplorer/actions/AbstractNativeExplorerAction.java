package org.haycco.nativeexplorer.actions;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.haycco.nativeexplorer.NativeExplorerPlugin;

/**
 * @author haycco
 */
public abstract class AbstractNativeExplorerAction implements IActionDelegate {

	protected IWorkbenchWindow window;
    protected Shell shell;
    protected ISelection currentSelection;
    public static final String WINDOWS = "win32";
    public static final String LINUX = "linux";
    public static final String MACOSX = "macosx";
    protected String osName;
    protected String systemBrowser;
    
    public AbstractNativeExplorerAction() {
        window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        systemBrowser = "explorer";
        osName = System.getProperty("osgi.os");
        if(WINDOWS.equalsIgnoreCase(osName)) {
        	systemBrowser = "explorer";
        } else if(LINUX.equalsIgnoreCase(osName)) {
        	systemBrowser = "nautilus";
        } else if(MACOSX.equalsIgnoreCase(osName)) {
        	systemBrowser = "open";
        }
    }
    
	public void run(IAction action) {
		if(currentSelection == null || currentSelection.isEmpty())
            return;
        if(currentSelection instanceof ITreeSelection) {
            ITreeSelection treeSelection = (ITreeSelection)currentSelection;
            TreePath paths[] = treeSelection.getPaths();
            for(int i = 0; i < paths.length; i++) {
                TreePath path = paths[i];
                IResource resource = null;
                Object segment = path.getLastSegment();
                if(segment instanceof IResource) {
                    resource = (IResource)segment;
                } else if(segment instanceof IJavaElement) {
                    resource = ((IJavaElement)segment).getResource();
                }
                if(resource != null) {
                    String browser = systemBrowser;
                    String location = resource.getLocation().toOSString();
                    if(resource instanceof IFile) {
                        location = ((IFile)resource).getParent().getLocation().toOSString();
                        if(WINDOWS.equalsIgnoreCase(osName)) {
                            browser = (new StringBuilder(String.valueOf(systemBrowser))).append(" /select,").toString();
                            location = ((IFile)resource).getLocation().toOSString();
                        }
                    }
                    openInBrowser(browser, location);
                }
            }

        } else if( (currentSelection instanceof ITextSelection) 
        		   || (currentSelection instanceof IStructuredSelection) ) {
            IEditorPart editor = window.getActivePage().getActiveEditor();
            if(editor != null)
            {
				IFile current_editing_file = (IFile)editor.getEditorInput().getAdapter(IFile.class);
                String browser = systemBrowser;
                String location = current_editing_file.getParent().getLocation().toOSString();
                if(WINDOWS.equalsIgnoreCase(osName))
                {
                    browser = (new StringBuilder(String.valueOf(systemBrowser))).append(" /select,").toString();
                    location = current_editing_file.getLocation().toOSString();
                }
                openInBrowser(browser, location);
            }
        }
	}

	public void selectionChanged(IAction action, ISelection selection) {
		currentSelection = selection;
	}
	
	/**
	 * Open in OS native explorer
	 * 
	 * @param browser OS Explorer
	 * @param location the open file's path
	 */
	protected void openInBrowser(String browser, String location) {
        try {
            if(!NativeExplorerPlugin.getDefault().isSupported()) {
                MessageDialog.openInformation(new Shell(), "Native Explorer", "This platform (" + System.getProperty("osgi.os") + ") is currently unsupported.\n" + "You can try to provide the correct command to execute in the Preference dialog.\n" + "If you succeed, please be kind to post your discovery on Native Explorer website 404,\n" + "or by email haycco@gmail.com. Thanks !");
                return ;
            }
            String target = NativeExplorerPlugin.getDefault().getTarget();
            if(target.indexOf("{0}") == -1)
                target = target.trim() + " {0}";
            if(!target.equalsIgnoreCase("explorer.exe {0}")) {
            	target = MessageFormat.format(target, location);
            	NativeExplorerPlugin.log("running: " + target);
                Runtime.getRuntime().exec(target);
            } else {
	            NativeExplorerPlugin.log("running: " + browser + " " + location);
		        if(WINDOWS.equalsIgnoreCase(osName))
		        	Runtime.getRuntime().exec((new StringBuilder(String.valueOf(browser))).append(" \"").append(location).append("\"").toString());
		        else
		        	Runtime.getRuntime().exec(new String[] {
		        			browser, location
		        	});
            }
         } catch(IOException e) {
             MessageDialog.openError(shell, "Native Explorer Error", (new StringBuilder("Can't open \"")).append(location).append("\"").toString());
             NativeExplorerPlugin.log(e);
         }
    }

}
