package org.sireum.form;

import javax.swing.*;
import java.awt.*;

public class JForm {

    private static int staticCounter = 0;
    private static final ThreadLocal<Integer> threadLocalCounter = new ThreadLocal<>();

    private final JFrame frame = new JFrame();
    private final JPanel panel = new JPanel();

    private int instanceCounter = 0;

    private final JLabel instanceLabel    = new JLabel("instance:     0");
    private final JLabel threadLocalLabel = new JLabel("thread local: 0");
    private final JLabel staticLabel      = new JLabel("static:       0");

    private final JButton addButton = new JButton();

    /*
     * Freezes GUI thread for 5 seconds. If all Swing instances are truly sandboxed, then freezing
     * this window should not prevent other counters from working.
     */
    private final JButton freezeButton = new JButton();


    public JForm(String title) {
        // periodically print thread to stdout
        final Timer timer = new Timer(1000, action ->
                System.out.println(title + " " + Thread.currentThread().toString()));
        timer.setRepeats(true);
        timer.start();

        addButton.setText("Click me!");
        addButton.addActionListener(action -> {
            instanceCounter += 1;
            staticCounter += 1;

            Integer tl = threadLocalCounter.get();
            if (tl == null) {
                tl = 0;
            }
            tl += 1;

            threadLocalCounter.set(tl);
            instanceLabel.setText("instance:     " + instanceCounter);
            threadLocalLabel.setText("thread local:     " + tl);
            staticLabel.setText("static:     " + staticCounter);
        });

        freezeButton.setText("Freeze GUI thread for 5 seconds!");
        freezeButton.addActionListener(action -> {
            System.out.println("START sleeping on thread " + Thread.currentThread().toString());
            try {
                Thread.sleep(5_000L);
            } catch (InterruptedException e) {
                System.out.println("Exception thrown when sleeping GUI thread " + title + ":");
                e.printStackTrace();
            }
            System.out.println("--> FINISHED sleeping on thread " + Thread.currentThread().toString());
        });

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(instanceLabel);
        panel.add(new JSeparator(SwingConstants.HORIZONTAL));
        panel.add(threadLocalLabel);
        panel.add(new JSeparator(SwingConstants.HORIZONTAL));
        panel.add(staticLabel);
        panel.add(new JSeparator(SwingConstants.HORIZONTAL));
        panel.add(addButton);
        panel.add(new JSeparator(SwingConstants.HORIZONTAL));
        panel.add(freezeButton);

        frame.setTitle(title);
        frame.setPreferredSize(new Dimension(480, 360));
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
