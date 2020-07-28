package com.ado.mysql.test.testDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ado.mysql.test.testDB.model.ShoppingList;

@Repository
public interface ShopItemRepository extends JpaRepository<ShoppingList, Integer> {

}
