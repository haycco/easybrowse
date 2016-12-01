package org.haycco.nativeexplorer.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author haycco
 */
public class NativeExplorerPopupAction extends AbstractNativeExplorerAction implements IObjectActionDelegate {
	
	public NativeExplorerPopupAction() {
		
	}
	
	public void setActivePart(IAction action, IWorkbenchPart selection) {
		window = selection.getSite().getWorkbenchWindow();
        shell = selection.getSite().getShell();
	}
	
}
