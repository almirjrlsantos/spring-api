package br.com.letscode.java.springapi.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MovieDetail {

    @JsonProperty("imdbID")
    private String imdbId;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private Integer year;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Released")
    private String released;
    @JsonProperty("Genre")
    private String genre;
    @JsonProperty("Language")
    private String language;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("imdbRating")
    private String imdbRating;

    public void setYear(String year) {
        this.year = convertYear(year);
    }

    private int convertYear(final String year) {
        if (year.matches("\\d+")) {
            return Integer.parseInt(year);
        }
        return Arrays.stream(year.split("\\D"))
                .map(Integer::parseInt)
                .findFirst()
                .orElseThrow();
    }
}
