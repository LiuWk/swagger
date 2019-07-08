package com.nepenthe.demo.service.repository;

import com.nepenthe.demo.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lwk
 * @date 2019-07-05 15:29
 */
public interface FilmRepository extends JpaRepository<Film, Integer> {
}
