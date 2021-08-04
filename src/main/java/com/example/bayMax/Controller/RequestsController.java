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

import java.security.Principal;
import java.util.List;
import java.util.Objects;
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
        int[] times = {9,10,11,12,13,14,15,16};
        model.addAttribute("times", times);
        model.addAttribute("doctors", doctors);
        model.addAttribute("patient", patient);
        return "appointment";
    }

    @PostMapping("/appointment")
    public RedirectView selectAppointment(long id, long doctorId,Principal principal, String date, String time) {
        boolean isExist = false;
        Users patient = userRepository.findUsersByUsername(principal.getName());
        System.out.println("Doctor name = " + doctorId);
        Users doctor = userRepository.findById(doctorId).orElseThrow();

        for (Requests existingRequest : doctor.getDoctorRequests()) {
            if (existingRequest.getPatient().getId() == id) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            Requests request = new Requests();
            String appointDate = date + " at " + time+":00";
            request.setCreatedAt(appointDate);
            request.setDoctor(doctor);
            request.setPatient(patient);
            request.setAccepted(false);
            requestsRepository.save(request);

        } else {
            System.out.println("Request is found");
            for (Requests request: patient.getPatientRequests()
                 ) {
                System.out.println(request);
            }
//            System.out.println(doctor.getDoctorRequests().toArray().toString());
//            System.out.println(.getPatientRequests().toArray().toString());
        }
        return new RedirectView("/appointment");
    }

    @GetMapping("/requests")
    public String getRequests(Principal principal, Model model) {
        Users doctor = userRepository.findUsersByUsername(principal.getName());
        Set<Requests> requests = doctor.getDoctorRequests();
        model.addAttribute("requests", requests);
        for (Requests request:doctor.getDoctorRequests()
             ) {
            System.out.println(request.getDoctor().getFullName());
        }

        return "requests";
    }

    @PostMapping("/requests")
    public RedirectView acceptRequests(Principal principal, long id, String accepted) {
        Users doctor=userRepository.findUsersByUsername(principal.getName());
        Users patient=userRepository.findById(id).orElseThrow();
        Requests request=new Requests();
        for (Requests chosenRequest:doctor.getDoctorRequests()
             ) {
            if(chosenRequest.getPatient()==patient){
                request = chosenRequest;
                break;
            }
        }
        if (Objects.equals(accepted, "Accept")) {
            request.setAccepted(true);
            requestsRepository.save(request);
        } else {
            requestsRepository.delete(request);
        }
        return new RedirectView("/requests");
    }
}
