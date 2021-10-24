package br.com.letscode.java.springapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResultSearch {

    @JsonProperty("Search")
    private List<MovieMinimal> resultList;
    private Integer total;
    private Boolean response;

    @JsonProperty("Response")
    public void setResponse(String response) {
        this.response = Boolean.valueOf(response);
    }

    @JsonProperty("totalResults")
    public void setTotal(String total) {
        this.total = Integer.parseInt(total);
    }

    public ResultSearch(List<MovieMinimal> resultList) {
        this.resultList = resultList;
        this.total = resultList.size();
        this.response = false;
    }
}
