let apiUrl = location.origin + '/graphql';
let includeCredentials = false;
let bearerToken: string;



// project specific client side API calls

export default{

	setBearerToken(token: string): void{
		bearerToken = token;
	},

	setApiUrl(url: string): void{
		apiUrl = url;
	},

	setIncludeCredentials(value: boolean): void{
		includeCredentials = value;
	},

	async findBookByUniquePath(path: string, selectionGraph: string, customHeaders?: Dictionary<string>): Promise<ExecutionResult<Book>>{
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit: RequestInit = {
				method: "POST",
				credentials: !!includeCredentials ? 'include' : 'omit',
				headers: requestHeaders,
				body: JSON.stringify({
					query: `query findBookByUniquePath($path: path) { findBookByUniquePath(path: $path)${selectionGraph} }`, 
					variables: {
						"path": path
					}
				})
			};
			return await (await fetch(apiUrl, requestInit)).json();
	},

	async findChapterByUniquePath(path: string, selectionGraph: string, customHeaders?: Dictionary<string>): Promise<ExecutionResult<Chapter>>{
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit: RequestInit = {
				method: "POST",
				credentials: !!includeCredentials ? 'include' : 'omit',
				headers: requestHeaders,
				body: JSON.stringify({
					query: `query findChapterByUniquePath($path: path) { findChapterByUniquePath(path: $path)${selectionGraph} }`, 
					variables: {
						"path": path
					}
				})
			};
			return await (await fetch(apiUrl, requestInit)).json();
	},

	async errorReports(input: PageRequest, selectionGraph: string, customHeaders?: Dictionary<string>): Promise<ExecutionResult<Page<ErrorReport>>>{
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit: RequestInit = {
				method: "POST",
				credentials: !!includeCredentials ? 'include' : 'omit',
				headers: requestHeaders,
				body: JSON.stringify({
					query: `query errorReports($input: PageRequestInput) { errorReports(input: $input)${selectionGraph} }`, 
					variables: {
						"input": input
					}
				})
			};
			return await (await fetch(apiUrl, requestInit)).json();
	},

	async createErrorReport(input: ErrorReport, selectionGraph: string, customHeaders?: Dictionary<string>): Promise<ExecutionResult<ErrorReport>>{
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit: RequestInit = {
				method: "POST",
				credentials: !!includeCredentials ? 'include' : 'omit',
				headers: requestHeaders,
				body: JSON.stringify({
					query: `mutation createErrorReport($input: ErrorReportInput) { createErrorReport(input: $input)${selectionGraph} }`, 
					variables: {
						"input": input
					}
				})
			};
			return await (await fetch(apiUrl, requestInit)).json();
	},

	async getMediaTagsByIds(input: Array<MediaTag>, selectionGraph: string, customHeaders?: Dictionary<string>): Promise<ExecutionResult<Array<MediaTag>>>{
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit: RequestInit = {
				method: "POST",
				credentials: !!includeCredentials ? 'include' : 'omit',
				headers: requestHeaders,
				body: JSON.stringify({
					query: `query getMediaTagsByIds($input: [Long]) { getMediaTagsByIds(input: $input)${selectionGraph} }`, 
					variables: {
						"input": input
					}
				})
			};
			return await (await fetch(apiUrl, requestInit)).json();
	},

	async createMediaTag(input: MediaTag, selectionGraph: string, customHeaders?: Dictionary<string>): Promise<ExecutionResult<MediaTag>>{
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit: RequestInit = {
				method: "POST",
				credentials: !!includeCredentials ? 'include' : 'omit',
				headers: requestHeaders,
				body: JSON.stringify({
					query: `mutation createMediaTag($input: MediaTagInput) { createMediaTag(input: $input)${selectionGraph} }`, 
					variables: {
						"input": input
					}
				})
			};
			return await (await fetch(apiUrl, requestInit)).json();
	},

	async updateMediaTag(input: MediaTag, selectionGraph: string, customHeaders?: Dictionary<string>): Promise<ExecutionResult<MediaTag>>{
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit: RequestInit = {
				method: "POST",
				credentials: !!includeCredentials ? 'include' : 'omit',
				headers: requestHeaders,
				body: JSON.stringify({
					query: `mutation updateMediaTag($input: MediaTagInput) { updateMediaTag(input: $input)${selectionGraph} }`, 
					variables: {
						"input": input
					}
				})
			};
			return await (await fetch(apiUrl, requestInit)).json();
	},

	async deleteMediaTag(input: MediaTag, selectionGraph: string, customHeaders?: Dictionary<string>): Promise<ExecutionResult<MediaTag>>{
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit: RequestInit = {
				method: "POST",
				credentials: !!includeCredentials ? 'include' : 'omit',
				headers: requestHeaders,
				body: JSON.stringify({
					query: `mutation deleteMediaTag($input: MediaTagInput) { deleteMediaTag(input: $input)${selectionGraph} }`, 
					variables: {
						"input": input
					}
				})
			};
			return await (await fetch(apiUrl, requestInit)).json();
	},

	async associateLinkedContentWithMediaTag(owner: MediaTag, input: Array<MediaContent>, selectionGraph: string, customHeaders?: Dictionary<string>): Promise<ExecutionResult<Array<MediaContent>>>{
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit: RequestInit = {
				method: "POST",
				credentials: !!includeCredentials ? 'include' : 'omit',
				headers: requestHeaders,
				body: JSON.stringify({
					query: `mutation associateLinkedContentWithMediaTag($owner: MediaTagInput, $input: [MediaContentInput]) { associateLinkedContentWithMediaTag(owner: $owner, input: $input)${selectionGraph} }`, 
					variables: {
						"owner": owner, 
						"input": input
					}
				})
			};
			return await (await fetch(apiUrl, requestInit)).json();
	},

	async updateLinkedContentOfMediaTag(owner: MediaTag, input: Array<MediaContent>, selectionGraph: string, customHeaders?: Dictionary<string>): Promise<ExecutionResult<Array<MediaContent>>>{
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit: RequestInit = {
				method: "POST",
				credentials: !!includeCredentials ? 'include' : 'omit',
				headers: requestHeaders,
				body: JSON.stringify({
					query: `mutation updateLinkedContentOfMediaTag($owner: MediaTagInput, $input: [MediaContentInput]) { updateLinkedContentOfMediaTag(owner: $owner, input: $input)${selectionGraph} }`, 
					variables: {
						"owner": owner, 
						"input": input
					}
				})
			};
			return await (await fetch(apiUrl, requestInit)).json();
	},

	async removeLinkedContentFromMediaTag(owner: MediaTag, input: Array<MediaContent>, selectionGraph: string, customHeaders?: Dictionary<string>): Promise<ExecutionResult<Array<MediaContent>>>{
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit: RequestInit = {
				method: "POST",
				credentials: !!includeCredentials ? 'include' : 'omit',
				headers: requestHeaders,
				body: JSON.stringify({
					query: `mutation removeLinkedContentFromMediaTag($owner: MediaTagInput, $input: [MediaContentInput]) { removeLinkedContentFromMediaTag(owner: $owner, input: $input)${selectionGraph} }`, 
					variables: {
						"owner": owner, 
						"input": input
					}
				})
			};
			return await (await fetch(apiUrl, requestInit)).json();
	},

	async associateMediaTagsWithVerse(owner: Verse, input: Array<MediaTag>, selectionGraph: string, customHeaders?: Dictionary<string>): Promise<ExecutionResult<Array<MediaTag>>>{
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit: RequestInit = {
				method: "POST",
				credentials: !!includeCredentials ? 'include' : 'omit',
				headers: requestHeaders,
				body: JSON.stringify({
					query: `mutation associateMediaTagsWithVerse($owner: VerseInput, $input: [MediaTagInput]) { associateMediaTagsWithVerse(owner: $owner, input: $input)${selectionGraph} }`, 
					variables: {
						"owner": owner, 
						"input": input
					}
				})
			};
			return await (await fetch(apiUrl, requestInit)).json();
	},

	async updateMediaTagsOfVerse(owner: Verse, input: Array<MediaTag>, selectionGraph: string, customHeaders?: Dictionary<string>): Promise<ExecutionResult<Array<MediaTag>>>{
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit: RequestInit = {
				method: "POST",
				credentials: !!includeCredentials ? 'include' : 'omit',
				headers: requestHeaders,
				body: JSON.stringify({
					query: `mutation updateMediaTagsOfVerse($owner: VerseInput, $input: [MediaTagInput]) { updateMediaTagsOfVerse(owner: $owner, input: $input)${selectionGraph} }`, 
					variables: {
						"owner": owner, 
						"input": input
					}
				})
			};
			return await (await fetch(apiUrl, requestInit)).json();
	},

	async removeMediaTagsFromVerse(owner: Verse, input: Array<MediaTag>, selectionGraph: string, customHeaders?: Dictionary<string>): Promise<ExecutionResult<Array<MediaTag>>>{
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit: RequestInit = {
				method: "POST",
				credentials: !!includeCredentials ? 'include' : 'omit',
				headers: requestHeaders,
				body: JSON.stringify({
					query: `mutation removeMediaTagsFromVerse($owner: VerseInput, $input: [MediaTagInput]) { removeMediaTagsFromVerse(owner: $owner, input: $input)${selectionGraph} }`, 
					variables: {
						"owner": owner, 
						"input": input
					}
				})
			};
			return await (await fetch(apiUrl, requestInit)).json();
	},

	async verseFreeTextSearch(input: FreeTextSearchPageRequest, selectionGraph: string, customHeaders?: Dictionary<string>): Promise<ExecutionResult<Page<Verse>>>{
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit: RequestInit = {
				method: "POST",
				credentials: !!includeCredentials ? 'include' : 'omit',
				headers: requestHeaders,
				body: JSON.stringify({
					query: `query verseFreeTextSearch($input: FreeTextSearchPageRequestInput) { verseFreeTextSearch(input: $input)${selectionGraph} }`, 
					variables: {
						"input": input
					}
				})
			};
			return await (await fetch(apiUrl, requestInit)).json();
	},

	async findVerseByUniquePath(path: string, selectionGraph: string, customHeaders?: Dictionary<string>): Promise<ExecutionResult<Verse>>{
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit: RequestInit = {
				method: "POST",
				credentials: !!includeCredentials ? 'include' : 'omit',
				headers: requestHeaders,
				body: JSON.stringify({
					query: `query findVerseByUniquePath($path: path) { findVerseByUniquePath(path: $path)${selectionGraph} }`, 
					variables: {
						"path": path
					}
				})
			};
			return await (await fetch(apiUrl, requestInit)).json();
	},

}

