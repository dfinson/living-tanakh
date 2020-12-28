<template>
  <section type="is-large">
    <!-- this displays the images of a verse/Chapter, all the images associated with all the tags in the verse/chapter-->
    <b-carousel :autoplay="false" indicator-custom :indicator-inside="false" :overlay="gallery" @click="switchGallery(true)" v-if="images.length > 0 && !oneTagSelected" style="background-color: black;">
      <b-carousel-item v-for="(item, i) in images" :key="i" >
        <a class="image ">
          <input type="checkbox" size="is-small" v-model="images[i].toBeDownloaded" style=" width: 35px; border:1px; padding: 1px; height: 35px;position: absolute;top: 20px;right: 20px; border-radius: 4px; background-color: transparent; " @change="sendImageToPreviewSelector(i)" />

          <img :src="getImgUrl(i)" style="margin-bottom: 10px" >
          <b-switch type="is-success"  style="font-family: 'Trebuchet MS'; direction: ltr; position: absolute; bottom: 20px; right: 20px; color: black " v-model="showCarousel"><strong>Carousel</strong></b-switch>
          <div class="tooltip" style=" width: 35px; border:1px; padding: 1px; height: 35px;position: absolute;top: 20px; left: 20px; background-color: #d8fffc; border-radius: 4px;">
            <img src="https://maayan-assets.s3.eu-central-1.amazonaws.com/info.png"  @mouseover="displayTooltip = true"/>
            <span class="tooltiptext" v-if="displayTooltip"><a class="toolClose" @click="displayTooltip = false" style="text-decoration: none; margin-right: 5px; font-size: 20px;" >x</a>
              This will contain a description for {{images[i].title}}
               </span>
          </div>
        </a>

      </b-carousel-item>
      <span v-if="gallery" @click="switchGallery(false)" class="modal-close is-large"/>
      <template slot="indicators" slot-scope="props" style="overflow-x: scroll" v-if="showCarousel">
        <figure class="al image" :draggable="false" >
          <img :draggable="false" :src="getThumbnailUrl(props.i)" :title="props.i" style="margin-bottom: 10px">
        </figure>
      </template>
    </b-carousel>

    <!-- this will display the Ma'ayan Logo in the event there is no content associated with the chapter/verse selected-->
  <div v-else-if="!oneTagSelected">
    <img src="https://maayan-assets.s3.eu-central-1.amazonaws.com/Ma'ayan+Logo05B.png">
    <div class="tooltip" style=" width: 35px; border:1px; padding: 1px; height: 35px;position: absolute;top: 20px; left: 20px; background-color: #d8fffc; border-radius: 4px;">
      <img src="https://maayan-assets.s3.eu-central-1.amazonaws.com/info.png"  @mouseover="displayTooltip = true"/>
      <span class="tooltiptext" v-if="displayTooltip"><a class="toolClose" @click="displayTooltip = false" style="text-decoration: none; margin-right: 5px; font-size: 20px" >x</a>
             No content associated with this Chapter/Verse
               </span>
    </div>
  </div>

    <!-- this will display the images associated with a single tag, when clicked on from the passuk in the passuk display-->
    <div v-else>
      <b-carousel :autoplay="false" indicator-custom :indicator-inside="false" :overlay="gallery" @click="switchGallery(true)" v-if="oneTagsImages.length > 0 && oneTagSelected" style="background-color: black;">
        <b-carousel-item v-for="(item, i) in oneTagsImages" :key="i">
          <a class="image ">

            <input type="checkbox" size="is-small" v-model="oneTagsImages[i].toBeDownloaded" style=" width: 35px; border:1px; padding: 1px; height: 35px;position: absolute;top: 20px;right: 20px; border-radius: 4px; background-color: transparent; " @change="sendImageOfOneTagToPreviewSelector(i)"/>

            <img :src="item.itemImageSrc"  style="margin-bottom: 10px">
            <b-switch type="is-success"  style="font-family: 'Trebuchet MS'; direction: ltr; position: absolute; bottom: 20px; right: 20px; color: black " v-model="showCarousel"><strong>Carousel</strong></b-switch>
            <div class="tooltip" style=" width: 35px; border:1px; padding: 1px; height: 35px;position: absolute;top: 20px; left: 20px; background-color: #d8fffc; border-radius: 4px;">
              <img src="https://maayan-assets.s3.eu-central-1.amazonaws.com/info.png"  @mouseover="displayTooltip = true"/>
              <span class="tooltiptext" v-if="displayTooltip"><a class="toolClose" @click="displayTooltip = false" style="text-decoration: none; margin-right: 5px; font-size: 20px" >x</a>
              This will contain a description for {{oneTagsImages[i].title}}
               </span>
            </div>
          </a>
        </b-carousel-item>
        <span v-if="gallery" @click="switchGallery(false)" class="modal-close is-large"/>
        <template slot="indicators" slot-scope="props" style="overflow-x: scroll" v-if="showCarousel">
          <figure class="al image" :draggable="false" >
            <img :draggable="false" :src="getThumbnailUrlForSingleTag(props.i)" :title="props.i" style="margin-bottom: 10px">
          </figure>
        </template>
      </b-carousel>
    </div>
  </section>
