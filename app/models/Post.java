package models;

import java.util.Map;
import java.util.TreeMap;

public class Post {

    private static Map<Long, Post> posts = new TreeMap<>();

    static {
        posts.put(1L, new Post(1L, "Test Blog", "Test Blog Text"));
    }

    public Long id;
    public String title;
    public String text;

    public Post() {
    }

    public Post(Long id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public static Post get(Long id) {
        return posts.get(id);
    }

    public String slug() {
        return this.title.toLowerCase().replace(' ', '-') + "-";
    }

    public static Post getBySlug(String slug) {
        for (Post post : posts.values()) {
            if (post.slug().equals(slug)) {
                return post;
            }
        }
        return null;
    }
}
