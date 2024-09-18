package com.ea.wsj_project.model.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpokenLanguage {

    @JsonProperty("english_name")
    private String englishName;

    @JsonProperty("iso_639_1")
    private String isoCode;

    @JsonProperty("name")
    private String name;

}