package org.haycco.nativeexplorer.actions;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * @author haycco
 */
public class NativeExplorerAction extends AbstractNativeExplorerAction implements IWorkbenchWindowActionDelegate {
    
	/**
	 * The constructor.
	 */
	public NativeExplorerAction() {
		
	}
	
	public void init(IWorkbenchWindow window) {
        this.window = window;
        shell = this.window.getShell();
    }

    public void dispose() {
    	
    }
}
