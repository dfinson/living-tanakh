<template>
  <div>
    <b-carousel v-if="images.length > 0" :autoplay="false" indicator-custom :indicator-inside="false" :overlay="gallery" @click="switchGallery(true)">
      <b-carousel-item v-for="(item, i) in images.length" :key="i">
        <a class="image">
          <span>{{getImgTitle(i)}}</span>
          <img :src="getImgUrl(i)" class="rotate180">
        </a>
      </b-carousel-item>
      <span v-if="gallery" @click="switchGallery(false)" class="modal-close is-large"/>
      <template slot="indicators" slot-scope="props">
        <figure class="al image" :draggable="false">
          <img :draggable="false" :src="getThumbnailUrl(props.i)" :title="props.i" class="rotate180">
        </figure>
      </template>
    </b-carousel>
  </div>
</template>

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

  private activeIndex = 0;
  private showThumbnails = true;
  private fullScreen = false;
  public gallery = false;

  public al = {
    hasGrayscale: true,
    itemsToShow: 2,
    breakpoints: {
      768: {
        hasGrayscale: false,
        itemsToShow: 4
      },
      960: {
        hasGrayscale: true,
        itemsToShow: 6
      }
    }
  }

 public getImgUrl(index: number): string{
    if(this.images !== undefined){
    return this.images[index].itemImageSrc;}
    else return "https://maayan-assets.s3.eu-central-1.amazonaws.com/MaayanLogo.jpeg";
 }

 public getThumbnailUrl(index: number): string{
   if(this.images !== undefined){
   return this.images[index].thumbnailImageSrc;}
   else return "https://maayan-assets.s3.eu-central-1.amazonaws.com/MaayanLogo.jpeg";
 }

 public getImgTitle(index: number): string{
   if(this.images !== undefined){
     return this.images[index].title;
   }
   else return "";
 }

  public switchGallery(value: boolean) {
    this.gallery = value
    if (value) {
      document.documentElement.classList.add('is-clipped')
    } else {
      document.documentElement.classList.remove('is-clipped')
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
    console.log(this.tags.flatMap(x =>x.linkedContent ).map(x=> new GalleriaImageItem(x!)));
    return (this.tags.flatMap(x =>x.linkedContent ).map(x=> new GalleriaImageItem(x!)));

      //this.tags.map(x => new GalleriaImageItem(x));//this.tags.flatMap(tag => tag.linkedContent).linkedContent.map((mediaContent: MediaContent): GalleriaImageItem => new GalleriaImageItem(mediaContent));
  }



}
</script>

<style>

</style>