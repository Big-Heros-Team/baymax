package com.example.bayMax.Controller;

import com.example.bayMax.Domain.*;
import com.example.bayMax.Infrastructure.*;
import org.apache.catalina.User;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class recordController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RecodRepository recodRepository;

    @Autowired
    RequestsRepository requestsRepository;

    @Autowired
    DrugsService drugsService;

    @Autowired
    UserService userService;

    @GetMapping("/records/{id}")
    String getRecords(@PathVariable("id") Long id, Model m) {
        Users patient = userRepository.findById(id).get();
        patient = userRepository.save(patient);
        m.addAttribute("user", patient);

        List<Drug> drugs = drugsService.gerAllDrugs();

        //TODO: get the user id and add it to the model instead static value
        m.addAttribute("drugs", drugs);

        return ("record");

    }


    @PostMapping("/addrecord/{id}")
    RedirectView postRecord(@RequestParam(value = "diagnostic") String diagnostic, @PathVariable("id") Long id) {
        Users patient = userRepository.findById(id).get();

        Record newRecord = new Record(diagnostic, patient);
        newRecord = recodRepository.save(newRecord);

        return new RedirectView("/records/"+id);

    }

    @PostMapping("/addDrug")
    public RedirectView addDrug(@RequestParam Long userId, @RequestParam Long drugId) {
        if (drugId != 0) {
            userService.assignDrugToUser(userId, drugId);
        }

        //TODO: redirect to drugs form
        return new RedirectView("/records/"+userId);
    }


    @GetMapping("/patients")
    public String getPatients(Model m, Principal p){

//        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users currentUser= userRepository.findUsersByUsername(p.getName());
        List<Requests> request = requestsRepository.findAllByDoctorAndIsAccepted(currentUser,true);
        List<Users> patients= new ArrayList<>();


        request.forEach((element)->{patients.add(element.getPatient());});
        m.addAttribute("patients",patients);

        return "patients.html";
    }



}
