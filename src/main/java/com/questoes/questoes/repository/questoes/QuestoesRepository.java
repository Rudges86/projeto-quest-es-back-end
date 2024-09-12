package com.questoes.questoes.repository.questoes;

import com.questoes.questoes.entity.Questoes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestoesRepository extends JpaRepository<Questoes, Long> {

    @Query("select q from Questoes q")
    Page<QuestoesProjection> findAllPageable(Pageable pageable);
}
