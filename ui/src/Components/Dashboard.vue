<template>
  <div class="fc-inner-wrapper">


    <!-- left hand container column-->
    <section class="fc-sidebar color-picker">
      <div class="text-xs-center">
        <h5 class="stacks_in_352_hdr text-muted  ">TANACH VISUAL DATABASE</h5>
      </div> <!-- column header-->
      <div class="stacks_in_249-foundry-card card  ">
        <img src="files/card_image_topper-249.png" class="img-fluid card-img-top" alt="Image">
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
                @send-selected-verse-to-passuk-display="sendVerseToPassukDisplay($event)"
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
      <!-- verse and media display components-->
      <div id='stacks_out_91' class='stacks_out'>

        <div id='stacks_in_91' class='stacks_in com_stacks4stacks_stacks_fontstack_stack'>
          <div id="fontStackstacks_in_91" class="fontStack ">

            <!-- verse display component-->
            <div id='stacks_out_95' class='stacks_out'>
              <passuk-display

                      :selected-verse="selectedVerse"
              ></passuk-display>
            </div>
            <!-- large media display component-->
            <div id='stacks_out_193' class='stacks_out'>
              <div id='stacks_in_193' class='stacks_in com_elixir_stacks_foundryImage_stack'>
                <div class="text-xs-center foundry-image-wrapper">
                  <img src="data:image/jpeg;base64,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">
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
import {Chapter, SearchCriteria} from "@/api/dto";
import BaseCard from "@/Components/BaseComponents/BaseCard.vue";
import PassukDisplay from "@/Components/search/PassukDisplay.vue";
import apifiClient from "@/api/apifiClient";

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

  public sendVerseToPassukDisplay(selectedVerse: Verse): void{
    console.log(selectedVerse);
    this.selectedVerse = selectedVerse;
    console.log(this.selectedVerse)
  }

  mounted(){
    apifiClient.findChapterByUniquePath()
  }



}

</script>

<style scoped>
  /*! CSS Used from: http://www.foundationstone.org/rw_common/themes/Foundry/consolidated-38.css?rwcache=628456071 ; media=all */



</style>