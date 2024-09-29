package com.jewelbankers.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jewelbankers.entity.Pledge;
import com.jewelbankers.services.PledgeService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/pledge")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class PledgeController {

    @Autowired
    private PledgeService pledgeService;

    @GetMapping("/all")
    public List<Pledge> getAllPledges() {
        return pledgeService.getAllPledges();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pledge> getPledgeById(@PathVariable Long id) {
        Optional<Pledge> pledge = pledgeService.getPledgeById(id);
        return pledge.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public List<Pledge> getPledgesByCustomerId(@PathVariable Long customerId) {
        return pledgeService.getPledgesByCustomerId(customerId);
    }

    @PostMapping("/add")
    public Pledge createPledge(@RequestBody Pledge pledge) {
        return pledgeService.savePledge(pledge);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pledge> updatePledge(@PathVariable Long id, @RequestBody Pledge pledgeDetails) {
        Pledge updatedPledge = pledgeService.updatePledge(id, pledgeDetails);
        return ResponseEntity.ok(updatedPledge);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePledge(@PathVariable Long id) {
        pledgeService.deletePledge(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/generate/{pledgeId}")
//    public ResponseEntity<String> generateAndSendBill(@PathVariable Long pledgeId) {
//        try {
//            this.pledgeService.generateAndSendBill(pledgeId);
//            return ResponseEntity.ok("Bill sent successfully and saved locally");
//        }   catch (Exception e) {
//            return ResponseEntity.status(500).body(e.getMessage());
//        }
//    }
}