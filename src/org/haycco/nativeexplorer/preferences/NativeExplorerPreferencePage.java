package org.haycco.nativeexplorer.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.haycco.nativeexplorer.NativeExplorerPlugin;

/**
 * @author haycco
 * @version 1.1
 */
public class NativeExplorerPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static final String P_TARGET = "org.haycco.nativeexplorer.targetPreference";
	
	public void init(IWorkbench iworkbench) {
	    
	}
	
    public NativeExplorerPreferencePage() {
        super(1);
        setPreferenceStore(NativeExplorerPlugin.getDefault().getPreferenceStore());
        setDescription("Set up your file explorer application.");
    }

    public void createFieldEditors() {
        addField(new StringFieldEditor("org.haycco.nativeexplorer.targetPreference", "&Target:", getFieldEditorParent()));
    }

}
