package entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Post implements Serializable,Comparable<Post> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String title;

    @Column
    private String tag;

    @Column
    private int likes;

    @ManyToOne
    private User user;

    @ManyToMany(mappedBy = "likedPosts")
    private Set<User> likerUsers = new HashSet<>();

    @Override
    public int compareTo(Post otherPost) {
        return otherPost.getLikes() - likes;
    }
}
