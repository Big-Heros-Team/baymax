package com.example.bayMax.Controller;

import com.example.bayMax.Domain.Blog;
import com.example.bayMax.Domain.Users;
import com.example.bayMax.Infrastructure.BlogsRepository;
import com.example.bayMax.Infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class BlogsController {
    @Autowired
    BlogsRepository blogsRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/blogs")
    public String addBlog() {
        return "blogs";
    }

    @PostMapping("/blogs")
    public String blogForm(@RequestParam String body) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users newUser = userRepository.findUsersByUsername(user.getUsername());
        Blog newBlog = new Blog(body);
        newBlog.setUser(newUser);
        blogsRepository.save(newBlog);
        return "allBlogs";
    }

    @GetMapping("/allBlogs")
    public String getAllBlog(Model model) {
        List<Blog> blogs = blogsRepository.findAll();
        model.addAttribute("blogs", blogs);
        return "allBlogs";
    }
}
