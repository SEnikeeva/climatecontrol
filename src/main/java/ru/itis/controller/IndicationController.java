package ru.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.model.Indication;
import ru.itis.repository.IndicationRepository;

import java.sql.Date;
import java.time.LocalDateTime;


@RestController
public class IndicationController {

    @Autowired
    IndicationRepository repository;
    @PostMapping("/inds")
    public @ResponseBody Indication indcations(@RequestBody Indication indication) {
        indication.setDate(new Date(new java.util.Date().getTime()));
        repository.save(indication);
        return indication;
    }
}
