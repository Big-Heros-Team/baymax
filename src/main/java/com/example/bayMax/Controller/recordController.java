package com.example.bayMax.Controller;

import com.example.bayMax.Domain.Record;
import com.example.bayMax.Domain.Users;
import com.example.bayMax.Infrastructure.RecodRepository;
import com.example.bayMax.Infrastructure.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class recordController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RecodRepository recodRepository;

    @GetMapping("/records/{id}")
    String getRecords(@PathVariable("id") Long id, Model m) {
        Users patient = userRepository.findById(id).get();
        patient = userRepository.save(patient);
        m.addAttribute("user", patient);
        Record addRecord = new Record("asdas", "sdfsdf", patient);

        return ("record");
    }


    @PostMapping("/addrecord/{id}")
    RedirectView postRecord(@RequestParam(value = "drugs") String drugs, @RequestParam(value = "diagnostic") String diagnostic, @PathVariable("id") Long id) {
        Users patient = userRepository.findById(id).get();

        Record newRecord = new Record(drugs, diagnostic, patient);
        newRecord = recodRepository.save(newRecord);

        return new RedirectView("/");

    }


}
