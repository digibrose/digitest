import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * Created by digibrose on 16/10/2015.
 * This Class contains the code to setup and run the main gui
 */
public class SymProto extends JPanel {

    /**
     *Declaration of panels
     */

    private JLayeredPane LP1;

    private JPanel Panel1;
    private JPanel Panel2;
    private SymImage Panel3;
    private ImagePanel Panel4;
    private JLabel QuestionText;

    private JPanel GlassPanel;


    /**
     * Declaration of Submit Button
     */
    private JButton Submit;

    /**
     * Declaration of WasteBin
     */

    private JLabel WasteBin;

    /**
     * Declaration of Question
     */


    private Question ActiveQuestion;
    private LinkedList<Question> QuestionList;
    private int questioncounter = 0;


    /**
     * Method to setup the MainGui on startup
     * @param QuestionList test
     */

    public SymProto(final LinkedList<Question> QuestionList){

        /**
         * Setup Question list
         */

        this.QuestionList = QuestionList;

        /**
         * Initial JFrame setup
         */

        JFrame f = new JFrame("Panel Test");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        /**
         * Primary Layered Pane LP1 setup
         */

        final JLayeredPane LP1 = new JLayeredPane();
        LP1.setLayout(new GridBagLayout());
        f.add(LP1);


        /**
         * Initialise first question as ActiveQuestion
         */

        ActiveQuestion = QuestionList.get(0);


        /**
         * Set up the Panel1 - Question Panel
         */

        Panel1 = new JPanel();
        GridBagConstraints gbc1 = new GridBagConstraints();
        Panel1.setLayout(null);
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.weighty = 0.25;
        gbc1.weightx = 0;
        gbc1.gridwidth = 2;
        gbc1.gridheight = 1;
        gbc1.fill = GridBagConstraints.BOTH;
        Panel1.setPreferredSize(new Dimension(663, 150));


        /**
         * Get Text from ActiveQuestion put in a Label and add to Panel1
         */

        QuestionText = new JLabel();
        QuestionText.setFont(new Font("Ariel", Font.LAYOUT_LEFT_TO_RIGHT, 20));
        QuestionText.setText(ActiveQuestion.getQuestion());
        QuestionText.setOpaque(true);
        Panel1.setLayout(null);
        Panel1.add(QuestionText);
        QuestionText.setBounds(0, 0, 663, 150);

        /**
         * Add Panel1 to base layered pane LP1
         */

        LP1.add(Panel1, gbc1, 2);

/**
 * Will at some point be an extra Panel
 *
        Panel2 = new JPanel();
        GridBagConstraints gbc2 = new GridBagConstraints();
        Panel2.setLayout(null);
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        gbc2.weighty = 0.25;
        gbc2.weightx = 0.5;
        gbc2.fill = GridBagConstraints.BOTH;
        Panel2.setBackground(Color.BLUE);
        LP1.add(Panel2, gbc2, 2);
*/

        /**
         * Set up Panel3 the Unit Cell Image Panel that icons will be dragged onto
         */

        Panel3 = new SymImage(ActiveQuestion.getSymBackground());
        GridBagConstraints gbc3 = new GridBagConstraints();
        Panel3.setLayout(null);
        Panel3.setPreferredSize(new Dimension(692, 519));
        gbc3.gridx = 0;
        gbc3.gridy = 1;
        gbc3.weighty = 0.75;
        gbc3.weightx = 0.7;
        gbc3.fill = GridBagConstraints.BOTH;
        LP1.add(Panel3, gbc3, 2);

        /**
         * Set up Panel4 the ImagePanel that holds the icons that are dragged to Panel3
         */

        Panel4 = new ImagePanel(ActiveQuestion.getSymLabels());
        GridBagConstraints gbc4 = new GridBagConstraints();
        Panel4.setPreferredSize(new Dimension(303, 519));
        gbc4.gridx = 1;
        gbc4.gridy = 1;
        gbc4.weighty = 0.75;
        gbc4.weightx = 0.3;
        gbc4.fill = GridBagConstraints.BOTH;
        Panel4.setBackground(Color.RED);
        LP1.add(Panel4, gbc4, 2);

        /**
         * Setup the Glass Panel that transfers the icons around
         */

        GlassPanel = new JPanel();
        GlassPanel.setLayout(null);
        GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.gridx = 0;
        gbc5.gridy = 0;
        gbc5.fill = GridBagConstraints.BOTH;
        gbc5.weightx = 1.0;
        gbc5.weighty = 1.0;
        gbc5.gridwidth = 2;
        gbc5.gridheight = 2;
        GlassPanel.setOpaque(false);

        /**
         * Add a Mouse Input Adapter, mover to the glass panel
         */

        final ImageMover mover = new ImageMover(Panel4, Panel1, Panel2, Panel3, GlassPanel, ActiveQuestion);
        GlassPanel.addMouseListener(mover);
        GlassPanel.addMouseMotionListener(mover);
        LP1.add(GlassPanel, gbc5, 0);


        /**
         * Setup a JLabel to act as the Waste bin for error icons and place it on the Glass Panel
         */

        WasteBin = new JLabel();
        WasteBin.setBackground(new Color(250, 0, 0, 90));
        WasteBin.setOpaque(true);
        WasteBin.setName("WasteBox");
        GlassPanel.add(WasteBin);
        WasteBin.setBounds(10, 160, 100, 100);

        /**
         * Setup a submit button to progress to new questions
         */

        Submit = new JButton();
        Submit.setBorder(BorderFactory.createLineBorder(Color.blue));
        Submit.setFont(new Font("Ariel", Font.LAYOUT_LEFT_TO_RIGHT, 24));
        Submit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Submit.setPreferredSize(new Dimension(200, 100));
        Submit.setBounds(740, 560, 200, 100);
        Submit.setText("Submit");
        Submit.setBackground(Color.ORANGE);
        Submit.setOpaque(true);
        GlassPanel.add(Submit);

        /**
         * Add an action listener for the Submit Button
         */

        Submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ActiveQuestion.CheckAnswer(Panel3.getComponents())) {
                    questioncounter++;
                    ActiveQuestion = QuestionList.get(questioncounter);
                    Panel3.ChangeImage(ActiveQuestion.getSymBackground());
                    Panel3.removeAll();
                   // Panel3.repaint();
                    QuestionText.setText(ActiveQuestion.getQuestion());
                    Panel4.ChangeLabels(ActiveQuestion.getSymLabels());
                    mover.ChangeHooks(ActiveQuestion.getSymHooks());
                    LP1.repaint();
                }
            }
        });

        /**
         * Final setup for the Frame
         */

        f.pack();
        f.setVisible(true);
        f.setLocation(100,100);
        f.setResizable(false);

    }

    /**
     * Main Method, sets up the question and passes it to MainGui
     * @param args
     */

    public static void main(String[] args) {

        Rectangle[] SymHooks = new Rectangle[4];
        SymHooks[0] = new Rectangle(242, 197 -150, 50, 50);
        SymHooks[1] = new Rectangle(74, 561 -150,50, 50);
        SymHooks[2] = new Rectangle(584, 197 -150, 50, 50);
        SymHooks[3] = new Rectangle(419, 561 -150, 50, 50);
        Rectangle[] SymHooks2 = new Rectangle[4];
        SymHooks2[0] = new Rectangle(506, 563 -150, 50, 50);
        SymHooks2[1] = new Rectangle(149, 564 -150, 50, 50);
        SymHooks2[2] = new Rectangle(506, 200 -150, 50, 50);
        SymHooks2[3] = new Rectangle(149, 200 -150, 50, 50);
        SymLabels[] SymLab = new SymLabels[4];
        SymLab[0] = new SymLabels("2fold.png", 2);
        SymLab[1] = new SymLabels("3fold.png", 3);
        SymLab[2] = new SymLabels("4fold.png", 4);
        SymLab[3] = new SymLabels("6fold.png", 6);
        SymLab[0].setPosition(new Point(17, 120));
        SymLab[1].setPosition(new Point(-5, 190));
        SymLab[2].setPosition(new Point(-5, 260));
        SymLab[3].setPosition(new Point(10, 320));
        Question FirstQuestion = new Question("Cell.png", SymHooks, SymLab, new int[] {2, 2, 2, 2}, "Put the symmetry operators on this P2 cell" );
        Question SecondQuestion = new Question("Cell2.png", SymHooks2, SymLab, new int[] {4, 4, 4, 4}, "Put the Four Folds on this P4 cell");

        LinkedList<Question> QuestionList = new LinkedList<Question>();
        QuestionList.add(FirstQuestion);
        QuestionList.add(SecondQuestion);
        System.out.println(System.getProperty("user.dir"));

        SymProto MainGui = new SymProto(QuestionList);

    }




}
