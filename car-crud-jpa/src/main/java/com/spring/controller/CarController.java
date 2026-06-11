package com.spring.controller;

import com.spring.dto.CarDTO;
import com.spring.service.CarService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 차량 목록, 등록, 상세, 수정, 삭제 요청을 처리하는 MVC Controller입니다.
 */
@RequestMapping("/cars")
@Controller
public class CarController {
    private final CarService carService;

    /**
     * 차량 관련 비즈니스 로직은 Service에 위임하기 위해 생성자 주입을 사용합니다.
     */
    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     * 차량 전체 목록을 조회해 list.html 화면에 전달합니다.
     */
    @GetMapping
    public ModelAndView list(ModelAndView view) {
        view.addObject("cars", carService.findAll());
        view.setViewName("list");
        return view;
    }

    /**
     * 신규 차량 등록 폼을 보여줍니다.
     */
    @GetMapping("/new")
    public ModelAndView newCar(ModelAndView view) {
        // th:object="${car}"가 null이 되지 않도록 빈 객체를 전달합니다.
        view.addObject("car", new CarDTO());
        view.setViewName("form");
        return view;
    }

    /**
     * 등록 폼에서 넘어온 차량 데이터를 검증한 뒤 저장합니다.
     */
    @PostMapping
    public String save(@Valid @ModelAttribute("car") CarDTO car,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes, Model model){
        try{
            if(bindingResult.hasErrors()){
                throw new Exception("입력값이 잘못되었습니다. 다시 확인하여 입력해 주세요.");
            }
            System.out.println(car);
             carService.save(car);
        }catch (Exception e){
            e.printStackTrace();
            return "form";
        }
        System.out.println(car);
        return "redirect:/cars/"+car.getCarId();
    }

    /**
     * 차량 ID로 상세 정보를 조회해 detail.html에 전달합니다.
     */
    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Integer id, ModelAndView view) {
        view.addObject("car", carService.findById(id));
        view.setViewName("detail");
        return view;
    }

    /**
     * 차량을 삭제하고 결과 메시지를 리다이렉트 후 한 번만 보여줍니다.
     */
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Integer id,
                         RedirectAttributes redirectAttributes) {
        int result = carService.deleteById(id);
        if (result == 1) {
            redirectAttributes.addFlashAttribute(
                    "successMessage", "차량이 성공적으로 삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "차량 삭제에 실패했습니다.");
        }
        return "redirect:/cars";
    }

    /**
     * 수정할 차량 데이터를 조회해 수정 폼에 채웁니다.
     */
    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Integer id, ModelAndView view) {
        view.addObject("car", carService.findById(id));
        view.setViewName("edit");
        return view;
    }

    /**
     * 수정 폼에서 넘어온 데이터를 검증한 뒤 기존 차량 정보를 업데이트합니다.
     */
    @PostMapping("/{id}/edit")
    public String edit(@Valid @ModelAttribute("car") CarDTO car,
                       BindingResult bindingResult,
                       @PathVariable Integer id,
                       RedirectAttributes redirectAttributes, Model model){
        try{
            if(bindingResult.hasErrors()){
                throw new Exception("입력값이 잘못되었습니다. 다시 확인하여 입력해 주세요.");
            }
            car.setCarId(id);
            carService.edit(car);
        }catch (Exception e){
            e.printStackTrace();
            return "edit";
        }

        return "redirect:/cars";
    }
}








