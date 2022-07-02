package engine.service;

import engine.model.Solution;
import engine.repository.SolutionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SolutionService {
    private final SolutionRepository solutionRepository;

    public SolutionService(SolutionRepository solutionRepository) {
        this.solutionRepository = solutionRepository;
    }

    public Page<Solution> findAllByCompletedBy(String completedBy, Pageable pageable) {
        return solutionRepository.findAllByCompletedBy(completedBy, pageable);
    }

    public void saveSolution(Solution toSave) {
        solutionRepository.save(toSave);
    }

}
