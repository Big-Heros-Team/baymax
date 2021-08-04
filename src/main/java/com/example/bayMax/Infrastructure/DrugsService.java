package com.example.bayMax.Infrastructure;

import com.example.bayMax.Domain.Drug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugsService {
    @Autowired
    DrugsRepository drugsRepository;

    public void addDrug(Drug drug) {
        drugsRepository.save(drug);
    }

    public Drug getDrug(Long id) {
        return drugsRepository.findByDrugId(id);
    }

    public List<Drug> gerAllDrugs() {
        return drugsRepository.findAll();
    }

}
