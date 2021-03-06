/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
 */
package org.xwiki.eclipse.wizards;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.xwiki.eclipse.XWikiEclipseConstants;
import org.xwiki.plugins.eclipse.XWikiEclipsePlugin;

public class PageSettingsPage extends WizardPage
{
    private NewPageWizardState newPageWizardState;

    public PageSettingsPage(String pageName)
    {
        super(pageName);
        setTitle("New page");
        setImageDescriptor(XWikiEclipsePlugin
            .getImageDescriptor(XWikiEclipseConstants.SPACE_SETTINGS_BANNER));
    }

    public void createControl(Composite parent)
    {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayoutFactory.fillDefaults().applyTo(composite);

        Composite spaceSettingsArea = createSpaceSettingsArea(composite);
        GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(
            spaceSettingsArea);

        setControl(composite);
    }

    private Composite createSpaceSettingsArea(Composite parent)
    {
        newPageWizardState = ((NewPageWizard) getWizard()).getNewPageWizardState();

        Composite composite = new Composite(parent, SWT.NONE);
        GridLayoutFactory.fillDefaults().applyTo(composite);

        Group group = new Group(composite, SWT.NONE);
        GridLayoutFactory.fillDefaults().numColumns(2).margins(5, 5).applyTo(group);
        GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(group);
        group.setText("Page settings");

        /* Page title */
        Label label = new Label(group, SWT.NONE);
        label.setText("Title:");

        final Text titleText = new Text(group, SWT.BORDER);
        GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(
            titleText);
        titleText.addModifyListener(new ModifyListener()
        {
            public void modifyText(ModifyEvent e)
            {
                newPageWizardState.setTitle(titleText.getText());
                getContainer().updateButtons();
            }
        });

        return composite;
    }
}
