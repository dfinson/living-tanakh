/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 2.24.612 on 2020-11-02 19:35:13.

export class Book {
  id: number;
  path: string;
  englishName?: string;
  hebrewName: string;
  chapters?: Chapter[];
}

export class Chapter {
  id: number;
  path: string;
  number: number;
  book?: Book;
  verses: Verse[];
  hebrewNumeral: string;
}

export class Verse {
  id: number;
  path: string;
  number: number;
  chapter?: Chapter;
  mediaTags: MediaTag[];
  fullHebrewText: string;
  fullEnglishText?: string;
  searchableHebrewText: string;
  highlightedVerseSegments: HighlightedVerseSegments;
  humanReadablePath: string;
  hebrewNumeral: string;
}

export class MediaTag {
  id: number;
  key?: string;
  description?: string;
  verses?: Verse[];
  linkedContent?: MediaContent[];
}

export class HighlightedVerseSegments {
  segments: PrefixedVerseSegment[];
  finalSuffix: string;
  plainHebrewFinalSuffix?: string;
}

export class MediaContent {
  id: number;
  mediaTag?: MediaTag;
  mediaType?: MediaType;
  description?: string;
  key?: string;
  signedDownloadUrl?: string;
  signedUploadUrl?: string;
  mimeType?: string;
  signedThumbnailUrl?: string;
}
export class PrefixedVerseSegment {
  prefix: string;
  highlightedKeyword: string;
  tag: MediaTag;
  plainHebrewPrefix?: string;
  plainHebrewHighlightedKeyword?: string;
  id?: number;
}

export class SearchCriteria {
  searchTerm: string;
  category: string[];
  book: string | null;
  chapter: string;
  passuk: string;
}
export class GalleriaImageItem {
  constructor(mediaContent: MediaContent) {
    this.itemImageSrc = mediaContent.signedDownloadUrl
      ? mediaContent.signedDownloadUrl
      : "";
    this.thumbnailImageSrc = this.itemImageSrc;
    this.alt = mediaContent.key ? mediaContent.key : "";
    this.title = this.alt;
    this.description = mediaContent.description ? mediaContent.description : "";
    this.toBeDownloaded = false;
  }

  itemImageSrc: string;
  thumbnailImageSrc: string;
  alt: string;
  title: string;
  description: string;
  toBeDownloaded?: boolean;

  /*constructor(img: GalleriaImageItem) {
        this.itemImageSrc = img.itemImageSrc;
        this.thumbnailImageSrc = img.thumbnailImageSrc;
        this.toBeDownloaded = img.toBeDownloaded;
        this.alt = img.alt;
        this.description = img.description;
        this.title - img.title;
    }*/
}

export class GalleriaResponsiveOption {
  constructor(breakpoint: string, numVisible: number) {
    this.breakpoint = breakpoint;
    this.numVisible = numVisible;
  }

  breakpoint: string;
  numVisible: number;
}

export enum MediaType {
  VIDEO = "VIDEO",
  IMAGE = "IMAGE",
  AUDIO = "AUDIO",
  DOCUMENT = "DOCUMENT",
  OTHER = "OTHER",
}

export class SortingObject {
  verse: Verse;
  categoryNumber: number;
}
