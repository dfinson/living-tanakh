<template >
  <v-app >
    <v-main >


        <!-- left hand container column-->
        <v-row>
          <v-col

              md="3"
              lg="3"
              xl="3"


          >

            <div class="text-xs-center">
              <h5 class="stacks_in_352_hdr text-muted  ">TANACH VISUAL DATABASE</h5>
            </div> <!-- column header-->


            <div >
              <a href="http://www.foundationstone.org/page-5/" style="text-decoration: none">
                <img src="https://maayan-assets.s3.eu-central-1.amazonaws.com/MaayanLogo.jpeg" class="img-fluid card-img-top" alt="Image">
              </a>
            </div>
              <!-- Special Circular image overlapped on top of the top image. -->
              <!-- Only displays if the top image is visible. -->

            <div>
              <div class="card-block">

                  <h3 class="stacks_in_255_hdr theme_style  ">Ma'ayan Home Page</h3>

                <p class='stacks_in_250 card-text   text-xs-left ' >
                  Welcome to the Ma'ayan demo page. Ma'ayan is being developed as the premier visual companion for the learning, teaching and development of TANACH. Click the link below to add your comments - we value your input!
                </p>
                <a href="http://sefaria.org" rel="" onclick="" target=""  class="card-link  " style="text-decoration: none">Sefaria</a>
                <a href="http://www.foundationstone.org" rel="" onclick="" target=""  class="card-link " style="text-decoration: none">Foundation Stone</a>
                <a href="http://www.foundationstone.org/page-5/" rel="" onclick="" target=""  class="card-link  " style="text-decoration: none">Ma'ayan</a>
              </div>
            </div><!-- column header-->
            <!-- search form-->
            <!--<div id='stacks_out_372' class='stacks_out' style="overflow-y: scroll; max-height: 600px">-->

              <b-tabs type="is-toggle" expanded v-model="activeTab">
                <b-tab-item  icon="magnify"   animated="true">
                  <search-controller
                      @display-selected-chapter="sendChapterToChapterDisplay($event)"
                      @send-search-term-to-dashboard="sendSearchTermToChapterDisplayAndMediaTagModal($event)"
                      @send-selected-verse-to-passuk-display="sendVerseToPassukDisplayAndSendTagIdToMediaComponentFromSearchResultItem($event)"
                      @change-trop = changeTrop($event)
                      :selected-verse-numeral="selectedVerse.hebrewNumeral"
                      @update-search-term="updateSearchTerm($event)"
                      @send-search-results-to-dashboard="sendToSearchResultsList($event)"
                      :path-arr="pathArr"
                  ></search-controller>
                </b-tab-item>
                <b-tab-item  icon="google-photos" style="text-decoration: none" animated="true" :disabled="disableSelectedImagesTab">
                  <preview-selector ref="myChild"
                      :selected-image="this.selectedImage"
                      @uncheck-all-images="uncheckAllImages"
               ></preview-selector>
                </b-tab-item>
              </b-tabs>


            <!--</div>-->
          </v-col>


          <!-- the right hand column-->
          <v-col


                 md="9"
              lg="9"
              xl="9"            class="grey darken-4" >
            <section class="fc-content-area color-picker" >
              <!-- just a header for left column-->

              <!-- chapter display component-->

              <div id='stacks_out_68' class='stacks_out' style="margin-top: 5px">
                <h1 v-if="selectedChapter !== undefined" style="font-size:20px; margin-right: 5px;
         'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:lightseagreen; text-align: center; margin-bottom:1px;" >{{selectedChapter.book.hebrewName + " " + selectedChapter.hebrewNumeral.replace(/['"]+/g, '') + colon}}<span>{{selectedVerse.hebrewNumeral.replace(/['"]+/g, '')}}</span></h1>





               <!-- <div id='stacks_in_68' class='stacks_in com_stacks4stacks_stacks_fontstack_stack'>!-->
                <div>
                  <div >

                        <div id="scrollbar_stacks_in_70"  style="height: 135px; overflow: auto ;direction: ltr; margin-right: 5px" >



                            <!-- chapter display-->
                            <chapter-display class="chapterD" style="background-color: rgba(51, 51, 51, 1.00);"
                                             :displayTrop="displayTrop"
                                             :selected-chapter="this.selectedChapter"
                                             :search-term="this.searchTerm"
                                             @send-passuk-to-passuk-display="sendVerseToPassukDisplayAndSendTagIdToMediaComponentOfVerseFromChapter($event)"
                                             @trop-changed="displayTrop = !displayTrop"
                            ></chapter-display>







                    </div>
                  </div>
                </div>
              </div>


              <!-- verse and media display components-->

                      <passuk-display dir="rtl" style="background-color: lightgrey"
                          :displayTrop="displayTrop"
                          :selected-verse="selectedVerse"
                          @send-tag-to-dashboard="sendTagToMediaPresenter($event)"
                      ></passuk-display>

              <!--search_Results_List component - is minimized if there are images loaded into the gallery component, but can always be expanded-->



                    <div id='stacks_out_193' class='stacks_out'  >

                      <!-- search Results List will be automatically minimized if a result with associated images is pressed-->
                    <v-row justify="center" style="margin-top: 10px" v-if="resultsWindowActive">

                      <v-card height="700" style="overflow-y: scroll; overflow-x: hidden">
                        <v-row justify="center">
                        <v-card-title style="color: darkcyan">
                          {{freeTextSearchResultsVerseArray.length.toString() + " Result/s:"}}
                        </v-card-title>
                    </v-row>
                        <search-results-list style="flex-shrink: 2"
                                             :search-results="freeTextSearchResultsVerseArray"
                                             :display-trop-to-search-result="displayTrop"
                                             @result-selected="sendSearchSelectionToController($event)"
                        ></search-results-list>
                      </v-card>
                    </v-row>


                      <!-- media display component-->
                          <media-tag-modal ref="mediaTagModalRef" v-if="!resultsWindowActive"
                              :tag-ids="tagIds"
                              :passuk-selected-from-chapter-display="passukSelectedFromChapterDisplay"
                              :search-term="searchTerm"
                              :selected-media-tag-id="selectedMediaTagId"
                              @send-image-to-preview-selector="sendImageToPreviewSelector($event)"
                              @remove-image-from-preview-selector="removeImageFromPreviewSelector($event)"
                              :remove-checked-images="removeCheckedImages"
                          ></media-tag-modal>


                  </div>




            </section>
          </v-col>
        </v-row>

    </v-main>
  </v-app>
