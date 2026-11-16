
package com.example.studytrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.studytrack.model.Assignment;

// Provides database operations for student assignments.
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

}
