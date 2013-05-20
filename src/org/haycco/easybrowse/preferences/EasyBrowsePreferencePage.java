package org.haycco.easybrowse.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.haycco.easybrowse.EasyBrowsePlugin;

/**
 * @author haycco 2011-7-25 ÉÏÎç10:11:58
 * @version V1.0
 */
public class EasyBrowsePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static final String P_TARGET = "org.haycco.easybrowse.targetPreference";
	
	public void init(IWorkbench iworkbench) {
	}
	
    public EasyBrowsePreferencePage() {
        super(1);
        setPreferenceStore(EasyBrowsePlugin.getDefault().getPreferenceStore());
        setDescription("Set up your file explorer application.");
    }

    public void createFieldEditors() {
        addField(new StringFieldEditor("org.haycco.easybrowse.targetPreference", "&Target:", getFieldEditorParent()));
    }

}
