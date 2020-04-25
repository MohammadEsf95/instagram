package entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "likerUser_post",
            joinColumns = {@JoinColumn(name = "likreUser_id")},
            inverseJoinColumns = {@JoinColumn(name = "likedPost_id")})
    private Set<Post> likedPosts = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "following_follower",
            joinColumns = {@JoinColumn(name = "following_id")},
            inverseJoinColumns = {@JoinColumn(name = "follower_id")})
    private Set<User> followers = new HashSet<>();

    @ManyToMany(mappedBy = "followers", fetch = FetchType.EAGER)
    private Set<User> followings = new HashSet<>();
}