</template>

<script lang = "ts">

/*
  Overview:

    Is the primary UI container for this application.
    Consists of a search & navigation bar,
    as well as a main text display area.

  Children:
    - SearchController
    - ChapterDisplay
    - AggregatedMediaDisplay (popup / modal)

  Parents:
    - App

  props:
    None

  emits:
    None

  data:
    - chapter: Chapter
    - highlightedVersePath: string
    - searchCriteria: SearchCriteria
*/

import { Component, Vue } from 'vue-property-decorator';
import SearchController from "@/Components/search/SearchController.vue";
import ChapterDisplay from "@/Components/search/ChapterDisplay.vue";
import MediaTagModal from "@/Components/MediaComponents/MediaTagModal.vue";
import {Chapter, GalleriaImageItem, PrefixedVerseSegment, SearchCriteria, Verse} from "@/api/dto";
import BaseCard from "@/Components/BaseComponents/BaseCard.vue";
import PassukDisplay from "@/Components/search/PassukDisplay.vue";
import apifiClient from "@/api/apifiClient";
import {PROPHETS, TORAH} from "@/api/TANAKH";
import PreviewSelector from "@/Components/search/PreviewSelector.vue";
import SearchResultsList from "@/Components/search/SearchResultsList.vue";

@Component({
  components: {
    SearchResultsList,
    PreviewSelector,
    ChapterDisplay,
    SearchController,
    BaseCard,
    MediaTagModal,
    PassukDisplay
  }
})

