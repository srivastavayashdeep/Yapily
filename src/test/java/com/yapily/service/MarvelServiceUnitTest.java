package com.yapily.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapily.model.Characters;
import com.yapily.repository.MarvelCharacterRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class MarvelServiceUnitTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private MarvelCharacterRepository marvelCharacterRepository;

    private String baseUrl;
    private String marvelCharactersUrl;
    private String marvelCharacterUrl;
    private String privateKey;
    private String publicKey;
    private ObjectMapper mapper;
    private MarvelService marvelService;

    @Before
    public void setUp() {
        baseUrl = "test";
        marvelCharactersUrl = "test";
        marvelCharacterUrl = "test";
        privateKey = "test";
        publicKey = "test";
        mapper = new ObjectMapper();
        marvelService = new MarvelService(restTemplate,
                marvelCharacterRepository,
                baseUrl,
                marvelCharactersUrl,
                marvelCharacterUrl,
                privateKey,
                publicKey);
    }

    @Test
    public void shouldReturnCharactersDetails() throws JsonProcessingException {
        final Characters characters = mapper.readValue(getCharJson(), Characters.class);

        final ResponseEntity<Characters> tResponseEntity = new ResponseEntity<>(characters, HttpStatus.OK);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                ArgumentMatchers.<Class<Characters>>any(),
                anyMap()
        )).thenReturn(tResponseEntity);
        final Characters characterValue = marvelService.retrieveMarvelCharacters("1","0");

        assertThat(characterValue.getData().getResults().size(),is(1));
        assertThat(characterValue.getData().getResults().get(0).getId(),is(1009742));
        assertThat(characterValue.getData().getResults().get(0).getName(),is("Zzzax"));
    }

    private String getCharJson(){
        return "{\n" +
                "    \"code\": 200,\n" +
                "    \"status\": \"Ok\",\n" +
                "    \"copyright\": \"© 2021 MARVEL\",\n" +
                "    \"attributionText\": \"Data provided by Marvel. © 2021 MARVEL\",\n" +
                "    \"attributionHTML\": \"<a href=\\\"http://marvel.com\\\">Data provided by Marvel. © 2021 MARVEL</a>\",\n" +
                "    \"etag\": \"27b87ee0b3d67252b9099de9304a000c4461272a\",\n" +
                "    \"data\": {\n" +
                "        \"offset\": 1492,\n" +
                "        \"limit\": 100,\n" +
                "        \"total\": 1493,\n" +
                "        \"count\": 1,\n" +
                "        \"results\": [\n" +
                "            {\n" +
                "                \"id\": 1009742,\n" +
                "                \"name\": \"Zzzax\",\n" +
                "                \"description\": \"A chain reaction in an atomic reactor, a result of a terrorist attack, accidentally created a force that absorbed the electrolytes of the nearby humans' brains, granting the explosion of energy a rudimentary sentience.  \",\n" +
                "                \"thumbnail\": {\n" +
                "                    \"path\": \"http://i.annihil.us/u/prod/marvel/i/mg/c/d0/4ced5ab9078c9\",\n" +
                "                    \"extension\": \"jpg\"\n" +
                "                },\n" +
                "                \"resourceURI\": \"http://gateway.marvel.com/v1/public/characters/1009742\",\n" +
                "                \"comics\": {\n" +
                "                    \"available\": 5,\n" +
                "                    \"collectionURI\": \"http://gateway.marvel.com/v1/public/characters/1009742/comics\",\n" +
                "                    \"items\": [\n" +
                "                        {\n" +
                "                            \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/37047\",\n" +
                "                            \"name\": \"Hulk (2008) #36\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/40023\",\n" +
                "                            \"name\": \"Hulk (2008) #36 (I Am Captain America Variant)\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/29541\",\n" +
                "                            \"name\": \"Incredible Hulks (2010) #602 (SHS VARIANT)\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/75831\",\n" +
                "                            \"name\": \"Power Man (1974) #47\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/17890\",\n" +
                "                            \"name\": \"West Coast Avengers (1985) #12\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"returned\": 5\n" +
                "                },\n" +
                "                \"series\": {\n" +
                "                    \"available\": 4,\n" +
                "                    \"collectionURI\": \"http://gateway.marvel.com/v1/public/characters/1009742/series\",\n" +
                "                    \"items\": [\n" +
                "                        {\n" +
                "                            \"resourceURI\": \"http://gateway.marvel.com/v1/public/series/3374\",\n" +
                "                            \"name\": \"Hulk (2008 - 2012)\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"resourceURI\": \"http://gateway.marvel.com/v1/public/series/8842\",\n" +
                "                            \"name\": \"Incredible Hulks (2010 - 2011)\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"resourceURI\": \"http://gateway.marvel.com/v1/public/series/20672\",\n" +
                "                            \"name\": \"Power Man (1974 - 1978)\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"resourceURI\": \"http://gateway.marvel.com/v1/public/series/3630\",\n" +
                "                            \"name\": \"West Coast Avengers (1985 - 1994)\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"returned\": 4\n" +
                "                },\n" +
                "                \"stories\": {\n" +
                "                    \"available\": 6,\n" +
                "                    \"collectionURI\": \"http://gateway.marvel.com/v1/public/characters/1009742/stories\",\n" +
                "                    \"items\": [\n" +
                "                        {\n" +
                "                            \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/38045\",\n" +
                "                            \"name\": \"Cover #38045\",\n" +
                "                            \"type\": \"cover\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/82130\",\n" +
                "                            \"name\": \"Interior #82130\",\n" +
                "                            \"type\": \"interiorStory\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/90817\",\n" +
                "                            \"name\": \"Interior #90817\",\n" +
                "                            \"type\": \"interiorStory\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/94784\",\n" +
                "                            \"name\": \"Incredible Hulks (2009) #602, SHS VARIANT\",\n" +
                "                            \"type\": \"cover\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/96343\",\n" +
                "                            \"name\": \"Hulk (2008) #36\",\n" +
                "                            \"type\": \"cover\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"resourceURI\": \"http://gateway.marvel.com/v1/public/stories/168522\",\n" +
                "                            \"name\": \"cover from Power Man (1978) #47\",\n" +
                "                            \"type\": \"cover\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"returned\": 6\n" +
                "                },\n" +
                "                \"events\": {\n" +
                "                    \"available\": 0,\n" +
                "                    \"collectionURI\": \"http://gateway.marvel.com/v1/public/characters/1009742/events\",\n" +
                "                    \"items\": [],\n" +
                "                    \"returned\": 0\n" +
                "                },\n" +
                "                \"urls\": [\n" +
                "                    {\n" +
                "                        \"type\": \"detail\",\n" +
                "                        \"url\": \"http://marvel.com/characters/2682/zzzax?utm_campaign=apiRef&utm_source=0b35181f56b0b91e938ca633e9febcb6\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"type\": \"wiki\",\n" +
                "                        \"url\": \"http://marvel.com/universe/Zzzax?utm_campaign=apiRef&utm_source=0b35181f56b0b91e938ca633e9febcb6\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"type\": \"comiclink\",\n" +
                "                        \"url\": \"http://marvel.com/comics/characters/1009742/zzzax?utm_campaign=apiRef&utm_source=0b35181f56b0b91e938ca633e9febcb6\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
    }

}