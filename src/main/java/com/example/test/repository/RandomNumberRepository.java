package com.example.test.repository;

import com.example.test.entity.RandomNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomNumberRepository extends JpaRepository<RandomNumber,Integer> {

}