/* id: number;
    mediaTag?: MediaTag;
    mediaType?: MediaType;
    description?: string;
    key?: string;
    signedDownloadUrl?: string;
    signedUploadUrl?: string;
    mimeType?: string;
    signedThumbnailUrl?: string;

       this.itemImageSrc = mediaContent.signedDownloadUrl ? mediaContent.signedDownloadUrl: '';
        this.thumbnailImageSrc = this.itemImageSrc;
        this.alt = mediaContent.key ? mediaContent.key : '';
        this.title = this.alt;
        this.description = mediaContent.description ? mediaContent.description : '';
    */
export default class Dashboard extends Vue{

  //region Members
  private displayMediaTagModal = false;
  public selectedMediaTagId = 0;
  public displayTrop = true;
  public tagIds: number[] = [];
  public colon = "";
  public selectedImage = new GalleriaImageItem({
    id:0,
    signedDownloadUrl: "",
    key:"",
    description:""

  });
  public activeTab = 0;
  public disableSelectedImagesTab = true;

  /*variables associated with the results of searches:*/
  public freeTextSearchResultsVerseArray: Verse[] = [];
  public isLoading = false;

  public pathArr: string[] = [];
  public searchCriteria: SearchCriteria;
  public selectedChapter = new Chapter();
  public searchTerm = "";
  public selectedVerse = new Verse();
  public passukSelectedFromChapterDisplay = false;
  public removeCheckedImages = false;
  //endregion

  //region Methods
  public updateSearchTerm(searchTerm: string){
    this.searchTerm = searchTerm;
  }

  public sendSearchSelectionToController(pathArr: string[]): void{
    //pathArr is passed as prop to searchController, which has a watcher set waiting for changes
    this.pathArr = [];
    for (const i of pathArr) {
      this.pathArr.push(i);
    }
  }

  private sendToSearchResultsList(searchResultsArray: Verse[]): void {
    searchResultsArray.forEach((verse: Verse) => {
      verse.highlightedVerseSegments.segments.forEach((segment: PrefixedVerseSegment, index: number) => segment.id = index)
      this.freeTextSearchResultsVerseArray.push(verse)
    })
    this.resultsWindowActive = true;
  }




  private sendTagToMediaPresenter(id: number): void{
    this.selectedMediaTagId = id;
    this.passukSelectedFromChapterDisplay = true;
  }

  public uncheckAllImages(): void{
    (this.$refs.mediaTagModalRef as any).uncheckAllImages();
  }

  public sendChapterToChapterDisplay(selectedChapter: Chapter): void{
    this.selectedChapter = selectedChapter;
    this.resultsWindowActive = false;
    this.colon = "";
    const temp = [];
    this.selectedVerse.hebrewNumeral = "";
    this.selectedVerse.highlightedVerseSegments.segments = [];
    this.selectedVerse.highlightedVerseSegments.plainHebrewFinalSuffix = "";
    this.selectedVerse.highlightedVerseSegments.finalSuffix = "";
    this.tagIds = [];
    if(this.selectedChapter !== undefined) {
      for (let i = 0; i < this.selectedChapter.verses.length; i++) {
        for (let j = 0; j < this.selectedChapter.verses[i].mediaTags.length; j++) {
          temp.push(this.selectedChapter.verses[i].mediaTags[j].id)
        }
      }
      const unique = temp.filter(function(elem, index, self) {
        return index === self.indexOf(elem);
      })
      for(let i = 0; i < unique.length; i++)
        this.tagIds.push(unique[i]);
      console.log(this.tagIds);
    }




  }


  public sendSearchTermToChapterDisplayAndMediaTagModal(searchTerm: string): void{
    this.searchTerm = searchTerm;
    console.log(this.searchTerm + "dashboard");
  }


  public resultsWindowActive = false;

