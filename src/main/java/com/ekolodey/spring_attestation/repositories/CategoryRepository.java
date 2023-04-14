package com.ekolodey.spring_attestation.repositories;

import com.ekolodey.spring_attestation.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