// project specific data model

export interface Book{
	hebrewName?: string;
	path?: string;
	englishName?: string;
	chapters?: Array<Chapter>;
	id?: number;
	tanakhSection?: TanakhSection;
}

export interface Chapter{
	hebrewNumeral?: string;
	path?: string;
	number?: number;
	book?: Book;
	id?: number;
	verses?: Array<Verse>;
}

export interface ErrorReport{
	occurredAt?: Date;
	stacktrace?: string;
	id?: number;
	message?: string;
}

export interface MediaContent{
	mimeType?: string;
	mediaTag?: MediaTag;
	description?: string;
	mediaType?: MediaType;
	id?: number;
	signedDownloadUrl?: string;
	key?: string;
	signedUploadUrl?: string;
}

export interface MediaTag{
	appliedScopePathPrefixes?: Array<string>;
	description?: string;
	id?: number;
	key?: string;
	verses?: Set<Verse>;
	linkedContent?: Set<MediaContent>;
}

export interface Verse{
	hebrewNumeral?: string;
	humanReadablePath?: string;
	path?: string;
	number?: number;
	chapter?: Chapter;
	fullHebrewText?: string;
	fullEnglishText?: string;
	mediaTags?: Set<MediaTag>;
	id?: number;
	searchableHebrewText?: string;
	highlightedVerseSegments?: any;
}