  //this function will also send the media components the tags in this selected verse.
  public sendVerseToPassukDisplayAndSendTagIdToMediaComponentFromSearchResultItem(selectedVerse: Verse): void{
    this.tagIds = [];
    this.colon = ":"
    this.selectedMediaTagId = 0;
    this.passukSelectedFromChapterDisplay = false;
     console.log("sendVerseToPassukDisplayAndSendTagIdToMediaComponentFromSearchResultItem");
    this.selectedVerse = selectedVerse;
    console.log(this.selectedVerse + "from da")
    if(this.selectedVerse.mediaTags !== undefined) {
      //turn off the search results tab
      this.resultsWindowActive = false;
      for (const tag in this.selectedVerse.mediaTags) {
        this.tagIds.push(this.selectedVerse.mediaTags[tag].id);
      }
      console.log(this.tagIds + "from dash");
    }
   // (this.$refs.mediaTagModalRef as any).foo();
  }

  public sendVerseToPassukDisplayAndSendTagIdToMediaComponentOfVerseFromChapter(selectedVerse: Verse): void{
    this.tagIds = [];
    this.colon = ":"
    this.passukSelectedFromChapterDisplay = true;
    //this.searchTerm = '';
    this.selectedMediaTagId = 0;
    // console.log(selectedVerse);
    this.selectedVerse = selectedVerse;

    if(this.selectedVerse.mediaTags !== undefined) {
      //turn off the search results tab
      this.resultsWindowActive = false;
      for (const tag in this.selectedVerse.mediaTags) {
        this.tagIds.push(this.selectedVerse.mediaTags[tag].id);
      }

    }
  }

  public sendImageToPreviewSelector(image: GalleriaImageItem): void{
    this.selectedImage  = new GalleriaImageItem( {
      id:Math.floor(Math.random() * 100) ,
      signedDownloadUrl: image.itemImageSrc,
      key:image.alt,
      description:image.description
    });
    //console.log("dashboard recieved image:" + this.selectedImage);
    this.disableSelectedImagesTab = false;
    setTimeout(()=>{
      this.activeTab = 1;
    }, 1000);
  }

  public removeImageFromPreviewSelector(title: string): void{
    (this.$refs.myChild as any).deleteImage(title);
  }

  public changeTrop(trop: boolean): void{
    this.displayTrop = trop;
  }

  //give the selectedChapter and Verse a default value so that they will not be undefined
  created(){
    this.selectedChapter = {
      id: 0,
      path: "",
      number: 0,
      book: {
        hebrewName:"שלום",
        id: 0,
        path:"",
      },
      verses  : [
        {
          id: 0,
          path: "",
          number: 0,
          mediaTags:[{id:0}],
          fullHebrewText: "",
          searchableHebrewText: "",
          highlightedVerseSegments: {
            segments:[{
              prefix:"",
              highlightedKeyword:"",
              plainHebrewPrefix:"",
              plainHebrewHighlightedKeyword:"",
              tag:{
                id:0
              }
            }],
            finalSuffix:"",
            plainHebrewFinalSuffix:"",
          },
          humanReadablePath: "",
          hebrewNumeral: ""
        }
      ],
      hebrewNumeral: ""
    }
    this.selectedVerse = {
      humanReadablePath:"",
      highlightedVerseSegments:{
        segments:[{
          prefix:"",
          highlightedKeyword:"",
          plainHebrewPrefix:'',
          plainHebrewHighlightedKeyword:"",
          tag:{
            id:0
          }
        }],
        finalSuffix:"",
        plainHebrewFinalSuffix:""
      },
      fullHebrewText:"",
      hebrewNumeral:"",
      number:0,
      path:"",
      id:0,
      searchableHebrewText:"",
      mediaTags:[{
        id:0,
      }]
    }

  }


  //endregion

}

</script>

<style scoped>
::-webkit-scrollbar {
  width: 9px;


}

/* Track */
::-webkit-scrollbar-track {
  background: #171717;
  border-radius: 5px;

}

/* Handle */
::-webkit-scrollbar-thumb {
  background: #044f4d;
  border-radius: 5px;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: #184f4d;
}
</style>