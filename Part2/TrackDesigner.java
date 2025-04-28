import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TrackDesigner extends JPanel {
    private JSpinner laneSpinner;
    private JTextField lengthField;
    private JComboBox<String> lengthUnitsComboBox;
    private JComboBox<String> shapeComboBox;
    private JComboBox<String> conditionComboBox;
    private JButton confirmButton;

    private List<ActionListener> trackCreationListeners = new ArrayList<>();

    private Track createdTrack; // <--- field to store the created track

    public TrackDesigner() {
        setLayout(new GridLayout(6, 2, 10, 10)); // Adjusted for an extra row

        // All your UI setup (same as before)
        JLabel laneLabel = new JLabel("Enter number of lanes:");
        // minimum of 1 and maximum of 1000
        SpinnerNumberModel laneModel = new SpinnerNumberModel(2, 1, 1000, 1);
        laneSpinner = new JSpinner(laneModel);

        JLabel lengthLabel = new JLabel("Enter track length:");
        lengthField = new JTextField();

        JLabel lengthUnitLabel = new JLabel("Select track length units:");
        String[] units = {"Meter", "Yard"};
        lengthUnitsComboBox = new JComboBox<>(units);

        JLabel shapeLabel = new JLabel("Select track shape:");
        String[] shapes = {"Straight", "Oval", "Figure-eight", "ZigZag"};
        shapeComboBox = new JComboBox<>(shapes);

        JLabel conditionLabel = new JLabel("Select track condition:");
        String[] conditions = {"Dry", "Muddy", "Icy"};
        conditionComboBox = new JComboBox<>(conditions);

        confirmButton = new JButton("Confirm Track");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTrack();
            }
        });

        add(laneLabel);
        add(laneSpinner);
        add(lengthLabel);
        add(lengthField);
        add(lengthUnitLabel);
        add(lengthUnitsComboBox); // Added the unit combo box here
        add(shapeLabel);
        add(shapeComboBox);
        add(conditionLabel);
        add(conditionComboBox);
        add(new JLabel()); // Empty space
        add(confirmButton);
    }

    private void createTrack() {
        try {
            // Get values from the fields
            int lanes = (Integer) laneSpinner.getValue();
            int length = Integer.parseInt(lengthField.getText());
            String unit = (String) lengthUnitsComboBox.getSelectedItem();
            String shape = (String) shapeComboBox.getSelectedItem();
            String condition = (String) conditionComboBox.getSelectedItem();

            if (length <= 0) {
                throw new NumberFormatException();
            }

            // Create the track with the units and other data
            createdTrack = new Track(lanes, length, unit, shape, condition);
            JOptionPane.showMessageDialog(this, "Track created successfully!");

            //notify the main class that the Track was created
            notifyTrackCreationListeners();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid positive number for track length!",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public void addTrackCreationListener(ActionListener listener) {
        trackCreationListeners.add(listener);
    }
    
    private void notifyTrackCreationListeners() {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "TrackCreated");
        for (ActionListener listener : trackCreationListeners) {
            listener.actionPerformed(event);
        }
    }
    public Track getCreatedTrack() { // <-- Add a public getter!
        return createdTrack;
    }
}
