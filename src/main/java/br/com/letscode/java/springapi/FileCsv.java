package br.com.letscode.java.springapi;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileCsv {

    public Path getPath(){
        URL url = this.getClass().getClassLoader().getResource("csv/cache.csv");
        File file = new File(url.getFile());
        return Path.of(file.getPath().replaceAll("%20", " "));
    }

    public List<ResultSearch> cacheCsv(String title) {
        Map<String, List<ResultSearch>> searches = addCacheClass();;

        Optional<Map.Entry<String, List<ResultSearch>>> mmFilter = searches.entrySet().stream()
                .filter(f -> f.getKey().equals(title))
                .findFirst();

        if (!mmFilter.isEmpty()){
            return mmFilter.get().getValue();
        }
        return null;
    }

    private Map<String, List<ResultSearch>> addCacheClass() {
        List<String> lineCsv = readFile();
        Map<String, List<ResultSearch>> map = new HashMap<>();

        for(String line : lineCsv){
            List<MovieMinimal> moviesCsv = new ArrayList<>();
            List<ResultSearch> resultSearches = new ArrayList<>();
            String[] rsSplit = line.split(";");

            String wordsSearch = rsSplit[0];
            int total = Integer.parseInt(rsSplit[2]);
            boolean response = Boolean.valueOf(rsSplit[3]);

            String[] list = rsSplit[1].split("/");
            for( int i=0; i < list.length; i+=3){
                String imdbID = list[i];
                String title = list[i+1];
                int year = Integer.parseInt(list[i+2]);
                moviesCsv.add(new MovieMinimal(imdbID, title, year));
                resultSearches.add(new ResultSearch(moviesCsv, total, response));
            }

            map.put(wordsSearch, resultSearches);
        }
        return map;
    }

    public List<String> readFile() {
        try(Stream<String> file = Files.lines(getPath())){
            return file.collect(Collectors.toList());
        } catch (IOException e){
            System.err.println("Erro ao ler o arquivo");
        }
        return null;
    }

    public void writeFile(ResultSearch rs, String wordsSearch) {
        String s =  stringCsv(rs, wordsSearch);
        if(s == null){
            return;
        }
        try(OutputStream fileOut = new BufferedOutputStream(Files.newOutputStream(this.getPath(), StandardOpenOption.APPEND))) {
            fileOut.write(s.getBytes());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private String stringCsv(ResultSearch rs, String wordsSearch) {
        StringBuilder add = new StringBuilder();

        add.append(wordsSearch + ";");

        List<MovieMinimal> movieMinimals  = rs.getResultList();
        if(movieMinimals == null){
            return null;
        }
        for(int i = 0; i < movieMinimals.size(); i++){
            MovieMinimal mm = movieMinimals.get(i);
            String imdbId = mm.getImdbId();
            String title = mm.getTitle();
            Integer year = mm.getYear();
            add.append(imdbId + "/");
            add.append(title + "/");
            add.append(year);
            if( i == movieMinimals.size()-1 ){
                add.append(";");
            } else {
                add.append("/");
            }
        }

        int total = rs.getTotal();
        add.append(total + ";");
        add.append("false\n");
        return add.toString();
    }
}
