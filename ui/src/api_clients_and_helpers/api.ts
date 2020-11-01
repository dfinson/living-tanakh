import SefariaTextsApiRequest from "@/dto/SefariaTextsApiRequest";
import store from '@/store';


export default {
    async fetchText(input: SefariaTextsApiRequest){
        const options: RequestInit = {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Request-Method": "POST",
            },
            body: JSON.stringify({
                query: `
                    query sefariaText($input: SefariaTextsApiRequestInput) { 
                        sefariaText(input: $input){
                                hebrewLocationReference
                                englishLocationReference
                                hebrewText
                                englishText
                        }
                    }`,
                variables: {
                    "input": input
                }
            })
        };
        const url = store.state.textApiBaseUrl
        return await (await fetch(url, options)).json();
    }
}