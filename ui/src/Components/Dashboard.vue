<template>
  <div class="fc-inner-wrapper">


    <!-- left hand container column-->
    <section class="fc-sidebar color-picker">
      <div class="text-xs-center">
        <h5 class="stacks_in_352_hdr text-muted  ">TANACH VISUAL DATABASE</h5>
      </div> <!-- column header-->
      <div class="stacks_in_249-foundry-card card  ">
        <img src="https://maayan-assets.s3.eu-central-1.amazonaws.com/MaayanLogo.jpeg" class="img-fluid card-img-top" alt="Image">
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
        </div>
      </div><!-- column header-->

      <!-- search form-->
      <div id='stacks_out_372' class='stacks_out'>
        <search-controller
                @display-selected-chapter="sendChapterToChapterDisplay($event)"
                @send-search-term-to-dashboard="sendSearchTermToChapterDisplay($event)"
                @send-selected-verse-to-passuk-display="sendVerseToPassukDisplayAndSendTagIdToMediaComponent($event)"
                @clear-all-results="clearAllResults()"
        ></search-controller>

        <!-- the social media icon badges  -->

      </div>

    </section>


    <!-- the right hand column-->
    <section class="fc-content-area color-picker">
      <!-- just a header for left column-->
      <div id='stacks_out_347' class='stacks_out'>
        <div id='stacks_in_347' class='stacks_in com_elixir_stacks_foundryParagraph_stack'>
          <p class='foundry-paragraph-override theme_style  theme_style  text-xs-left '>
            <span style="color:#FEFEFE;">2. Click on a pasuk to select. Your choice will appear in its own window below:</span>
          </p>
        </div>
      </div>
      <!-- chapter display component-->
      <div id='stacks_out_68' class='stacks_out'>
        <div id='stacks_in_68' class='stacks_in com_stacks4stacks_stacks_fontstack_stack'>
          <div id="fontStackstacks_in_68" class="fontStack ">
            <div id='stacks_out_70' class='stacks_out'>
              <div id='stacks_in_70' class='stacks_in com_cosculture_stack_scrollbar_1_stack'>
                <div id="scrollbar_stacks_in_70"  style="height: 135px; overflow: auto;" >
                  <div class="mCustomScrollBox mCS-light-2" id="style-1" style="position:relative; height:100%; max-width:100%;">
                    <div class="mCSB_container" >
                      <div id='stacks_out_72' class='stacks_out'>

                    <!-- chapter display-->
                    <chapter-display class="chapterD"
                            :displayTrop="displayTrop"
                            :selected-chapter="this.selectedChapter"
                            :search-term="this.searchTerm"
                            @send-tag-to-dashboard="handleMediaTagIdSelected($event)"
                     ></chapter-display>
              </div>
                    </div>

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
            <div id='stacks_out_95' class='stacks_out'>
              <passuk-display
                      :displayTrop="displayTrop"
                      :selected-verse="selectedVerse"
              ></passuk-display>
            </div>
            <!-- large media display component-->
            <div id='stacks_out_193' class='stacks_out'>
              <div id='stacks_in_193' class='stacks_in com_elixir_stacks_foundryImage_stack'>
                <div class="text-xs-center foundry-image-wrapper">
                <media-tag-modal
                 :tag-ids="tagIds"
                ></media-tag-modal>
                </div>
              </div>
            </div>
            <!-- small media display components-->
            <div id='stacks_out_162' class='stacks_out'>
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
            </div>
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
import {Chapter, SearchCriteria, Verse} from "@/api/dto";
import BaseCard from "@/Components/BaseComponents/BaseCard.vue";
import PassukDisplay from "@/Components/search/PassukDisplay.vue";
import apifiClient from "@/api/apifiClient";
import {PROPHETS, TORAH} from "@/api/TANAKH";

@Component({
  components: {
    ChapterDisplay,
    SearchController,
    BaseCard,
      MediaTagModal,
    PassukDisplay
  }
})
export default class Dashboard extends Vue{
  private displayMediaTagModal = false;
  private selectedMediaTagId: number;
  public displayTrop = true;
  public tagIds: number[] = [];

  private handleMediaTagIdSelected(id: number): void{
    this.selectedMediaTagId = id;
    this.displayMediaTagModal = true;
  }
  public searchCriteria: SearchCriteria;
  public selectedChapter = new Chapter();
  public searchTerm = "";
  public selectedVerse = new Verse();

  public sendChapterToChapterDisplay(selectedChapter: Chapter): void{
    this.selectedChapter = selectedChapter;
    this.selectedVerse.hebrewNumeral = "";
    this.selectedVerse.highlightedVerseSegments.segments = [];
    this.selectedVerse.highlightedVerseSegments.plainHebrewFinalSuffix = "";
    this.selectedVerse.highlightedVerseSegments.finalSuffix = "";
  }


  public sendSearchTermToChapterDisplay(searchTerm: string): void{
    this.searchTerm = searchTerm;
    console.log(this.searchTerm + "dashboard");
  }
  public clearAllResults(): void{
    this.selectedChapter = new Chapter();
    this.selectedVerse = new Verse();
    this.searchTerm = "";
  }

  //this function will also send the media components the tags in this selected verse.
  public sendVerseToPassukDisplayAndSendTagIdToMediaComponent(selectedVerse: Verse): void{
    this.tagIds = [];
   // console.log(selectedVerse);
    this.selectedVerse = selectedVerse;
    if(this.selectedVerse.mediaTags !== undefined) {
      for (const tag in this.selectedVerse.mediaTags) {
        this.tagIds.push(this.selectedVerse.mediaTags[tag].id);}
    }
      }


}

</script>

<style scoped>
  /*! CSS Used from: http://www.foundationstone.org/rw_common/themes/Foundry/consolidated-38.css?rwcache=628456071 ; media=all */

  .mCustomScrollBox mCS-light-2 {
    width: auto;
    margin-right: 30px;
    overflow-y: scroll;
  }


</style>