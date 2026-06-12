package com.spring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.District;
import com.spring.entity.Listing;
import com.spring.entity.ListingCategory;
import com.spring.entity.SaleStatus;
import com.spring.repository.ListingRepository;

@Service
@Transactional(readOnly = true)
public class ListingService {

	private final ListingRepository listingRepository;

	public ListingService(ListingRepository listingRepository) {
		this.listingRepository = listingRepository;
	}

	// TODO 4-1
	public Page<Listing> search(String keyword, ListingCategory category, District district,
			SaleStatus saleStatus, Integer minPrice, Integer maxPrice, Pageable pageable) {
		return Page.empty();
	}

	// TODO 4-2
	public Listing findById(Long id) {
		throw new UnsupportedOperationException("TODO 4-2");
	}

	@Transactional
	public Listing save(Listing listing) {
		return listingRepository.save(listing);
	}

	@Transactional
	public void update(Long id, Listing form) {
		// TODO 4-3
	}

	@Transactional
	public void delete(Long id) {
		// TODO 4-4
	}
}
