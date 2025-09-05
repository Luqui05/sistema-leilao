package com.lucas.slbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.slbackend.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {}
