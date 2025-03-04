package org.tutorBridge.controllers;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.tutorBridge.dto.*;
import org.tutorBridge.services.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tutors")
public class TutorController {
    private final TutorService tutorService;
    private final AbsenceService absenceService;
    private final ReservationService reservationService;
    private final PlanService planService;
    private final AvailabilityService availabilityService;

    public TutorController(TutorService tutorService,
                           AbsenceService absenceService,
                           ReservationService reservationService,
                           PlanService planService,
                           AvailabilityService availabilityService) {
        this.tutorService = tutorService;
        this.absenceService = absenceService;
        this.reservationService = reservationService;
        this.planService = planService;
        this.availabilityService = availabilityService;
    }

    @PostMapping("/register")
    public Map<String, String> registerTutor(@Valid @RequestBody TutorRegisterDTO tutorData) {
        tutorService.registerTutor(tutorData);
        return Collections.singletonMap("message", "Tutor registered successfully");
    }

    @PutMapping("/account")
    public TutorUpdateDTO updateTutorInfo(@Valid @RequestBody TutorUpdateDTO tutorData, Authentication authentication) {
        String email = authentication.getName();
        return tutorService.updateTutorInfo(tutorService.fromEmail(email), tutorData);
    }

    @GetMapping("/account")
    public TutorUpdateDTO getTutorInfo(Authentication authentication) {
        String email = authentication.getName();
        return tutorService.getTutorInfo(tutorService.fromEmail(email));
    }

    @GetMapping("/specialization")
    public TutorSpecializationDTO getTutorSpecializations(Authentication authentication) {
        String email = authentication.getName();
        return tutorService.getSpecializations(tutorService.fromEmail(email));
    }

    @GetMapping("/availability")
    public List<AvailabilityDTO> getAvailabilities(@RequestBody(required = false) TimeFrameDTO timeframe,
                                                   Authentication authentication) {
        timeframe = TimeFrameDTO.fillInEmptyFields(timeframe);
        String email = authentication.getName();
        return availabilityService.getAvailabilities(tutorService.fromEmail(email), timeframe);
    }


    @PutMapping("/availability")
    public List<AvailabilityDTO> setWeeklyAvailability(@Valid @RequestBody WeeklySlotsDTO weeklySlotsDTO,
                                                       Authentication authentication) {
        String email = authentication.getName();
        return availabilityService.addWeeklyAvailability(tutorService.fromEmail(email), weeklySlotsDTO);
    }

    @PostMapping("/absence")
    public List<AbsenceDTO> addAbsence(@RequestBody @Valid AbsenceDTO absenceDTO, Authentication authentication) {
        String email = authentication.getName();
        return absenceService.addAbsence(tutorService.fromEmail(email), absenceDTO.getStart(), absenceDTO.getEnd());
    }

    @DeleteMapping("/absence/{absenceId}")
    public List<AbsenceDTO> deleteAbsence(@PathVariable(name = "absenceId") Long absenceId,
                                          Authentication authentication) {
        String email = authentication.getName();
        return absenceService.deleteAbsence(tutorService.fromEmail(email), absenceId);
    }

    @GetMapping("/absence")
    public List<AbsenceDTO> getAbsences(@RequestBody(required = false) TimeFrameDTO timeframe,
                                        Authentication authentication) {
        timeframe = TimeFrameDTO.fillInEmptyFields(timeframe);
        String email = authentication.getName();
        return absenceService.getAbsences(tutorService.fromEmail(email), timeframe);
    }


    @PostMapping("/reservation/status")
    public PlanResponseDTO changeReservationsStatus(@Valid @RequestBody StatusChangesDTO changes,
                                                    Authentication authentication) {
        String email = authentication.getName();
        var tutor = tutorService.fromEmail(email);
        reservationService.changeReservationStatus(tutor, changes.getChanges());
        return planService.getPlanForTutor(tutor, TimeFrameDTO.fillInEmptyFields(null));
    }

    @GetMapping("/reservation")
    public PlanResponseDTO getPlan(Authentication authentication, TimeFrameDTO timeframe) {
        String email = authentication.getName();
        return planService.getPlanForTutor(tutorService.fromEmail(email), timeframe);
    }

}
