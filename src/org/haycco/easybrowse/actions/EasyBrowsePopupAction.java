package org.haycco.easybrowse.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author haycco 2011-7-25 ионГ10:12:06
 * @version V1.0
 */
public class EasyBrowsePopupAction extends AbstractEasyBrowseAction implements IObjectActionDelegate {
	
	public EasyBrowsePopupAction() {
		
	}
	
	public void setActivePart(IAction action, IWorkbenchPart selection) {
		window = selection.getSite().getWorkbenchWindow();
        shell = selection.getSite().getShell();
	}
	
}
