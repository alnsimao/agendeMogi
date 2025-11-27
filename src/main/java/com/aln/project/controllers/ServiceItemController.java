package com.aln.project.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aln.project.model.ServiceItem;
import com.aln.project.service.ServiceItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/service-items")
@RequiredArgsConstructor
public class ServiceItemController {

    private final ServiceItemService service;

    @PostMapping
    public ResponseEntity<ServiceItem> create(@RequestBody ServiceItem item) {
        return ResponseEntity.ok(service.create(item));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ServiceItem>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceItem> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceItem> update(@PathVariable Long id, @RequestBody ServiceItem item) {
        return ResponseEntity.ok(service.update(id, item));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ServiceItem> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancel(id));
    }
}
