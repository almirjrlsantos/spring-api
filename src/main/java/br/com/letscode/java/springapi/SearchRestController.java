package br.com.letscode.java.springapi;

import br.com.letscode.java.springapi.detail.MovieDetailRestRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class SearchRestController {
    FileCsv fileCsv = new FileCsv();

    private final MovieMinimalRestRepository restRepository;
    private final MovieDetailRestRepository restRepositoryDetail;

    public SearchRestController(MovieMinimalRestRepository restRepository, MovieDetailRestRepository restRepositoryDetail) {
        this.restRepository = restRepository;
        this.restRepositoryDetail = restRepositoryDetail;
    }

    //"/movies/{id}"
    @GetMapping("/movies/{id}")
    public MovieMinimalRestRepository detail(@PathVariable String id){
        return this.restRepository;
    }

    @GetMapping("/search")
    public Optional<ResultSearch> search(@RequestParam String title){
        List<ResultSearch> resultSearch = fileCsv.cacheCsv(title);
        if (resultSearch == null){
            ResultSearch rs = this.restRepository.search(title);
            fileCsv.writeFile(rs, title);
            return Optional.ofNullable(rs);
        } else {
            return resultSearch.stream().findFirst();
        }
    }


}