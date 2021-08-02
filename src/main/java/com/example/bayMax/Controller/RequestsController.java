package com.example.bayMax.Controller;

import com.example.bayMax.Domain.Requests;
import com.example.bayMax.Domain.Roles;
import com.example.bayMax.Domain.Users;
import com.example.bayMax.Infrastructure.RequestsRepository;
import com.example.bayMax.Infrastructure.RolesRepository;
import com.example.bayMax.Infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
public class RequestsController {

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RequestsRepository requestsRepository;


    @GetMapping("/appointment")
    public String appointmentForm(Model model, Principal principal) {
        Users patient = userRepository.findUsersByUsername(principal.getName());
        Roles roles = rolesRepository.findRolesByName("DOCTOR");
        List<Users> doctors = userRepository.findAllByRoles(roles);

        model.addAttribute("doctors", doctors);
        model.addAttribute("patient", patient);
        return "appointment";
    }

    @PostMapping("/appointment")
    public RedirectView selectAppointment(long id, long doctorId) {
        boolean isExist = false;
        Users users = userRepository.findById(id).orElseThrow();
        System.out.println("Doctor name = " + doctorId);
        Users doctor = userRepository.findById(doctorId).orElseThrow();
        Requests request = new Requests(users, doctor, false);
        for (Requests existingRequest : doctor.getRequests2()) {
            if (existingRequest.getPatient().getId() == id) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            requestsRepository.save(request);
            doctor.getRequests2().add(request);
            userRepository.save(doctor);
        } else {
            System.out.println("Request is found");
        }
        return new RedirectView("/appointment");
    }

    @GetMapping("/requests")
    public String getRequests(Principal principal, Model model) {
        Users doctor = userRepository.findUsersByUsername(principal.getName());
        Set<Requests> requests = doctor.getRequests2();
        model.addAttribute("requests", requests);
        for (Requests request:doctor.getRequests2()
             ) {
            System.out.println(request.getDoctor().getFullName());
        }

        return "requests";
    }

    @PostMapping("/requests")
    public RedirectView acceptRequests(Principal principal, long id, boolean accepted, long requestId) {
        Users patient = userRepository.findById(id).orElseThrow();
        Users doctor = userRepository.findUsersByUsername(principal.getName());
        Requests request = requestsRepository.findById(requestId).orElseThrow();
        if (accepted) {
            request.setAccepted(true);
            requestsRepository.save(request);
            patient.getRequests().add(request);
        } else {
            requestsRepository.delete(request);
            doctor.getRequests2().remove(request);
        }
        return new RedirectView("/requests");
    }
}
