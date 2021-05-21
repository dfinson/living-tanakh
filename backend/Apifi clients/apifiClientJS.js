let apiUrl = location.origin + '/graphql';
let includeCredentials = false;
let bearerToken;



// project specific client side API calls

export default{

	setBearerToken(token){
		bearerToken = token;
	},

	setApiUrl(url){
		apiUrl = url;
	},

	setIncludeCredentials(value){
		includeCredentials = value;
	},

	async findBookByUniquePath(path, selectionGraph, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit = {
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

	async findChapterByUniquePath(path, selectionGraph, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit = {
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

	async errorReports(input, selectionGraph, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit = {
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

	async createErrorReport(input, selectionGraph, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit = {
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

	async getMediaTagsByIds(input, selectionGraph, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit = {
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

	async createMediaTag(input, selectionGraph, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit = {
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

	async updateMediaTag(input, selectionGraph, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit = {
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

	async deleteMediaTag(input, selectionGraph, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit = {
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

	async associateLinkedContentWithMediaTag(owner, input, selectionGraph, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit = {
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

	async updateLinkedContentOfMediaTag(owner, input, selectionGraph, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit = {
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

	async removeLinkedContentFromMediaTag(owner, input, selectionGraph, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit = {
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

	async associateMediaTagsWithVerse(owner, input, selectionGraph, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit = {
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

	async updateMediaTagsOfVerse(owner, input, selectionGraph, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit = {
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

	async removeMediaTagsFromVerse(owner, input, selectionGraph, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit = {
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

	async verseFreeTextSearch(input, selectionGraph, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit = {
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

	async findVerseByUniquePath(path, selectionGraph, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken) requestHeaders["Authorization"] = bearerToken;
			const requestInit = {
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