export enum Writings{
	PSALMS,
	PROVERBS,
	JOB,
	RUTH,
	LAMENTATIONS,
	ECCLESIASTES,
	ESTHER,
	DANIEL,
	EZRA,
	NEHEMIAH,
	CHRONICLES
}

export enum MediaType{
	VIDEO,
	IMAGE,
	AUDIO,
	DOCUMENT,
	OTHER
}

export enum Prophets{
	JOSHUA,
	JUDGES_I,
	JUDGES_II,
	SAMUEL_I,
	SAMUEL_II,
	KINGS_I,
	KINGS_II,
	ISAIAH,
	JEREMIAH,
	EZEKIEL,
	HOSEA,
	JOEL,
	AMOS,
	OBADIAH,
	JONAH,
	MICHA,
	NAHUM,
	HABAKKUK,
	ZEPHANIAH,
	HAGGAI,
	ZECHARIAH,
	MALACHI
}

export enum TanakhSection{
	TORAH,
	PROPHETS,
	WRITINGS
}

export enum Torah{
	GENESIS,
	EXODUS,
	LEVITICUS,
	NUMBERS,
	DEUTERONOMY
}

// Apifi utils object model

// represents a subset of the overall data, corresponding to server side JPA pagination
export interface Page<T>{
   content?: Array<T>;
   totalPagesCount?: number;
   totalItemsCount?: number;
   pageNumber?: number;
   customValues?: Map<string, any>;
}

// input to specify desired pagination parameters
export interface PageRequest{
   pageNumber?: number;
   sortBy?: string;
   pageSize?: number;
   sortDirection?: SortDirection;
   fetchAll?: boolean;
}

// input to specify desired pagination parameters, as well as a string value for server side free text search
export interface FreeTextSearchPageRequest extends PageRequest{
   searchTerm: string;
}

// enum type to specify pagination sort ordering
export enum SortDirection{
   ASC = 'ASC',
   DESC = 'DESC'
}

// a wrapper around any return value from the GraphQL server
export interface ExecutionResult<T>{
   data?: T;
   errors?: Array<ExecutionResultError>;
}

// should be fairly self explanatory
export interface ExecutionResultError{
   message: string;
   path?: Array<string>;
   locations?: Array<ExecutionResultErrorLocation>;
   extensions?: Map<string, any>;
}

// should be fairly self explanatory
export interface ExecutionResultErrorLocation{
   line: number;
   column: number;
}

// for custom headers to attach to query or mutation HTTP requests
export interface Dictionary<T>{
   [Key: string]: T;
}