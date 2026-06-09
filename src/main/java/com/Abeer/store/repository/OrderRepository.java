package com.abeer.store.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.abeer.store.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>  {
    public Page<Order> findByUser_id(long id, Pageable pageable);
}
