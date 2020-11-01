export default class SefariaTextsApiRequest{

    private book: string;
    private chapter: number;

    constructor(book: string, chapter: number) {
        this.book = book;
        this.chapter = chapter;
    }

}