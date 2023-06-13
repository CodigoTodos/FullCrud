package com.brunoalves.crud;

import com.brunoalves.crud.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestH2Repository extends JpaRepository<Product,Long> {
}
