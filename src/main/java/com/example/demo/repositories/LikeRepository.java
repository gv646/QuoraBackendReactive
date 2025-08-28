package com.example.demo.repositories;

import com.example.demo.models.Like;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends ReactiveCrudRepository<Like, String> {
}
