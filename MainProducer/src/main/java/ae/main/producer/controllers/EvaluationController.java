package ae.main.producer.controllers;

import ae.main.producer.services.evaluation.EvaluationService;
import lombok.SneakyThrows;
import main.dto.EvaluationCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/{facility_id}")
    public String getFacilityPage(HttpSession session,
                                  @ModelAttribute("model") ModelMap model,
                                  @PathVariable("facility_id") Long facilityId) {


        model.addAttribute("facilityId", facilityId);
        return "evaluationPage";
    }

    @GetMapping("/{facility_id}/create")
    public String getEvaluationCreationPage(@ModelAttribute("model") ModelMap model,
                                            @PathVariable("facility_id") Long facilityId) {
        model.addAttribute("facilityId", facilityId);
        return "evaluation_create";
    }

    @PostMapping("/{facility_id}/create")
    @SneakyThrows
    public String getEvaluationCreationPage(@PathVariable("facility_id") Long facilityId,
                                            EvaluationCreateDto evaluationDto) {
        evaluationDto.setFacilityId(facilityId);
        evaluationService.createEvaluation(evaluationDto);
        wait(10);

        return "redirect:/evaluation/" + facilityId;
    }
}
