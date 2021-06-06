package com.yapily.service;

import static com.yapily.util.MD5Hash.getHash;
import static com.yapily.util.AppConstants.ACCEPT;
import static com.yapily.util.AppConstants.ACCEPT_TYPE;
import static com.yapily.util.AppConstants.LIMIT;
import static com.yapily.util.AppConstants.TS;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpMethod.GET;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yapily.exception.CharacterNotFoundException;
import com.yapily.model.Characters;
import com.yapily.model.MarvelChar;
import com.yapily.model.MarvelCharacter;
import com.yapily.model.Result;
import com.yapily.repository.MarvelCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MarvelService {

    private RestTemplate restTemplate;
    private String baseUrl;
    private String marvelCharactersUrl;
    private String marvelCharacterUrl;
    private String privateKey;
    private String publicKey;
    private MarvelCharacterRepository marvelCharacterRepository;

    @Autowired
    public MarvelService(final RestTemplate restTemplate,
                         final MarvelCharacterRepository marvelCharacterRepository,
                         @Value("${base.url}") final String baseUrl,
                         @Value("${marvel.characters}") final String marvelCharactersUrl,
                         @Value("${marvel.character}") final String marvelCharacterUrl,
                         @Value("${marvel.privateKey}") final String privateKey,
                         @Value("${marvel.publicKey}") final String publicKey) {
        this.restTemplate = restTemplate;
        this.marvelCharacterRepository = marvelCharacterRepository;
        this.baseUrl = baseUrl;
        this.marvelCharactersUrl = marvelCharactersUrl;
        this.marvelCharacterUrl = marvelCharacterUrl;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public void preLoadMarvelCharacters() {
        Integer offset = 0;
        Characters characters = retrieveMarvelCharacters(LIMIT, offset.toString());
        while (!characters.getData().getResults().isEmpty()) {
            final List<MarvelCharacter> marvelCharacters =
                    characters
                            .getData()
                            .getResults()
                            .stream().map(e -> new MarvelCharacter(e.getId(), e.getName()))
                            .collect(Collectors.toList());

            marvelCharacterRepository.saveAll(marvelCharacters);
            offset += 100;
            characters = retrieveMarvelCharacters(LIMIT, offset.toString());
        }
    }

    public Characters retrieveMarvelCharacters(final String limit,
                                               final String offset) {

        final HttpEntity<String> entity = new HttpEntity<>(getHeader());
        final ResponseEntity<Characters> accountsResponseEntity = restTemplate.exchange(baseUrl + marvelCharactersUrl, GET, entity, Characters.class, getParameters(limit, offset, null));
        return accountsResponseEntity.getBody();
    }

    public List<Integer> retrieveMarvelCharactersId() {
        final List<MarvelCharacter> all = marvelCharacterRepository.findAll();
        return all.stream().map(MarvelCharacter::getCharacterID).collect(Collectors.toList());
    }

    public MarvelChar retrieveMarvelCharacterById(final String characterId) {

        final HttpEntity<String> entity = new HttpEntity<>(getHeader());
        final ResponseEntity<Characters> accountsResponseEntity = restTemplate.exchange(baseUrl + marvelCharacterUrl, GET, entity, Characters.class, getParameters("1", "0", characterId));
        final Characters characters = accountsResponseEntity.getBody();
        if (nonNull(characters) && nonNull(characters.getData())) {
            final Optional<Result> first = Optional.ofNullable(characters.getData().getResults().stream().findFirst().orElseThrow(() -> new CharacterNotFoundException("Character not found")));
            return new MarvelChar(first.get().getId(), first.get().getName(), first.get().getDescription(), first.get().getThumbnail());
        } else {
            throw new CharacterNotFoundException("Character not found");
        }
    }

    private HttpHeaders getHeader() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(ACCEPT, ACCEPT_TYPE);
        return httpHeaders;
    }

    private Map<String, String> getParameters(final String limit,
                                              final String offset,
                                              final String characterId) {
        final Map<String, String> parameter = new HashMap<>();
        parameter.put("ts", TS);
        parameter.put("apikey", publicKey);
        parameter.put("hash", getHash(TS + privateKey + publicKey));
        parameter.put("limit", limit);
        parameter.put("offset", offset);
        parameter.put("characterId", characterId);
        return parameter;
    }
}
