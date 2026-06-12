package com.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.entity.District;
import com.spring.entity.Listing;
import com.spring.entity.ListingCategory;
import com.spring.entity.SaleStatus;

public interface ListingRepository extends JpaRepository<Listing, Long> {

	/**
	 * ━━ TODO 3 ━━
	 * keyword, category, district, saleStatus, minPrice, maxPrice
	 * null이면 해당 조건 무시 · Pageable
	 */
	@Query("SELECT l FROM Listing l WHERE 1=1")
	Page<Listing> search(
			@Param("keyword") String keyword,
			@Param("category") ListingCategory category,
			@Param("district") District district,
			@Param("saleStatus") SaleStatus saleStatus,
			@Param("minPrice") Integer minPrice,
			@Param("maxPrice") Integer maxPrice,
			Pageable pageable);
}
