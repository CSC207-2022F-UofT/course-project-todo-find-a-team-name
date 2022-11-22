package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

/**
 * JPanel that request input needed for recommend BR use case, including
 * breadth categories and preferred time
 */
public class RecommendBRInputScreen extends JPanel implements ActionListener{

    private final RecommendBRController controller;
    private final JCheckBox[] checkBoxes;
    private final JRadioButton[] radioButtons;

    /**
     * Constructs RecommendBRInputScreen from the given RecommendBRController and timetable id.
     * It displays check boxes so user can select breadth categories, and radio buttons so user can
     * select preferred time. It also displays submit input button to start recommending breadth courses.
     *
     * @param controller controller used to recommend breadth courses
     * @param timetableId id of the timetable
     */
    public RecommendBRInputScreen(RecommendBRController controller) {
        super();
        this.controller = controller;

        setLayout(new BorderLayout());
        JLabel title = new JLabel("<html><u>BR Recommender</u></html>");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        add(title, BorderLayout.PAGE_START);

        JCheckBox br1CheckBox = new JCheckBox("Creative and Cultural Representations");
        JCheckBox br2CheckBox = new JCheckBox("Thought, Belief, and Behaviour");
        JCheckBox br3CheckBox = new JCheckBox("Society and Its Institutions");
        JCheckBox br4CheckBox = new JCheckBox("Living Things and Their Environment");
        JCheckBox br5CheckBox = new JCheckBox("The Physical and Mathematical Universes");

        checkBoxes = new JCheckBox[] {br1CheckBox, br2CheckBox, br3CheckBox, br4CheckBox, br5CheckBox};

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        JLabel instruction1 = new JLabel("Choose breadth category(s): ");
        panel1.add(instruction1, BorderLayout.PAGE_START);

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.add(br1CheckBox);
        checkBoxPanel.add(br2CheckBox);
        checkBoxPanel.add(br3CheckBox);
        checkBoxPanel.add(br4CheckBox);
        checkBoxPanel.add(br5CheckBox);

        panel1.add(checkBoxPanel, BorderLayout.CENTER);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        JLabel instruction2 = new JLabel("Choose the preferred time: ");
        panel2.add(instruction2, BorderLayout.PAGE_START);

        JRadioButton early = new JRadioButton("early");
        JRadioButton balanced = new JRadioButton("balanced");
        JRadioButton late = new JRadioButton("late");
        JRadioButton dontCare = new JRadioButton("don't care");

        radioButtons = new JRadioButton[] {early, balanced, late, dontCare};

        JPanel radioButtonPanel = new JPanel();
        ButtonGroup radioButtons = new ButtonGroup();

        radioButtons.add(early);
        radioButtons.add(balanced);
        radioButtons.add(late);
        radioButtons.add(dontCare);

        radioButtonPanel.add(early);
        radioButtonPanel.add(balanced);
        radioButtonPanel.add(late);
        radioButtonPanel.add(dontCare);

        panel2.add(radioButtonPanel, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(panel1);
        panel.add(panel2);
        add(panel, BorderLayout.CENTER);

        JButton recommendBr = new JButton("Submit Input");
        recommendBr.addActionListener(this);

        this.add(recommendBr, BorderLayout.PAGE_END);

        this.setVisible(true);

    }

    /**
     * If submit input button is clicked, pass in the input to the controller to
     * start BR recommendation
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        HashSet<String> brCategoriesSelected = new HashSet<>();

        for (int i = 0; i < checkBoxes.length; i++){
            if (checkBoxes[i].isSelected()){
                brCategoriesSelected.add(String.valueOf(i + 1));
            }
        }

        String preferredTime = "don't care";
        for (JRadioButton radioButton : radioButtons) {
            if (radioButton.isSelected()) {
                preferredTime = radioButton.getText();
                break;
            }
        }

        controller.recommendBr(brCategoriesSelected, preferredTime);
    }
}
