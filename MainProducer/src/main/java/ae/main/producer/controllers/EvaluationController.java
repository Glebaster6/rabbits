package ae.main.producer.controllers;

import ae.main.producer.services.authentication.AuthenticationService;
import ae.main.producer.services.evaluation.EvaluationService;
import lombok.SneakyThrows;
import main.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/evaluation")
public class EvaluationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/{facility_id}")
    public String getFacilityPage(@ModelAttribute("model") ModelMap model,
                                  @PathVariable("facility_id") Long facilityId,
                                  Authentication authentication) {
        evaluationService.getFacilityData(GetFacilityDataDto.builder()
                .facilityId(facilityId)
                .build(), authentication);
        model.addAttribute("facilityId", facilityId);
        return "facility";
    }

    @GetMapping("/create")
    public String getEvaluationCreationPage() {
        return "evaluation_create";
    }

    @PostMapping("/create")
    public String getEvaluationCreationPage(EvaluationCreateDto evaluationDto,
                                            Authentication authentication) {
        evaluationService.createEvaluation(evaluationDto, authentication);
        return "redirect:/";
    }

    @GetMapping("/{facility_id}/delete/{evaluation_id}")
    public String deleteEvaluation(@PathVariable("facility_id") Long facilityId,
                                   @PathVariable("evaluation_id") Long evaluationId,
                                   Authentication authentication) {
        if (authenticationService.getUserByAuthentication(authentication).getFacilityId() == facilityId) {
            evaluationService.deleteEvaluation(DeleteEvaluationDto.builder().evaluationId(evaluationId).build());
        } else {
            return "redirect:/";
        }

        return "redirect:/evaluation/" + facilityId;
    }

    @GetMapping("/{facility_id}/info/{evaluation_id}")
    public String getEvaluationData(@PathVariable("facility_id") Long facilityId,
                                    @PathVariable("evaluation_id") Long evaluationId,
                                    Authentication authentication) {


        if (authenticationService.getUserByAuthentication(authentication).getFacilityId() == facilityId) {
            evaluationService.getEvaluationDto(
                    GetEvaluationDataDto.builder()
                            .evaluationId(evaluationId)
                            .facilityId(facilityId)
                            .build()
                    , authentication);
        } else {
            return "redirect:/";
        }

        return "evaluation_info";
    }

    @GetMapping("/{facility_id}/info/{evaluation_id}/period/{period}")
    public String getDataInPeriod(@PathVariable("facility_id") Long facilityId,
                                  @PathVariable("evaluation_id") Long evaluationId,
                                  @PathVariable("period") Integer period,
                                  Authentication authentication
    ) {

        if (authenticationService.getUserByAuthentication(authentication).getFacilityId() == facilityId) {
            evaluationService.getEvaluationDataByEvaluationAndPeriod(
                    GetEvaluationDataByEvaluationAndPeriodDto.builder()
                            .evaluationId(evaluationId)
                            .period(period)
                            .build(), authentication
            );
        } else {
            return "redirect:/";
        }

        return "period_info";
    }

    @GetMapping("/{facility_id}/info/{evaluation_id}/period/{period}/evaluation_data/{evaluation_data_id}")
    public String getEvaluationDataResultDto(@PathVariable("facility_id") Long facilityId,
                                             @PathVariable("evaluation_id") Long evaluationId,
                                             @PathVariable("period") Integer period,
                                             @PathVariable("evaluation_data_id") Long evaluationDataId,
                                             Authentication authentication){
        evaluationService.getEvaluationResultById(GetCommodityGroupResultDto.builder()
                .id(evaluationDataId)
                .build(), authentication
        );
        return "commodity_group_result";
    }
}
