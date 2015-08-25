package com.lc.moviehell.service;

import com.lc.moviehell.dao.domain.Movie;

import java.util.List;

/**
 * Created by lc on 15/8/21.
 */
public interface IMovieService {
    public int insertMovie(Movie movie);

    public Movie getMovieById(long id);

    public List<Movie> getMoviesByOffset(int offset);

    public boolean hasMovie(long id);

    public int getMovieCount();
}
