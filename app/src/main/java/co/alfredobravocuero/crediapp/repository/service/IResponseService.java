package co.alfredobravocuero.crediapp.repository.service;

/**
 * Created by jggomez on 15-Jun-17.
 */

public interface IResponseService<T> {

    void response(T object);

    void error(Exception e);
}
