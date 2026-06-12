package com.spring.init;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.spring.entity.District;
import com.spring.entity.Listing;
import com.spring.entity.ListingCategory;
import com.spring.entity.SaleStatus;

/**
 * 학습용 샘플 판매글 500건 생성 (사진 대신 이모지 썸네일)
 */
public final class SampleListingGenerator {

	public static final int DEFAULT_COUNT = 500;

	private static final String[] NICKNAMES = {
			"민수", "서연", "지훈", "하늘", "동네언니", "키보드덕", "강남토끼", "마포고양이",
			"성수라떼", "종로곰", "중고왕", "거래왕", "깔끔이", "에눌이", "직거래만", "택배가능",
			"새벽배송", "주말만", "급처분", "네고환영", "쿨거래", "찜많음", "리뷰좋음", "첫거래"
	};

	private static final String[] CONDITIONS = {
			"상태 좋아요", "거의 새것", "1~2회 사용", "사용감 적음", "기스 거의 없음",
			"정품 박스 포함", "구성품 전부 있음", "직거래 선호", "네고 가능", "급처라 저렴해요"
	};

	private record CatalogItem(String emoji, String name, int minPrice, int maxPrice) {}

	private static final CatalogItem[] DIGITAL = {
			new CatalogItem("📱", "아이폰 14", 550000, 950000),
			new CatalogItem("📱", "아이폰 13", 400000, 700000),
			new CatalogItem("📱", "갤럭시 S23", 350000, 650000),
			new CatalogItem("📱", "갤럭시 A시리즈", 80000, 250000),
			new CatalogItem("💻", "맥북 에어 M1", 700000, 1100000),
			new CatalogItem("💻", "맥북 프로 13", 900000, 1500000),
			new CatalogItem("💻", "LG 그램 15", 500000, 900000),
			new CatalogItem("💻", "삼성 노트북", 300000, 700000),
			new CatalogItem("📟", "아이패드 에어", 350000, 650000),
			new CatalogItem("📟", "아이패드 미니", 250000, 450000),
			new CatalogItem("⌨️", "기계식 키보드", 30000, 180000),
			new CatalogItem("🖱️", "게이밍 마우스", 15000, 120000),
			new CatalogItem("🖥️", "27인치 모니터", 80000, 350000),
			new CatalogItem("🎧", "에어팟 프로", 80000, 220000),
			new CatalogItem("🎧", "갤럭시 버즈", 40000, 150000),
			new CatalogItem("📷", "미러리스 카메라", 300000, 1200000),
			new CatalogItem("🎮", "닌텐도 스위치", 180000, 320000),
			new CatalogItem("🎮", "PS5", 350000, 550000),
			new CatalogItem("🔋", "보조배터리", 10000, 50000),
			new CatalogItem("⌚", "애플워치", 150000, 450000),
	};

	private static final CatalogItem[] FURNITURE = {
			new CatalogItem("🪑", "이케아 책상", 30000, 150000),
			new CatalogItem("🪑", "사무용 의자", 50000, 300000),
			new CatalogItem("🛋️", "2인용 소파", 80000, 400000),
			new CatalogItem("🛏️", "싱글 침대 프레임", 50000, 250000),
			new CatalogItem("📚", "5단 책장", 20000, 120000),
			new CatalogItem("🗄️", "수납장", 30000, 180000),
			new CatalogItem("🪞", "전신거울", 15000, 80000),
			new CatalogItem("🛁", "행거 스탠드", 10000, 60000),
			new CatalogItem("🧺", "빨래바구니 세트", 5000, 35000),
			new CatalogItem("🪵", "원목 테이블", 60000, 350000),
	};

	private static final CatalogItem[] CLOTHING = {
			new CatalogItem("👕", "나이키 후드 M", 25000, 90000),
			new CatalogItem("👕", "아디다스 티셔츠", 10000, 50000),
			new CatalogItem("🧥", "패딩 점퍼 L", 50000, 250000),
			new CatalogItem("👖", "리바이스 청바지", 15000, 70000),
			new CatalogItem("👟", "뉴발란스 530", 50000, 150000),
			new CatalogItem("👟", "나이키 운동화", 40000, 180000),
			new CatalogItem("👜", "숄더백", 20000, 200000),
			new CatalogItem("🧢", "모자", 5000, 40000),
			new CatalogItem("🧣", "목도리", 5000, 50000),
			new CatalogItem("👗", "원피스", 15000, 120000),
	};

	private static final CatalogItem[] BOOK = {
			new CatalogItem("📕", "스프링 부트 입문", 8000, 25000),
			new CatalogItem("📗", "JPA 프로그래밍", 10000, 35000),
			new CatalogItem("📘", "클린 코드", 8000, 20000),
			new CatalogItem("📙", "이펙티브 자바", 10000, 28000),
			new CatalogItem("📚", "해리포터 세트", 20000, 60000),
			new CatalogItem("📖", "자기계발 베스트", 3000, 15000),
			new CatalogItem("📓", "수능 기출 모음", 5000, 20000),
			new CatalogItem("📒", "영어 회화 교재", 5000, 18000),
			new CatalogItem("📕", "만화책 10권", 15000, 50000),
			new CatalogItem("📗", "디자인 패턴", 12000, 30000),
	};

