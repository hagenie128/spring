package com.spring.problem04;

/**
 * [도전 문제 4-1] Student 클래스를 완성하세요.
 *
 * 요구사항:
 *   1. 아래 필드를 선언하세요.
 *        String name  — 학생 이름
 *        int    age   — 나이
 *        String major — 전공
 *
 *   2. 기본 생성자(파라미터 없음)와 전체 필드를 받는 생성자를 작성하세요.
 *
 *   3. 각 필드의 getter / setter 를 작성하세요.
 *
 *   4. introduce() 메서드를 완성하세요.
 *        출력 형식:
 *        "안녕하세요! 저는 [name]이고, [age]살이며 [major]을 공부하고 있습니다."
 */
public class Student {

	private String name;
	private int age;
	private String major;

    // TODO 2: 기본 생성자를 작성하세요.
	public Student() {
	}
	
    // TODO 2: 전체 필드 생성자를 작성하세요.
	public Student(String name, int age, String major) {
		super();
		this.name = name;
		this.age = age;
		this.major = major;
	}

    // TODO 3: getter / setter 를 작성하세요.
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}



    // TODO 4: introduce() 메서드를 완성하세요.
    public void introduce() {
        // TODO: System.out.println(...)으로 자기소개 출력
    	System.out.println(String.format("안녕하세요! 저는 %s이고, "
    			+ "%d살이며 %s을 공부하고 있습니다.", 
    			name,age,major));
    }



}
