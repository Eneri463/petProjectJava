package com.example.petProject.repositories;

import com.example.petProject.models.Order;
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
            SELECT o
            FROM Order o
            LEFT JOIN FETCH o.customer
            LEFT JOIN FETCH o.orderList
            LEFT JOIN FETCH o.orderList.product
            WHERE o.id IN (
                          SELECT ord.id
                          FROM (
                                  SELECT
                                  ord1.id as id,
                                  row_number() OVER(
                                    ORDER BY ord1.id
                                  ) as ranking
                                  FROM Order ord1
                                  WHERE ord1.status != TRUE
                              ) ord
                          WHERE ord.ranking > :offset 
                          ORDER BY id
                          FETCH FIRST :limit ROWS ONLY
                          )
            """)
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<Order> findAllOrderIn(@Param("offset") int offset, @Param("limit") int limit);

    @Query("""
            
            SELECT o
            FROM Order o
            LEFT JOIN FETCH o.customer
            LEFT JOIN FETCH o.orderList
            LEFT JOIN FETCH o.orderList.product
            WHERE o.customer.id = :userId AND o.date IN
                                                      (
                                                        SELECT ord1.date
                                                        FROM (
                                                                SELECT ord.date date,
                                                                row_number() OVER(
                                                                                ORDER BY ord.date
                                                                                 ) as ranking
                                                                FROM Order ord
                                                                WHERE ord.status != TRUE AND ord.customer.id = :userId
                                                              ) ord1
                                                        WHERE ord1.ranking > :offset 
                                                        ORDER BY ord1.date
                                                        FETCH FIRST :limit ROWS ONLY
                                                      )
            """)
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<Order> findByUserId(@Param("userId") Long userId, @Param("offset") int offset, @Param("limit") int limit);


    @Transactional
    @Modifying
    @Query("UPDATE Order SET status = true WHERE id = :id")
    void setValue(@Param("id") UUID id);

    @Query("""
            SELECT o
            FROM Order o
            LEFT JOIN FETCH o.customer
            LEFT JOIN FETCH o.orderList
            LEFT JOIN FETCH o.orderList.product
            WHERE o.id = :id
            """)
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<Order> getByIdNotProxy(@Param("id") UUID id);
}