</template>
<!--
 <div class="tooltip" style=" width: 35px; border:1px; padding: 1px; height: 35px;position: absolute;
  top: 20px;
  left: 20px; background-color: #d8fffc; border-radius: 4px;">
            <img src="https://maayan-assets.s3.eu-central-1.amazonaws.com/info.png"  @mouseover="displayTooltip = true"/>
            <span class="tooltiptext" v-if="displayTooltip"><a class="toolClose" @click="displayTooltip = false" style="text-decoration: none; margin-right: 5px; font-size: 22px" >x</a>
              This will contain a description for {{images[i].title}}
               </span>
          </div>
-->
<script lang="ts">
  import {Component, Prop, Vue, Watch} from "vue-property-decorator";
  import {GalleriaImageItem, GalleriaResponsiveOption, MediaContent, MediaTag} from "@/api/dto";
import ImageCacher from "@/Components/MediaComponents/ImageCacher.vue"
import CustomGalleriaFooter from '@/Components/MediaComponents/CustomGalleriaFooter.vue';


@Component({
  components: {CustomGalleriaFooter, ImageCacher}
})



export default class MediaPresenter extends Vue{
  @Prop({required:true})
  private tags: MediaTag[];

  @Prop()
  public selectedMediaTagId: number;

  private activeIndex = 0;
  private showThumbnails = true;
  private fullScreen = false;
  public gallery = false;
  public displayTooltip = false;
  public oneTagSelected = false;
  public showCarousel = true;


  public sendImageToPreviewSelector(index: number): void{
    if(this.images[index].toBeDownloaded){
      console.log(this.images[index].itemImageSrc)
      this.$emit('send-image-to-preview-selector', this.images[index])
    }

    else{
      this.$emit('remove-image-from-preview-selector', this.images[index])
    }

  }

  public sendImageOfOneTagToPreviewSelector(index: number): void{
    if(this.oneTagsImages[index].toBeDownloaded)
      this.$emit('send-image-to-preview-selector', this.oneTagsImages[index])
    else{
      this.$emit('remove-image-from-preview-selector', this.oneTagsImages[index])
    }

  }


 public getImgUrl(index: number): string{
    if(this.images[index] !== undefined){
    return this.images[index].itemImageSrc;}
    else return "https://maayan-assets.s3.eu-central-1.amazonaws.com/MaayanLogo.jpeg";
 }

 public getThumbnailUrl(index: number): string{
   if(this.images[index] !== undefined){
   return this.images[index].thumbnailImageSrc;}
   else return "https://maayan-assets.s3.eu-central-1.amazonaws.com/MaayanLogo.jpeg";
 }

 public getThumbnailUrlForSingleTag(index: number): string{
    if(this.oneTagsImages[index] !== undefined){
      return this.oneTagsImages[index].thumbnailImageSrc;
    }
    else return "https://maayan-assets.s3.eu-central-1.amazonaws.com/MaayanLogo.jpeg";
 }

 public getImgTitle(index: number): string{
   if(this.images[index] !== undefined){
     return this.images[index].title;
   }
   else return "Welcome to Ma'ayan";
 }

  public switchGallery(value: boolean) {
    this.gallery = value;
    if (value) {
      document.documentElement.classList.add('is-clipped')
    } else {
      document.documentElement.classList.remove('is-clipped')
    }
  }

  @Watch('selectedMediaTagId')
  onPropertyChanged(){
    if(this.selectedMediaTagId !== 0){
      this.oneTagSelected = true;
      console.log(this.oneTagsImages + "from one tag")
    }
    else{
      this.oneTagSelected = false;
    }
  }







  /*private get responsiveOptions(): GalleriaResponsiveOption[]{
    return [
        new GalleriaResponsiveOption('1024px', 5),
        new GalleriaResponsiveOption('768px', 3),
        new GalleriaResponsiveOption('560px', 1)
    ];
  }*/


  private onThumbnailButtonClick(): void{
    console.log('onThumbnailButtonClick');
  }



