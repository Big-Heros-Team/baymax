package com.example.bayMax.Controller;

import com.example.bayMax.Domain.Roles;
import com.example.bayMax.Domain.Users;
import com.example.bayMax.Infrastructure.RolesRepository;
import com.example.bayMax.Infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String homePage(Principal principal, Model model) {
        if (principal != null) {
            Users user = userRepository.findUsersByUsername(principal.getName());
            model.addAttribute("user", user);
        }
        Roles role = rolesRepository.findRolesByName("doctor");
        List<Users> doctors = userRepository.findAllByRoles(role);
        model.addAttribute("doctors", doctors);
        return "home";
    }

    @GetMapping("/about")
    public String getaboutPage() {
        return "about";
    }

    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup";
    }

    @GetMapping("/login")
    public String getLoginPge() {

        return "login";
    }

    @GetMapping("/myprofile")
    public String getProfilePage(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Users users = userRepository.findUsersByUsername(userDetails.getUsername());
        model.addAttribute("username", userDetails.getUsername());

        model.addAttribute("userInfo", users);
        return "profile";
    }

    @GetMapping("/profile/{id}")
    public String getUserProfilePage(Model model, @PathVariable Long id) {

        Users users = userRepository.findById(id).orElseThrow();

        model.addAttribute("userInfo", users);
        return "profile";
    }

    // Add new users with user (patient) role
    @PostMapping("/signup")
    public RedirectView trySignUp(@RequestParam String firstname,
                                  @RequestParam String lastname,
                                  @RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String location,
                                  @RequestParam String bloodType,
                                  @RequestParam Date dateOfBirth,
                                  @RequestParam Long nationalId) {
        Users newUser = new Users(firstname, lastname, dateOfBirth, location, bloodType, nationalId, username, bCryptPasswordEncoder.encode(password));
        newUser.addRole(rolesRepository.findRolesByName("USER"));
        userRepository.save(newUser);

//        UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(newUser,null,new ArrayList<>());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/");
    }

//    @GetMapping("/access-denied")
//    public String getAccessDenied() {
//        return "error";
//    }


    // Add new users with doctor role
    @PostMapping("/doctors")
    public RedirectView addDoctors(@RequestParam String firstname,
                                   @RequestParam String lastname,
                                   @RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam String location,
                                   @RequestParam String bloodType,
                                   @RequestParam Date dateOfBirth,
                                   @RequestParam Long nationalId) {
        Users newUser = new Users(firstname, lastname, dateOfBirth, location, bloodType, nationalId, username, bCryptPasswordEncoder.encode(password));
        newUser.addRole(rolesRepository.findRolesByName("DOCTOR"));
        userRepository.save(newUser);
        return new RedirectView("/myprofile");
    }


    @GetMapping("/doctors")
    public String getDoctorsForm() {
        return "doctors";
    }

    // delete users
    @PostMapping("/delete")
    public RedirectView deleteUser(@RequestParam Long userId, @RequestParam String confirm) {
        if (confirm.toLowerCase(Locale.ROOT).trim().equals("delete")) {
            userRepository.deleteById(userId);
        }
        return new RedirectView("/delete");
    }

    // get delete form
    @GetMapping("/delete")
    public String deleteForm(Model model) {
        List<Users> users = userRepository.findAll();
        users.remove(0);
        model.addAttribute("users", users);
        return "delete";
    }

}