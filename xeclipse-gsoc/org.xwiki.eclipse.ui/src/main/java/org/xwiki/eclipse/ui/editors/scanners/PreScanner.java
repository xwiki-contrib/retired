package org.xwiki.eclipse.ui.editors.scanners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.xwiki.eclipse.ui.editors.scanners.detectors.PreWordDetector;
import org.xwiki.eclipse.ui.editors.scanners.detectors.WhitespaceDetector;
import org.xwiki.eclipse.ui.editors.util.ColorProvider;

/**
 * @author venkatesh
 */

public class PreScanner extends RuleBasedScanner
{
    @SuppressWarnings("unchecked")
    public PreScanner(ColorProvider provider, Display display)
    {

        // monospace font
        IToken xwiki_pre =
            new Token(new TextAttribute(provider.getColor(ColorProvider.XWIKI_PRECODE), null, SWT.NORMAL, new Font(
                display, "Monospace", 10, SWT.NORMAL)));
        List rules = new ArrayList();
        rules.add(new WhitespaceRule(new WhitespaceDetector()));
        // Add word rule for keywords, types, and constants.
        WordRule wordRule = new WordRule(new PreWordDetector(), xwiki_pre);
        rules.add(wordRule);
        IRule[] result = new IRule[rules.size()];
        rules.toArray(result);
        setRules(result);
    }
}
