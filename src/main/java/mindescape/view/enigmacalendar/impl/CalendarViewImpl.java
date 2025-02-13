package mindescape.view.enigmacalendar.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mindescape.controller.enigmacalendar.impl.CalendarControllerImpl;
import mindescape.view.enigmacalendar.api.CalendarView;

/**
 * Implementation of the calendar view that displays a daily schedule with time slots and activities.
 * The layout dynamically adjusts to the resizing of the window.
 */
public class CalendarViewImpl implements CalendarView {

    private final JPanel panel;
    private final JLabel titleLabel;
    private final JPanel schedulePanel;
    private final JLabel[] timeLabels;
    private final JLabel[] activityLabels;

    /**
     * Constructor that initializes the calendar view.
     * Creates the panel structure with time slot and activity labels, and adds logic
     * to resize the text based on the window's width.
     * 
     * @param controller The controller that manages the calendar view.
     */
    public CalendarViewImpl(final CalendarControllerImpl controller) {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.DARK_GRAY);

        final JButton button = new JButton("Quit");
        button.addActionListener(e -> controller.quit());
        panel.add(button, BorderLayout.SOUTH);

        titleLabel = new JLabel("Daily Schedule", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.NORTH);

        final String[] timeSlots = {"09:00 - 12:00", "12:00 - 13:00", "14:00 - 16:00", "16:00 - 21:00"};
        final String[] activities = {
                "Group psychological session",
                "Lunch in the canteen",
                "Afternoon rest",
                "Outdoor activities"
        };

        schedulePanel = new JPanel();
        schedulePanel.setLayout(new GridLayout(timeSlots.length, 1, 10, 10));
        schedulePanel.setBackground(Color.DARK_GRAY);

        timeLabels = new JLabel[timeSlots.length];
        activityLabels = new JLabel[activities.length];

        // Creates the labels for time slots and activities, then adds them to the panel
        for (int i = 0; i < timeSlots.length; i++) {
            final JPanel entryPanel = new JPanel(new BorderLayout());
            entryPanel.setBackground(new Color(50, 50, 50));
            entryPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

            timeLabels[i] = new JLabel(timeSlots[i], SwingConstants.CENTER);
            timeLabels[i].setFont(new Font("Arial", Font.BOLD, 18));
            timeLabels[i].setForeground(Color.WHITE);
            entryPanel.add(timeLabels[i], BorderLayout.WEST);

            activityLabels[i] = new JLabel(activities[i], SwingConstants.CENTER);
            activityLabels[i].setFont(new Font("Arial", Font.PLAIN, 18));
            activityLabels[i].setForeground(Color.WHITE);
            entryPanel.add(activityLabels[i], BorderLayout.CENTER);

            schedulePanel.add(entryPanel);
        }

        panel.add(schedulePanel, BorderLayout.CENTER);

        // Adds a listener to resize the text when the window is resized
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                final int width = panel.getWidth();
                final int fontSize = Math.max(14, width / 30);
                titleLabel.setFont(new Font("Arial", Font.BOLD, fontSize + 6));
                for (final JLabel label : timeLabels) {
                    label.setFont(new Font("Arial", Font.BOLD, fontSize));
                }
                for (final JLabel label : activityLabels) {
                    label.setFont(new Font("Arial", Font.PLAIN, fontSize));
                }
                button.setFont(new Font("Arial", Font.BOLD, fontSize));
            }
        });
    }

    /**
     * Returns the panel that contains the calendar view.
     * 
     * @return The panel containing the calendar view.
     */
    @Override
    public JPanel getPanel() {
        return panel;
    }

}
