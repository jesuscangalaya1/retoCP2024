package com.test.cp.repository;

import com.test.cp.entity.OrderEntity;
import com.test.cp.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OrderRepository extends  JpaRepository<OrderEntity, Long>{

    Page<OrderEntity> findAllByDeletedFalse(Pageable pageable);


    Optional<OrderEntity> findByOrderIdAndDeletedFalse(Long id);

    // Consulta para desactivar un producto por su ID
    @Modifying
    @Transactional
    @Query("UPDATE OrderEntity p SET p.deleted = true WHERE p.orderId = :orderId")
    void desactivarOrder(@Param("orderId") Long orderId);
}
