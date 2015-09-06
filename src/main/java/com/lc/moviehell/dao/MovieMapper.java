package com.lc.moviehell.dao;

import com.lc.moviehell.dao.domain.Movie;

import java.util.List;

/**
 * Created by lc on 15/8/21.
 */
public interface MovieMapper {
    int insertMovie(Movie movie);

    Movie getMovieById(long id);

    List<Movie> getMoviesByOffset(int offset);

    int getMovieCount();

    List<Movie> searchMovie(String key);
}
