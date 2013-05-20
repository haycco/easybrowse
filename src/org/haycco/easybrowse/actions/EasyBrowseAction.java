package org.haycco.easybrowse.actions;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * @author haycco 2011-7-25 ионГ10:12:12
 * @version V1.0
 */
public class EasyBrowseAction extends AbstractEasyBrowseAction implements IWorkbenchWindowActionDelegate {
    
	/**
	 * The constructor.
	 */
	public EasyBrowseAction() {
		
	}
	
	public void init(IWorkbenchWindow window) {
        this.window = window;
        shell = this.window.getShell();
    }

    public void dispose() {
    	
    }
}
