package com.nepenthe.demo.service;

import com.nepenthe.demo.entity.Film;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author lwk
 * @date 2019-07-05 15:54
 */
public interface FilmService {
    /**
     * 查询电影列表
     *
     * @return
     */
    Page<Film> getFilmsListPage(Integer page, Integer size);

    /**
     * 查询列表
     *
     * @return
     */
    List<Film> getFilms(Integer page, Integer size);
}
