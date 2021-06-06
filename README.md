# Yapily Tech Challenge by Yashdeep Srivastava

Start the application command : mvn spring-boot:run 

The application will be triggered by get commands

The GET command detail :-

Endpoint :- 
```
GET : http://localhost:9000/marvel/characters
```

Response :
```
[ 1009718, 1017100, 1009144, 1010699, 1016823 ]
```

...............................................................


Endpoint :- 
```
GET : http://localhost:9000/marvel/characters/{characterId}
```

Response :
```
{
"id": 1009718,
"name": "Wolverine",
"description": "Born with super-human senses and the power to heal from almost any
                wound, Wolverine was captured by a secret Canadian organization and given an
                unbreakable skeleton and claws. Treated like an animal, it took years for him to
                control himself. Now, he's a premiere member of both the X-Men and the Avengers.",
"thumbnail": {
    "path": "http://i.annihil.us/u/prod/marvel/i/mg/2/60/537bcaef0f6cf",
    "extension": "jpg"
}
```


To view the data :

Access DB : http://127.0.0.1:9000/marvel/h2

Once you connect H2 database using console, you can see the tables created

See the record count in DB :- SELECT count(*) FROM MARVEL_CHARACTER ;