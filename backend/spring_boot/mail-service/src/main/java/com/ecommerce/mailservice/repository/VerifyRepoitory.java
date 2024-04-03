package com.ecommerce.mailservice.repository;

import com.ecommerce.mailservice.model.Verify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifyRepoitory extends JpaRepository<Verify,String> {
}
