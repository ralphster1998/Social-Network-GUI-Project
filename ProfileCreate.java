
/**This class is to create the very first frame that appears when the program runs
 * This is where users can create a profile. They can create their user name, choose an icon, and join the network
 */

import java.awt.*;

import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class ProfileCreate extends JFrame {

    // constructor
    public ProfileCreate() {
        this.setTitle("The Social Network");//setting title for the frame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.add(getPanel());
    }
    
    // method to create panel for the Profile Create page
    public JPanel getPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setLayout(new BorderLayout());
        
        // setting up subpanels for the main panel on top of this method
        JPanel topPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel();
        
        GridBagConstraints c = new GridBagConstraints();// layout manager

        // setting title for the Panel on top
        JLabel title = new JLabel("CREATE PROFILE TO JOIN NETWORK"); // 0 b/c label is displayed at the center
        title.setFont(new Font("Arial", 1, 24));
        c.gridx = 1; c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        topPanel.add(title, c);
        
        JLabel nameLabel = new JLabel("Name"); // set width to 5 characters
        nameLabel.setFont(new Font("Arial", 1, 24));
        
        // setting a text field along with a label 
        // to get the self user name. Then, adding them
        // on the top panel
        JTextField nameInput = new JTextField("", 10);
        nameInput.setFont(new Font("Arial", 1, 24));
        c.gridx = 0; c.gridy = 1;
        c.insets = new Insets(10, 10, 10, 10);
        topPanel.add(nameLabel, c);
        
        c.gridx = 1; c.gridy = 1;
        c.insets = new Insets(10, 10, 10, 10);
        topPanel.add(nameInput, c);
        
        // All images to scale
        ImageIcon img = new ImageIcon(getClass().getResource("guy.jpg"));
        Image imgScaled = img.getImage();
        Image newImage = imgScaled.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon rightImg = new ImageIcon(newImage);
        
        ImageIcon img2 = new ImageIcon(getClass().getResource("girl.jpg"));
        Image imgScaled2 = img2.getImage();
        Image newImage2 = imgScaled2.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon rightImg2 = new ImageIcon(newImage2);
        
        ImageIcon img3 = new ImageIcon(getClass().getResource("default.jpg"));
        Image imgScaled3 = img3.getImage();
        Image newImage3 = imgScaled3.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon rightImg3 = new ImageIcon(newImage3);
        
        // image icon array, where the three images are added
        ImageIcon [] rightImages =
        {
            rightImg,
            rightImg2,
            rightImg3
        };
        
        // setting the Select Image label
        JLabel selectImage = new JLabel("Select Image: ");
        selectImage.setFont(new Font("Arial", 1, 18));
        c.gridx = 0; c.gridy = 2;
        c.insets = new Insets(10, 10, 10, 10);
        selectImage.setPreferredSize(new Dimension(150, 200));
        topPanel.add( selectImage, c );
        
        // setting JComboBox to get the self user optional image; this has user 
        // to pick a picture
        JComboBox<ImageIcon> imageOptions = new JComboBox<>( rightImages );
        c.gridx = 1; c.gridy = 2;
        c.insets = new Insets(10, 10, 10, 10);
        imageOptions.setPreferredSize(new Dimension(150, 83));
        topPanel.add( imageOptions, c );
        
        // setting the JOIN button
        JButton joinButton = new JButton("JOIN");
        joinButton.setPreferredSize(new Dimension(80, 20));
        buttonPanel.add(joinButton);
        
        // setting panel layout
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        // Message Dialog confirming the self user has joined the network
        // once user presses JOIN button
        joinButton.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                               ImageIcon image = (ImageIcon) imageOptions.getSelectedItem();
                               dispose();
                               JOptionPane.showMessageDialog(null, nameInput.getText() 
                                       + ", you have joined the network.");
                               MainPage mainPage = new MainPage(nameInput.getText(), image, rightImages, new ArrayList<>());
                               mainPage.pack();
                               mainPage.setVisible(true);         
                   }
           });

        
        return panel;
    }
}