package com.spring.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.entity.District;
import com.spring.entity.Listing;
import com.spring.entity.ListingCategory;
import com.spring.entity.SaleStatus;
import com.spring.service.ListingService;

@Controller
@RequestMapping("/listings")
public class ListingController {

	private final ListingService listingService;

	public ListingController(ListingService listingService) {
		this.listingService = listingService;
	}

	/** TODO 5: 피드 목록 + 필터 */
	@GetMapping
	public String list(
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "category", required = false) ListingCategory category,
			@RequestParam(value = "district", required = false) District district,
			@RequestParam(value = "saleStatus", required = false) SaleStatus saleStatus,
			@RequestParam(value = "minPrice", required = false) Integer minPrice,
			@RequestParam(value = "maxPrice", required = false) Integer maxPrice,
			@PageableDefault(size = 12) Pageable pageable,
			Model model) {
		return "listing/list";
	}

	/** TODO 7 */
	@GetMapping("/new")
	public String createForm(Model model) {
		return "listing/form";
	}

	/** TODO 8 */
	@PostMapping
	public String create(Listing listing, RedirectAttributes ra) {
		return "redirect:/listings";
	}

	/** TODO 6 */
	@GetMapping("/{id}")
	public String detail(@PathVariable("id") Long id, Model model) {
		return "listing/detail";
	}

	/** TODO 9-1 */
	@PostMapping("/{id}")
	public String update(@PathVariable("id") Long id, Listing listing, RedirectAttributes ra) {
		return "redirect:/listings";
	}

	/** TODO 9-2 */
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable("id") Long id, RedirectAttributes ra) {
		return "redirect:/listings";
	}

	/** TODO 9-3 */
	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable("id") Long id, Model model) {
		return "listing/form";
	}
}
