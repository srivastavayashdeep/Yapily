package com.yapily.controller;


import static org.springframework.http.ResponseEntity.ok;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import com.yapily.exception.ValidationException;
import com.yapily.model.MarvelChar;
import com.yapily.service.MarvelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarvelController {

    private static final Logger logger = LoggerFactory.getLogger(MarvelController.class);

    @Autowired
    private MarvelService marvelService;

    @RequestMapping(method = RequestMethod.GET, path = "/characters")
    public ResponseEntity<List<Integer>> getAllMarvelCharacterIds() {

        final List<Integer> retrieveMarvelCharactersId = marvelService.retrieveMarvelCharactersId();
        return ok(retrieveMarvelCharactersId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/characters/{characterId}")
    public ResponseEntity<MarvelChar> getMarvelCharacterById(@PathVariable String characterId) {

        validatePathVariable(characterId);
        final MarvelChar marvelChar = marvelService.retrieveMarvelCharacterById(characterId);
        return ok(marvelChar);
    }

    private void validatePathVariable(final String characterId) {
        String regex = "[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(characterId);
        if(characterId.length() != 7 || !matcher.matches()) {
            throw new ValidationException("The characterId Path variable is not correct");
        }
    }

    @PostConstruct
    private void initMarvelCharacterStore() {
        marvelService.preLoadMarvelCharacters();
    }
}
