package pl.coderslab.javaGym.service.dataService;

import pl.coderslab.javaGym.service.AbstractService;

import java.util.List;

public interface AbstractDataService<T> extends AbstractService<T> {

    T save(T t);

    List<T> findAll();

    T findById(Long id);

    Boolean deleteById(Long id);

    T edit (T t, Long id);

}
