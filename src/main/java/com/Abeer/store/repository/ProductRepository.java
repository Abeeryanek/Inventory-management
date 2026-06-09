package com.abeer.store.repository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.abeer.store.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    public boolean existsByProductName(String Name);
}