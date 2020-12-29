<template>
  <div class="fc-inner-wrapper">


    <!-- left hand container column-->
    <section class="fc-sidebar color-picker">
      <div class="text-xs-center">
        <h5 class="stacks_in_352_hdr text-muted  ">TANACH VISUAL DATABASE</h5>
      </div> <!-- column header-->
      <div class="stacks_in_249-foundry-card card  ">
        <a href="http://www.foundationstone.org/page-5/">
        <img src="https://maayan-assets.s3.eu-central-1.amazonaws.com/MaayanLogo.jpeg" class="img-fluid card-img-top" alt="Image">
        </a>
        <!-- Special Circular image overlapped on top of the top image. -->
        <!-- Only displays if the top image is visible. -->
        <div class="card-block">
          <div class="text-xs-left">
            <h3 class="stacks_in_255_hdr theme_style  ">Ma'ayan Home Page</h3>
          </div>
          <p class='stacks_in_250 card-text   text-xs-left ' >
            Welcome to the Ma'ayan demo page. Ma'ayan is being developed as the premier visual companion for the learning, teaching and development of TANACH. Click the link below to add your comments - we value your input!
          </p>
          <a href="http://sefaria.org" rel="" onclick="" target=""  class="card-link  ">Sefaria</a>
          <a href="http://www.foundationstone.org" rel="" onclick="" target=""  class="card-link  ">Foundation Stone</a>
          <a href="http://www.foundationstone.org/page-5/" rel="" onclick="" target=""  class="card-link  ">Ma'ayan</a>
        </div>
      </div><!-- column header-->

      <!-- search form-->
      <div id='stacks_out_372' class='stacks_out'>
        <b-tabs type="is-toggle" expanded v-model="activeTab">
          <b-tab-item label="Search Tab"  animated="true">
            <search-controller
                    @display-selected-chapter="sendChapterToChapterDisplay($event)"
                    @send-search-term-to-dashboard="sendSearchTermToChapterDisplay($event)"
                    @send-selected-verse-to-passuk-display="sendVerseToPassukDisplayAndSendTagIdToMediaComponent($event)"
                    @change-trop = changeTrop($event)
                    :selected-verse-numeral="selectedVerse.hebrewNumeral"
            ></search-controller>
          </b-tab-item>
          <b-tab-item label="Selected Images" animated="true" :disabled="disableSelectedImagesTab">
            <preview-selector
                    :selected-image="this.selectedImage"
                    :image-to-be-deleted="imageToBeDeleted"
            ></preview-selector>
          </b-tab-item>
        </b-tabs>



        <!-- the social media icon badges  -->

      </div>

    </section>


    <!-- the right hand column-->
    <section class="fc-content-area color-picker">
      <!-- just a header for left column-->
      <div id='stacks_out_347' class='stacks_out'>
        <div id='stacks_in_347' class='stacks_in com_elixir_stacks_foundryParagraph_stack'>
          <p class='foundry-paragraph-override theme_style  theme_style  text-xs-center '>
            <span style="color:#FEFEFE;">Click on a passuk to select. Your choice will appear in its own window below:</span>

          </p>
        </div>
      </div>
      <!-- chapter display component-->
      <div id='stacks_out_68' class='stacks_out'>
        <h1 v-if="selectedChapter !== undefined" style="font-size:20px; margin-right: 5px;
         'Lucida Grande', LucidaGrande, Verdana, sans-serif; color:lightseagreen; text-align: center; margin-bottom:1px;" >{{selectedChapter.book.hebrewName + " " + selectedChapter.hebrewNumeral + colon}}<span>{{selectedVerse.hebrewNumeral}}</span></h1>

        <div id='stacks_in_68' class='stacks_in com_stacks4stacks_stacks_fontstack_stack'>
          <div id="fontStackstacks_in_68" class="fontStack" >
            <div id='stacks_out_70' class='stacks_out'  >
              <div id='stacks_in_70' class='stacks_in com_cosculture_stack_scrollbar_1_stack'>
                <div id="scrollbar_stacks_in_70"  style="height: 135px; overflow: auto ;direction: ltr;" >
                      <div id='stacks_out_72' class='stacks_out'  >

                    <!-- chapter display-->
                    <chapter-display class="chapterD"
                            :displayTrop="displayTrop"
                            :selected-chapter="this.selectedChapter"
                            :search-term="this.searchTerm"
                            @send-passuk-to-passuk-display="sendVerseToPassukDisplayAndSendTagIdToMediaComponent($event)"
                             @trop-changed="displayTrop = !displayTrop"
                     ></chapter-display>
              </div>
                    </div>

                  </div>
                </div>
              </div>
        </div>
       </div>

      <!-- verse and media display components-->
      <div id='stacks_out_91' class='stacks_out'>

        <div id='stacks_in_91' class='stacks_in com_stacks4stacks_stacks_fontstack_stack'>
          <div id="fontStackstacks_in_91" class="fontStack ">

            <!-- verse display component-->
            <div id='stacks_out_95' class='stacks_out' >
              <passuk-display
                      :displayTrop="displayTrop"
                      :selected-verse="selectedVerse"
                      @send-tag-to-dashboard="sendTagToMediaPresenter($event)"
              ></passuk-display>

            </div>
            <!-- large media display component-->
            <div id='stacks_out_193' class='stacks_out' >
              <div id='stacks_in_193' class='stacks_in com_elixir_stacks_foundryImage_stack'>
                <div class="text-xs-center foundry-image-wrapper" >
                <media-tag-modal
                 :tag-ids="tagIds"
                 :selected-media-tag-id="selectedMediaTagId"
                 @send-image-to-preview-selector="sendImageToPreviewSelector($event)"
                 @remove-image-from-preview-selector="removeImageFromPreviewSelector($event)"
                ></media-tag-modal>
                </div>

              </div>

            </div>
            <!-- small media display components-->
           <!-- <div id='stacks_out_162' class='stacks_out'>
              <div id='stacks_in_162' class='stacks_in com_elixir_stacks_foundryGallery_stack'>
                <div style="display: block" class="gallery-wrapper">
                  <div class="gallery_wrapper">
                    <div class="row ">
                      <div class="gallery_column">
                        <span class="gallery-indicator"><i class="fa fa-fw fa-search"></i></span>
                        <a href="files/gallery_image-167.png" data-caption="">
                          <img>
                        </a>
                      </div>
                      <div class="gallery_column">
                        <span class="gallery-indicator"><i class="fa fa-fw fa-search"></i></span>
                        <a href="files/gallery_image-166.png" data-caption="">
                          <img>
                        </a>
                      </div>
                      <div class="gallery_column">
                        <span class="gallery-indicator"><i class="fa fa-fw fa-search"></i></span>
                        <a href="files/gallery_image-165.png" data-caption="">
                          <img>
                        </a>
                      </div>
                      <div class="gallery_column">
                        <span class="gallery-indicator"><i class="fa fa-fw fa-search"></i></span>
                        <a href="files/gallery_image-163.png" data-caption="">
                          <img>
                        </a>
                      </div>
                      <div class="gallery_column">
                        <span class="gallery-indicator"><i class="fa fa-fw fa-search"></i></span>
                        <a href="files/gallery_image-168.png" data-caption="">
                          <img>
                        </a>
                      </div>
                      <div class="gallery_column">
                        <span class="gallery-indicator"><i class="fa fa-fw fa-search"></i></span>
                        <a href="files/gallery_image-171.png" data-caption="">
                          <img>
                        </a>
                      </div>
                      <div class="gallery_column">
                        <span class="gallery-indicator"><i class="fa fa-fw fa-search"></i></span>
                        <a href="files/gallery_image-169.png" data-caption="">
                          <img>
                        </a>
                      </div>
                      <div class="gallery_column">
                        <span class="gallery-indicator"><i class="fa fa-fw fa-search"></i></span>
                        <a href="files/gallery_image-170.png" data-caption="">
                          <img src="">
                        </a>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>-->
          </div>

        </div>

      </div>

    </section>

  </div>
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
import {Chapter, GalleriaImageItem, SearchCriteria, Verse} from "@/api/dto";
import BaseCard from "@/Components/BaseComponents/BaseCard.vue";
import PassukDisplay from "@/Components/search/PassukDisplay.vue";
import apifiClient from "@/api/apifiClient";
import {PROPHETS, TORAH} from "@/api/TANAKH";
import PreviewSelector from "@/Components/search/PreviewSelector.vue";

