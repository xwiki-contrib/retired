
package com.xpn.xwiki.wiked.internal.xwt.cf;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Widget;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import com.xpn.xwiki.wiked.internal.xwt.AbstractSWTFactory;
import com.xpn.xwiki.wiked.internal.xwt.ObjectRegistry;
import com.xpn.xwiki.wiked.internal.xwt.XWTException;

/**
 * Composite factory for JavaBeans
 */
public class SelectionListenerFactory extends AbstractSWTFactory {

    protected Object createObject(Widget parent, Element element) 
        throws XWTException {
		try {
			Node node = XPathAPI.selectSingleNode(element, "text()");
            if (node != null && node instanceof Text) {
                String code = ((Text)node).getData();
                if (code != null && code.length() > 0) {
                	if (parent instanceof Button) {
	                    ((Button)parent).addSelectionListener(
	                        new GroovySelectionListner(code, this.getObjectRegistry()));
                	} else if (parent instanceof CCombo) {
	                    ((CCombo)parent).addSelectionListener(
	                        new GroovySelectionListner(code, this.getObjectRegistry()));
                	}
                }
            }
            return null;
		} catch (TransformerException ex) {
			throw new XWTException(ex);
		}
	}

    private static class GroovySelectionListner implements SelectionListener {

        private String code;
        private GroovyShell shell;
    
		public GroovySelectionListner(String code, ObjectRegistry widgetRegistry) {
			this.code = code;
            Binding binding = new Binding();
            String[] ids = widgetRegistry.getObjectIds();
            for (int i = 0; i < ids.length; i++) {
            	binding.setVariable(ids[i], widgetRegistry.getObject(ids[i]));
			}
            this.shell = new GroovyShell(getClass().getClassLoader(), binding);
		}

		public void widgetSelected(SelectionEvent event) {
            try {
                this.shell.setVariable("event", event);
            	this.shell.evaluate(this.code);
            } catch (Exception ex) {
                IllegalStateException isex = new IllegalStateException();
                isex.initCause(ex);
            	throw isex;
            }
    	}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
        }
    }
    
}