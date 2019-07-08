package com.nepenthe.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.nepenthe.demo.entity.Film;
import com.nepenthe.demo.service.FilmService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmServiceImplTest {

    @Autowired
    private FilmService filmService;

    @Test
    public void getFilmsList() {
        Page<Film> list = filmService.getFilmsListPage(1, 10);
        System.out.println(JSON.toJSONString(list));
    }
}