package org.lic.hell.movie;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Created by lc on 15/7/23.
 */
public abstract class AbstractGui {

    public void run() {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        shell.setBounds(100, 100, 900, 600);
        create(shell);
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.beep();
        display.dispose();
    }

    public abstract void create(Shell shell);

}
