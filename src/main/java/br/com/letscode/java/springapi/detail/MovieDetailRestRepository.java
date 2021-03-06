package br.com.letscode.java.springapi.detail;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "movieDetailRest", url = "${omdb.url}")
public interface MovieDetailRestRepository {
    @GetMapping
    MovieDetail detail(@RequestParam("i") String imdbID);
}