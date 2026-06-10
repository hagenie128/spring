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

@RequestMapping("/cars")
@Controller
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ModelAndView list(ModelAndView view) {
        view.addObject("cars", carService.findAll());
        view.setViewName("list");
        return view;
    }

    @GetMapping("/new")
    public ModelAndView newCar(ModelAndView view) {
        view.addObject("car", new CarDTO());
        view.setViewName("form");
        return view;
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("car") CarDTO car, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes, Model model){
        try{
            if(bindingResult.hasErrors()){
                throw new Exception("입력값이 잘못되었습니다. 다시 확인하여 입력해 주세요.");
            }
            carService.save(car);
        }catch (Exception e){
//            model.addAttribute("car",car);
            e.printStackTrace();
            return "form";
        }

        return "redirect:/cars";
    }

    @GetMapping("/{id}")
    public ModelAndView details(@PathVariable Integer id, ModelAndView view, RedirectAttributes redirectAttributes){
        view.addObject("car", carService.findById(id));
        view.setViewName("detail");
        return view;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        carService.delete(id);
        return "redirect:/cars";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@Valid @PathVariable Integer id, ModelAndView view, RedirectAttributes redirectAttributes){
        view.addObject("car", carService.findById(id));
        view.setViewName("edit");
        return view;
    }

}








