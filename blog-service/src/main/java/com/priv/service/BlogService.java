package com.priv.service;


import com.priv.client.UserServiceClient;
import com.priv.dao.BlogDao;
import com.priv.dto.BlogDetailDTO;
import com.priv.dto.RespDTO;
import com.priv.entity.Blog;
import com.priv.entity.User;
import com.priv.exception.CommonException;
import com.priv.exception.ErrorCode;
import com.priv.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * @author fenghen
 */

@Service
public class BlogService {

    @Autowired
    BlogDao blogDao;

    @Autowired
    UserServiceClient userServiceClient;

    public Blog postBlog(Blog blog) {
        return blogDao.save(blog);
    }

    public List<Blog> findBlogs(String username) {
        return blogDao.findByUsername(username);
    }


    public BlogDetailDTO findBlogDetail(Long id) {
        Optional<Blog> blogOptional = blogDao.findById(id);
        Blog blog=null;
        if(blogOptional!=null){
            blog=blogOptional.get();
        }
        if (null == blog) {
            throw new CommonException(ErrorCode.BLOG_IS_NOT_EXIST);
        }
        RespDTO<User> respDTO = userServiceClient.getUser(UserUtils.getCurrentToken(), blog.getUsername());
        if (respDTO==null) {
            throw new CommonException(ErrorCode.RPC_ERROR);
        }
        BlogDetailDTO blogDetailDTO = new BlogDetailDTO();
        blogDetailDTO.setBlog(blog);
        blogDetailDTO.setUser(respDTO.data);
        return blogDetailDTO;
    }

}
