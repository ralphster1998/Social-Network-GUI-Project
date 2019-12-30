
/*This class is to create the main page which pops up after a user creates profile
 * This is where users can leave network, modify profile, or add friends
 * */

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class MainPage extends JFrame {
    private static String name;//user name
    private static ImageIcon selectedImage;//the selfUser's image
    
    // the images array, where the selfUser can choose from 
    // when creating profile. Also used for people in the network
    private static ImageIcon[] rightImages;
    
    // the selfUser is the user who is using the GUI application for this project

    private User.Network graph;
    
    //the 5 users in the network including the selfUser
    private static User selfUser, user2, user3, user4, user5; 
    private static User[] users; //the users array
    
    // this is so it keeps the states of all the users
    private static int gottenBack = 0; 
    
    // these are private member panels to set up the main page
    private static JPanel toolBarPanel, friendSearchPanel, generalDisplay;

    // constructor
    public MainPage(String name, ImageIcon selectedImage, 
                    ImageIcon [] rightImages, ArrayList<User> savedFriends) {
            
        this.setTitle("The Social Network Main Page"); // setting title for the frame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                
        // set the name for the selfUser
        this.name = name;
        
        // set the right images array
        this.rightImages = rightImages;
        
        // resize the selfUser's image
        Image profileImageScale = selectedImage.getImage();
        Image scaledImage = profileImageScale.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon rightImage = new ImageIcon(scaledImage);
        this.selectedImage = rightImage;

        // instantiates selfUser's info
        selfUser = new User(this.name, this.selectedImage, 0, true);
        
        // other madeup users
        user2 = new User("Nelson", this.rightImages[0], 1, false);
        user3 = new User("Emily", this.rightImages[2], 2, true);
        user4 = new User("Dashion",this.rightImages[1], 3, false);
        user5 = new User("Lee", this.rightImages[2], 4, true);
        
        // SETUP THE SOCIAL NETWORK INCLUDING THE SELF USER 
        // BY PUTTING USERS IN THE GRAPH AND FRIENDS RELATIONSHIPS
        // WHERE NEEDED EXCEPT WITH THE SELF USER
        graph = new User.Network();
        users = new User[SocialNetworkStock.NETWORK_USERS];
        users[0] = selfUser;
        users[1] = user2;
        users[2] = user3;
        users[3] = user4;
        users[4] = user5;
        User.addFriend(graph, user2, user3); 
        User.addFriend(graph, user2, user4); 
        User.addFriend(graph, user2, user5); 
        User.addFriend(graph, user3, user4); 
        User.addFriend(graph, user4, user5); 
        
        if (savedFriends.size() > 0) {
            for (User friend: savedFriends) {
                    // get the id of the checked user so you're able to add the friends
                    // of selfUser back in after modifying the profile
                int idCatched = friend.getId();
                User.addFriend(graph, selfUser, users[idCatched]);
                System.out.println("\nGetting selfUser's saved friends after modifying profile: ");
                System.out.println("selfUser's friends?: " + selfUser.getFriends());
            }
        }
               
        // create the panels of the Main Page
        toolBarPanel = getToolStatusPanel();
        friendSearchPanel = friendSearchPanel();   
        generalDisplay = getPeopleDisplay();
        generalDisplay.setPreferredSize(new Dimension(800, 175 * users.length));
        
        JScrollPane scroll = new JScrollPane(generalDisplay, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(800,500));
        
        // then adds the panels to the Main Page
        this.add(toolBarPanel, BorderLayout.WEST);
        this.add(scroll, BorderLayout.EAST);
        this.add(friendSearchPanel, BorderLayout.PAGE_END);
        paintPeoplePanel("");
        paintToolBarPanel();
        gottenBack++;
    }
    
    
    // setting up the toolBarPanel
    private JPanel getToolStatusPanel() {
        JPanel toolBarPanel = new JPanel(new GridBagLayout());

        toolBarPanel.setPreferredSize(new Dimension(300, 500));
        
        return toolBarPanel;
    }
    
    
    
    // setting up the friendSearchPanel
    private JPanel friendSearchPanel() {
        JPanel friendSearchPanel = new JPanel();
        
        
        Dimension buttonSize = new Dimension(150, 50);
        GridBagConstraints c = new GridBagConstraints();

        
        // add Name Search Label to bottom panel of Main Page
        JLabel nameLabel = new JLabel("Name Search: "); // set width to 5 characters
        nameLabel.setFont(new Font("Arial", 1, 24));
        c.gridx = 0; c.gridy = 1;
        c.insets = new Insets(10, 10, 10, 10);
        friendSearchPanel.add(nameLabel, c);
        
        // add Name Input field to bottom panel of Main Page
        JTextField nameInput = new JTextField("", 10);
        nameInput.setFont(new Font("Arial", 1, 24));
        c.gridx = 1; c.gridy = 1;
        c.insets = new Insets(10, 10, 10, 10);
        friendSearchPanel.add(nameInput, c);
        
        // add search button to bottom panel of Main Page
        JButton goTo = new JButton("Search");
        goTo.setPreferredSize(buttonSize);
        c.gridx = 2; c.gridy = 1;
        c.insets = new Insets(10, 10, 10, 10);
        friendSearchPanel.add(goTo, c);        
        friendSearchPanel.setPreferredSize(new Dimension(200, 70));
   
        // this action listener will search names once it is clicked
        goTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintPeoplePanel(nameInput.getText());
            }
        });
    
        return friendSearchPanel;
        
    } 
    
    
    
    // setting up the display to list users of the network except the selfUser
    private JPanel getPeopleDisplay() {
        JPanel peopleDisplay = new JPanel();
        peopleDisplay.setPreferredSize(new Dimension(800, 500));
        peopleDisplay.setBorder(BorderFactory.createEtchedBorder());
        peopleDisplay.setBackground(Color.WHITE);
        
        return peopleDisplay;
    }
    
   // displays the box of each user on the display, where it displays users in 
   // the network
   public JPanel displayUserBox(User user) {
        
        // gets image of user and resizes it
        ImageIcon img = user.getImage();
        Image imgScaled = img.getImage();
        Image newImage = imgScaled.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
        ImageIcon rightImg = new ImageIcon(newImage);
        JLabel image = new JLabel(rightImg);
        
        // set layouts of the user box
        JPanel west = new JPanel(new GridBagLayout());
        west.setPreferredSize(new Dimension(200, 190));
        
        JPanel center = new JPanel(new GridBagLayout());
        center.setPreferredSize(new Dimension(550, 190));
        
        JPanel east = new JPanel(new GridBagLayout());
        center.setPreferredSize(new Dimension(300, 190));
        
        JPanel userBox = new JPanel();
        userBox.add(west, BorderLayout.WEST);
        userBox.add(center, BorderLayout.CENTER);
        userBox.add(east, BorderLayout.EAST);
        
        // Image on Left Side
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.NORTHWEST;
        west.add(image, c);
        
        // User Info In The Center
        JLabel name = new JLabel(" Name: " + user.getName());
        c.gridx = 0; c.gridy = 0;
        center.add(name, c);
        
        JLabel currentStatus = new JLabel("Status: " + user.getStatus());
        c.gridx = 0; c.gridy = 1;
        center.add(currentStatus, c);
        
        // display friends list only if they're friends; if not friends, keep it private
        String privateFriends = "(Can't show --> Private)";
        if (selfUser.isFriends(user)) {
            privateFriends = "" + user.getFriends();
        }
        JLabel friendsList = new JLabel("Their Friends: " + privateFriends);
        c.gridx = 0; c.gridy = 2;
        center.add(friendsList, c);
        
        
        Dimension buttonSize = new Dimension(250, 100);
        JButton friendAdd = new JButton("+ Add Friend");
        friendAdd.setPreferredSize(buttonSize);
        
        JButton sayHi = new JButton("Say Hi");
        sayHi.setPreferredSize(buttonSize);
        
        // add friend section
        c.gridx = 1; c.gridy = 3;
        c.gridwidth = c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 33;
        c.weighty = 20;
        
        // display the correct button if the selfUser is friends with each user
        // or not (Say Hi button or Add Friend Button respectively)
        if (selfUser.isFriends(user)) {
            east.add(sayHi, c);
        } else {
            east.add(friendAdd, c);
        }
        
        
        // when users click the Add Friend button,
        // this message pops up confirming added friends successfully
        // This also makes both users friends; therefore, being added
        // to the network
        friendAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User.addFriend(graph, selfUser, user);
                JOptionPane.showMessageDialog(null,
                                               "You and " + user.getName()
                                               + " are now friends!");
                paintPeoplePanel("");
                paintToolBarPanel();
            }
        });
        
            
        // when users click the Say Hi button, this 'Hi' message pops up            
        sayHi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                                               "Hi " + user.getName());
            }
        });
        
        
    
        return userBox;
   }
    
    // this method puts the list of users on the right hand side of the Main Page
    // the method also keeps track of the friend relationships through the console
    public void paintPeoplePanel(String nameInput) {
        // refreshes the panel when needed
        generalDisplay.removeAll();
        generalDisplay.revalidate();
        generalDisplay.repaint();
          
          // show the users besides the selfUser on the display
          // also checks the search bar if a name is being searched or not
          System.out.println("\nTRACK FRIEND RELATIONSHIPS THROUGH CONSOLE: ");
          for (int i=1; i < users.length; i++) {
              if(nameInput.equals(users[i].getName())) {
                  User checkUser = users[i];
                  generalDisplay.add(displayUserBox(checkUser), BorderLayout.CENTER);
                  System.out.println(checkUser.getName() + "'s friends: " + checkUser.getFriends());
              }
              else if (nameInput.equals("")) {
                  User checkUser = users[i];
                  generalDisplay.add(displayUserBox(checkUser), BorderLayout.CENTER);
                  System.out.println(checkUser.getName() + "'s friends: " + checkUser.getFriends());
              } 
                            
          }
          
          System.out.println(selfUser.getName() + "'s friends: " + selfUser.getFriends());

      }
    
    public void paintToolBarPanel() {
        // refreshes the panel when needed
        toolBarPanel.removeAll();
        toolBarPanel.revalidate();
        toolBarPanel.repaint();
        
        Dimension buttonSize = new Dimension(150, 50);
        
        // sets panel to show profile info of selfUser
        JPanel profileInfo = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();  
        
        toolBarPanel.add(profileInfo);
        
        // adds title on top of profile
        c.gridx = 0; c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        JLabel title = new JLabel("Your Profile"); // 0 b/c label is displayed at the center
        title.setFont(new Font("Arial", 1, 24));
        profileInfo.add(title, c);
        
        // then adds the selfUser's image
        c.gridx = 0; c.gridy = 1;
        c.insets = new Insets(10, 10, 10, 10);
        profileInfo.add(new JLabel(selfUser.getImage()), c);
        
        // then adds the name
        c.gridx = 0; c.gridy = 2;
        c.insets = new Insets(10, 10, 10, 10);
        profileInfo.add(new JLabel("Name: " + selfUser.getName()), c);
        
        // then adds the selfUser's status
        c.gridx = 0; c.gridy = 3;
        c.insets = new Insets(10, 10, 10, 10);
        profileInfo.add(new JLabel("Status: " + selfUser.getStatus()), c);
        
        // then adds the selfUser's list of friends
        c.gridx = 0; c.gridy = 4;
        c.insets = new Insets(10, 10, 10, 10);
        profileInfo.add(new JLabel("Friends: " + selfUser.getFriends()), c);
            
        // then add Leave Network button
        c.gridx = 0; c.gridy = 5;
        c.insets = new Insets(10, 10, 10, 10);
        JButton networkLeave = new JButton("Leave Network");
        networkLeave.setPreferredSize(buttonSize);  
        toolBarPanel.add(networkLeave, c);
        
        // if selfUser presses Leave Network Button, it receives the message
        // , leaves the network, and ends the program
        networkLeave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
                JOptionPane.showMessageDialog(null, "You have left The Social Network.");
                
                // this has selfUser leave the graph network as well
                for (int i=1; i < users.length; i++) {
                    User.removeFriend(graph, selfUser, users[i]);
                }
                
                dispose();
            }
        });
        
        // then add Modify Profile button
        c.gridx = 0; c.gridy = 6;
        c.insets = new Insets(10, 10, 10, 10);
        JButton modifiedProfile = new JButton("Modify Profile");
        modifiedProfile.setPreferredSize(buttonSize);  
        toolBarPanel.add(modifiedProfile, c);
        
        // gets a copy of friends b/c once the profile of selfUser is modified, 
        // the friends of the original copy will be gone
        ArrayList<User> savedFriends = (ArrayList<User>) selfUser.getFriends().clone();

        // once selfUser clicks on Modify Profile button, the network is
        // removed but put back later on after selfUser modifies their profile
        modifiedProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
                JOptionPane.showMessageDialog(null, "Moving to Modify Profile.");          
                dispose();
                for (int i=1; i < users.length; i++) {
                    User.removeFriend(graph, selfUser, users[i]);
                }
                ProfileModify mainPage = new ProfileModify(selfUser.getName(), savedFriends);
                mainPage.pack();
                mainPage.setVisible(true);  
            }
        });
        
    }
}