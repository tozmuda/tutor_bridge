package org.tutorBridge.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tutorBridge.dto.*;
import org.tutorBridge.entities.Specialization;
import org.tutorBridge.entities.Tutor;
import org.tutorBridge.repositories.AvailabilityRepo;
import org.tutorBridge.repositories.SpecializationRepo;
import org.tutorBridge.repositories.TutorRepo;
import org.tutorBridge.repositories.UserRepo;
import org.tutorBridge.validation.ValidationException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TutorService extends UserService<Tutor> {
    private final TutorRepo tutorRepo;
    private final AvailabilityRepo availabilityRepo;
    private final SpecializationRepo specializationRepo;

    public TutorService(TutorRepo tutorRepo,
                        AvailabilityRepo availabilityRepo,
                        UserRepo userDao,
                        PasswordEncoder pe,
                        SpecializationRepo specializationRepo) {
        super(userDao, pe);
        this.tutorRepo = tutorRepo;
        this.availabilityRepo = availabilityRepo;
        this.specializationRepo = specializationRepo;
    }

    private static TutorUpdateDTO fromTutorToDTO(Tutor tutor) {
        return new TutorUpdateDTO(
                tutor.getFirstName(),
                tutor.getLastName(),
                tutor.getPhone(),
                tutor.getBirthDate(),
                tutor.getSpecializations()
                        .stream()
                        .map(Specialization::getName)
                        .collect(Collectors.toSet()),
                tutor.getBio());
    }

    @Transactional
    public void registerTutor(TutorRegisterDTO tutorData) {
        Tutor tutor = new Tutor(
                tutorData.getFirstName(),
                tutorData.getLastName(),
                tutorData.getPhone(),
                tutorData.getEmail(),
                tutorData.getPassword(),
                tutorData.getBio(),
                tutorData.getBirthDate()
        );
        tutor.setSpecializations(
                tutorData.getSpecializations().stream()
                        .map(Specialization::new)
                        .collect(Collectors.toSet())
        );

        for (Specialization specialization : tutor.getSpecializations()) {
            Optional<Specialization> existingSpecialization = specializationRepo.findByName(specialization.getName());
            if (existingSpecialization.isPresent()) {
                specialization.setSpecializationId(existingSpecialization.get().getSpecializationId());
            } else {
                specializationRepo.save(specialization);
            }
        }
        registerUser(tutor);
    }

    public TutorSpecializationDTO getSpecializations(Tutor tutor) {
        return new TutorSpecializationDTO(tutor.getSpecializations().stream()
                .map(Specialization::getName)
                .collect(Collectors.toSet()));
    }

    @Override
    protected void saveUser(Tutor user) {
        tutorRepo.save(user);
    }

    @Transactional
    public TutorUpdateDTO updateTutorInfo(Tutor tutor, TutorUpdateDTO tutorData) {
        if (tutorData.getFirstName() != null) tutor.setFirstName(tutorData.getFirstName());
        if (tutorData.getLastName() != null) tutor.setLastName(tutorData.getLastName());
        if (tutorData.getPhone() != null) tutor.setPhone(tutorData.getPhone());
        if (tutorData.getBirthDate() != null) tutor.setBirthDate(tutorData.getBirthDate());
        if (tutorData.getBio() != null) tutor.setBio(tutorData.getBio());
        if (tutorData.getSpecializations() != null) {
            Set<Specialization> specializations = getOrCreateSpecializations(tutorData.getSpecializations());
            tutor.setSpecializations(specializations);
        }

        tutorRepo.update(tutor);

        return fromTutorToDTO(tutor);
    }

    private Set<Specialization> getOrCreateSpecializations(Set<String> specializationNames) {
        return specializationNames.stream()
                .map(name -> specializationRepo.findByName(name)
                        .orElseGet(() -> {
                            Specialization newSpec = new Specialization(name);
                            specializationRepo.save(newSpec);
                            return newSpec;
                        }))
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public TutorUpdateDTO getTutorInfo(Tutor tutor) {
        return fromTutorToDTO(tutor);
    }

    public Tutor fromEmail(String email) {
        return tutorRepo.findByEmail(email)
                .orElseThrow(() -> new ValidationException("Tutor not found"));
    }
}
