import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WizardGUI {
    public WizardGUI(){
        JFrame frame = new JFrame("Wizard App");
        frame.setSize(900,300);
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //This panel will contain Buttons to sort by spell school
        JPanel panel = new JPanel();
        ButtonGroup buttonGroup = new ButtonGroup();
        JLabel wizardSchool = new JLabel("WIZARD SCHOOLS");

        //Create a radio button for each school
        /**************************************************************/
        Map<JRadioButton,String> schoolButtonMap = new HashMap<JRadioButton,String>();

        JRadioButton abjuration = new JRadioButton("Abjuration");
        JRadioButton conjuration = new JRadioButton("Conjuration");
        JRadioButton divination = new JRadioButton("Divination");
        JRadioButton enchantment = new JRadioButton("Enchantment");
        JRadioButton evocation = new JRadioButton("Evocation");
        JRadioButton illusion = new JRadioButton("Illusion");
        JRadioButton necromancy = new JRadioButton("Necromancy");
        JRadioButton transmutation = new JRadioButton("Transmutation");

        //Add the buttons to a map so they can be accessed easier in the action listener
        schoolButtonMap.put(abjuration,"ABJ");
        schoolButtonMap.put(conjuration,"CON");
        schoolButtonMap.put(divination,"DIV");
        schoolButtonMap.put(enchantment,"ENC");
        schoolButtonMap.put(evocation,"EVO");
        schoolButtonMap.put(illusion,"ILL");
        schoolButtonMap.put(necromancy,"NEC");
        schoolButtonMap.put(transmutation,"TRN");

        //Add all radio buttons to button group
        buttonGroup.add(abjuration);
        buttonGroup.add(conjuration);
        buttonGroup.add(divination);
        buttonGroup.add(enchantment);
        buttonGroup.add(evocation);
        buttonGroup.add(illusion);
        buttonGroup.add(necromancy);
        buttonGroup.add(transmutation);
        abjuration.setSelected(true);

        //Add label and buttons to the panel
        panel.add(wizardSchool);
        panel.add(abjuration);
        panel.add(conjuration);
        panel.add(divination);
        panel.add(enchantment);
        panel.add(evocation);
        panel.add(illusion);
        panel.add(necromancy);
        panel.add(transmutation);

        frame.add(panel);
        /*****************************************************/

        //Panel 2 will contain Spell Levels
        JPanel panel2 = new JPanel();
        ButtonGroup buttonGroup2 = new ButtonGroup();
        JLabel spellLevel = new JLabel("SPELL LEVEL");

        /******************************************************/
        ArrayList<JRadioButton> levelButtonList = new ArrayList<JRadioButton>();
        JRadioButton level1 = new JRadioButton("Level 1");
        JRadioButton level2 = new JRadioButton("Level 2");
        JRadioButton level3 = new JRadioButton("Level 3");
        JRadioButton level4 = new JRadioButton("Level 4");
        JRadioButton level5 = new JRadioButton("Level 5");
        JRadioButton level6 = new JRadioButton("Level 6");
        JRadioButton level7 = new JRadioButton("Level 7");
        JRadioButton level8 = new JRadioButton("Level 8");
        JRadioButton level9 = new JRadioButton("Level 9");

        //Add level buttons to ArrayList
        levelButtonList.add(level1);
        levelButtonList.add(level2);
        levelButtonList.add(level3);
        levelButtonList.add(level4);
        levelButtonList.add(level5);
        levelButtonList.add(level6);
        levelButtonList.add(level7);
        levelButtonList.add(level8);
        levelButtonList.add(level9);


        //Add to the button group
        buttonGroup2.add(level1);
        buttonGroup2.add(level2);
        buttonGroup2.add(level3);
        buttonGroup2.add(level4);
        buttonGroup2.add(level5);
        buttonGroup2.add(level6);
        buttonGroup2.add(level7);
        buttonGroup2.add(level8);
        buttonGroup2.add(level9);
        level1.setSelected(true);

        //Add buttons to the panel
        panel2.add(spellLevel);
        panel2.add(level1);
        panel2.add(level2);
        panel2.add(level3);
        panel2.add(level4);
        panel2.add(level5);
        panel2.add(level6);
        panel2.add(level7);
        panel2.add(level8);
        panel2.add(level9);

        //Add level panel to the Frame
        frame.add(panel2);
        /****************************************************************/

        //Panel 3 will have a search bar to look up specific spells
        //Panel 3 will have the submit Button as well
        JPanel panel3 = new JPanel();

        //Panel 4 is where  results will appear
        JPanel panel4 = new JPanel();
        JTextArea pane = new JTextArea();
        pane.setColumns(25);
        pane.setEditable(false);
        pane.setLineWrap(true);
        pane.setWrapStyleWord(true);
        panel4.add(pane);

        JTextField search = new JTextField(15);
        JButton submit = new JButton("Submit");
        //Submit needs action listener
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get the school and level of spell
                String spellSchool  ="";

                for(Map.Entry<JRadioButton,String> entry : schoolButtonMap.entrySet()){
                    if(entry.getKey().isSelected()){
                        spellSchool = entry.getValue();
                    }
                }

                String spellLevel = "";
                for(int i = 0; i<levelButtonList.size(); i++){
                    if(levelButtonList.get(i).isSelected()){
                        spellLevel = "level"+(i+1)+"spells";
                    }
                }

                String query;
                int queryUsed = 0;
                String spell = search.getText();
                if(spell.isEmpty()){
                    //Print spells from selected school and level
                    query = "SELECT * FROM "+spellLevel+" WHERE SpellSchool = '" + spellSchool +"'";
                }
                else {
                    query = "SELECT * FROM " + spellLevel + " WHERE SpellName = '" + spell + "'";
                    queryUsed = 1;
                }

                Connector c = new Connector();
                     if(c.open()){
                         try {
                            String text = "";
                            Statement stmt = c.conn.createStatement();
                            ResultSet rs = stmt.executeQuery(query);
                            //System.out.println(query);
                            while(rs.next()) {
                                if(queryUsed == 1) {
                                   text += rs.getString(2);
                                }
                                else {
                                    text += rs.getString(1);
                                    text += "\n";
                                }
                                pane.setText(text);
                            }
                         }
                        catch(SQLException err){
                            err.printStackTrace();
                        }
                    }
                }
        });
        panel3.add(search);
        panel3.add(submit);

        frame.add(panel3);
        frame.add(panel4);

        frame.setVisible(true);
    }
}