
/* This class is to create a user account and a network of users
 * */

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ImageIcon;


class User {
    private int id; // user id
    private String name; // stores the user name
    private ImageIcon image; // stores the user image
    private boolean status; // store the user status
    private ArrayList<User> friends; //list of the user's friends
    
    // get the user friend list
    public ArrayList<User> getFriends() {
        return friends;
    }
    
    // get the user ID 
    public int getId() {
        return id;
    }
    
    // get the user name
    public String getName() {
        return name;
    }

    //get the user image
    public ImageIcon getImage() {
        return image;
    }

    // get the user status
    public String getStatus() {
        if (status == false) 
            return "Offline";
        else
            return "Online";
    }

    // set the user ID
    public void setId(int id) {
        this.id = id;
    }

    // set the user name
    public void setName(String name) {
        this.name = name;
    }

    // set the user image
    public void setImage(ImageIcon image) {
        this.image = image;
    }

    // set the user status  
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    // a user account
    User(String name, ImageIcon image, int id, boolean status) {
        this.name = name;
        this.image = image;
        this.id = id;
        this.status = status; 
        friends = new ArrayList<>();
    }
    
    // class to make network
    static class Network
    { 
        // list of users in the network using adjacency list
        LinkedList<User> users[]; 
        
        Network() {
            
            users = new LinkedList[SocialNetworkStock.NETWORK_USERS]; 
            
            for(int i = 0; i < SocialNetworkStock.NETWORK_USERS; i++){ 
                users[i] = new LinkedList<>(); 
            } 
        }
        
    } 
    
    // Adds an edge to an undirected graph when a user adds a friend
    static void addFriend(Network graph, User src, User dest) 
    { 
        // Add an edge from src to dest.  
        graph.users[src.id].add(dest);
        src.friends.add(dest);
          
        // Since graph is undirected, add an edge from dest
        // to src also 
        graph.users[dest.id].add(src); 
        dest.friends.add(src);
    } 
 
     // removes an edge from an undirected graph if needed (especially when
     // the selfUser's profile is modified. We have to remove the friends
     // from the selfUser and put it back later after profile is modified.
     static void removeFriend(Network graph, User src, User dest) 
     { 
         // removes an edge from src to dest.  
         graph.users[src.id].remove(dest);
         src.friends.remove(dest);
           
         // Since graph is undirected, remove an edge from dest 
         // to src also 
         graph.users[dest.id].remove(src); 
         dest.friends.remove(src);
     } 
    
     // print the graph if needed to test the network in the console
     static void printGraph(Network graph, User[] users) 
     {        
         for(int v = 0; v < SocialNetworkStock.NETWORK_USERS; v++) 
         { 
             System.out.println("Adjacency list of vertex "+ v); 
             System.out.println("Friends of: + " + users[v]); 
             for(User pCrawl: graph.users[v]){ 
                 System.out.print(" -> "+pCrawl); 
                 System.out.print("\n"); 
             }      
         } 
     }
    
    // this main method was used to test the network and to track the relationships
    // in the console, but it is not
    // part of the GUI
    public static void main(String args[]) 
    { 
            
        Network graph = new Network(); // create a network
        
        // setting user name, icon, id, and status
        User user1 = new User("My", new ImageIcon(), 0, true); 
        User user2 = new User("Nelson", new ImageIcon(), 1, false);
        User user3 = new User("Emily", new ImageIcon(), 2, false);
        User user4 = new User("Dashion", new ImageIcon(), 3, false);
        User user5 = new User("Ralp", new ImageIcon(), 4, false);
        
        
        //adding the users to the network
        User[] users = new User[SocialNetworkStock.NETWORK_USERS];
        users[0] = user1;
        users[1] = user2;
        users[2] = user3;
        users[3] = user4;
        users[4] = user5;
        
        
        addFriend(graph, user2, user3); 
        addFriend(graph, user2, user4); 
        addFriend(graph, user2, user5); 
        addFriend(graph, user3, user4); 
        addFriend(graph, user4, user5); 
        
        printGraph(graph, users);
        
    } 
    
    // checks to see if  the user object and another user is are friends or not
    public boolean isFriends(User user2) {
        return friends.contains(user2);
    }
    
    // returns the name of the user
    public String toString() {
        return name;
    }
    
    // need this method to clone an object and return the object especially when a user is modified
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    
    
}