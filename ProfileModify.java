
/* This class is to create the modify profile page
 * This is where the self user can change name and icon, then go back to the Main Page 
 * */

import java.awt.*;

import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class ProfileModify extends JFrame {
    private String selfName; 
    private ArrayList<User> savedFriends;
    
    public ProfileModify(String selfName, ArrayList<User> savedFriends) {
        this.setTitle("The Social Network");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.selfName = selfName;
        this.savedFriends = savedFriends;
        this.add(getPanel());
    }
    
    // method to create panel for the Modify Profile page
    public JPanel getPanel() {
        JPanel panel = new JPanel(); //create an object of type JPanel
        panel.setPreferredSize(new Dimension(800, 600)); //setting the JPanel size
        panel.setLayout(new BorderLayout()); //setting the JPanel layout
        
        // setting up subpanels for the main panel on top of this method
        JPanel topPanel = new JPanel(new GridBagLayout()); //title panel
        JPanel buttonPanel = new JPanel();
        
        GridBagConstraints c = new GridBagConstraints();// layout manager

        // setting title for the Panel on top
        JLabel title = new JLabel("MODIFY PROFILE"); // 0 b/c label is displayed at the center
        title.setFont(new Font("Arial", 1, 24));
        c.gridx = 1; c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        topPanel.add(title, c);
        
        
        JLabel nameLabel = new JLabel("Name"); // set width to 5 characters
        nameLabel.setFont(new Font("Arial", 1, 24));
        c.gridx = 0; c.gridy = 1;
        c.insets = new Insets(10, 10, 10, 10);
        topPanel.add(nameLabel, c);
        
        // setting a text field along with a label 
        // to get the selfUser name. Then, adding them
        // on the top panel
        JTextField nameInput = new JTextField(this.selfName, 10);
        nameInput.setFont(new Font("Arial", 1, 24));
        c.gridx = 1; c.gridy = 1;
        c.insets = new Insets(10, 10, 10, 10);
        topPanel.add(nameInput, c);
        
        
        
        // Images to scale
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
        
        // put the images in an array so it's placed in JComboBox
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
        
        // setting JComboBox to get the selfUser image; this has user 
        // to change a picture
        JComboBox<ImageIcon> imageOptions = new JComboBox<>( rightImages );
        c.gridx = 1; c.gridy = 2;
        c.insets = new Insets(10, 10, 10, 10);
        imageOptions.setPreferredSize(new Dimension(150, 83));
        topPanel.add( imageOptions, c );
        
        // setting the GO BACK button
        JButton backButton = new JButton("GO BACK");
        backButton.setPreferredSize(new Dimension(80, 20));
        buttonPanel.add(backButton);
        
        // setting panel layout
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        // the back button shows a message Dialog confirming the self user has modified profile 
        // then goes back the main page while it also transfers the user's saved list of friends
        backButton.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                               ImageIcon image = (ImageIcon) imageOptions.getSelectedItem();
                               dispose();
                               JOptionPane.showMessageDialog(null, nameInput.getText() 
                                       + ", you modified the profile. ");
                               MainPage cart = new MainPage(nameInput.getText(), image, 
                                               rightImages, savedFriends);
                               cart.pack();
                               cart.setVisible(true);         
                   }
           });

        
        return panel;
    }
}