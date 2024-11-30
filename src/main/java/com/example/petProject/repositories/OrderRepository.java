package com.example.petProject.repositories;

import com.example.petProject.models.Order;
import com.example.petProject.models.projections.OrderProjection;
import jakarta.persistence.QueryHint;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("""
            select o
            from Order o
            inner join fetch o.customer
            inner join fetch o.orderList or
            inner join  fetch o.orderList.product
            where o.status IS NOT TRUE
            """)
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<OrderProjection> findAllOrder();

    @Query("""
            select o
            from Order o
            inner join fetch o.customer
            inner join fetch o.orderList
            inner join  fetch o.orderList.product
            where o.status IS NOT TRUE AND o.customer.id = :userId 
            """)
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<OrderProjection> findByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("update Order set status = true where id = :id")
    void setValue(@Param("id") UUID id);


    @Query("""
            select o
            from Order o
            inner join fetch o.customer
            inner join fetch o.orderList
            inner join  fetch o.orderList.product
            where o.id = :id 
            """)
    @QueryHints(value = {@QueryHint(name = "org.hibernate.readOnly", value = "true"), @QueryHint(name = "org.hibernate.fetchSize", value = "1")})
    List<OrderProjection> findViewById(UUID id);

}
