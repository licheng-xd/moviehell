package org.lic.hell.movie;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.PaintObjectEvent;
import org.eclipse.swt.custom.PaintObjectListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.io.IOException;
import java.net.URL;

/**
 * Created by lc on 15/7/23.
 */
public class MainGui extends AbstractGui {
    public static void main(String[] args) {
        new MainGui().run();
    }

    private StyledText content;

    private Image[] images;

    private int[] offsets;

    private Image defaultImg;

    @Override public void create(Shell shell) {
        defaultImg = new Image(Display.getCurrent(), "/Users/lc/Pictures/404.png");
        shell.setSize(900, 600);
        shell.setText("Moive Hell");
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 3;
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        gridLayout.horizontalSpacing = 0;
        gridLayout.makeColumnsEqualWidth = true;
        shell.setLayout(gridLayout);

        load(shell);
    }

    public void load(Shell shell) {
        Composite composite1 = new Composite(shell, SWT.NONE);
        composite1.setBackground(
            Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
        GridData gridData1 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData1.horizontalSpan = 1;
        gridData1.grabExcessVerticalSpace = true;
        gridData1.grabExcessHorizontalSpace = true;
        gridData1.verticalAlignment = SWT.FILL;
        composite1.setLayoutData(gridData1);

        GridLayout compLayout = new GridLayout();
        compLayout.numColumns = 10;
        compLayout.marginWidth = 0;
        compLayout.marginHeight = 0;
        compLayout.horizontalSpacing = 0;
        compLayout.verticalSpacing = 0;
        compLayout.makeColumnsEqualWidth = true;
        composite1.setLayout(compLayout);

        GridData gridData2 = new GridData(GridData.FILL_BOTH);
        gridData2.horizontalSpan = 2;
        gridData2.grabExcessVerticalSpace = true;
        gridData2.grabExcessHorizontalSpace = true;
        gridData2.verticalAlignment = SWT.FILL;

        Composite composite2 = new Composite(shell, SWT.NONE);
        composite2.setLayoutData(new GridData(GridData.FILL_BOTH));
        composite2.setLayout(new GridLayout());
        composite2.setLayoutData(gridData2);
        composite2.setBackground(
            Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

        images = new Image[]{defaultImg,};
        offsets = new int[images.length];

        String loadingText = "Welcome to Movie Hell";
        content = new StyledText(composite2, SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
        content.setEditable(false);
        content.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        content.setText(loadingText);

        /*int lastOffset = 0;
        for (int i = 0; i < images.length; i++) {
            int offset = loadingText.indexOf("\uFFFC", lastOffset);
            offsets[i] = offset;
            addImage(images[i], offset);
            lastOffset = offset + 1;
        }*/

        content.addVerifyListener(new VerifyListener() {
            public void verifyText(VerifyEvent e) {
                int start = e.start;
                int replaceCharCount = e.end - e.start;
                int newCharCount = e.text.length();
                for (int i = 0; i < offsets.length; i++) {
                    int offset = offsets[i];
                    if (start <= offset && offset < start + replaceCharCount) {
                        // this image is being deleted from the text
                        if (images[i] != null && !images[i].isDisposed()) {
                            images[i].dispose();
                            images[i] = null;
                        }
                        offset = -1;
                    }
                    if (offset != -1 && offset >= start)
                        offset += newCharCount - replaceCharCount;
                    offsets[i] = offset;
                }
            }
        });
        content.addPaintObjectListener(new PaintObjectListener() {
            public void paintObject(PaintObjectEvent event) {
                GC gc = event.gc;
                StyleRange style = event.style;
                int start = style.start;
                for (int i = 0; i < offsets.length; i++) {
                    int offset = offsets[i];
                    if (start == offset) {
                        Image image = images[i];
                        int x = event.x;
                        int y = event.y + event.ascent - style.metrics.ascent;
                        gc.drawImage(image, x, y);
                    }
                }
            }
        });

        for (int i=0; i<LocalCache.getInstance().size(); i++) {
            final Button bt = new Button(composite1, SWT.NONE);
            final Movie movie = LocalCache.getInstance().get(i);
            bt.setText(movie.getName());
            bt.addMouseListener(new MouseListener() {
                @Override public void mouseDoubleClick(MouseEvent mouseEvent) {
                }

                @Override public void mouseDown(MouseEvent mouseEvent) {
                    System.out.println("mouse down");
                    tranText(movie);
                }

                @Override public void mouseUp(MouseEvent mouseEvent) {
                }
            });
            GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
            gd.horizontalSpan = 10;
            gd.grabExcessVerticalSpace = true;
            gd.grabExcessHorizontalSpace = true;
            gd.verticalAlignment = SWT.FILL;
            bt.setLayoutData(gd);
        }
    }

    void addImage(Image image, int offset) {
        StyleRange style = new StyleRange();
        style.start = offset;
        style.length = 1;
        Rectangle rect = image.getBounds();
        style.metrics = new GlyphMetrics(rect.height, 0, rect.width);
        content.setStyleRange(style);
    }

    public void tranText(Movie movie) {
        StringBuilder sb = new StringBuilder("\uFFFC\r\n\r\n");
        sb.append(movie.getName()).append("\r\n\r\n")
            .append(movie.getIntroduce()).append("\r\n\r\n")
            .append(movie.getHref()).append("\r\n\r\n");
        String text = sb.toString();

        content.setText(text);

        StyleRange style = new StyleRange();
        style.underline = true;
        style.underlineStyle = SWT.UNDERLINE_LINK;
        int[] ranges = {text.indexOf(movie.getHref()), movie.getHref().length()};
        StyleRange[] styles = {style,};
        content.setStyleRanges(ranges, styles);

        Image image = loadImage(movie.getImg());
        images = new Image[]{image,};
        offsets = new int[images.length];

        int lastOffset = 0;
        for (int i = 0; i < images.length; i++) {
            int offset = text.indexOf("\uFFFC", lastOffset);
            offsets[i] = offset;
            addImage(images[i], offset);
            lastOffset = offset + 1;
        }
        content.getParent().layout();
    }

    /**
     * Loads next image from folder
     */
    private Image loadImage(String url) {
        try {
            Image img = new Image(Display.getCurrent(), new URL(url).openStream());
            ImageData data = img.getImageData();
            data = data.scaledTo(data.width/2, data.height/2);
            return new Image(Display.getCurrent(), data);
        } catch (IOException ex) {
            System.err.println(ex);
            return new Image(Display.getCurrent(), "/Users/lc/Pictures/404.png");
        }
    }
}