  public get images(){
    if(this.tags.some(tag =>!tag.linkedContent)) return [];
    return (this.tags.flatMap(x =>x.linkedContent ).map(x=> new GalleriaImageItem(x!)));
  }

  public get oneTagsImages(){
    if(this.tags.some(tag =>!tag.linkedContent) || !(this.tags.some(tag => tag.id === this.selectedMediaTagId))){
      //alligator.includes("thick scales");
//alligator.find((el, idx) => typeof el === "string" && idx === 2); // returns '4 foot tall'
      return [];
    }
    //return (this.tags.)
    const temp = this.tags.filter(tag => tag.id === this.selectedMediaTagId);
    console.log( temp.flatMap(x => x.linkedContent).map(x => new GalleriaImageItem(x!)))
    return temp.flatMap(x => x.linkedContent).map(x => new GalleriaImageItem(x!));
  }





}
</script>

<style scoped>

  .tooltip {
    position: relative;
    display: inline-block;

  }

  .tooltip .tooltiptext {
    visibility: visible;
    width: 120px;
    background-color:cornflowerblue;
    color: blue;
    text-align: center;
    border-radius: 6px;
    padding: 5px 0;
    font-family: "Trebuchet MS";
    font-size: 15px;
    opacity: 1;
    transition: opacity 1s;

    /* Position the tooltip */
    position: absolute;
    z-index: 1;
    top: 35px;
    right: -350%;

  }

  .tooltip.tooltiptext:hover{
    visibility: visible;
  }

.toolClose:hover{
  color: red;
}
.image{
  position: relative;
  text-align: center;
  color: white;
}

.checkbox {    position: absolute;
  top: 20px;
  right: 45px; }

  .icon{
    position: absolute;
    bottom:40px;
    left:45px;
  }

  /*! CSS Used from: Embedded */

  .image[data-v-f3a93d2e] {
    position: relative;
    text-align: center;
    color: white;
  }

  .is-active .al img[data-v-f3a93d2e] {
    border: 1px solid #fff;
  }

  .al img[data-v-f3a93d2e] {
    border: 1px solid transparent;
    margin: 1px;
  }


  /*! CSS Used from: Embedded */

  figure {
    margin: 0;
    padding: 0;
  }

  *,
  *::before,
  *::after {
    -webkit-box-sizing: inherit;
    box-sizing: inherit;
  }

  img {
    height: auto;
    max-width: 100%;
  }

  figure {
    display: block;
  }

  a {
    color: #7957d5;
    cursor: pointer;
    text-decoration: none;
  }

  a:hover {
    color: #363636;
  }

  img {
    height: auto;
    max-width: 100%;
  }

  .image {
    display: block;
    position: relative;
  }

  .image img {
    display: block;
    height: auto;
    width: 100%;
  }

  .carousel .carousel-indicator {
    width: 100%;
    padding: 2px;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
  }

  .carousel .carousel-indicator.has-custom {
    -ms-flex-wrap: nowrap;
    flex-wrap: nowrap;
    -webkit-box-pack: start;
    -ms-flex-pack: start;
    justify-content: flex-start;
    -webkit-overflow-scrolling: touch;
    overflow: hidden;
    overflow-x: auto;
  }

  .carousel .carousel-indicator.has-custom.is-small .indicator-item {
    -webkit-box-flex: 1;
    -ms-flex: 1 0 10%;
    flex: 1 0 10%;
  }

  .carousel .carousel-indicator .indicator-item:not(:last-child) {
    margin-right: 2px;
  }


  /*! CSS Used from: Embedded */

  @media all {
    a {
      background-color: transparent;
    }
    a:active {
      outline: 0;
    }
    a:hover {
      outline: 0;
    }
    img {
      border: 0;
    }
    @media print {
      *,
      *::before,
      *::after {
        text-shadow: none!important;
        box-shadow: none!important;
      }
      a,
      a:visited {
        text-decoration: underline;
      }
      img {
        page-break-inside: avoid;
      }
      img {
        max-width: 100%!important;
      }
    }
    *,
    *::before,
    *::after {
      box-sizing: inherit;
    }
    a {
      color: #0275d8;
      text-decoration: none;
    }
    a:focus,
    a:hover {
      color: #014c8c;
      text-decoration: underline;
    }
    a:focus {
      outline: thin dotted;
      outline: 5px auto -webkit-focus-ring-color;
      outline-offset: -2px;
    }
    img {
      vertical-align: middle;
    }
    a {
      touch-action: manipulation;
    }
    #stacks_in_193 img {
      display: inline-block;

    }
  }
  /* Clearfix (clear floats) */

  /* Responsive layout - makes the three columns stack on top of each other instead of next to each other */
</style>