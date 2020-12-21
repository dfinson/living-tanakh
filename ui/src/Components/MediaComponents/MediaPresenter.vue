<template>
    <b-carousel :autoplay="false" indicator-custom :indicator-inside="false"  v-if="images.length > 0" :overlay="gallery" @click="switchGallery(true)">
      <b-carousel-item v-for="(item, i) in images" :key="i">
        <a class="image ">
          <b-checkbox class="checkbox" type="is-white" size="is-large" v-model="item.toBeDownloaded" @input="sendImageToPreviewSelector(i)"></b-checkbox>
          <img :src="getImgUrl(i)">
        </a>
      </b-carousel-item>
      <span v-if="gallery" @click="switchGallery(false)" class="modal-close is-large"/>
      <template slot="indicators" slot-scope="props">
        <figure class="al image" :draggable="false">

          <img :draggable="false" :src="getThumbnailUrl(props.i)" :title="props.i">
        </figure>
      </template>
    </b-carousel>
  <div v-else>
    <img src="https://maayan-assets.s3.eu-central-1.amazonaws.com/Ma'ayan+Logo05B.png">
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

  public sendImageToPreviewSelector(index: number): void{
    if(this.images[index].toBeDownloaded)
      this.$emit('send-image-to-preview-selector', this.images[index])
    else{
      this.$emit('remove-image-from-preview-selector', this.images[index])
    }

  }
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
   else return "Welcome to Ma'ayan";
 }

  public switchGallery(value: boolean) {
    this.gallery = value;

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

<style scoped>

.image{
  position: relative;
  text-align: center;
  color: white;
}
.is-active .al img {
  border: 1px solid #fff;
}
.al img {
  border: 1px solid transparent;
  margin: 1px;
}

.checkbox {    position: absolute;
  top: 20px;
  right: 45px; }

</style>