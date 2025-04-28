import javax.swing.*;
import java.awt.*;

public class HorseCustomizationPanel extends JPanel {
    private JTextField nameField;
    private JComboBox<String> breedComboBox;
    private JComboBox<String> colorComboBox;
    private JComboBox<String> symbolComboBox;
    private JComboBox<String> saddleComboBox;
    private JComboBox<String> horseshoesComboBox;
    private JComboBox<String> accessoriesComboBox;

    public HorseCustomizationPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name input field
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Horse Name:"), gbc);
        
        gbc.gridx = 1;
        nameField = new JTextField(15);
        add(nameField, gbc);

        // Breed combo box
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Select Breed:"), gbc);
        
        gbc.gridx = 1;
        breedComboBox = new JComboBox<>(new String[] {"Thoroughbred", "Arabian", "Quarter Horse"});
        add(breedComboBox, gbc);

        // Coat color combo box
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Select Coat Color:"), gbc);
        
        gbc.gridx = 1;
        colorComboBox = new JComboBox<>(new String[] {"Brown", "Black", "Gray", "White"});
        add(colorComboBox, gbc);

        // Symbol combo box
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Select Symbol:"), gbc);
        
        gbc.gridx = 1;
        symbolComboBox = new JComboBox<>(new String[] {"🐎", "🦄", "🐴"});
        add(symbolComboBox, gbc);

        // Saddle combo box
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Select Saddle:"), gbc);
        
        gbc.gridx = 1;
        saddleComboBox = new JComboBox<>(new String[] {"Standard", "Comfortable"});
        add(saddleComboBox, gbc);

        // Horseshoes combo box
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Select Horseshoes:"), gbc);
        
        gbc.gridx = 1;
        horseshoesComboBox = new JComboBox<>(new String[] {"Regular", "Lightweight"});
        add(horseshoesComboBox, gbc);

        // Accessories combo box
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Select Accessories:"), gbc);
        
        gbc.gridx = 1;
        accessoriesComboBox = new JComboBox<>(new String[] {"None", "Bridle", "Blanket", "Hat"});
        add(accessoriesComboBox, gbc);
    }

    // Getters for all fields
    public String getHorseName() {
        return nameField.getText().trim();
    }
    
    public JTextField getNameField() {
        return nameField;
    }
    
    public JComboBox<String> getBreedComboBox() { 
        return breedComboBox; 
    }
    
    public JComboBox<String> getColorComboBox() { 
        return colorComboBox; 
    }
    
    public JComboBox<String> getSymbolComboBox() { 
        return symbolComboBox; 
    }
    
    public JComboBox<String> getSaddleComboBox() { 
        return saddleComboBox; 
    }
    
    public JComboBox<String> getHorseshoesComboBox() { 
        return horseshoesComboBox; 
    }
    
    public JComboBox<String> getAccessoriesComboBox() { 
        return accessoriesComboBox; 
    }
}