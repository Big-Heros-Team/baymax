package com.example.bayMax.Controller;

import com.example.bayMax.Domain.Requests;
import com.example.bayMax.Domain.Reviews;
import com.example.bayMax.Domain.Users;
import com.example.bayMax.Infrastructure.RequestsRepository;
import com.example.bayMax.Infrastructure.ReviewsRepository;
import com.example.bayMax.Infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    ReviewsRepository reviewsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RequestsRepository requestsRepository;

    // get all reviews
    @GetMapping("/reviews")
    public String getReviews(Model model){
        List<Reviews> reviews = reviewsRepository.findAll();
        model.addAttribute("reviews", reviews);
        return "reviews";
    }

    // add new review
    @PostMapping("/addReviews")
    public RedirectView addReview(@RequestParam Long id, @RequestParam String body, Principal principal){
        Users doctor = userRepository.findById(id).orElseThrow();
        Users patient = userRepository.findUsersByUsername(principal.getName());
        Reviews review = new Reviews(body);
        review.setDoctor(doctor.getFullName());
        review.setUser(patient);
        reviewsRepository.save(review);
//        userRepository.save(doctor);

        return new RedirectView("/reviews");
    }

    // get add review form
    @GetMapping("/addReviewsForm")
    public String getAddForm(Model model, Principal principal){
        Users newUser = userRepository.findUsersByUsername(principal.getName());
        List<Users> doctors = new ArrayList<>();
        for (Requests request: newUser.getPatientRequests()
        ) {
            if (request.isAccepted() && reviewsRepository.findReviewsByUserAndDoctor(newUser ,request.getDoctor().getFullName()) == null){
                doctors.add(request.getDoctor());
            }
            if (request.isAccepted() && reviewsRepository.findReviewsByUserAndDoctor(newUser ,request.getDoctor().getFullName()) != null){
                requestsRepository.delete(request);
            }
        }

            model.addAttribute("doctors",doctors);
        return "reviewForm";
    }

}
