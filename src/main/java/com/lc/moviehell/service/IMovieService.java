package com.lc.moviehell.service;

import com.lc.moviehell.dao.domain.Movie;

import java.util.List;

/**
 * Created by lc on 15/8/21.
 */
public interface IMovieService {
    int insertMovie(Movie movie);

    Movie getMovieById(String id);

    List<Movie> getMoviesByOffset(int offset);

    boolean hasMovie(String id);

    int getMovieCount();

    List<Movie> search(String key);
}