	private static final CatalogItem[] LIVING = {
			new CatalogItem("🌬️", "공기청정기", 50000, 350000),
			new CatalogItem("🧹", "무선 청소기", 80000, 400000),
			new CatalogItem("🍳", "인덕션", 30000, 200000),
			new CatalogItem("☕", "커피머신", 40000, 300000),
			new CatalogItem("🍚", "전기밥솥", 30000, 180000),
			new CatalogItem("🧊", "미니 냉장고", 50000, 250000),
			new CatalogItem("🪴", "화분 3종", 5000, 40000),
			new CatalogItem("🛁", "가습기", 10000, 80000),
			new CatalogItem("🧴", "디퓨저 세트", 5000, 35000),
			new CatalogItem("🍽️", "식기 세트", 10000, 60000),
	};

	private static final CatalogItem[] ETC = {
			new CatalogItem("🎸", "통기타", 50000, 300000),
			new CatalogItem("🎹", "미디 키보드", 80000, 400000),
			new CatalogItem("🎨", "물감·캔버스 세트", 15000, 80000),
			new CatalogItem("⚽", "축구공", 10000, 50000),
			new CatalogItem("🚲", "자전거", 80000, 500000),
			new CatalogItem("🛹", "전동킥보드", 150000, 600000),
			new CatalogItem("🧸", "인형", 5000, 80000),
			new CatalogItem("🎁", "선물세트", 10000, 100000),
			new CatalogItem("🔧", "공구 세트", 20000, 150000),
			new CatalogItem("🧳", "캐리어", 30000, 200000),
	};

	private SampleListingGenerator() {
	}

	public static List<Listing> generate(int count) {
		Random random = new Random(1306L);
		List<Listing> result = new ArrayList<>(count);
		ListingCategory[] categories = ListingCategory.values();
		District[] districts = District.values();
		SaleStatus[] statuses = SaleStatus.values();

		for (int i = 0; i < count; i++) {
			ListingCategory category = categories[i % categories.length];
			if (i % 7 == 0) {
				category = categories[random.nextInt(categories.length)];
			}

			CatalogItem item = pickItem(category, random, i);
			District district = districts[random.nextInt(districts.length)];
			SaleStatus status = pickStatus(random);
			int price = item.minPrice() + random.nextInt(Math.max(1, item.maxPrice() - item.minPrice() + 1));
			price = (price / 1000) * 1000;

			String suffix = variantSuffix(random);
			String title = item.emoji() + " " + item.name() + suffix;
			String description = buildDescription(item, district, random);

			Listing listing = new Listing();
			listing.setTitle(title);
			listing.setDescription(description);
			listing.setPrice(price);
			listing.setCategory(category);
			listing.setDistrict(district);
			listing.setSaleStatus(status);
			listing.setSellerNickname(NICKNAMES[random.nextInt(NICKNAMES.length)]);
			listing.setThumbnailEmoji(item.emoji());
			listing.setCreatedAt(randomCreatedAt(random));
			result.add(listing);
		}
		return result;
	}

	private static CatalogItem pickItem(ListingCategory category, Random random, int index) {
		CatalogItem[] catalog = switch (category) {
			case DIGITAL -> DIGITAL;
			case FURNITURE -> FURNITURE;
			case CLOTHING -> CLOTHING;
			case BOOK -> BOOK;
			case LIVING -> LIVING;
			case ETC -> ETC;
		};
		if (index % 11 == 0) {
			return catalog[random.nextInt(catalog.length)];
		}
		return catalog[index % catalog.length];
	}

	private static SaleStatus pickStatus(Random random) {
		int roll = random.nextInt(100);
		if (roll < 70) {
			return SaleStatus.SELLING;
		}
		if (roll < 88) {
			return SaleStatus.RESERVED;
		}
		return SaleStatus.SOLD;
	}

	private static String variantSuffix(Random random) {
		String[] suffixes = { "", " (급처)", " (네고가능)", " (거의새것)", " (박스포함)", " (직거래)" };
		return suffixes[random.nextInt(suffixes.length)];
	}

	private static String buildDescription(CatalogItem item, District district, Random random) {
		String condition = CONDITIONS[random.nextInt(CONDITIONS.length)];
		return item.name() + " 판매합니다.\n"
				+ condition + " · " + district.getLabel() + " 근처 직거래 가능.\n"
				+ "문의 주시면 빠르게 답변드릴게요!";
	}

	private static LocalDateTime randomCreatedAt(Random random) {
		int daysAgo = random.nextInt(90);
		int hours = random.nextInt(24);
		int minutes = random.nextInt(60);
		return LocalDateTime.now()
				.minusDays(daysAgo)
				.minusHours(hours)
				.minusMinutes(minutes);
	}
}
