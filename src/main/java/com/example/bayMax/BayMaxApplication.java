package com.example.bayMax;

import com.example.bayMax.Domain.Drug;
import com.example.bayMax.Domain.Roles;
import com.example.bayMax.Domain.Users;
import com.example.bayMax.Infrastructure.DrugsService;
import com.example.bayMax.Infrastructure.RolesRepository;
import com.example.bayMax.Infrastructure.UserRepository;
import com.example.bayMax.jasonToObjectModel.DrugApi;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.List;

@SpringBootApplication
public class BayMaxApplication implements CommandLineRunner {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    DrugsService drugsService;


    public static void main(String[] args) {

        SpringApplication.run(BayMaxApplication.class, args);

    }


    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findAll().isEmpty()) {
            Date date = new Date(100, 10, 1);
            Roles admin = new Roles("ADMIN");
            Roles doctor = new Roles("DOCTOR");
            Roles user = new Roles("USER");
            rolesRepository.saveAll(List.of(admin, doctor, user));

            Users newAdminUser = new Users("firstname", "lastname", date, "Amman, Jordan", "A+", 999999L, "admin", bCryptPasswordEncoder.encode("password"));
            newAdminUser.addRole(rolesRepository.findRolesByName("ADMIN"));
            userRepository.save(newAdminUser);

            Users newPatientUser = new Users("ghadeer", "farrar", new Date(2001, 10, 9), "Amman, Jordan", "B+", 123456L, "ghadeer", bCryptPasswordEncoder.encode("123"));
            newPatientUser.addRole(rolesRepository.findRolesByName("USER"));
            userRepository.save(newPatientUser);

            Users newPatientUser2 = new Users("test", "test", new Date(2001, 10, 9), "Amman, Jordan", "B+", 123456L, "test", bCryptPasswordEncoder.encode("123"));
            newPatientUser2.addRole(rolesRepository.findRolesByName("USER"));
            userRepository.save(newPatientUser2);

        }

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        // get the drugs from the API and save it in database
        int numberOfHits = 5; //number of drugs = number of hits * 100
        for (int counter = 1; counter <= (numberOfHits * 95); ) {
            String url = "https://dailymed.nlm.nih.gov/dailymed/services/v2/drugnames.json?page=" + counter;
            getDrugsFromApi(url);
            counter += 95;
        }

    }

    /**
     * function to get the drugs from the API
     *
     * @param url:
     * @throws: IOException
     */
    public void getDrugsFromApi(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");

        InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String data = bufferedReader.readLine();

        bufferedReader.close();

        Gson gson = new Gson();
        DrugApi drugApi = gson.fromJson(data, DrugApi.class);

        for (int index = 0; index < drugApi.getData().length; index++) {
            String drugName = drugApi.getData()[index].get("drug_name").toString();
            saveDrugInDB(drugName);
        }
    }

    /**
     * function to save the drug instance in the database
     *
     * @param drug instance
     */
    public void saveDrugInDB(String drug) {
        drugsService.addDrug(new Drug(drug));
    }
}
