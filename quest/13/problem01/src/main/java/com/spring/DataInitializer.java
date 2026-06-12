package com.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.Listing;
import com.spring.init.SampleListingGenerator;
import com.spring.repository.ListingRepository;

/**
 * 샘플 판매글 대량 생성 — TODO 1~2 완성 후 피드·페이징 연습용
 */
@Component
public class DataInitializer implements CommandLineRunner {

	private static final int BATCH_SIZE = 100;

	private final ListingRepository listingRepository;

	@Value("${app.sample-data.count:500}")
	private int sampleCount;

	public DataInitializer(ListingRepository listingRepository) {
		this.listingRepository = listingRepository;
	}

	@Override
	@Transactional
	public void run(String... args) {
		if (listingRepository.count() > 0) {
			return;
		}

		List<Listing> samples = SampleListingGenerator.generate(sampleCount);
		for (int i = 0; i < samples.size(); i += BATCH_SIZE) {
			int end = Math.min(i + BATCH_SIZE, samples.size());
			listingRepository.saveAll(samples.subList(i, end));
		}
	}
}
