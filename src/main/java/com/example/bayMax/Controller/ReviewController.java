package com.example.bayMax.Controller;

import com.example.bayMax.Domain.Reviews;
import com.example.bayMax.Domain.Users;
import com.example.bayMax.Infrastructure.ReviewsRepository;
import com.example.bayMax.Infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    ReviewsRepository reviewsRepository;

    @Autowired
    UserRepository userRepository;

    // get all reviews
    @GetMapping("/reviews")
    public String getReviews(Model model){
        List<Reviews> reviews = reviewsRepository.findAll();
        model.addAttribute("reviews", reviews);
        return "reviews";
    }

    // add new review
    @PostMapping("/addReviews")
    public RedirectView addReview(@RequestParam Long id, @RequestParam String body){
        Users user = userRepository.findById(id).orElseThrow();
        Reviews review = new Reviews(body);
        user.addReview(review);
        return new RedirectView("/reviews");
    }

    // get add review form
    @GetMapping("/addReviews")
    public String getAddForm(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication().;

        return "reviewForm";
    }

}
