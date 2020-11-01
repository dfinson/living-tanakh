let apiUrl = location.origin + '/graphql';
let bearerToken = undefined;

export default{

	setBearerToken(token){
		bearerToken = token;
	},

	setApiUrl(url){
		apiUrl = url;
	},

	async associateMediaTagsWithVerse(owner, input, expectedReturn, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders !== undefined) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken !== undefined) requestHeaders["Authorization"] = bearerToken;
			let opts = {
				method: "POST",
				credentials: "include",
				headers: requestHeaders,
				body: JSON.stringify({
					query: `mutation associateMediaTagsWithVerse($owner: VerseInput, $input: [MediaTagInput]) { associateMediaTagsWithVerse(owner: $owner, input: $input)${expectedReturn} }`, 
					variables: {
						"owner": owner, 
						"input": input
					}, 
					operationName: "associateMediaTagsWithVerse"
				})
			};
			return await (await fetch(apiUrl, opts)).json();
	},

	async updateMediaTagsOfVerse(owner, input, expectedReturn, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders !== undefined) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken !== undefined) requestHeaders["Authorization"] = bearerToken;
			let opts = {
				method: "POST",
				credentials: "include",
				headers: requestHeaders,
				body: JSON.stringify({
					query: `mutation updateMediaTagsOfVerse($owner: VerseInput, $input: [MediaTagInput]) { updateMediaTagsOfVerse(owner: $owner, input: $input)${expectedReturn} }`, 
					variables: {
						"owner": owner, 
						"input": input
					}, 
					operationName: "updateMediaTagsOfVerse"
				})
			};
			return await (await fetch(apiUrl, opts)).json();
	},

	async removeMediaTagsFromVerse(owner, input, expectedReturn, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders !== undefined) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken !== undefined) requestHeaders["Authorization"] = bearerToken;
			let opts = {
				method: "POST",
				credentials: "include",
				headers: requestHeaders,
				body: JSON.stringify({
					query: `mutation removeMediaTagsFromVerse($owner: VerseInput, $input: [MediaTagInput]) { removeMediaTagsFromVerse(owner: $owner, input: $input)${expectedReturn} }`, 
					variables: {
						"owner": owner, 
						"input": input
					}, 
					operationName: "removeMediaTagsFromVerse"
				})
			};
			return await (await fetch(apiUrl, opts)).json();
	},

	async verseFreeTextSearch(input, expectedReturn, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders !== undefined) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken !== undefined) requestHeaders["Authorization"] = bearerToken;
			let opts = {
				method: "POST",
				credentials: "include",
				headers: requestHeaders,
				body: JSON.stringify({
					query: `query verseFreeTextSearch($input: FreeTextSearchPageRequestInput) { verseFreeTextSearch(input: $input)${expectedReturn} }`, 
					variables: {
						"input": input
					}, 
					operationName: "verseFreeTextSearch"
				})
			};
			return await (await fetch(apiUrl, opts)).json();
	},

	async findVerseByUniquePath(path, expectedReturn, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders !== undefined) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken !== undefined) requestHeaders["Authorization"] = bearerToken;
			let opts = {
				method: "POST",
				credentials: "include",
				headers: requestHeaders,
				body: JSON.stringify({
					query: `query findVerseByUniquePath($path: String!) { findVerseByUniquePath(path: $path)${expectedReturn} }`, 
					variables: {
						"path": path
					}, 
					operationName: "findVerseByUniquePath"
				})
			};
			return await (await fetch(apiUrl, opts)).json();
	},

	async associateLinkedContentWithMediaTag(owner, input, expectedReturn, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders !== undefined) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken !== undefined) requestHeaders["Authorization"] = bearerToken;
			let opts = {
				method: "POST",
				credentials: "include",
				headers: requestHeaders,
				body: JSON.stringify({
					query: `mutation associateLinkedContentWithMediaTag($owner: MediaTagInput, $input: [MediaContentInput]) { associateLinkedContentWithMediaTag(owner: $owner, input: $input)${expectedReturn} }`, 
					variables: {
						"owner": owner, 
						"input": input
					}, 
					operationName: "associateLinkedContentWithMediaTag"
				})
			};
			return await (await fetch(apiUrl, opts)).json();
	},

	async updateLinkedContentOfMediaTag(owner, input, expectedReturn, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders !== undefined) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken !== undefined) requestHeaders["Authorization"] = bearerToken;
			let opts = {
				method: "POST",
				credentials: "include",
				headers: requestHeaders,
				body: JSON.stringify({
					query: `mutation updateLinkedContentOfMediaTag($owner: MediaTagInput, $input: [MediaContentInput]) { updateLinkedContentOfMediaTag(owner: $owner, input: $input)${expectedReturn} }`, 
					variables: {
						"owner": owner, 
						"input": input
					}, 
					operationName: "updateLinkedContentOfMediaTag"
				})
			};
			return await (await fetch(apiUrl, opts)).json();
	},

	async removeLinkedContentFromMediaTag(owner, input, expectedReturn, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders !== undefined) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken !== undefined) requestHeaders["Authorization"] = bearerToken;
			let opts = {
				method: "POST",
				credentials: "include",
				headers: requestHeaders,
				body: JSON.stringify({
					query: `mutation removeLinkedContentFromMediaTag($owner: MediaTagInput, $input: [MediaContentInput]) { removeLinkedContentFromMediaTag(owner: $owner, input: $input)${expectedReturn} }`, 
					variables: {
						"owner": owner, 
						"input": input
					}, 
					operationName: "removeLinkedContentFromMediaTag"
				})
			};
			return await (await fetch(apiUrl, opts)).json();
	},

	async findChapterByUniquePath(path, expectedReturn, customHeaders){
			let requestHeaders = { "Content-Type": "application/json" }
			if(customHeaders !== undefined) requestHeaders = Object.assign({}, requestHeaders, customHeaders);
			if(bearerToken !== undefined) requestHeaders["Authorization"] = bearerToken;
			let opts = {
				method: "POST",
				credentials: "include",
				headers: requestHeaders,
				body: JSON.stringify({
					query: `query findChapterByUniquePath($path: String!) { findChapterByUniquePath(path: $path)${expectedReturn} }`, 
					variables: {
						"path": path
					}, 
					operationName: "findChapterByUniquePath"
				})
			};
			return await (await fetch(apiUrl, opts)).json();
	},

}