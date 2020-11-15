/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 2.24.612 on 2020-11-02 19:35:13.

export class Book {
    id?: number;
    path?: string;
    englishName?: string;
    hebrewName?: string;
    chapters?: Chapter[];
}

export class Chapter {
    id?: number;
    path?: string;
    number?: number;
    book?: Book;
    verses?: Verse[];
    hebrewNumeral?: string;
}

export class Verse {
    id?: number;
    path?: string;
    number?: number;
    chapter?: Chapter;
    mediaTags?: MediaTag[];
    fullHebrewText?: string;
    fullEnglishText?: string;
    searchableHebrewText?: string;
    hebrewNumeral?: string;
}

export class MediaTag {
    id?: number;
    title?: string;
    description?: string;
    verses?: Verse[];
    linkedContent?: MediaContent[];
}

export class MediaContent {
    id?: number;
    mediaTag?: MediaTag;
    mediaType?: MediaType;
    name?: string;
    description?: string;
    storageKey?: string;
    signedDownloadUrl?: string;
    signedUploadUrl?: string;
}

export class SearchCriteria{
    searchTerm: string;
    category: string;
    book: string;
    chapter: number;
}

export enum MediaType {
    VIDEO = "VIDEO",
    IMAGE = "IMAGE",
    AUDIO = "AUDIO",
    DOCUMENT = "DOCUMENT",
    OTHER = "OTHER",
}
