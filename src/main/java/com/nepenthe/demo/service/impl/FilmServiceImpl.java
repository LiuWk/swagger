package com.nepenthe.demo.service.impl;

import com.nepenthe.demo.entity.Film;
import com.nepenthe.demo.service.FilmService;
import com.nepenthe.demo.service.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lwk
 * @date 2019-07-05 15:56
 */
@Service("filmService")
public class FilmServiceImpl implements FilmService {
    @Autowired
    private FilmRepository filmRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<Film> getFilmsListPage(Integer page, Integer size) {
        return filmRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Film> getFilms(Integer page, Integer size) {
        Page<Film> list = filmRepository.findAll(null, PageRequest.of(page, size));
        return list.getContent();
    }

}
