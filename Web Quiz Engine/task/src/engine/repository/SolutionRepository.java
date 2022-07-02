package engine.repository;

import engine.model.Solution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SolutionRepository  extends PagingAndSortingRepository<Solution, Long> {
    Page<Solution> findAllByCompletedBy(String completedBy, Pageable pageable);
}
