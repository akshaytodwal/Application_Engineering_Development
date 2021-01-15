/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab7.analytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lab7.entities.Comment;
import lab7.entities.Post;
import lab7.entities.User;

/**
 *
 * @author harshalneelkamal
 */
public class AnalysisHelper {
    // find user with Most Likes
    // TODO
    
    //1 - average number of likes per comment
    public void avgLikesPerComment() {
         Map<Integer, Comment> comments = DataStore.getInstance().getComments();
        int likeNumber = 0;
        int commentNumber = comments.size();
        for (Comment c : comments.values()) {
            likeNumber += c.getLikes();
        }
        System.out.println("*******");
        System.out.println("\n1. Average likes per comment: " + likeNumber / commentNumber);
        System.out.println(" ");
    }
   
    
    //2  - post with most likes comments     
            public void postWithMostLikes() {

        Map<Integer, Integer> likesList = new HashMap<Integer, Integer>();
        Map<Integer, Post> posts11 =  DataStore.getInstance().getPosts() ;
        int finalLike = 0;

        System.out.println("*******");
        System.out.println("\n2. Post with most Like Commnets:");

        for (Post post : DataStore.getInstance().getPosts().values()) {
            for (Comment comm : post.getComments()) {

                if (comm.getLikes() > finalLike) {

                    finalLike = comm.getLikes();}
            }

            likesList.put(post.getPostId(), finalLike);
            finalLike = 0;
        }
        
         Map<Integer, Integer> result1 = likesList.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        
         
         int count  = 0 ; 
    for (Map.Entry<Integer, Integer> entry : result1.entrySet()) {
           if (count >= 1) {
               return;
           }           
           
           //System.out.println("2. Post with most like comments:");
           System.out.println("Post Id = " + entry.getKey() );
           System.out.println("Total number of Likes " + entry.getValue());
           System.out.println("Posting User Id = " + posts11.get(entry.getKey()).getUserId() );
          // System.out.println("Comment are " + posts11.get(entry.getKey()).getComments() );
          System.out.println(" ");
           count++;
       }
    }
            
    //3 - post With Most Comments
    public void postWithMostComments() {
        
     Map<Integer , Integer> noofcomments = new HashMap<Integer , Integer>();
 
     System.out.println("*******");
     System.out.println("\n3. Post with Most Number of Comments:");
     
     for (Post post : DataStore.getInstance().getPosts().values()) {
         noofcomments.put(post.getPostId(), post.getComments().size());
     }
     
     int keyPost = Collections.max(noofcomments.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
     int commentPost = noofcomments.get(keyPost);
     
     for (Map.Entry<Integer , Integer> entry : noofcomments.entrySet()) {
         
         if (entry.getValue() == commentPost ) {
         //System.out.println("3. Posts with Most Number of Comments: ");    
         System.out.println("Post ID :  "+ entry.getKey() + " , Total number of comments : " + entry.getValue());
         //System.out.println(" ");
         }
     
     
     }
     
     }
    
    //4 - top 5 inactive users with total post
        public void topInactiveWithTotalPost() {
        
        System.out.println(" ");
        System.out.println("*******");    
        System.out.println("\n4. Top 5 Inactive Users with total number of posts:");
        // System.out.println("\n4 :");
    
    Map<Integer, Post> posts =  DataStore.getInstance().getPosts() ;
    Map<Integer, User> user =  DataStore.getInstance().getUsers() ;
     Map<Integer , Integer> noOfPosts = new HashMap<>();
    
    for (Post post : posts.values()) {
        
        if (noOfPosts.containsKey(post.getUserId())) {
        
        noOfPosts.replace(post.getUserId(), noOfPosts.get(post.getUserId()) + 1) ;
        }
        else {
             noOfPosts.put(post.getUserId(),1);
        }
    }          
          Map<Integer, Integer> result1 = noOfPosts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
          
   int count  = 0 ; 
    for (Map.Entry<Integer, Integer> entry : result1.entrySet()) {
           if (count >= 5) {
               return;
           }
           
           System.out.println("User Id = " + entry.getKey() + " , Total Post = " + entry.getValue());
           count++;
       }
        System.out.println(" ");
     
    }
        
    //5 - top 5 inactive Users With Least Comment
    public void inactiveUsersWithLeastComment() {
        Map<Integer, Integer> userCommentcount = new HashMap<Integer, Integer>();
        Map<Integer, User> userMap = DataStore.getInstance().getUsers();

        List<User> userList = new ArrayList(userMap.values());
        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                //so as to get descending list
                return u1.getComments().size() - u2.getComments().size();
            }
        });
        System.out.println(" ");
        System.out.println("*******");
        System.out.println(" ");
        System.out.println("5. The 5 most inactive Users based on comments:");
        for (int i = 0; i < userList.size() && i < 5; i++) {
            System.out.println("User ID:" + userList.get(i).getId());
        }
    }

    //6 - top 5 Inactive Users Overall (sum of comments, post and likes)
    public void top5InactiveUsersOverall() {
        Map<Integer, User> userMap = DataStore.getInstance().getUsers();
        Map<Integer, Post> posts = DataStore.getInstance().getPosts();
        Map<Integer, Integer> newUserMap = new HashMap<>();
        for (User user : userMap.values()) {
            newUserMap.put(user.getId(), user.getComments().size());
            for (Comment comment : user.getComments()) {
                newUserMap.put(user.getId(), newUserMap.get(user.getId()) + comment.getLikes());
            }

            for (Post post : posts.values()) {
                newUserMap.put(post.getUserId(), (post.getUserId()) + 1);
            }
            List<Map.Entry<Integer, Integer>> list = new LinkedList<>(newUserMap.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
                public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                    return (o1.getValue()).compareTo(o2.getValue());
                }
            });
            HashMap<Integer, Integer> temp = new LinkedHashMap<>();
            for (Map.Entry<Integer, Integer> aa : list) {
                temp.put(aa.getKey(), aa.getValue());
            }
            System.out.println(" ");
            System.out.println("*******");
            System.out.println("\n6. Top 5 inactive users on the basis of posts, likes and comments:");
            int count = 0;
            for (Map.Entry<Integer, Integer> entry : temp.entrySet()) {
                if (count >= 5) {
                    return;
                }
                System.out.println("User Id = " + entry.getKey() + " , Total contribution = " + entry.getValue());
                count++;
            }
        }
    }

    //7 - top 5 Proactive Users Overall (sum of comments, post and likes)
    public void top5ProactiveUsersOverall() {
        Map<Integer, User> userMap = DataStore.getInstance().getUsers();
        Map<Integer, Post> posts = DataStore.getInstance().getPosts();
        Map<Integer, Integer> newUserMap = new HashMap<>();
        for (User user : userMap.values()) {
            newUserMap.put(user.getId(), user.getComments().size());
            for (Comment comment : user.getComments()) {
                newUserMap.put(user.getId(), newUserMap.get(user.getId()) + comment.getLikes());
            }
        }
        for (Post post : posts.values()) {
            newUserMap.put(post.getUserId(), (post.getUserId()) + 1);
        }
        List<Map.Entry<Integer, Integer>> list = new LinkedList<>(newUserMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        HashMap<Integer, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        System.out.println(" ");
        System.out.println("*******");
        System.out.println("\n7. Top 5 proactive users by posts, comments and likes:");
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : temp.entrySet()) {
            if (count >= 5) {
                return;
            }
            System.out.println("User Id = " + entry.getKey() + " , Total contribution = " + entry.getValue());
            count++;
        }
    }
     
    

}
