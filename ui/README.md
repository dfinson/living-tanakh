# Tanach media engine UI

### Sefaria API Data Model
The data model is presented in typescript but implemted in Java 
on the backend as well. The idea here is to have a single reference point for all aspects of our data model to avoid mismatches between the front and back ends.

```ts
interface SefariaTextsApiRequest{
    book: string;
    chapter: number;
}
```

```ts
interface SefariaTextsApiResponse {
    hebrewLocationReference: string;
    englishLocationReference: string;
    hebrewText: string;
    englishText: string;
}
```

