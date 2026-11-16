package com.lms.learningmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.learningmanagementsystem.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}