@Component({
  components: {
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

  public imageToBeDeleted = new GalleriaImageItem({
    id:0,
    signedDownloadUrl: "",
    key:"",
    description:""

  });
  public searchCriteria: SearchCriteria;
  public selectedChapter = new Chapter();
  public searchTerm = "";
  public selectedVerse = new Verse();

  //endregion

  //region Methods
  private sendTagToMediaPresenter(id: number): void{
    this.selectedMediaTagId = id;
  }

  public sendChapterToChapterDisplay(selectedChapter: Chapter): void{
    this.selectedChapter = selectedChapter;
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
          //this.tagIds.push(this.selectedChapter.verses[i].mediaTags[j].id);
         // console.log(this.selectedChapter.verses[i].mediaTags[j].id);
        }
      }
      const unique = temp.filter(function(elem, index, self) {
        return index === self.indexOf(elem);
      })
     for(let i = 0; i < unique.length; i++)
       this.tagIds.push(unique[i]);
     //console.log(this.tagIds);
    }


  }


  public sendSearchTermToChapterDisplay(searchTerm: string): void{
    this.searchTerm = searchTerm;
    console.log(this.searchTerm + "dashboard");
  }


  //this function will also send the media components the tags in this selected verse.
  public sendVerseToPassukDisplayAndSendTagIdToMediaComponent(selectedVerse: Verse): void{
    this.tagIds = [];
    this.colon = ":"
    this.selectedMediaTagId = 0;
   // console.log(selectedVerse);
    this.selectedVerse = selectedVerse;
    if(this.selectedVerse.mediaTags !== undefined) {
      for (const tag in this.selectedVerse.mediaTags) {
        this.tagIds.push(this.selectedVerse.mediaTags[tag].id);}
    }
      }

  public sendImageToPreviewSelector(image: GalleriaImageItem): void{
     this.selectedImage  = new GalleriaImageItem( {
       id:Math.floor(Math.random() * 100) ,
       signedDownloadUrl: image.itemImageSrc,
       key:image.alt,
       description:image.description
     });
      console.log("dashboard recieved image:" + this.selectedImage);
      this.disableSelectedImagesTab = false;
    setTimeout(()=>{
      this.activeTab = 1;
    }, 1000);
    }

    public removeImageFromPreviewSelector(image: GalleriaImageItem): void{
      this.imageToBeDeleted  = new GalleriaImageItem( {
        id:Math.floor(Math.random() * 100) ,
        signedDownloadUrl: image.itemImageSrc,
        key:image.alt,
        description:image.description
      });
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

<style >
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