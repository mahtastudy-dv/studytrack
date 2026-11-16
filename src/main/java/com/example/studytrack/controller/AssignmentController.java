
package com.example.studytrack.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.studytrack.model.Assignment;
import com.example.studytrack.repository.AssignmentRepository;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;

    // Connects the controller to the repository.
    public AssignmentController(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    // Returns all assignments from the database.
    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    // Creates and saves a new assignment.
    @PostMapping
    public Assignment createAssignment(@RequestBody Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    // Updates an existing assignment using its ID.
    @PutMapping("/{id}")
    public ResponseEntity<Assignment> updateAssignment(
            @PathVariable Long id,
            @RequestBody Assignment updatedAssignment) {

        return assignmentRepository.findById(id).map(assignment -> {
            assignment.setTitle(updatedAssignment.getTitle());
            assignment.setCourse(updatedAssignment.getCourse());
            assignment.setDescription(updatedAssignment.getDescription());
            assignment.setDeadline(updatedAssignment.getDeadline());
            assignment.setStatus(updatedAssignment.getStatus());

            Assignment savedAssignment = assignmentRepository.save(assignment);
            return ResponseEntity.ok(savedAssignment);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deletes an assignment if it exists.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {

        if (!assignmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        assignmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
