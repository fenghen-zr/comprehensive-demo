package com.priv.dto;

import com.priv.entity.Blog;
import com.priv.entity.User;


/**
 * @author fenghen
 */
public class BlogDetailDTO {
    private Blog blog;
    private User user;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "BlogDetailDTO{" +
                "blog=" + blog +
                ", user=" + user +
                '}';
    }
}
