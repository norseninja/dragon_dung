import backgrounds.BackgroundDataBase;
import backgrounds.Background;
import character.attributes.Attributes;
import character.races.RaceLibrary;
import utilities.DiceRoller;
import utilities.HintTextField;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class CharacterCreator {
    private final CharacterSheet characterSheet;
    private character.Character newCharacter = null;
    private final Attributes atr = new Attributes();
    private final RaceLibrary raceLibrary = new RaceLibrary();
    private final BackgroundDataBase backgrounds = new BackgroundDataBase();
    private final DiceRoller r;
    private final String[] attributes = {"Select attribute", "Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
    private final JComboBox<String> attribute1Box = new JComboBox<>(attributes);
    private final JComboBox<String> attribute2Box = new JComboBox<>(attributes);
    private final JComboBox<String> attribute3Box = new JComboBox<>(attributes);
    private final JComboBox<String> attribute4Box = new JComboBox<>(attributes);
    private final JComboBox<String> attribute5Box = new JComboBox<>(attributes);
    private final JComboBox<String> attribute6Box = new JComboBox<>(attributes);
    private final JTextArea attributeDescriptionArea = new JTextArea();

    public CharacterCreator(CharacterSheet characterSheet) {
        this.characterSheet = characterSheet;
        this.r = new DiceRoller();

        JFrame frame = new JFrame("Character Creator");
        frame.setSize(600, 600);
        frame.setResizable(false);

        JTabbedPane mainPanel = new JTabbedPane();
        frame.getContentPane().add(mainPanel);
        //mainPanel.setBackground(Color.lightGray);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        //A text area that will show a description of the currently selected choice.
        JTextArea descriptionArea = new JTextArea();
        Dimension descriptionAreaDimension = new Dimension(200, 400);
        descriptionArea.setPreferredSize(descriptionAreaDimension);
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JTextArea featuresDescriptionArea = new JTextArea();
        featuresDescriptionArea.setPreferredSize(new Dimension(200, 200));
        featuresDescriptionArea.setEditable(false);
        featuresDescriptionArea.setLineWrap(true);
        featuresDescriptionArea.setWrapStyleWord(true);

        JComboBox<String> raceSelect = new JComboBox<>(this.raceLibrary.getRaces());
        Dimension dd = new Dimension(130, 10);
        raceSelect.setPreferredSize(dd);

        JComboBox<String> subRaceSelect = new JComboBox<>(this.raceLibrary.getSubraces((String) raceSelect.getSelectedItem()));
        subRaceSelect.setPreferredSize(dd);

        raceSelect.addActionListener(e -> {

            descriptionArea.setText(raceLibrary.getRace((String) raceSelect.getSelectedItem()).getRaceName() + "\n\n" +
                    raceLibrary.getRace((String) raceSelect.getSelectedItem()).getRaceDescription());
            featuresDescriptionArea.setText(raceLibrary.getRace((String) raceSelect.getSelectedItem()).getRaceFeaturesAsString());
            subRaceSelect.removeAllItems();
            for (String string : this.raceLibrary.getSubraces((String) raceSelect.getSelectedItem())) {
                subRaceSelect.addItem(string);
            }
        });

        subRaceSelect.addActionListener(e -> {
            try {
                descriptionArea.setText(raceLibrary.getSubrace((String) subRaceSelect.getSelectedItem()).getSubraceName() +
                        "\n\n" + raceLibrary.getSubrace((String) subRaceSelect.getSelectedItem()).getSubraceDescription());
                featuresDescriptionArea.setText(raceLibrary.getSubrace((String) subRaceSelect.getSelectedItem()).getSubraceName() + " features:\n\n" + raceLibrary.getSubrace((String) subRaceSelect.getSelectedItem()).getSubraceFeaturesAsString());
            } catch(NullPointerException exception) {

            }
        });

        String[] classes = new String[]{"Select Class", "Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Rogue", "Sorcerer", "Warlock", "Wizard"};
        JComboBox<String> classSelect = new JComboBox<>(classes);
        classSelect.setPreferredSize(dd);

        JComboBox<String> subClassSelect = new JComboBox<>();
        subClassSelect.setPreferredSize(dd);

        classSelect.addActionListener(event -> {
            String selectedClass = (String) classSelect.getSelectedItem();

            switch (Objects.requireNonNull(selectedClass)) {
                case "Cleric" -> {
                    subClassSelect.removeAllItems();
                    subClassSelect.addItem("Divine Domain");
                    subClassSelect.addItem("Arcana");
                    subClassSelect.addItem("Death");
                    subClassSelect.addItem("Forge");
                    subClassSelect.addItem("Grave");
                    subClassSelect.addItem("Knowledge");
                    subClassSelect.addItem("Life");
                    subClassSelect.addItem("Light");
                    subClassSelect.addItem("Nature");
                    subClassSelect.addItem("Order");
                    subClassSelect.addItem("Peace");
                    subClassSelect.addItem("Tempest");
                    subClassSelect.addItem("Trickery");
                    subClassSelect.addItem("Twilight");
                    subClassSelect.addItem("War");
                }
                case "Sorcerer" -> {
                    subClassSelect.removeAllItems();
                    subClassSelect.addItem("Sorcerous Origin");
                    subClassSelect.addItem("Aberrant Mind");
                    subClassSelect.addItem("Clockwork Soul");
                    subClassSelect.addItem("Divine Soul");
                    subClassSelect.addItem("Draconic Bloodline");
                    subClassSelect.addItem("Shadow Magic");
                    subClassSelect.addItem("Storm Sorcery");
                    subClassSelect.addItem("Wild Magic");
                }
                case "Warlock" -> {
                    subClassSelect.removeAllItems();
                    subClassSelect.addItem("Otherworldly Patron");
                    subClassSelect.addItem("The Archfey");
                    subClassSelect.addItem("The Celestial");
                    subClassSelect.addItem("The Fathomless");
                    subClassSelect.addItem("The Fiend");
                    subClassSelect.addItem("The Genie");
                    subClassSelect.addItem("The Great Old One");
                    subClassSelect.addItem("The Hexblade");
                    subClassSelect.addItem("The Undead");
                    subClassSelect.addItem("The Undying");
                }
                default -> {
                    subClassSelect.removeAllItems();
                    subClassSelect.addItem("N/A");
                }
            }
        });

        JComboBox<String> backgroundSelect = new JComboBox<>(new String[]{"Select Background"});
        for (Background background : backgrounds.getBackgrounds()) {
            if (!background.getName().equals("Select Background")) {
                backgroundSelect.addItem(background.getName());
            }
        }
        backgroundSelect.setPreferredSize(dd);
        backgroundSelect.addActionListener(event -> {
            try {
                String b = (String) backgroundSelect.getSelectedItem();
                descriptionArea.setText(backgrounds.getBackground(b).getName()
                        + ":\n" + backgrounds.getBackground(b).getDescription()
                        + "\n\n" + backgrounds.getBackground(b).getFeature()[0]
                        + ":\n" + backgrounds.getBackground(b).getFeature()[1]);

            } catch (NullPointerException e) {
                descriptionArea.setText("No background selected.");
            }
        });

        JLabel nameLabel = new JLabel("Name: ");
        JLabel raceLabel = new JLabel("Race:");
        JLabel subRaceLabel = new JLabel("Subrace:");
        JLabel classLabel = new JLabel("Class:");
        JLabel subClassLabel = new JLabel("Subclass:");
        JLabel backgroundLabel = new JLabel("Background:");

        HintTextField nameTextField = new HintTextField("Character Name");
        nameTextField.setPreferredSize(dd);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0,2));


        inputPanel.add(nameLabel);
        inputPanel.add(nameTextField);
        inputPanel.add(raceLabel);
        inputPanel.add(raceSelect);
        inputPanel.add(subRaceLabel);
        inputPanel.add(subRaceSelect);
        inputPanel.add(classLabel);
        inputPanel.add(classSelect);
        inputPanel.add(subClassLabel);
        inputPanel.add(subClassSelect);
        inputPanel.add(backgroundLabel);
        inputPanel.add(backgroundSelect);

        JPanel raceClassBackgroundPanel = new JPanel();
        raceClassBackgroundPanel.setLayout(new GridBagLayout());
        raceClassBackgroundPanel.setBorder(new EmptyBorder(10,10,10,10));
        raceClassBackgroundPanel.setBackground(mainPanel.getBackground());
        descriptionArea.setBackground(raceClassBackgroundPanel.getBackground());
        featuresDescriptionArea.setBackground(raceClassBackgroundPanel.getBackground());
        inputPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));


        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        raceClassBackgroundPanel.add(inputPanel, c);
        c.fill = GridBagConstraints.NONE;
        raceClassBackgroundPanel.add(Box.createVerticalStrut(50), c);
        c.fill = GridBagConstraints.BOTH;
        raceClassBackgroundPanel.add(featuresDescriptionArea, c);
        c.weightx = 0;
        c.gridx = 1;
        c.gridheight = 3;
        raceClassBackgroundPanel.add(Box.createHorizontalStrut(10), c);
        c.gridx = 2;
        c.weightx = 1;
        raceClassBackgroundPanel.add(descriptionArea, c);

        JLabel rollTypeLabel = new JLabel("Roll type: ");
        JCheckBox standardRollBox = new JCheckBox("4d6, keep 3");
        standardRollBox.setSelected(true);
        JCheckBox hardRollBox = new JCheckBox("3d6");
        standardRollBox.addActionListener(e -> hardRollBox.setSelected(!hardRollBox.isSelected()));
        hardRollBox.addActionListener(e -> standardRollBox.setSelected(!hardRollBox.isSelected()));

        attributeDescriptionArea.setEditable(false);

        attribute1Box.addActionListener(event -> showAttributeDescription(attribute1Box));
        attribute2Box.addActionListener(event -> showAttributeDescription(attribute2Box));
        attribute3Box.addActionListener(event -> showAttributeDescription(attribute3Box));
        attribute4Box.addActionListener(event -> showAttributeDescription(attribute4Box));
        attribute5Box.addActionListener(event -> showAttributeDescription(attribute5Box));
        attribute6Box.addActionListener(event -> showAttributeDescription(attribute6Box));

        Dimension labelSize = new Dimension(100,50);
        JLabel attribute1Label = new JLabel();
        attribute1Label.setPreferredSize(labelSize);
        attribute1Label.setBorder(new BevelBorder(BevelBorder.LOWERED));
        JLabel attribute2Label = new JLabel();
        attribute2Label.setPreferredSize(labelSize);
        attribute2Label.setBorder(new BevelBorder(BevelBorder.LOWERED));
        JLabel attribute3Label = new JLabel();
        attribute3Label.setPreferredSize(labelSize);
        attribute3Label.setBorder(new BevelBorder(BevelBorder.LOWERED));
        JLabel attribute4Label = new JLabel();
        attribute4Label.setPreferredSize(labelSize);
        attribute4Label.setBorder(new BevelBorder(BevelBorder.LOWERED));
        JLabel attribute5Label = new JLabel();
        attribute5Label.setPreferredSize(labelSize);
        attribute5Label.setBorder(new BevelBorder(BevelBorder.LOWERED));
        JLabel attribute6Label = new JLabel();
        attribute6Label.setPreferredSize(labelSize);
        attribute6Label.setBorder(new BevelBorder(BevelBorder.LOWERED));

        JButton attribute1RollButton = new JButton("Roll");
        attribute1RollButton.addActionListener(e -> {
            if (standardRollBox.isSelected()) {
                attribute1Label.setText("" + this.r.roll("4d6k3"));
            } else {
                attribute1Label.setText("" + this.r.roll("3d6"));
            }
        });

        JButton attribute2RollButton = new JButton("Roll");
        attribute2RollButton.addActionListener(e -> {
            if (standardRollBox.isSelected()) {
                attribute2Label.setText("" + this.r.roll("4d6k3"));
            } else {
                attribute2Label.setText("" + this.r.roll("3d6"));
            }
        });

        JButton attribute3RollButton = new JButton("Roll");
        attribute3RollButton.addActionListener(e -> {
            if (standardRollBox.isSelected()) {
                attribute3Label.setText("" + this.r.roll("4d6k3"));
            } else {
                attribute3Label.setText("" + this.r.roll("3d6"));
            }
        });

        JButton attribute4RollButton = new JButton("Roll");
        attribute4RollButton.addActionListener(e -> {
            if (standardRollBox.isSelected()) {
                attribute4Label.setText("" + this.r.roll("4d6k3"));
            } else {
                attribute4Label.setText("" + this.r.roll("3d6"));
            }
        });

        JButton attribute5RollButton = new JButton("Roll");
        attribute5RollButton.addActionListener(e -> {
            if (standardRollBox.isSelected()) {
                attribute5Label.setText("" + this.r.roll("4d6k3"));
            } else {
                attribute5Label.setText("" + this.r.roll("3d6"));
            }
        });

        JButton attribute6RollButton = new JButton("Roll");
        attribute6RollButton.addActionListener(e -> {
            if (standardRollBox.isSelected()) {
                attribute6Label.setText("" + this.r.roll("4d6k3"));
            } else {
                attribute6Label.setText("" + this.r.roll("3d6"));
            }
        });

        JPanel rollAttributesPanel = new JPanel();
        rollAttributesPanel.setLayout(new GridLayout(0,3));
        rollAttributesPanel.add(rollTypeLabel);
        rollAttributesPanel.add(standardRollBox);
        rollAttributesPanel.add(hardRollBox);
        rollAttributesPanel.add(attribute1RollButton);
        rollAttributesPanel.add(attribute1Label);
        rollAttributesPanel.add(attribute1Box);
        rollAttributesPanel.add(attribute2RollButton);
        rollAttributesPanel.add(attribute2Label);
        rollAttributesPanel.add(attribute2Box);
        rollAttributesPanel.add(attribute3RollButton);
        rollAttributesPanel.add(attribute3Label);
        rollAttributesPanel.add(attribute3Box);
        rollAttributesPanel.add(attribute4RollButton);
        rollAttributesPanel.add(attribute4Label);
        rollAttributesPanel.add(attribute4Box);
        rollAttributesPanel.add(attribute5RollButton);
        rollAttributesPanel.add(attribute5Label);
        rollAttributesPanel.add(attribute5Box);
        rollAttributesPanel.add(attribute6RollButton);
        rollAttributesPanel.add(attribute6Label);
        rollAttributesPanel.add(attribute6Box);

        JPanel attributesPanel = new JPanel();
        attributesPanel.setLayout(new GridLayout(1,0));
        attributesPanel.add(rollAttributesPanel);
        attributesPanel.add(attributeDescriptionArea);

        JPanel personalityPanel = new JPanel();
        JButton finalizeCharacterButton = new JButton("Create Character");

        finalizeCharacterButton.addActionListener(e -> {
            HashMap<String, String> attributes = new HashMap<>();
            attributes.put((String) attribute1Box.getSelectedItem(), attribute1Label.getText());
            attributes.put((String) attribute2Box.getSelectedItem(), attribute2Label.getText());
            attributes.put((String) attribute3Box.getSelectedItem(), attribute3Label.getText());
            attributes.put((String) attribute4Box.getSelectedItem(), attribute4Label.getText());
            attributes.put((String) attribute5Box.getSelectedItem(), attribute5Label.getText());
            attributes.put((String) attribute6Box.getSelectedItem(), attribute6Label.getText());

            this.newCharacter = new character.Character(nameTextField.getText()
                    , (String) classSelect.getSelectedItem()
                    , (String) subRaceSelect.getSelectedItem()
                    , (String) backgroundSelect.getSelectedItem()
                    , Integer.parseInt(attributes.get("Strength"))
                    , Integer.parseInt(attributes.get("Dexterity"))
                    , Integer.parseInt(attributes.get("Constitution"))
                    , Integer.parseInt(attributes.get("Intelligence"))
                    , Integer.parseInt(attributes.get("Wisdom"))
                    , Integer.parseInt(attributes.get("Charisma")));

            this.characterSheet.sendCharacterToSheet(this.newCharacter);
        });

        personalityPanel.add(finalizeCharacterButton);

        mainPanel.addTab("Race, Class & Background",raceClassBackgroundPanel);
        mainPanel.addTab("Attributes", attributesPanel);
        mainPanel.addTab("Personality & Description", personalityPanel);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width / 2 - frame.getWidth() / 2, d.height / 2 - frame.getHeight() / 2);
        frame.setVisible(true);
    }
    private void updateAttributeList() {
        ArrayList<JComboBox<String>> boxes = new ArrayList<>();
        boxes.add(attribute1Box);
        boxes.add(attribute2Box);
        boxes.add(attribute3Box);
        boxes.add(attribute4Box);
        boxes.add(attribute5Box);
        boxes.add(attribute6Box);
        ArrayList<String> selectedItems = new ArrayList<>();
        ArrayList<String> unSelectedItems = new ArrayList<>(Arrays.asList(attributes));
        for (JComboBox<String> box : boxes) {
            selectedItems.add((String) box.getSelectedItem());
            unSelectedItems.remove((String) box.getSelectedItem());
        }
        for (JComboBox<String> box : boxes) {
            ArrayList<String> containedInBox = new ArrayList<>();
            int size = box.getItemCount();
            for (int i = 0; i < size; i++) {
                containedInBox.add(box.getItemAt(i));
            }
            for (String selectedItem : selectedItems) {
                if (!selectedItem.equals(box.getSelectedItem())) {
                    box.removeItem(selectedItem);
                }
            }
            for (String unSelectedItem : unSelectedItems) {
                if (!containedInBox.contains(unSelectedItem)) {
                    box.addItem(unSelectedItem);
                    containedInBox.add(unSelectedItem);
                }
            }
            if (!containedInBox.contains("Select attribute")) {
                box.addItem("Select attribute");
            }
        }
    }
    private void showAttributeDescription(JComboBox<String> combo) {
        String selectedAttribute = (String) combo.getSelectedItem();
        updateAttributeList();
        if (!Objects.equals(selectedAttribute, "Select attribute")) {
            attributeDescriptionArea.setText(atr.getAttribute(selectedAttribute).getDescription());
        } else {
            this.attributeDescriptionArea.setText("\n\n\n\n Select an attribute.");
        }
    }

    public character.Character getCharacter() {
        return this.newCharacter;
    }
}
